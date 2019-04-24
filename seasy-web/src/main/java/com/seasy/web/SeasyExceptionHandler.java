package com.seasy.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.seasy.core.exception.SeasyException;

public class SeasyExceptionHandler implements HandlerExceptionResolver {
	private Logger logger = Logger.getLogger(SeasyExceptionHandler.class);
	
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		logger.error(ex.getMessage(), ex);
		
		Map<String, Object> model = new HashMap<String, Object>();  
        model.put("code", ResponseConstants.CODE_ERROR); 
        model.put("message", ex.getMessage()); 
        model.put("exception", ex); 
        
        //Ajax Request
        String xRequestedWith = request.getHeader("X-Requested-With");
	    if (StringUtils.isNotEmpty(xRequestedWith) && xRequestedWith.indexOf("XMLHttpRequest") != -1) {
	    	return new ModelAndView("error/ajax-error.jsp", model);
	    }
	    
	    if(ex instanceof SeasyException){
			return new ModelAndView("error/error.jsp", model);
	    }else if(ex instanceof RuntimeException){
			return new ModelAndView("error/error.jsp", model);
		}else{
			return new ModelAndView("error/error.jsp", model);
		}
	    
	    //如果返回null，则展示web.xml中的error-page的配置的页面
	    //return null;
	}
	
}
