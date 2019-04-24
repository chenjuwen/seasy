<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/init.jsp"%> 

<style type="text/css">
	#psersonalInfo .datagrid-btable td {height:40px;}
	
	#modifyPassword table {border-collapse: collapse; border: 1px solid #AED0EA;}
	#modifyPassword td {height:50px; border:1px dotted #AED0EA; font-size:13px; }
	#modifyPassword input {height:30px; }
</style>

<table style="width:97%; margin:15px 0 5px 5px; ">
  <tr>
    <td style="border-bottom:2px solid #d7ebf9; font-size:13px; ">个人信息</td>
  </tr>
</table>

<div id="personalTabs" class="easyui-tabs" style="margin-left:5px; width:99%; height:90%;">
    <div id="psersonalInfo" title="个人资料" style="padding:20px;">
		<table class="easyui-datagrid" width="100%" 
		data-options="showHeader:false, autoRowHeight:false, singleSelect:true">
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
    </div>
    
    <div id="modifyPassword" title="修改密码" style="padding:20px;">
        <form id="frmModifyPassword" method="post">
			<input name="userId" type="hidden" value="${dto.id}"/>
			
			<table width="500" border=1>
				<tr>
					<td align="right">原始密码: </td>
					<td>
						<input name="oldPassword" type="password" size="30" 
						class="easyui-validatebox" data-options="required:true,validType:'password'" />
					</td>
				</tr>
				<tr>
					<td align="right">新密码: </td>
					<td>
						<input id="newPassword" name="newPassword" type="password" size="30" 
						class="easyui-validatebox" data-options="required:true,validType:'password'" />
					</td>
				</tr>
				<tr>
					<td align="right">确认新密码: </td>
					<td>
						<input name="confirmPassword" type="password" size="30" 
						class="easyui-validatebox" required="true" validType="confirmPassword['#newPassword']"/>
					</td>
				</tr>
			</table>
		    
		    <div align="center" style="width:500px; padding-top:10px;">
		        <input type="button" value=" 保存 " onclick="javascript:saveData();">&nbsp;&nbsp;
				<input type="reset" value=" 重置  ">
		    </div> 
		</form>
    </div>
</div>

<script type="text/javascript">
	$(function(){
		$.extend($.fn.validatebox.defaults.rules, {
			password: {
				validator: function (value) {  
		            var reg = /^[a-zA-Z0-9_]{6,10}$/;  
		            return reg.test(value);  
		        },  
		        message: '请输入6-10个字母、数字、下划线组合的字符串'
			},
		    confirmPassword: {
		        validator: function(value, param){
		            return value == $(param[0]).val();
		        },
		        message: '两次输入的新密码不一致'
		    }
		});
	});
	
	function saveData(){
		$("#frmModifyPassword").form("submit", {
			url: "admin/user/modifyPassword",
			onSubmit: function(){
				return $("#frmModifyPassword").form("validate");
			},
			success: function(data){
				data = jQuery.parseJSON(data);
				if("success" == data.code){
					$.messager.show({title:"提示", msg:"操作成功！", timeout:2000});
				}else{
					var msg = "操作失败";
					if(data.message != ""){
						msg = data.message;
					}
					$.messager.alert("错误", msg, "error");
				}
				$("input[type=reset]").trigger("click");
			}
		});
	}
</script>
