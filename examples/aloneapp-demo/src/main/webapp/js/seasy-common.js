function postAjaxWithResult(_url, _dataType, _data){
	_dataType = _dataType || "json";
	_data = _data || {};
	
	var result = null;
	jQuery.ajax({
		url: _url,
		type: "POST",
		dataType: _dataType,
		data: _data,
		async: false,
		success: function(data){
			result = data;
		},
		error: function(){
			result = null;
		}
	});
	return result;
}