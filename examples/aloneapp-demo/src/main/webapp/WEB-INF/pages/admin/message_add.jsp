<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/init.jsp"%> 

<style type="text/css">
	#frmContainer {padding-left:20px; padding-right:20px;}
	#frmContainer table {border:1px solid #AED0EA; border-collapse:collapse; margin-top:10px;}
	#frmContainer td {border:1px solid #AED0EA; padding:5px; font-size:12px; height:45px; }
	#frmContainer .radio_label{position:relative; top:-11px; }
	#frmContainer input {height:30px;}
</style>

<form id="frmContainer" method="post">
	<table width="100%">
		<tr>
			<td align="right">类型: </td>
			<td>
				<div>
					<input type="radio" name="type" value="ALL"/><span class="radio_label">所有用户</span>
				</div>
				
				<div>
					<input type="radio" name="type" value="ADMIN"/><span class="radio_label">管理员</span>
				</div>
				
				<div>
					<input type="radio" name="type" value="ROLES"/><span class="radio_label">特定角色</span><br>
					<input type="hidden" id="roleIds" name="roleIds">
					<input type="text" size="55" readonly="readonly" id="roleNames" class="easyui-validatebox"/>
					<a href="#" class="easyui-linkbutton" id="btnSelectRoles" onclick="javascript:_selectRoles();">选择角色</a>
				</div>
				
				<div>
					<input type="radio" name="type" value="USERS"/><span class="radio_label">特定用户</span><br>
					<input type="hidden" id="userIds" name="userIds">
					<input type="text" size="55" readonly="readonly" id="userNames" class="easyui-validatebox"/>
					<a href="#" class="easyui-linkbutton" id="btnSelectUsers" onclick="javascript:_selectUsers();">选择用户</a>
				</div>
			</td>
		</tr>
		<tr>
			<td align="right">内容: </td>
			<td>
				<textarea id="contents" name="contents" rows="5" cols="60" class="easyui-validatebox" required="true"></textarea>
			</td>
		</tr>
	</table>
    
    <div align="center" style="padding-top:10px;">
        <input type="button" value=" 发送 " onclick="javascript:_send();">&nbsp;&nbsp;
		<input type="reset" value=" 重置  ">
    </div> 
</form>

<div id="divSubContainer"></div>

<script type="text/javascript">
	function _selectRoles(){
		openWindow("divSubContainer", "选择角色", "admin/message/selectRoles", 600, 450);
	}
	
	function _selectUsers(){
		openWindow("divSubContainer", "选择用户", "admin/message/selectUsers", 600, 450);
	}
	
	function _send(){
		var value = $("input[name='type']:checked").val();
		if(value == null || value.length <= 0){
			$.messager.alert("提示", "请选择站内信类型！", "info");
			return;
		}else{
			if("ROLES" == value){
				var roleIds = $("#roleIds").val();
				if(roleIds == null || roleIds.length <= 0){
					$.messager.alert("提示", "请选择角色！", "info");
					return;
				}
			}else if("USERS" == value){
				var userIds = $("#userIds").val();
				if(userIds == null || userIds.length <= 0){
					$.messager.alert("提示", "请选择用户！", "info");
					return;
				}
			}
		}
		
		$("#frmContainer").form("submit", {
			url: "admin/message/save",
			onSubmit: function(){
				return $("#frmContainer").form("validate");
			},
			success: function(data){
				data = jQuery.parseJSON(data);
				if("success" == data.code){
					showMessage("<%=ctx%>", "提示", "操作成功！");
					closeWindow("divContainer");
					_query();
				}else{
					$.messager.alert("错误", data.message, "error");
				}
			}
		});
	}
	
	$(function(){
		$("input[name='type']").on("click", function(){
			var type = $(this).val();
			if("ROLES" == type){
				$("#userIds").val("");
				$("#userNames").val("");
			}else if("USERS" == type){
				$("#roleIds").val("");
				$("#roleNames").val("");
			}else{
				$("#roleIds").val("");
				$("#roleNames").val("");
				$("#userIds").val("");
				$("#userNames").val("");
			}
		});
	});
</script>
