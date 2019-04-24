<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/init.jsp"%> 

<style type="text/css">
	#frmUserEdit {padding-left:5px; padding-right:5px; }
	#frmUserEdit td {background-color:#fff; color:#000; }
</style>

<form id="frmUserEdit" method="post">
	基本信息：<br>
	<table class="easyui-datagrid" width="100%" data-options="showHeader:false">
		<thead>
	        <tr>
	            <th data-options="field:'f1', width:150"></th>
	            <th data-options="field:'f2', width:200"></th>
	        </tr>
	    </thead>
		<tbody>
			<tr>
				<td>登录账号: </td>
				<td>${dto.loginName}</td>
			</tr>
			<tr>
				<td>用户姓名: </td>
				<td>${dto.username}</td>
			</tr>
			<tr>
				<td>手机号: </td>
				<td>${dto.phone}</td>
			</tr>
			<tr>
				<td>EMAIL: </td>
				<td>${dto.email}</td>
			</tr>
			<tr>
				<td>登录次数: </td>
				<td>${dto.loginTimes}</td>
			</tr>
			<tr>
				<td>最近登录时间: </td>
				<td>
					<fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${dto.lastLoginTime}" />
				</td>
			</tr>
			<tr>
				<td>状态: </td>
				<td>
					<c:choose>
						<c:when test="${dto.enabled == '1'}">可用</c:when>
						<c:when test="${dto.enabled == '0'}">禁用</c:when>
						<c:otherwise>${dto.enabled}</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</tbody>
	</table>
	<br>
	
	角色列表：<br>
	<table class="easyui-datagrid" style="width:100%">
	    <thead>
	        <tr>
	            <th data-options="field:'roleNo', width:150, align:'center'">角色编号</th>
	            <th data-options="field:'roleName', width:150, align:'center'">角色名称</th>
	            <th data-options="field:'roleDesc', width:150, align:'center'">角色描述</th>
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach items="${roleList}" var="role">
		        <tr>
		            <td>${role.roleNo}</td>
		            <td>${role.roleName}</td>
		            <td>${role.roleDesc}</td>
		        </tr>
	        </c:forEach>
	    </tbody>
	</table>
</form>
