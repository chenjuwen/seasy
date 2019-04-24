<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="init.jsp"%> 

<style type="text/css">
	.menuItem {background-color:#E6E6FA; height:20px; padding:7px 0 0 25px; cursor:pointer; border:1px solid #f2f5f7; }
	.menuItem .selected {background-color:#FFCC99;}
</style>

<div id="userMenus" class="easyui-accordion" style="width:100%; margin:0px;">
	<c:forEach items="${topMenus}" var="item" varStatus="s1">
		<div id="${item.id}" title="${item.name}" iconCls="${item.img}">
			<c:forEach items="${item.subs}" var="subitem" varStatus="s2">
				<c:if test="${subitem.url != '#'}">
					<div class="menuItem" id="${subitem.id}" title="${subitem.name}" url="<%=ctx%>${subitem.url}">${subitem.name}</div>
				</c:if>
				<c:if test="${subitem.url == '#'}">
					<div class="menuItem" id="${subitem.id}" title="${subitem.name}" url="${subitem.url}">${subitem.name}</div>
				</c:if>
			</c:forEach>
	    </div>
	</c:forEach>
</div>

<script type="text/javascript">
	function addTab(_id, _title, _url){
		var b = $("#userTabs").tabs("exists", _title);
		if(b){
			$("#userTabs").tabs("close", _title);
		}
		
		var newTab = {
			id: _id,
			title: _title,
			href: _url,
			closable: true,
			cache: false
		};
		$("#userTabs").tabs("add", newTab);
			
		/*
		if(b == false){
			var newTab = {
				id: _id,
				title: _title,
				href: _url,
				closable: true,
				cache: false
			};
			$("#userTabs").tabs("add", newTab);
		}else{
			$('#userTabs').tabs('select', _title);
		}
		*/
	}
	
	$(function(){
		$("#userMenus").find("div.menuItem").hover(
			function(){
				$(this).css("background-color", "#FFCC99");
			},
			function(){
				$(this).css("background-color", "#E6E6FA");
			}
		);
		
		$("#userMenus").on("click", "div.menuItem", function(){
			$("#userMenus").find("div.menuItem").css("font-weight", "normal");
			
			var id = $(this).attr("id");
			var title = $(this).attr("title");
			var url = $(this).attr("url");
			addTab(id, title, url);
			
			$(this).css("font-weight", "bold");
		});
	});
</script>