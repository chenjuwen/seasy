package com.seasy.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import com.seasy.core.test.BaseServiceTest;
import com.seasy.core.util.JsonUtil;
import com.seasy.interfaces.dto.ResourcesDTO;
import com.seasy.interfaces.service.ResourceService;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ResourceServiceTest extends BaseServiceTest{
	@Resource
	private ResourceService resourceService;
	
	@Test
	public void resourceSecurity(){
		List<ResourcesDTO> list = resourceService.selectByParentId(null);
		System.out.println(JsonUtil.convertToJsonArrayString(list));
		
		list = resourceService.selectByRoleId(1L);
		System.out.println(JsonUtil.convertToJsonArrayString(list));
	}
	
	@Test
	@Rollback
	public void delete(){
		resourceService.delete(1L);
		resourceService.deleteRelationRole(1L);
	}
	
	@Test
	public void cascadeSelectResources(){
		List<ResourcesDTO> list = resourceService.cascadeSelectByParentId(null);
		System.out.println(JsonUtil.formatJsonString(JsonUtil.convertToJsonArrayString(list)));
	}
	
}
