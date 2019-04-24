package com.seasy.web.security.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.seasy.web.controller.AbstractSeasyController;
import com.seasy.web.security.SecurityUser;

public class ShiroAbstractController extends AbstractSeasyController {
	@Override
	public SecurityUser getUser() {
		Subject subject = SecurityUtils.getSubject();
		SecurityUser user = (SecurityUser) subject.getPrincipal();
		return user;
	}

	@Override
	public Long getCurrentUserId() {
		SecurityUser user = getUser();
		if(user != null){
			return user.getId();
		}
		return null;
	}

	@Override
	public String getCurrentUsername() {
		SecurityUser user = getUser();
		if(user != null){
			return user.getUsername();
		}
		return "";
	}

}
