<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/init.jsp"%> 

<style type="text/css">
	#frmRoleEdit {padding-left:20px; padding-right:20px;}
	#frmRoleEdit input {height:30px;}
	.fitem {margin-top:20px;}
</style>

<form id="frmRoleEdit" method="post">
	<input name="id" type="hidden" value="${role.id}"/> 
	
	<div class="fitem"> 
    	<label>角色编号: </label> 
        <input name="roleNo" type="text" value="${role.roleNo}" size="35" class="easyui-validatebox" required="true"/> 
    </div> 
    
    <div class="fitem"> 
    	<label>角色名称: </label> 
        <input name="roleName" type="text" value="${role.roleName}" size="35" class="easyui-validatebox" required="true" /> 
	</div> 
       
	<div class="fitem"> 
    	<label>角色描述: </label> 
        <input name="roleDesc" type="text" value="${role.roleDesc}" size="35" class="easyui-validatebox" required="true" /> 
    </div> 
    
    <div align="center" style="padding-top:30px;">
        <input type="button" value=" 保存 " onclick="javascript:saveRole();">&nbsp;&nbsp;
		<input type="reset" value=" 重置 ">
    </div> 
</form>

<script type="text/javascript">
	function saveRole(){
		$("#frmRoleEdit").form("submit", {
			url: "admin/role/saveOrUpdate",
			onSubmit: function(){
				return $("#frmRoleEdit").form("validate");
			},
			success: function(data){
				data = jQuery.parseJSON(data);
				if("success" == data.code){
					$.messager.show({title:"提示", msg:"操作成功！", timeout:2000});
					$("#divRoleList").datagrid("reload");
					$("#divRoleEdit").window("close");
				}else{
					$.messager.alert("错误", data.message, "error");
				}
			}
		});
	}
</script>