<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/init.jsp"%> 

<style type="text/css">
	#frmResourceAdd {padding-left:20px; padding-right:20px;}
	#frmResourceAdd td {font-size:12px; height:60px; }
	#frmResourceAdd input {height:30px;}
</style>

<form id="frmResourceAdd" method="post">
	<input name="parentId" type="hidden" value="${parentId}"/>
	<table width="100%">
		<tr>
			<td align="right">资源编号: </td>
			<td>
				<input name="resNo" type="text" size="50" class="easyui-validatebox" required="true" />
			</td>
		</tr>
		<tr>
			<td align="right">资源名称: </td>
			<td>
				<input name="resName" type="text" size="50" class="easyui-validatebox" required="true" />
			</td>
		</tr>
		<tr>
			<td align="right">资源URL: </td>
			<td>
				<input name="resUrl" type="text" size="50" />
			</td>
		</tr>
		<tr>
			<td align="right">备注: </td>
			<td>
				<input name="remarks" type="text" size="50" />
			</td>
		</tr>
	</table>
    
    <div align="center" style="padding-top:10px;">
        <input type="button" value=" 保存 " onclick="javascript:saveData();">&nbsp;&nbsp;
		<input type="reset" value=" 重置  ">
    </div> 
</form>

<script type="text/javascript">
	function saveData(){
		$("#frmResourceAdd").form("submit", {
			url: "admin/resource/saveOrUpdate",
			onSubmit: function(){
				return $("#frmResourceAdd").form("validate");
			},
			success: function(data){
				data = jQuery.parseJSON(data);
				if("success" == data.code){
					showMessage("<%=ctx%>", "提示", "操作成功！");
					closeWindow("divResourceAdd");
					showResourceTree();
				}else{
					$.messager.alert("错误", data.message, "error");
				}
			}
		});
	}
</script>
