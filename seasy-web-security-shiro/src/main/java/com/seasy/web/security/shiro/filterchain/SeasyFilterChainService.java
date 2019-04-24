package com.seasy.web.security.shiro.filterchain;

import java.util.Map;

public interface SeasyFilterChainService {
	public abstract void initFilterChainDefinitions();
	public abstract Map<String, String> initOtherFilterChainDefinitions();
	public abstract void updateFilterChainDefinitions();
}
