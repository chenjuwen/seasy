package com.seasy.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import com.github.pagehelper.PageInfo;
import com.seasy.core.test.BaseServiceTest;
import com.seasy.interfaces.dto.FilterChainDefinsDTO;
import com.seasy.interfaces.service.FilterChainDefinsService;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class FilterChainDefinsServiceTest extends BaseServiceTest{
	@Resource
	private FilterChainDefinsService filterChainDefinsService;
	
	@Test
	@Rollback
	public void createData(){
		for(int i=1; i<=20; i++){
			FilterChainDefinsDTO dto = new FilterChainDefinsDTO("/chainName"+i+"/**", "user", i);
			filterChainDefinsService.insert(dto);
		}
	}
	
	@Test
	public void query(){
		PageInfo<FilterChainDefinsDTO> pageInfo = filterChainDefinsService.selectByPage(3, 5, null);
		for(FilterChainDefinsDTO dto : pageInfo.getList()){
			System.out.println(dto.getChainName() + ", " + dto.getChainDefinition() + ", " + dto.getChainOrder());
		}
	}
	
	@Test
	@Rollback
	public void filterChainDefinsServiceTest(){
		//insert
		FilterChainDefinsDTO dto1 = new FilterChainDefinsDTO("/admin/role/**", "user,resourceAuth[true],anyRole[admin]", 10);
		filterChainDefinsService.insert(dto1);
		
		FilterChainDefinsDTO dto2 = new FilterChainDefinsDTO("/admin/**", "user,resourceAuth[true],anyRole[admin]", 20);
		filterChainDefinsService.insert(dto2);
		
		FilterChainDefinsDTO dto3 = new FilterChainDefinsDTO("/**", "user,resourceAuth", 9999);
		filterChainDefinsService.insert(dto3);
		
		//findAll
		List<FilterChainDefinsDTO> list = filterChainDefinsService.findAll();
		Assert.assertTrue(list.size() > 0);
		
		//selectByPage
		PageInfo<FilterChainDefinsDTO> pageInfo = filterChainDefinsService.selectByPage(1, 1, null);
		System.out.println(pageInfo.getList().get(0).getChainName() + ", " + pageInfo.getList().get(0).getChainDefinition());
		Assert.assertNotNull(pageInfo.getList());
		
		//delete
		boolean b = filterChainDefinsService.delete(dto1.getId());
		Assert.assertTrue(b);
	}
	
}
