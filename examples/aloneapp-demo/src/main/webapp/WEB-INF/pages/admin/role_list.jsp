<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/init.jsp"%> 

<style type="text/css">
	.roleName {height:25px;}
</style>

<table id="divRoleList">
	<thead>
		<tr>
			<th field="roleNo" width="100" align="center">角色编号</th>
			<th field="roleName" width="150" align="center">角色名称</th>
			<th field="roleDesc" width="150" align="center">角色描述</th>
			<th field="operateTime" width="150" align="center">操作时间</th>
			<th field="op" width="150" align="center" formatter="formatItem">操作</th>
		</tr>
	</thead>
</table>
	
<div id="divRoleToolbar">
	<div style="padding:5px 0 5px 10px;">
		角色名称：<input type="text" id="roleName" class="roleName"/>
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="javascript:queryRole();">查询</a>
	</div>
	
	<div style="padding:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:roleAdd();">新增</a>
	</div>
</div>

<div id="divRoleAdd"></div>
<div id="divRoleEdit"></div>
<div id="divRelateRoleResource"></div>

<script type="text/javascript">
	function formatItem(val, row){
		var s = "<a href='#' style='color:blue;' onclick='javascript:relateRoleResource(" + row.id + ");'>关联资源</a>&nbsp;&nbsp;";
		s += "<a href='#' style='color:blue;' onclick='javascript:roleEdit(" + row.id + ");'>编辑</a>&nbsp;&nbsp;";
		s += "<a href='#' style='color:blue;' onclick='javascript:roleDelete(" + row.id + ");'>删除</a>";
		return s;
	}
	
	function relateRoleResource(roleId){
		openWindow("divRelateRoleResource", "资源树", "admin/resource/roleResourceTree?roleId="+roleId, 300, 400);
	}
	
	function roleAdd(){
		openWindow("divRoleAdd", "角色添加", "admin/role/add", 500, 300);
	}
	
	//edit
	function roleEdit(id){
		openWindow("divRoleEdit", "角色修改", "admin/role/edit?id="+id, 500, 300);
	}
	
	//delete
	function roleDelete(id){
		$.messager.confirm('确认','确定要删除?', function(b){
			if(b){
				var data = postAjaxWithResult("admin/role/delete", "json", {"id":id});
				if(data != null && "success" == data.code){
					$.messager.show({title:"提示", msg:"删除成功！", timeout:2000});
					queryRole();
				}else{
					$.messager.alert("提示", "操作失败！", "error");
				}
			}
		});
	}
	
	//query
	$(function(){
		queryRole();
	});
	
	function queryRole(){
		loadGridData(1, 10);
	}
	
	function loadGridData(_pageNumber, _pageSize){
		$("#divRoleList").datagrid({
			toolbar: "#divRoleToolbar",
			url: "admin/role/queryForPage",
			queryParams: {
				roleName: $("#roleName").val()
			},
			singleSelect: true,
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
						colspan: 5
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