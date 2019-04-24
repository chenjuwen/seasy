<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/init.jsp"%> 

<style type="text/css">
	.button {font-size:11px;}
</style>

<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north', border:false" style="height:40px; padding-left:10px; padding-top:10px;" align="left">
    	<input type="button" class="button" value=" 保存 " onclick="javascript:saveRelation();">&nbsp;&nbsp;
    	<input type="button" class="button" value=" 关 闭 " onclick="javascript:closeWindow('divRelateRoleResource');">
    </div>
    
    <div id="resourceTree" data-options="region:'center', border:false" style="margin:5px;"></div>
</div>
    
<script type="text/javascript">
	var roleId = "${roleId}";
	
	function saveRelation(){
		var resIds = "";
		var nodes = $('#resourceTree').tree('getChecked', ['checked','indeterminate']);
		if(nodes && nodes.length>0){
			for(var i=0; i<nodes.length; i++){
				if(i>0){
					resIds += "," + nodes[i].id;
				}else{
					resIds = nodes[i].id;
				}
			}
		}
		
		var data = postAjaxWithResult("admin/resource/saveTree", "json", {roleId: roleId, resIds: resIds});
		if("success" == data.code){
			$.messager.show({title:"提示", msg:"操作成功！", timeout:2000});
			closeWindow("divRelateRoleResource");
		}else{
			$.messager.alert("错误", data.message, "error");
		}
	}
	
	var isFirstLoad = true;
	$(function(){
		loadTree();
		
		if(isFirstLoad){
			isFirstLoad = false;
			loadTree();
		}
	});
	
	function loadTree(){
		$("#resourceTree").tree({
			url: "<%=ctx%>/admin/resource/loadRoleResourceTree?roleId="+roleId,
			method: "post",
			checkbox: function(node){
				return true;
			},
			lines: true,
			cascadeCheck: false,
	        onCheck: function (node, checked) {
	        	if (checked) {
	              	var parentNode = $("#resourceTree").tree('getParent', node.target);
	              	if (parentNode != null) {
	              		$("#resourceTree").tree('check', parentNode.target);
	              	}
	            } else {
	              	var childNode = $("#resourceTree").tree('getChildren', node.target);
	              	if (childNode.length > 0) {
	                	for (var i = 0; i < childNode.length; i++) {
	                  		$("#resourceTree").tree('uncheck', childNode[i].target);
	                	}
	              	}
	            }
	    	}
		});
	}
</script>