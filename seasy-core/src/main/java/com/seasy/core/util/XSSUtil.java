package com.seasy.core.util;

import org.apache.commons.lang.StringEscapeUtils;

public class XSSUtil {
	/**
	 * Html
	 */
	public static String escapeHtml(String text) {
		if(text!=null && text.trim().length()>0){
			return StringEscapeUtils.escapeHtml(text);
		}else{
			return "";
		}
	}

	public static String unescapeHtml(String text) {
		if(text!=null && text.trim().length()>0){
			return StringEscapeUtils.unescapeHtml(text);
		}else{
			return "";
		}
	}
	
	/**
	 * JavaScript
	 */
	public static String escapeJavaScript(String text) {
		if(text!=null && text.trim().length()>0){
			return StringEscapeUtils.escapeJavaScript(text);
		}else{
			return "";
		}
	}
	
	public static String unescapeJavaScript(String text) {
		if(text!=null && text.trim().length()>0){
			return StringEscapeUtils.unescapeJavaScript(text);
		}else{
			return "";
		}
	}
	
	/**
	 * Sql
	 */
	public static String escapeSql(String text) {
		if(text!=null && text.trim().length()>0){
			return StringEscapeUtils.escapeSql(text);
		}else{
			return "";
		}
	}
	
	/**
	 * Xml
	 */
	public static String escapeXml(String text) {
		if(text!=null && text.trim().length()>0){
			return StringEscapeUtils.escapeXml(text);
		}else{
			return "";
		}
	}
	
	public static String unescapeXml(String text) {
		if(text!=null && text.trim().length()>0){
			return StringEscapeUtils.unescapeXml(text);
		}else{
			return "";
		}
	}
	
	public static void main(String[] args) {
		System.out.println(escapeHtml("<b alt='dd' value=\"ee\">text</b>"));
		System.out.println(escapeJavaScript("alert(0);"));
	}
	
}
