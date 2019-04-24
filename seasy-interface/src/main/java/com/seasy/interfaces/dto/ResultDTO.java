package com.seasy.interfaces.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.seasy.interfaces.AbstractBaseDTO;

@XmlRootElement
public class ResultDTO extends AbstractBaseDTO {
	private static final long serialVersionUID = -4597665414334979803L;
	
	public static final String CODE_SUCCESS = "success";
	public static final String CODE_ERROR = "error";
	
	private String code;
	private String message;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
