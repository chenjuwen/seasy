<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/init.jsp"%> 
<script type="text/javascript" src="<c:url value="/js/seasy-easyui.js"/>"></script>

<table id="divDefinsList">
	<thead>
		<tr>
			<th field="chainName" width="200" align="center">过滤链定义名称</th>
			<th field="chainDefinition" width="350" align="center">过滤链定义内容</th>
			<th field="chainOrder" width="50" align="center">优先级</th>
			<th field="op" width="100" align="center" formatter="formatItem">操作</th>
		</tr>
	</thead>
</table>
	
<div id="divDefinsToolbar">
	<div style="padding:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:addRecord();">新增</a>&nbsp;&nbsp;
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="javascript:loadDefinsToPlatform();">更新安全策略</a>
	</div>
</div>

<div id="divDefinsAdd"></div>
<div id="divDefinsEdit"></div>

<script type="text/javascript">
	function formatItem(val, row){
		var s = "<a href='#' style='color:blue;' onclick='javascript:editRecord(" + row.id + ");'>编辑</a>&nbsp;&nbsp;";
		s += "<a href='#' style='color:blue;' onclick='javascript:deleteRecord(" + row.id + ");'>删除</a>";
		return s;
	}
	
	function addRecord(){
		openWindow("divDefinsAdd", "安全过滤链定义 添加", "admin/filterChainDefins/add", 500, 300);
	}
	
	//edit
	function editRecord(id){
		openWindow("divDefinsEdit", "安全过滤链定义 修改", "admin/filterChainDefins/edit?id="+id, 500, 300);
	}
	
	//delete
	function deleteRecord(id){
		$.messager.confirm('确认','确定要删除?', function(b){
			if(b){
				var data = postAjaxWithResult("admin/filterChainDefins/delete", "json", {"id":id});
				if(data != null && "success" == data.code){
					$.messager.show({title:"提示", msg:"删除成功！", timeout:2000});
					queryData();
				}else{
					$.messager.alert("提示", "操作失败！", "error");
				}
			}
		});
	}
	
	function loadDefinsToPlatform(){
		var data = postAjaxWithResult("admin/filterChainDefins/loadDefinsToPlatform");
		if(data != null && "success" == data.code){
			$.messager.show({title:"提示", msg:"操作成功！", timeout:2000});
		}else{
			$.messager.alert("提示", "操作失败！", "error");
		}
	}
	
	//query
	$(function(){
		queryData();
	});
	
	function queryData(){
		loadGridData(1, 30);
	}
	
	function loadGridData(_pageNumber, _pageSize){
		$("#divDefinsList").datagrid({
			toolbar: "#divDefinsToolbar",
			url: "admin/filterChainDefins/queryForPage",
			singleSelect: true,
			rownumbers: true,
			pagination: true,
			pageNumber: _pageNumber,
			pageSize: _pageSize,
			onLoadSuccess: function(data){
				if(data.total <= 0){
					$('#divDefinsList').datagrid("appendRow", {
						roleNo: "没有数据"
					});
					$('#divDefinsList').datagrid("mergeCells", {
						index: 0,
						field: "chainName",
						colspan: 4
					});
				}
			}
		});
	    
		var pager = $('#divDefinsList').datagrid('getPager'); 
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