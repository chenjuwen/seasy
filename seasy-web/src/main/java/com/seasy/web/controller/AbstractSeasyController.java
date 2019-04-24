package com.seasy.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.seasy.web.security.SecurityContext;
import com.seasy.web.security.SecurityUser;

public abstract class AbstractSeasyController implements SecurityContext {
	public static final String REQUEST_PRODUCES_JSON = "application/json;charset=UTF-8";
	public static final String REQUEST_PRODUCES_HTML = "text/html;charset=UTF-8";
	
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	
	public static final String RESULT_CODE = "code";
	public static final String RESULT_MESSAGE = "message";
	
	public String getParameterValue(HttpServletRequest request, String key, String defaultValue){
		String value = StringUtils.trimToEmpty(request.getParameter(key));
		if(StringUtils.isEmpty(value)){
			value = defaultValue;
		}
		return value;
	}
	
	/**
	 * 获取日期型请求参数值
	 * @param request 
	 * @param key 键值
	 * @param pattern 日期格式
	 */
	public Date getDateValue(HttpServletRequest request, String key, String pattern){
		if(StringUtils.isEmpty(pattern)){
			pattern = DEFAULT_DATE_PATTERN;
		}
		
		String value = request.getParameter(key);
		if(StringUtils.isEmpty(value)){
			return null;
		}
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public abstract SecurityUser getUser();
	
	public abstract Long getCurrentUserId();
	
	public abstract String getCurrentUsername();
	
}
