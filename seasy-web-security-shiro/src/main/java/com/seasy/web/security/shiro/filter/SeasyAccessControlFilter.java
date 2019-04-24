package com.seasy.web.security.shiro.filter;

import java.util.Map;

import org.apache.shiro.web.filter.AccessControlFilter;

public abstract class SeasyAccessControlFilter extends AccessControlFilter {
	public Map<String, Object> getAppliedPaths(){
		return appliedPaths;
	}
}
