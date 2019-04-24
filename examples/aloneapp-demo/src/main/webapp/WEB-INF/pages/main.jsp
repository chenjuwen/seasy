<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="init.jsp"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  	<head>
	    <title>index</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<link rel="stylesheet" href="<c:url value="/css/easyui/icon.css"/>" />
		<link rel="stylesheet" href="<c:url value="/css/easyui/color.css"/>" />
		<link rel="stylesheet" href="<c:url value="/css/easyui/cupertino/easyui.css"/>" />
		<link rel="stylesheet" href="<c:url value="/css/seasy-common.css"/>" />
		
		<script type="text/javascript" src="<c:url value="/js/jquery.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/jquery.easyui.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/jquery.easyui.plus.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/locale/easyui-lang-zh_CN.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/seasy-common.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/seasy-easyui.js"/>"></script>
  	</head>
  
	<body class="easyui-layout">
	    <div data-options="region:'north',href:'header',border:false" style="height:60px;"></div>
	    <div data-options="region:'west',title:' ${today}',split:true,href:'menu'" style="width:230px;"></div>
	    <div data-options="region:'center',title:'',href:'personal',border:false" style="padding:0px;"></div>
	</body>
</html>
