package com.seasy.core;

import com.seasy.core.exception.SeasyExceptionUtil;
import com.seasy.interfaces.dto.ExceptionInfoDTO;

public class SeasyExceptionTest {
	public static void main(String[] args) {
		try{
			int i = 1 / 0;
			System.out.println(i);
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("\n");
			
			ExceptionInfoDTO dto = SeasyExceptionUtil.getExceptionInfo(ex, "error", "com.seasy.");
			System.out.println(dto.getCode() + "\n");
			System.out.println(dto.getMessage() + "\n");
			System.out.println(dto.getSummary() + "\n");
			System.out.println(dto.getDetails());
		}
	}
}
