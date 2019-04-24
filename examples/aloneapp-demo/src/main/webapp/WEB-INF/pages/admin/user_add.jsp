<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/init.jsp"%> 

<style type="text/css">
	#frmUserAdd {padding-left:20px; padding-right:20px;}
	#frmUserAdd td {font-size:12px; height:45px; }
	#frmUserAdd input {height:30px;}
</style>

<form id="frmUserAdd" method="post">
	<table width="100%">
		<tr>
			<td align="right">登录账号: </td>
			<td>
				<input name="loginName" type="text" size="30" class="easyui-validatebox" required="true" />
			</td>
		</tr>
		<tr>
			<td align="right">登录密码: </td>
			<td>
				<input name="password" type="text" value="123456" size="30" class="easyui-validatebox" required="true" />
			</td>
		</tr>
		<tr>
			<td align="right">用户姓名: </td>
			<td>
				<input name="username" type="text" size="30" class="easyui-validatebox" required="true" />
			</td>
		</tr>
		<tr>
			<td align="right">手机号: </td>
			<td>
				<input name="phone" type="text" size="30"/>
			</td>
		</tr>
		<tr>
			<td align="right">EMAIL: </td>
			<td>
				<input name="email" type="text" size="30"/>
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
		$("#frmUserAdd").form("submit", {
			url: "admin/user/saveOrUpdate",
			onSubmit: function(){
				return $("#frmUserAdd").form("validate");
			},
			success: function(data){
				data = jQuery.parseJSON(data);
				if("success" == data.code){
					showMessage("<%=ctx%>", "提示", "操作成功！");
					closeWindow("divUserContainer");
					queryUser();
				}else{
					$.messager.alert("错误", data.message, "error");
				}
			}
		});
	}
</script>
