<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/init.jsp"%> 

<style type="text/css">
	#frmUserEdit {padding-left:5px; padding-right:5px; }
	.tdRelateRole {font-size:12px; }
</style>


<table id="relateRole" width="100%" border="0">
	<tr>
		<td class="tdRelateRole" width="43%" valign="top">
			<table id="dgUserRoles" class="easyui-datagrid" style="width:100%" 
				data-options="title:'用户已关联角色'">
			    <thead>
			        <tr>
			            <th data-options="field:'ck', checkbox:true, width:20, align:'center'"></th>
			            <th data-options="field:'id', hidden:true">ID</th>
			            <th data-options="field:'roleNo', width:80, align:'center'">角色编号</th>
			            <th data-options="field:'roleName', width:80, align:'center'">角色名称</th>
			            <th data-options="field:'roleDesc', width:80, align:'center'">角色描述</th>
			        </tr>
			    </thead>
			    <tbody>
			    	<c:forEach items="${userRoles}" var="role">
				        <tr>
				            <td></td>
				            <td>${role.id}</td>
				            <td>${role.roleNo}</td>
				            <td>${role.roleName}</td>
				            <td>${role.roleDesc}</td>
				        </tr>
			        </c:forEach>
			    </tbody>
			</table>
		</td>
		
		<td class="tdRelateRole" width="70" align="center">
			<a href="#" class="easyui-linkbutton" iconCls="arrow-left" plain="true" onclick="javascript:addRelate();">添加</a>
			<br><br><br>
			<a href="#" class="easyui-linkbutton" iconCls="arrow-right" plain="true" onclick="javascript:deleteRelate();">删除</a>
		</td>
		
		<td class="tdRelateRole" width="43%" valign="top">
			<table id="dgUserAvailableRoles" class="easyui-datagrid" style="width:100%"
				data-options="title:'可用角色'">
			    <thead>
			        <tr>
			            <th data-options="field:'ck', checkbox:true, width:20, align:'center'"></th>
			            <th data-options="field:'id', hidden:true">ID</th>
			            <th data-options="field:'roleNo', width:80, align:'center'">角色编号</th>
			            <th data-options="field:'roleName', width:80, align:'center'">角色名称</th>
			            <th data-options="field:'roleDesc', width:80, align:'center'">角色描述</th>
			        </tr>
			    </thead>
			    <tbody>
			    	<c:forEach items="${userAvailableRoles}" var="role">
				        <tr>
				            <td></td>
				            <td>${role.id}</td>
				            <td>${role.roleNo}</td>
				            <td>${role.roleName}</td>
				            <td>${role.roleDesc}</td>
				        </tr>
			        </c:forEach>
			    </tbody>
			</table>
		</td>
	</tr>
</table>
<script type="text/javascript">
	var userId = "${userId}";
	function addRelate(){
		var checkedItems = $("#dgUserAvailableRoles").datagrid('getChecked');
		var roleIds = "";
		jQuery.each(checkedItems, function(index, item){
			if(index <= 0){
				roleIds = item.id;
			}else{
				roleIds += "," + item.id;
			}
		});
		
		if(roleIds != ""){
			$.messager.confirm('确认', "确定要添加？", function(b){
				if(b){
					var data = postAjaxWithResult("admin/user/insertUserRoles", "json", {"userId":userId, "roleIds":roleIds});
					if(data != null && "success" == data.code){
						showMessage("<%=ctx%>", "提示", "操作成功！");
						refreshWindow("divUserContainer", "admin/user/relateUserRole?userId="+userId);
					}else{
						$.messager.alert("提示", "操作失败！", "error");
					}
				}
			});
		}else{
			$.messager.alert("提示", "请勾选右边的角色！", "info");
		}
	}
	
	function deleteRelate(){
		var checkedItems = $("#dgUserRoles").datagrid('getChecked');
		var roleIds = "";
		jQuery.each(checkedItems, function(index, item){
			if(index <= 0){
				roleIds = item.id;
			}else{
				roleIds += "," + item.id;
			}
		});
		
		if(roleIds != ""){
			$.messager.confirm('确认', "确定要删除？", function(b){
				if(b){
					var data = postAjaxWithResult("admin/user/deleteUserRoles", "json", {"userId":userId, "roleIds":roleIds});
					if(data != null && "success" == data.code){
						showMessage("<%=ctx%>", "提示", "操作成功！");
						refreshWindow("divUserContainer", "admin/user/relateUserRole?userId="+userId);
					}else{
						$.messager.alert("提示", "操作失败！", "error");
					}
				}
			});
		}else{
			$.messager.alert("提示", "请勾选左边的角色！", "info");
		}
	}
</script>