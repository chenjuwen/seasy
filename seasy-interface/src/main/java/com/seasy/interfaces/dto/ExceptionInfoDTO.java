package com.seasy.interfaces.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.seasy.interfaces.AbstractBaseDTO;

@XmlRootElement(name="ExceptionInfo")
public class ExceptionInfoDTO extends AbstractBaseDTO {
	private static final long serialVersionUID = -2581817750935993059L;
	
	private String code;
	private String message;
	private String summary;
	private String details;
	private Date date;
	
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
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
