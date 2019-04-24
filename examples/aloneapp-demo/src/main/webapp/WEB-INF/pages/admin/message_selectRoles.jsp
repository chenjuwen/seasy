<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/init.jsp"%> 

<style type="text/css">
	.roleName {height:25px;}
</style>

<table id="divRoleList">
	<thead>
		<tr>
			<th data-options="field:'ck', checkbox:true, width:20, align:'center'"></th>
			<th data-options="field:'id', hidden:true">ID</th>
			<th data-options="field:'roleNo', width:150, align:'center'">角色编号</th>
			<th data-options="field:'roleName', width:150, align:'center'">角色名称</th>
		</tr>
	</thead>
</table>
	
<div id="divRoleToolbar">
	<div style="padding:5px 0 5px 10px;">
		角色名称：<input type="text" id="roleName" class="roleName"/>
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="javascript:queryRole();">查询</a>
		<a href="#" class="easyui-linkbutton" style="position:relative;float:right;margin-right:20px;" iconCls="icon-ok" onclick="javascript:_saveRole();">确定</a>
	</div>
</div>

<script type="text/javascript">
	function _saveRole(){
		var checkedItems = $("#divRoleList").datagrid('getChecked');
		var roleIds = "";
		var roleNames = "";
		jQuery.each(checkedItems, function(index, item){
			if(index <= 0){
				roleIds = item.id;
				roleNames = item.roleName;
			}else{
				roleIds += "," + item.id;
				roleNames += "," + item.roleName;
			}
		});
		
		$("#roleIds").val(roleIds);
		$("#roleNames").val(roleNames);
		closeWindow("divSubContainer");
	}
	
	$(function(){
		queryRole();
	});
	
	function queryRole(){
		loadGridData(1, 10);
	}
	
	function loadGridData(_pageNumber, _pageSize){
		$("#divRoleList").datagrid({
			toolbar: "#divRoleToolbar",
			url: "admin/message/queryForSelectRoles",
			queryParams: {
				roleName: $("#roleName").val()
			},
			singleSelect: false,
			rownumbers: true,
			pagination: true,
			pageNumber: _pageNumber,
			pageSize: _pageSize,
			onLoadSuccess: function(data){
				if(data.total <= 0){
					$('#divRoleList').datagrid("appendRow", {
						roleNo: "没有数据"
					});
					$('#divRoleList').datagrid("mergeCells", {
						index: 0,
						field: "roleNo",
						colspan: 2
					});
				}
			}
		});
	    
		var pager = $('#divRoleList').datagrid('getPager'); 
	    $(pager).pagination({ 
	        onSelectPage: function(_pageNumber, _pageSize){ 
	        	loadGridData(_pageNumber, _pageSize); 
            },
            onChangePageSize: function(_pageSize){
            	loadGridData(1, _pageSize); 
            }
	    }); 
	}
</script>