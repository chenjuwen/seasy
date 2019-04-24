package com.seasy.web.security.shiro.filter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.seasy.interfaces.dto.ResourcesDTO;
import com.seasy.interfaces.service.ResourceService;
import com.seasy.interfaces.service.UserService;
import com.seasy.web.MenuResource;
import com.seasy.web.security.SecurityUser;
import com.seasy.web.security.shiro.SeasyUsernamePasswordToken;
import com.seasy.web.security.shiro.SecurityConstants;

public class SeasyFormAuthenticationFilter extends FormAuthenticationFilter {
	public static final String DEFAULT_MENU_URL = "#";
	public static final String LOGIN_SUCCESS_URL = "/index.jsp";

	private static Logger log = LoggerFactory.getLogger(SeasyFormAuthenticationFilter.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private ResourceService resourceService;
	
	public String getCaptchaParam() {
		return "captcha";
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		String captcha = getCaptcha(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		
		if (StringUtils.isEmpty(password)) {
			char[] array = new char[0];
			return new SeasyUsernamePasswordToken(username, array, rememberMe, host, captcha);
		} else {
			return new SeasyUsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha);
		}
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest req,
			ServletResponse resp) throws Exception {
		HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        
        SecurityUser securityUser = (SecurityUser)subject.getPrincipal();

        // 用户拥有的资源权限
		List<ResourcesDTO> userResList = userService.getAllResourceByUserId(securityUser.getId());
		securityUser.setResourceDTOList(userResList);
		securityUser.setResourceUrlList(new ArrayList<String>());
		for(ResourcesDTO dto:userResList){
			securityUser.getResourceIDMap().put(dto.getId(), dto);
			if(StringUtils.isNotEmpty(dto.getResUrl())){
				securityUser.getResourceUrlMap().put(dto.getResUrl(), dto);
				securityUser.getResourceUrlList().add(dto.getResUrl());
			}
		}
		
		// 全部权限，用于过滤不限制的请求
		List<ResourcesDTO> resList = resourceService.getAllResource();
		securityUser.setAllResourceUrlList(new ArrayList<String>());
		for(ResourcesDTO dto: resList){
			if(StringUtils.isNotEmpty(dto.getResUrl())){
				securityUser.getAllResourceUrlList().add(dto.getResUrl());
			}
		}

        // 用户有权访问的菜单
		List<ResourcesDTO> topResList = userService.getTopResourceByUserId(securityUser.getId());
		List<MenuResource> topMenuList = new ArrayList<MenuResource>();
		for(ResourcesDTO dto : topResList){
			MenuResource menuRes = new MenuResource();
			menuRes.setId(dto.getId());
			menuRes.setNo(dto.getResNo());
			menuRes.setName(dto.getResName());
			menuRes.setPid(dto.getParentId());
			menuRes.setImg(dto.getResImg());
			menuRes.setUrl(dto.getResUrl());
			menuRes.setOwner(dto.getOwner());
			
			if(StringUtils.isEmpty(dto.getResUrl()) || dto.getResUrl().equals("null")){
				menuRes.setUrl(DEFAULT_MENU_URL);
			}
			topMenuList.add(menuRes);
		}
		
		// 寻找第二级菜单
		for(ResourcesDTO dto: userResList){
			for(MenuResource topMenu: topMenuList){
				if(topMenu.getId().equals(dto.getParentId())){
					MenuResource subMenuRes = new MenuResource();
					subMenuRes.setId(dto.getId());
					subMenuRes.setNo(dto.getResNo());
					subMenuRes.setName(dto.getResName());
					subMenuRes.setPid(dto.getParentId());
					subMenuRes.setImg(dto.getResImg());
					subMenuRes.setUrl(dto.getResUrl());
					subMenuRes.setOwner(dto.getOwner());
					
					if(StringUtils.isEmpty(dto.getResUrl()) || dto.getResUrl().equals("null")){
						subMenuRes.setUrl(DEFAULT_MENU_URL);
					}
					topMenu.getSubs().add(subMenuRes);
				}
			}
		}
        
		request.getSession().setAttribute(SecurityConstants.SESSION_ATTR_KEY__MENULIST,topMenuList);
		request.getSession().setAttribute(SecurityConstants.SESSION_ATTR_KEY__FIRSTMENUID,Integer.MIN_VALUE);
		request.getSession().setAttribute(SecurityConstants.SESSION_ATTR_KEY__SECONDMENUID,Integer.MIN_VALUE);
		
		//更新登录信息
		userService.updateLoginInfo(securityUser.getId());
		
		// 写登陆日志
		// GOTO
        
        request.getSession().removeAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORTYPE);
        request.getSession().removeAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORMSG);
        
		// 跳转到主页
        request.getRequestDispatcher(LOGIN_SUCCESS_URL).forward(request, response);
        
        return false;
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		try{
	        HttpServletRequest req = (HttpServletRequest)request;
	        String errorMessage = (String)req.getSession().getAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORMSG);
	        
	        if(StringUtils.isEmpty(errorMessage)){
				if(e instanceof CredentialsException){
			        req.getSession().setAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORTYPE, "username");
			        req.getSession().setAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORMSG, SecurityConstants.ERROR_USERNAME_PASSWORD);
				}
	        }
		}catch (Exception ex) {
			ex.printStackTrace();
			log.error("onLoginFailure", ex);
		}
		
		return super.onLoginFailure(token, e, request, response);
	}
}