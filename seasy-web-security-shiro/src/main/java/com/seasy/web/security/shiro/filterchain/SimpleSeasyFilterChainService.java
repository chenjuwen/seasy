package com.seasy.web.security.shiro.filterchain;

import java.util.HashMap;
import java.util.Map;

public class SimpleSeasyFilterChainService extends AbstractSeasyFilterChainService {
	@Override
	public Map<String, String> initOtherFilterChainDefinitions() {
		return new HashMap<String, String>();
	}
}
