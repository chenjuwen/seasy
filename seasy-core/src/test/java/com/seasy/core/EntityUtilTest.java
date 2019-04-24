package com.seasy.core;

import java.util.Date;
import java.util.Map;

import org.junit.Test;

import com.seasy.core.util.EntityUtil;
import com.seasy.interfaces.dto.ExceptionInfoDTO;

import junit.framework.Assert;

public class EntityUtilTest {
	@Test
	public void entity2Map(){
		ExceptionInfoDTO dto = new ExceptionInfoDTO();
		dto.setCode("error");
		dto.setMessage("this is error message");
		dto.setSummary("this is error summary");
		dto.setDetails("this is error details");
		dto.setDate(new Date());
		
		Map<String, String> map = EntityUtil.entity2Map(dto);
		System.out.println(map);
		Assert.assertNotNull(map);
	}
	
}
