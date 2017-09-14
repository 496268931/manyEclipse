$(function() {
	$.getScript(basePath + "/static/js/common/common_oper.js");
	$("#add").on("click", addProvince);
	$("#modify").on("click", modifyProvince);
	$("#delete").on("click", deleteProvince);
	$("#query").on("click", search);
});

function search() {
	trimTextInput("provinceListForm");
	$("#provinceListForm").submit();
}

function addProvince() {
	openIFrame(basePath + "/ProvinceController/toProvinceDetail.do", "medium",
			__resourcecode__);
}

function modifyProvince() {
	var provinceId = getCheckedId();
	if (provinceId) {
		openIFrame(basePath
				+ "/ProvinceController/toProvinceDetail.do?provinceId="
				+ provinceId, "medium", __resourcecode__);
	} else {
		alert("请选择记录！");
	}
}

function deleteProvince() {
	var provinceId = getCheckedId();
	if (provinceId) {
		if (confirm("是否确认删除？")) {
			var delLayer = layer.load("正在删除...");
			$.ajax({
				url : basePath + "/ProvinceController/deleteProvince.do",
				type : "post",
				data : {
					provinceId : provinceId
				},
				dataType : "json",
				success : function(data) {
					if (data.status == 1) {
						refresh();
						layer.close(delLayer);
						alert(data.message);
					} else {
                        layer.close(delLayer);
						alert(data.message);
					}
				}
			});
		}
	} else {
		alert("请选择记录！");
	}
}

function refresh() {
    submitWithParam("provinceListForm", "provinceList");
}
