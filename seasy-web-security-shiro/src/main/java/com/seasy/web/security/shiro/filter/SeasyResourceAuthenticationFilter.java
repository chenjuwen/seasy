package com.seasy.web.security.shiro.filter;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;

import com.seasy.web.MenuResource;
import com.seasy.web.security.SecurityUser;
import com.seasy.web.security.shiro.SecurityConstants;

/**
 * 判断用户是否有菜单访问权限
 */
public class SeasyResourceAuthenticationFilter extends SeasyAccessControlFilter {
	private String unauthorizedUrl;
	private String continueProcessing;
	
	/**
	 * 是否允许访问
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		Subject subject = SecurityUtils.getSubject();
		SecurityUser securityUser = (SecurityUser) subject.getPrincipal();

		String[] arr = (String[])mappedValue;
		if(arr != null && arr.length > 0){
			continueProcessing = StringUtils.trimToEmpty(arr[0]);
		}else{
			continueProcessing = "false";
		}
		
		String uri = req.getRequestURI();
		String path = req.getContextPath();
		String url = uri.substring(path.length());
		
		//## 菜单资源权限判断
		boolean hasMenuAuthority = false;
		if (securityUser != null && securityUser.getAllResourceUrlList().size() > 0 && securityUser.getResourceUrlList().size() > 0) {
			if (securityUser.getAllResourceUrlList().contains(url)) {
				List<MenuResource> menuList = (List<MenuResource>)req.getSession().getAttribute(SecurityConstants.SESSION_ATTR_KEY__MENULIST);
				boolean isFound = false;
				for(MenuResource menu: menuList){
					for(MenuResource subMenu: menu.getSubs()){
						if(subMenu.getUrl().equals(url)){
							req.getSession().setAttribute(SecurityConstants.SESSION_ATTR_KEY__FIRSTMENUID,subMenu.getPid());
							req.getSession().setAttribute(SecurityConstants.SESSION_ATTR_KEY__SECONDMENUID,subMenu.getId());
							
							req.getSession().setAttribute(SecurityConstants.SESSION_ATTR_KEY__FIRSTMENUNAME,menu.getName());
							req.getSession().setAttribute(SecurityConstants.SESSION_ATTR_KEY__SECONDMENUNAME,subMenu.getName());
							
							isFound = true;
							break;
						}
					}
					
					if(isFound) break;
				}
				
				//用户是否有资源访问权限
				hasMenuAuthority = securityUser.getResourceUrlList().contains(url);
			}
		}
	    
	    //返回true表示允许访问，返回false表示不允许访问
	    if(hasMenuAuthority){
	    	return true;
	    }else{
	    	return false;
	    }
	}

	/**
	 * 访问拒绝时触发该方法
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		//返回false表示该拦截器已处理，直接返回即可。返回true表示需要继续处理
		if("true".equalsIgnoreCase(continueProcessing)){
			return true;
		}else{
			if(unauthorizedUrl != null){
				WebUtils.issueRedirect(request, response, unauthorizedUrl);
			}else{
				WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
			return false;
		}
	}
	
	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}

	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}

}
