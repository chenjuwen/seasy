package com.seasy.web.security.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.seasy.web.security.SecurityUser;

public class SeasyUsernamePasswordToken extends UsernamePasswordToken {
	private static final long serialVersionUID = -2823808445827632008L;
	
	private String captcha;
	private SecurityUser securityUser;
	
	public SeasyUsernamePasswordToken() {
		super();
	}

	public SeasyUsernamePasswordToken(String username, char[] password, boolean rememberMe, 
			String host, String captcha) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	@Override
	public Object getPrincipal() {
		return securityUser;
	}
	
	public void setShiroUser(SecurityUser securityUser) {
		this.securityUser = securityUser;
	}
	
}