<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>error page</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  </head>
  
  <body>
  	Exception: <br><br>
	<%  
		Exception e = (Exception)request.getAttribute("exception"); 
		out.print(e.toString()); 
	%> 
  </body>
</html>
