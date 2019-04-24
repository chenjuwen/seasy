package com.seasy.web.security;

public interface SecurityContext {
	public abstract SecurityUser getUser();
	
	public abstract String getCurrentUsername();
	
}
