package com.seasy.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.seasy.core.util.JsonUtil;
import com.seasy.interfaces.dto.ExceptionInfoDTO;

public class JsonUtilTest {
	public static void main(String[] args) {
		List<ExceptionInfoDTO> list = new ArrayList<ExceptionInfoDTO>();
		
		ExceptionInfoDTO dto = new ExceptionInfoDTO();
		dto.setCode("error");
		dto.setMessage("error message");
		dto.setSummary("error summary");
		dto.setDetails("error details");
		dto.setDate(new Date());
		System.out.println(JsonUtil.object2JsonString(dto));
		
		list.add(dto);
		System.out.println(JsonUtil.convertToJsonArrayString(list));
	}
}
