package com.seasy.web.security.shiro.filterchain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.seasy.interfaces.dto.FilterChainDefinsDTO;
import com.seasy.interfaces.service.FilterChainDefinsService;

public class DBSeasyFilterChainService extends AbstractSeasyFilterChainService {
	@Autowired
	private FilterChainDefinsService filterChainDefinsService;
	
	@Override
	public Map<String, String> initOtherFilterChainDefinitions() {
		Map<String, String> definitionsMap = new HashMap<String, String>();
		
		List<FilterChainDefinsDTO> definitionsList = filterChainDefinsService.findAll();
		if(CollectionUtils.isEmpty(definitionsList)){
			return definitionsMap;
		}
		
		for(FilterChainDefinsDTO dto: definitionsList){
			definitionsMap.put(StringUtils.trimToEmpty(dto.getChainName()), StringUtils.trimToEmpty(dto.getChainDefinition()));
		}
		return definitionsMap;
	}
	
}
