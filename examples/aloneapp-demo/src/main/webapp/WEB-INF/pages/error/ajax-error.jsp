<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  
	String code = (String)request.getAttribute("code"); 
	String message = (String)request.getAttribute("message");
%> 
{"code":"<%=code%>", "message":"<%=message%>"}