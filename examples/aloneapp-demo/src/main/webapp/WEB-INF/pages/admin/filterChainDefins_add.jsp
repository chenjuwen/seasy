<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/init.jsp"%> 

<style type="text/css">
	#frmDefinsAdd {padding-left:20px; padding-right:20px;}
	#frmDefinsAdd td {font-size:12px; height:60px; }
	#frmDefinsAdd input {height:30px;}
</style>

<form id="frmDefinsAdd" method="post">
	<table width="100%">
		<tr>
			<td align="right">过滤链定义名称: </td>
			<td>
				<input name="chainName" type="text" size="50" class="easyui-validatebox" required="true"/>
				<br>范例：/admin/**
			</td>
		</tr>
		<tr>
			<td align="right">过滤链定义内容: </td>
			<td>
				<input name="chainDefinition" type="text" size="50" class="easyui-validatebox" required="true" />
				<br>范例：user,resourceAuth[true],anyRole[admin]
			</td>
		</tr>
		<tr>
			<td align="right">优先级: </td>
			<td><input name="chainOrder" type="text" size="5" class="easyui-validatebox" required="true" value="0"/></td>
		</tr>
	</table>
    
    <div align="center" style="padding-top:10px;">
        <input type="button" value=" 保存 " onclick="javascript:saveData();">&nbsp;&nbsp;
		<input type="reset" value=" 重置  ">
    </div> 
</form>

<script type="text/javascript">
	function saveData(){
		$("#frmDefinsAdd").form("submit", {
			url: "admin/filterChainDefins/saveOrUpdate",
			onSubmit: function(){
				return $("#frmDefinsAdd").form("validate");
			},
			success: function(data){
				data = jQuery.parseJSON(data);
				if("success" == data.code){
					$.messager.show({title:"提示", msg:"操作成功！", timeout:2000});
					closeWindow("divDefinsAdd");
					queryData();
				}else{
					$.messager.alert("错误", data.message, "error");
				}
			}
		});
	}
</script>