package com.seasy.web.security.shiro;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.ClientWebApplicationException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.Encodes;

import com.google.code.kaptcha.Constants;
import com.seasy.core.SeasyConstants;
import com.seasy.core.exception.SeasyExceptionUtil;
import com.seasy.interfaces.dto.ResourcesDTO;
import com.seasy.interfaces.dto.RolesDTO;
import com.seasy.interfaces.dto.UsersDTO;
import com.seasy.interfaces.service.ResourceService;
import com.seasy.interfaces.service.UserService;
import com.seasy.web.security.SecurityUser;

public class SeasyAuthorizingRealm extends AuthorizingRealm {
	private static Logger logger = LoggerFactory.getLogger(SeasyAuthorizingRealm.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private ResourceService resourceService;
	
	/**
	 * 认证函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		SeasyUsernamePasswordToken token = null;
		try {
			SecurityUtils.getSubject().getSession().removeAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORTYPE);
			SecurityUtils.getSubject().getSession().removeAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORMSG);

			token = (SeasyUsernamePasswordToken) authcToken;
			
			//空值判断
			if (StringUtils.isEmpty(token.getUsername()) || null == token.getPassword()
					|| 0 == token.getPassword().length) {
				setSessionAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORMSG, SecurityConstants.INPUT_USERNAME_PASSWORD);
				return null;
			}
			
			if (StringUtils.isEmpty(token.getCaptcha())) {
				setSessionAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORMSG, SecurityConstants.INPUT_CAPTCHA);
				return null;
			}

			setSessionAttribute("username", token.getUsername());
			setSessionAttribute("password", new String(token.getPassword()));
			token.setCaptcha(token.getCaptcha().toLowerCase());
			setSessionAttribute("captcha", token.getCaptcha());

			//验证码是否正确
			try {
				String captchaId = (String)SecurityUtils.getSubject().getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
				if (!token.getCaptcha().equalsIgnoreCase(captchaId)) {
					setSessionAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORMSG, SecurityConstants.ERROR_CAPTCHA);
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				setSessionAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORMSG, SecurityConstants.ERROR_TIMEOUT);
				return null;
			}

			//验证用户名是否存在
			UsersDTO user = userService.selectByLoginName(token.getUsername());
			if(user == null){
				setSessionAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORMSG, SecurityConstants.ERROR_USERNAME_PASSWORD);
				return null;
			}
			
			//状态是否禁用
			if(1 != user.getEnabled()){
				setSessionAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORMSG, SecurityConstants.ERROR_STATUS);
				return null;
			}
			
			SecurityUser securityUser = new SecurityUser(user.getId(), user.getLoginName(), user.getUsername(), token.getCaptcha());
			securityUser.setPassword(new String(token.getPassword()));

			token.setShiroUser(securityUser);

			byte[] salt = Encodes.decodeHex(user.getSalt());
			return new SimpleAuthenticationInfo(securityUser, user.getPassword(), ByteSource.Util.bytes(salt), getName());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("doGetAuthenticationInfo error", e);
			
			if (ClientWebApplicationException.class.isInstance(e)) {
				setSessionAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORMSG, SecurityConstants.ERROR_SERVER);
			} else {
				StringBuffer sb = new StringBuffer();
				sb.append(SecurityConstants.ERROR_SYSTEM);
				sb.append(": " + SeasyExceptionUtil.getRootCause(e).getMessage());
				
				setSessionAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORMSG, sb.toString());
			}
			return null;
		}
	}

	public void setSessionAttribute(String key, Object value) {
		SecurityUtils.getSubject().getSession().setAttribute(key, value);
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		try {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			
			SecurityUser securityUser = (SecurityUser)principals.getPrimaryPrincipal();
			
			//用户的角色权限
			List<RolesDTO> roleList = userService.getAllRoleByUserId(securityUser.getId());
			for(RolesDTO dto: roleList){
				info.addRole(dto.getRoleNo());
			}
			
			//用户的资源权限
			List<ResourcesDTO> resouceList = userService.getAllResourceByUserId(securityUser.getId());
			for(ResourcesDTO dto: resouceList){
				if(StringUtils.isNotEmpty(dto.getResUrl())){
					info.addStringPermission(dto.getResUrl());
				}
			}
			
			securityUser.setResourceUrlList(new ArrayList<String>());
			for(ResourcesDTO dto: resouceList){
				if(StringUtils.isNotEmpty(dto.getResUrl())){
					securityUser.getResourceUrlList().add(dto.getResUrl());
				}
			}
			
			//用户的一级菜单
			List<ResourcesDTO> reslist = userService.getTopResourceByUserId(securityUser.getId());
			setSessionAttribute(SecurityConstants.SESSION_ATTR_KEY__RESLIST, reslist);
			
			//所有资源
			List<ResourcesDTO> allResources = resourceService.getAllResource();
			securityUser.setAllResourceUrlList(new ArrayList<String>());
			for(ResourcesDTO dto: allResources){
				if(StringUtils.isNotEmpty(dto.getResUrl())){
					securityUser.getAllResourceUrlList().add(dto.getResUrl());
				}
			}

			return info;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("doGetAuthorizationInfo error", e);
		}
		return null;
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(SecurityConstants.HASH_ALGORITHM);
		matcher.setHashIterations(SeasyConstants.DIGESTS_SHA1_ITERATIONS);
		setCredentialsMatcher(matcher);
	}

}
