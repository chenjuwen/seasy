<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/init.jsp"%> 

<style type="text/css">
	.keyword {height:25px;}
	#divUserToolbar td {font-size:12px;}
</style>

<table id="divUserList">
	<thead>
		<tr>
			<th data-options="field:'ck', checkbox:true, width:20, align:'center'"></th>
			<th data-options="field:'id', hidden:true">ID</th>
			<th field="loginName" width="150" align="center">登录账号</th>
			<th field="username" width="150" align="center">用户姓名</th>
		</tr>
	</thead>
</table>
	
<div id="divUserToolbar">
	<div style="padding:5px 0 5px 10px;">
		账号/姓名：<input type="text" id="keyword" name="keyword" class="keyword"/>
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="javascript:queryUser();">查询</a>
		<a href="#" class="easyui-linkbutton" style="position:relative;float:right;margin-right:20px;" iconCls="icon-ok" onclick="javascript:_saveUser();">确定</a>
	</div>
</div>

<script type="text/javascript">
	function _saveUser(){
		var checkedItems = $("#divUserList").datagrid('getChecked');
		var userIds = "";
		var userNames = "";
		jQuery.each(checkedItems, function(index, item){
			userIds += item.id + ";";
			if(index <= 0){
				userNames = item.username;
			}else{
				userNames += "," + item.username;
			}
		});
		
		$("#userIds").val(userIds);
		$("#userNames").val(userNames);
		closeWindow("divSubContainer");
	}
	
	//query
	$(function(){
		queryUser();
	});
	
	function queryUser(){
		loadGridData(1, 20);
	}
	
	function loadGridData(_pageNumber, _pageSize){
		$("#divUserList").datagrid({
			toolbar: "#divUserToolbar",
			url: "admin/message/queryForSelectUsers",
			queryParams: {
				keyword: $("#keyword").val()
			},
			singleSelect: false,
			rownumbers: true,
			pagination: true,
			pageNumber: _pageNumber,
			pageSize: _pageSize,
			onLoadSuccess: function(data){
				if(data.total <= 0){
					$('#divUserList').datagrid("appendRow", {
						loginName: "没有数据"
					});
					$('#divUserList').datagrid("mergeCells", {
						index: 0,
						field: "loginName",
						colspan: 2
					});
				}
			}
		});
	    
		var pager = $('#divUserList').datagrid('getPager'); 
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