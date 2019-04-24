package com.seasy.web.security.shiro.filter;

import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.util.WebUtils;

/**
 * 根据角色判断用户是否有访问权限
 */
public class SeasyRoleAuthenticationFilter extends SeasyAccessControlFilter {
	private String unauthorizedUrl;

	/**
	 * 是否允许访问
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		
		//角色权限判断 
		boolean hasRoleAuthority = false;
	    String[] rolesArray = (String[])mappedValue;
	    if(rolesArray == null || rolesArray.length == 0){
	    	hasRoleAuthority = true;
	    }else{
	    	Set<String> roles = CollectionUtils.asSet(rolesArray);
		    for(String role : roles){
	            if(subject.hasRole(role)){  
	            	hasRoleAuthority = true;
	            	break;
	            }  
	        } 
	    }
	    
	    //返回true表示允许访问，返回false表示不允许访问
	    if(hasRoleAuthority){
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
		if(unauthorizedUrl != null){
			WebUtils.issueRedirect(request, response, unauthorizedUrl);
		}else{
			WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		//返回false表示该拦截器已处理，直接返回即可。返回true表示需要继续处理
		return false; 
	}
	
	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}

	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}

}
