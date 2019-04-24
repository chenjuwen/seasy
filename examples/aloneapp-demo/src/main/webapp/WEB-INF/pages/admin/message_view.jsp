<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/init.jsp"%> 

<style type="text/css">
	#frmContainer {padding-left:5px; padding-right:5px; margin-top:5px; }
	#frmContainer td {background-color:#fff; color:#000; }
	#frmContainer .datagrid-btable td {height:35px;}
</style>

<form id="frmContainer" method="post">
	<table class="easyui-datagrid" width="100%" data-options="showHeader:false">
		<thead>
	        <tr>
	            <th data-options="field:'f1', width:100"></th>
	            <th data-options="field:'f2', width:450"></th>
	        </tr>
	    </thead>
		<tbody>
			<tr>
				<td>类型: </td>
				<td>
					<c:choose>
						<c:when test="${dto.type == 'ALL'}">所有用户</c:when>
						<c:when test="${dto.type == 'ADMIN'}">管理员</c:when>
						<c:when test="${dto.type == 'ROLES'}">特定角色</c:when>
						<c:when test="${dto.type == 'USERS'}">特定用户</c:when>
						<c:otherwise>${dto.type}</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>内容: </td>
				<td>${dto.contents}</td>
			</tr>
			<tr>
				<td>发送人姓名: </td>
				<td>${dto.sendUserName}</td>
			</tr>
			<tr>
				<td>接收用户ID: </td>
				<td>${dto.receiveId}</td>
			</tr>
			<tr>
				<td>已接收用户ID: </td>
				<td>${dto.receiveYes}</td>
			</tr>
			<tr>
				<td>已删除用户ID: </td>
				<td>${dto.deleteYes}</td>
			</tr>
			<tr>
				<td>操作时间: </td>
				<td>
					<fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${dto.operateTime}" />
				</td>
			</tr>
		</tbody>
	</table>
</form>
