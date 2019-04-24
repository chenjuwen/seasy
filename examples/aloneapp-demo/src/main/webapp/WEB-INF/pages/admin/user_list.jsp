<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/init.jsp"%> 

<style type="text/css">
	.keyword {height:25px;}
	.enabled {height:25px;}
	#divUserToolbar td {font-size:12px;}
</style>

<table id="divUserList">
	<thead>
		<tr>
			<th field="loginName" width="80" align="center">登录账号</th>
			<th field="username" width="100" align="center">用户姓名</th>
			<th field="phone" width="80" align="center">手机号</th>
			<th field="email" width="130" align="center">EMAIL</th>
			<th field="lastLoginTime" width="130" align="center">最后登录时间</th>
			<th field="operateTime" width="130" align="center">操作时间</th>
			<th field="enabled" width="40" align="center" formatter="renderState">状态</th>
			<th field="op" width="240" align="center" formatter="formatItem">操作</th>
		</tr>
	</thead>
</table>
	
<div id="divUserToolbar">
	<table>
		<tr>
			<td>
				账号/姓名：<input type="text" id="keyword" name="keyword" class="keyword"/>
			</td>
			<td width="110" align="right">
				状态：
				<select id="enabled" name="enabled" class="enabled">
					<option value=""></option>
					<option value="1">可用</option>
					<option value="0">禁用</option>
				</select>
			</td>
			<td width="70" align="right">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="javascript:queryUser();">查询</a>
			</td>
		</tr>
	</table>
	
	<div style="padding:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:userAdd();">新增</a>
	</div>
</div>

<div id="divUserContainer"></div>

<script type="text/javascript">
	function renderState(val, row){
		if("0" == val){
			return "禁用";
		}else if("1" == val){
			return "可用";
		}else{
			return "";
		}
	}
	
	function formatItem(val, row){
		var s = "<a href='#' style='color:blue;' onclick='javascript:viewUser(" + row.id + ");'>查看</a>&nbsp;&nbsp;";
		s += "<a href='#' style='color:blue;' onclick='javascript:userEdit(" + row.id + ");'>编辑</a>&nbsp;&nbsp;";
		s += "<a href='#' style='color:blue;' onclick='javascript:relateUserRole(" + row.id + ");'>关联角色</a>&nbsp;&nbsp;";
		s += "<a href='#' style='color:blue;' onclick='javascript:resetPassword(" + row.id + ");'>重置密码</a>&nbsp;&nbsp;";
		
		if(row.enabled == 1){
			s += "<a href='#' style='color:red;' onclick='javascript:updateState(" + row.id + "," + row.enabled + ");'>禁用</a>";
		}else{
			s += "<a href='#' style='color:green;' onclick='javascript:updateState(" + row.id + "," + row.enabled + ");'>激活</a>";
		}
		return s;
	}
	
	function userAdd(){
		openWindow("divUserContainer", "添加用户", "admin/user/add", 600, 450);
	}
	
	function viewUser(id){
		openWindow("divUserContainer", "查看用户", "admin/user/view?id="+id, 600, 450);
	}
	
	function userEdit(id){
		openWindow("divUserContainer", "修改用户", "admin/user/edit?id="+id, 600, 450);
	}
	
	function relateUserRole(userId){
		openWindow("divUserContainer", "分配角色", "admin/user/relateUserRole?userId="+userId, 700, 450);
	}
	
	function resetPassword(userId){
		$.messager.confirm('确认', "确定要重置密码？", function(b){
			if(b){
				var data = postAjaxWithResult("admin/user/resetPassword", "json", {"id":userId});
				if(data != null && "success" == data.code){
					showMessage("<%=ctx%>", "提示", "操作成功！");
				}else{
					$.messager.alert("提示", "操作失败！", "error");
				}
			}
		});
	}
	
	function updateState(id, enabled){
		var msg = "";
		if(1 == enabled){
			msg = "确定禁用该用户？";
			enabled = 0;
		}else{
			msg = "确定激活该用户？";
			enabled = 1;
		}
		
		$.messager.confirm('确认', msg, function(b){
			if(b){
				var data = postAjaxWithResult("admin/user/updateState", "json", {"id":id, "enabled":enabled});
				if(data != null && "success" == data.code){
					showMessage("<%=ctx%>", "提示", "操作成功！");
					queryUser();
				}else{
					$.messager.alert("提示", "操作失败！", "error");
				}
			}
		});
	}
	
	//query
	$(function(){
		queryUser();
	});
	
	function queryUser(){
		loadGridData(1, 10);
	}
	
	function loadGridData(_pageNumber, _pageSize){
		$("#divUserList").datagrid({
			toolbar: "#divUserToolbar",
			url: "admin/user/queryForPage",
			queryParams: {
				keyword: $("#keyword").val(),
				enabled: $("#enabled").val()
			},
			singleSelect: true,
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
						colspan: 8
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