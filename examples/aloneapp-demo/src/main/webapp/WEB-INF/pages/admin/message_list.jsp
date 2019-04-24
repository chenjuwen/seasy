<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/init.jsp"%> 

<style type="text/css">
	.contents {height:25px;}
	.type {height:25px;}
	#divToolbar td {font-size:12px;}
</style>

<table id="divList">
	<thead>
		<tr>
			<th field="type" width="100" align="center" formatter="renderType">类型</th>
			<th field="contents" width="300" align="center">内容</th>
			<th field="sendUserName" width="100" align="center">发送人</th>
			<th field="operateTime" width="130" align="center">操作时间</th>
			<th field="op" width="80" align="center" formatter="formatItem">操作</th>
		</tr>
	</thead>
</table>
	
<div id="divToolbar">
	<table>
		<tr>
			<td>
				内容：<input type="text" id="contents" name="contents" class="contents"/>
			</td>
			<td width="150" align="right">
				类型：
				<select id="type" name="type" class="type">
					<option value=""></option>
					<option value="ALL">所有用户</option>
					<option value="ADMIN">管理员</option>
					<option value="ROLES">特定角色</option>
					<option value="USERS">特定用户</option>
				</select>
			</td>
			<td width="70" align="right">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="javascript:_query();">查询</a>
			</td>
		</tr>
	</table>
	
	<div style="padding:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:_add();">发送站内信</a>
	</div>
</div>

<div id="divContainer"></div>

<script type="text/javascript">
	function renderType(val, row){
		if("ALL" == val){
			return "所有用户";
		}else if("ADMIN" == val){
			return "管理员";
		}else if("ROLES" == val){
			return "特定角色";
		}else if("USERS" == val){
			return "特定用户";
		}else{
			return "";
		}
	}
	
	function formatItem(val, row){
		var s = "<a href='#' style='color:blue;' onclick='javascript:_view(" + row.id + ");'>查看</a>&nbsp;&nbsp;";
		s += "<a href='#' style='color:blue;' onclick='javascript:_delete(" + row.id + ");'>删除</a>";
		return s;
	}
	
	function _add(){
		openWindow("divContainer", "发送站内信", "admin/message/add", 600, 450);
	}
	
	function _view(id){
		openWindow("divContainer", "查看", "admin/message/view?id="+id, 600, 450);
	}
	
	function _delete(id){
		$.messager.confirm('确认', "确定要删除？", function(b){
			if(b){
				var data = postAjaxWithResult("admin/message/delete", "json", {"id":id});
				if(data != null && "success" == data.code){
					showMessage("<%=ctx%>", "提示", "操作成功！");
					_query();
				}else{
					$.messager.alert("提示", "操作失败！", "error");
				}
			}
		});
	}
	
	//query
	$(function(){
		_query();
	});
	
	function _query(){
		loadGridData(1, 20);
	}
	
	function loadGridData(_pageNumber, _pageSize){
		$("#divList").datagrid({
			toolbar: "#divToolbar",
			url: "admin/message/queryForPage",
			queryParams: {
				contents: $("#contents").val(),
				type: $("#type").val()
			},
			singleSelect: true,
			rownumbers: true,
			pagination: true,
			pageNumber: _pageNumber,
			pageSize: _pageSize,
			onLoadSuccess: function(data){
				if(data.total <= 0){
					$('#divList').datagrid("appendRow", {
						loginName: "没有数据"
					});
					$('#divList').datagrid("mergeCells", {
						index: 0,
						field: "type",
						colspan: 5
					});
				}
			}
		});
	    
		var pager = $('#divList').datagrid('getPager'); 
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