package com.seasy.core.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;

import com.seasy.interfaces.dto.ExceptionInfoDTO;

public class SeasyExceptionUtil {
	public static ExceptionInfoDTO getExceptionInfo(Throwable ex){
		return getExceptionInfo(ex, null, null);
	}
	
	public static ExceptionInfoDTO getExceptionInfo(Throwable ex, String code){
		return getExceptionInfo(ex, code, null);
	}
	
	/**
	 * 从异常类提取出各种粒度的异常信息
	 * 
	 * @param ex 异常类
	 * @param code 异常代码
	 * @param keyword 关键字
	 * @return
	 *  	ExceptionInfoDTO
	 */
	public static ExceptionInfoDTO getExceptionInfo(Throwable ex, String code, String keyword){
		ExceptionInfoDTO dto = new ExceptionInfoDTO();
		dto.setCode(code);
		dto.setMessage(ex.getMessage());
		dto.setSummary(getSummaryInfo(ex, keyword));
		dto.setDetails(getDetailedInfo(ex));
		return dto;
	}
	
	/**
	 * 从异常类中提取异常详细信息
	 * 
	 * @param ex 异常类
	 * @return
	 * 		异常详细信息
	 */
	private static String getDetailedInfo(Throwable ex){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		return sw.toString();
	}
	
	/**
	 * 从异常类中提取包含关键字的异常摘要信息
	 * 
	 * @param ex 异常类
	 * @param keyword 关键字
	 * @return 
	 * 		异常摘要信息
	 */
	private static String getSummaryInfo(Throwable ex, String keyword){
		StringBuffer sb = new StringBuffer();
		sb.append(ex.toString());
		
		if(StringUtils.isNotEmpty(keyword)){
			boolean hasDot = false;
			boolean isFirstLine = true;
			String detailedInfo = getDetailedInfo(ex);
			if(StringUtils.isNotEmpty(detailedInfo)){
				for(String lineText : detailedInfo.split("\\n")){
					if(lineText.indexOf(keyword) != -1){
						sb.append("\n" + lineText);
						break;
					}else{
						if(!isFirstLine && !hasDot){
							sb.append("\n......");
							hasDot = true;
						}
					}
					isFirstLine = false;
				}
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * 获取根异常类
	 * @param e 当前层级异常类
	 * @return
	 */
	public static Throwable getRootCause(Throwable e){
		Throwable tmp = e.getCause();
		while(tmp.getCause() != null){
			tmp = tmp.getCause();
		}
		return tmp;
	}
	
	/**
	 * 从当前层级异常类开始往上获取异常信息，直到最顶层异常类
	 * @param e 当前层级异常类
	 * @return 
	 */
	public static String traceAllCause(Throwable e){
		int count = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("---------- start ----------\n");
		sb.append(String.valueOf(++count) + ". " + e.toString() + "\n\n");
		
		Throwable tmp = e.getCause();
		while(tmp != null){
			sb.append(String.valueOf(++count) + ". " + tmp.toString() + "\n\n");
			tmp = tmp.getCause();
		}
		sb.append("---------- end ----------\n");
		
		return sb.toString();
	}
	
}
