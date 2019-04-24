<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="init.jsp"%> 

<style type="text/css">
	.logout {position:relative; float:right; margin-left:8px; background:url('images/logout.png') no-repeat left center;}
	.logout a {font-size:13px; color:blue; margin-left:15px;}
	.userInfo {position:relative; float:right; margin-left:8px; background:url('images/personal.png') no-repeat left center;}
	.userInfo a {font-size:13px; color:blue; margin-left:15px; }
	.message {position:relative; float:right; margin-left:8px; background:url('images/message.png') no-repeat left center;}
	.message a {font-size:13px; color:blue; margin-left:17px;}
</style>

<div style="width:100%; padding-top:20px;">
	<div></div>
	<div class="logout"><a href="javascript:_logout();" title="退出系统">退出系统</a>&nbsp;&nbsp;</div>
	<div class="userInfo"><a href="javascript:_personalInfo();" title="个人信息">您好, ${user.username}</a></div>
	<div class="message"><a href="javascript:_userMessages();" title="站内信">站内信</a></div>
</div>

<script type="text/javascript">
	function _userMessages(){
		
	}
	
	function _personalInfo(){
		addTab("0", "个人资料", "<c:url value="/admin/user/personalInfo"/>");
	}
	
	function _logout(){
		$.messager.confirm('确认', "确定要退出系统？", function(b){
			if(b){
				top.window.location.href = "<c:url value="/logout"/>";
			}
		});
	}
</script>