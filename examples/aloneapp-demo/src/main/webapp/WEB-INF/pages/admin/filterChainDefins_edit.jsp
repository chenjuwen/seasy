<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/init.jsp"%> 

<style type="text/css">
	#frmDefinsEdit {padding-left:20px; padding-right:20px;}
	#frmDefinsEdit td {font-size:12px; height:60px; }
	#frmDefinsEdit input {height:30px;}
</style>

<form id="frmDefinsEdit" method="post">
	<input name="id" type="hidden" value="${dto.id}"/> 
	<table width="100%">
		<tr>
			<td align="right">过滤链定义名称: </td>
			<td>
				<input name="chainName" type="text" value="${dto.chainName}" size="50" class="easyui-validatebox" required="true"/>
				<br>范例：/admin/**
			</td>
		</tr>
		<tr>
			<td align="right">过滤链定义内容: </td>
			<td>
				<input name="chainDefinition" type="text" value="${dto.chainDefinition}" size="50" class="easyui-validatebox" required="true" />
				<br>范例：user,resourceAuth[true],anyRole[admin]
			</td>
		</tr>
		<tr>
			<td align="right">优先级: </td>
			<td><input name="chainOrder" type="text" value="${dto.chainOrder}" size="5" class="easyui-validatebox" required="true"/></td>
		</tr>
	</table>
    
    <div align="center" style="padding-top:10px;">
        <input type="button" value=" 保存 " onclick="javascript:saveData();">&nbsp;&nbsp;
		<input type="reset" value=" 重置  ">
    </div> 
</form>

<script type="text/javascript">
	function saveData(){
		$("#frmDefinsEdit").form("submit", {
			url: "admin/filterChainDefins/saveOrUpdate",
			onSubmit: function(){
				return $("#frmDefinsEdit").form("validate");
			},
			success: function(data){
				data = jQuery.parseJSON(data);
				if("success" == data.code){
					$.messager.show({title:"提示", msg:"操作成功！", timeout:2000});
					$("#divDefinsList").datagrid("reload");
					closeWindow("divDefinsEdit");
				}else{
					$.messager.alert("错误", data.message, "error");
				}
			}
		});
	}
</script>