<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  	<head>
	    <title>系统登录</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<link rel="stylesheet" href="<c:url value="/css/easyui/icon.css"/>" />
		<link rel="stylesheet" href="<c:url value="/css/easyui/color.css"/>" />
		<link rel="stylesheet" href="<c:url value="/css/easyui/cupertino/easyui.css"/>" />
		
		<style type="text/css">
			#loginWin {width:450px; height:300px; padding:10px;}
			#loginForm td {font-size:13px; height:50px;}
			#loginForm input {width:250px; height:30px;}
			#loginForm input.captcha {width:173px;}
			#captchaImg {width:70px; height:30px; position:relative; top:10px; cursor:pointer;}
		</style>
		
		<script type="text/javascript" src="<c:url value="/js/jquery.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/jquery.easyui.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/seasy-common.js"/>"></script>
  	</head>
	<body>
	    <div id="loginWin" class="easyui-window" title="系统登录"
   		minimizable="false" maximizable="false" resizable="false" collapsible="false" closable="false" draggable="false">
    		<div class="easyui-layout" data-options="fit:true">
            	<div data-options="region:'center',border:false" style="padding:20px 0 0 30px;background:#fff;border:1px solid #ccc;">
        			<form id="loginForm" name="loginForm" method="post" action="<c:url value="/login"/>">
        				<table>
        					<tr>
        						<td align="right" width="70">帐&nbsp;号:</td>
        						<td><input type="text" id="username" name="username"/></td>
        					</tr>
        					<tr>
        						<td align="right">密&nbsp;码:</td>
        						<td><input type="password" id="password" name="password"/></td>
        					</tr>
        					<tr>
        						<td align="right">验证码:</td>
        						<td>
									<input type="text" id="captcha" name="captcha" class="captcha"></input>
                					<img id="captchaImg" alt="点击刷新验证码" src="<c:url value="/kaptcha/kaptcha.jpg?r=1"/>"/>
								</td>
        					</tr>
        					<tr>
        						<td colspan="2" style="text-align:center; color:red;" id="showMsg">
									<%
										String errorMessage = (String)session.getAttribute("errorMessage");
										if(StringUtils.isNotEmpty(errorMessage)){
											out.print(errorMessage);
										}
									%>
        						</td>
        					</tr>
        				</table>
        			</form>
				</div>
				
            	<div data-options="region:'south',border:false" style="text-align:right;padding:8px 0 4px 0;">
                	<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="_login()"> 登录 </a>&nbsp;
                	<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="_reset()"> 重置 </a>
            	</div>
    		</div>
		</div>
		
		<script type="text/javascript">
			function _login(){
				if($("#username").val()==""){
			         $("#showMsg").html("账号不能为空！");
			         $("#username").focus();
			    }else if($("#password").val()==""){
			         $("#showMsg").html("密码不能为空！");
			         $("#password").focus();
			    }else if($("#captcha").val()==""){
			         $("#showMsg").html("验证码不能为空！");
			         $("#captcha").focus();
			    }else{
			    	$("#loginForm").submit();
				} 
			}
			
			function _reset(){
				$('#loginForm').form('clear');
			}

			$(function(){
			    $("#username").focus();
			    
			    var img = $("#captchaImg");
				img.click(function(){
					var url = img.attr("src");
					url = url.substring(0, url.indexOf("?")) + "?r=" + Math.random();
					img.attr("src",url);
				});
				
				//确保重新登录时在顶级窗口显示登录页面
				var url = window.location.href;
				if(url.indexOf("login") == -1){
					top.location.href = "<c:url value="/index.jsp"/>";
				}
			});
			
			document.onkeydown = function(e){
			    var event = e || window.event;  
			    var code = event.keyCode || event.which || event.charCode;
			    if (code == 13) {
			    	_login();
			    }
			}
		</script>
	</body>
</html>
