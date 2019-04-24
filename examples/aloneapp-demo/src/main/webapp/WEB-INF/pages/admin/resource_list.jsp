<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/init.jsp"%> 
<link rel="stylesheet" href="<c:url value="/css/treegrid-3.0.css"/>" />
<script type="text/javascript" src="<c:url value="/js/treegrid-3.0.js"/>"></script>

<div id="divResourceTree" style="width:100%;"></div>

<div id="divResourceAdd"></div>
<div id="divResourceEdit"></div>

<script type="text/javascript">
var config = { 
        "columns":[  
            {"title": "资源名称", "field": "resName", "width":"250"},  
            {"title": "资源编号", "field": "resNo", "width":"150"},
            {"title": "资源URL", "field": "resUrl"},  
            {"title": "备注", "field": "remarks"}  
        ],
		"menus":[
			{"code":"mnuAddChild", "title": "添加下级资源", "handler": "mnuFunction"},
			{"code":"mnuEdit", "title": "编辑资源", "handler": "mnuFunction"},
			{"code":"mnuDelete", "title": "删除资源", "handler": "mnuFunction"}
		],
		onDblClickRow: function(trid, index, data){
			mnuFunction("mnuEdit", trid);
		}
    };
  
    var treeGrid = null;
	$(function(){
		treeGrid = jQuery("#divResourceTree").showTreeGrid(config);
		showResourceTree();
	});
	
	function showResourceTree(){
		var data = postAjaxWithResult("admin/resource/loadChildren");
		if(data != null){
			treeGrid.setDataset(data);  
	        treeGrid.show(); 
	        treeGrid.expandAll();
		}
	}
    
    function mnuFunction(menuCode, trid){
    	var rowData = treeGrid.getRowData(trid);
    	
    	if("mnuAddChild" == menuCode){
			openWindow("divResourceAdd", "资源 添加", "admin/resource/add?parentId="+rowData.id, 500, 500);
    	}else if("mnuEdit" == menuCode){
			openWindow("divResourceEdit", "资源 修改", "admin/resource/edit?id="+rowData.id, 500, 500);
    	}else if("mnuDelete" == menuCode){
    		$.messager.confirm('确认','确定要删除?', function(b){
    			if(b){
    				var data = postAjaxWithResult("admin/resource/delete", "json", {"id": rowData.id});
    				if(data != null && "success" == data.code){
    					showMessage("<%=ctx%>", "提示", "删除成功！");
    					showResourceTree();
    				}else{
    					var msg = "操作失败！";
    					if(data.message != null && data.message != ""){
    						msg  = data.message;
    					}
    					$.messager.alert("提示", msg, "error");
    				}
    			}
    		});
    	}
    }
</script>