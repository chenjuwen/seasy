<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/init.jsp"%> 

<style type="text/css">
	#frmRoleAdd {padding-left:20px; padding-right:20px;}
	#frmRoleAdd input {height:30px;}
	.fitem {margin-top:20px;}
</style>

<form id="frmRoleAdd" method="post">
	<div class="fitem"> 
    	<label>角色编号: </label> 
        <input name="roleNo" type="text" size="35" class="easyui-validatebox" required="true"/> 
    </div> 
    
    <div class="fitem"> 
    	<label>角色名称: </label> 
        <input name="roleName" type="text" size="35" class="easyui-validatebox" required="true" /> 
	</div> 
       
	<div class="fitem"> 
    	<label>角色描述: </label> 
        <input name="roleDesc" type="text" size="35" class="easyui-validatebox" required="true" /> 
    </div> 
    
    <div align="center" style="padding-top:30px;">
        <input type="button" value=" 保存 " onclick="javascript:saveRole();">&nbsp;&nbsp;
		<input type="reset" value=" 重置  ">
    </div> 
</form>

<script type="text/javascript">
	function saveRole(){
		$("#frmRoleAdd").form("submit", {
			url: "admin/role/saveOrUpdate",
			onSubmit: function(){
				return $("#frmRoleAdd").form("validate");
			},
			success: function(data){
				data = jQuery.parseJSON(data);
				if("success" == data.code){
					$.messager.show({title:"提示", msg:"操作成功！", timeout:2000});
					$("#divRoleAdd").window("close");
					queryRole();
				}else{
					$.messager.alert("错误", data.message, "error");
				}
			}
		});
	}
</script>