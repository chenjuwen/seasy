function openWindow(_containerId, _title, _url, _width, _height){
	var win = $("#"+_containerId).window({
		title: _title,
		width: _width,
		height: _height,
		modal: true,
		closed: true,
		minimizable: false,
		maximizable: false,
		collapsible: false,
		href: _url
	});
	$(win).window("open");
}

function closeWindow(_containerId){
	$("#"+_containerId).window("close");
}

function refreshWindow(_containerId, _url){
	$("#"+_containerId).window("refresh", _url);
}

function showMessage(contextPath, _title, _msg){
	var _msg = "<div style='padding-left:25px; background:url("+contextPath+"/css/easyui/icons/ok.png) no-repeat;'>"+_msg+"</div>";
	$.messager.show({title:_title, msg:_msg, timeout:2000});
}
