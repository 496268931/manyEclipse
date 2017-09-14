$(function() {
	$("#add").on("click", addDistrict);
	$("#modify").on("click", modifyDistrict);
	$("#delete").on("click", deleteDistrict);
	$("#search").on("click", search);
});

function search() {
	trimTextInput("districtListForm");
	$("#districtListForm").submit();
}

function addDistrict() {
	openIFrame(basePath + "/DistrictController/toDistrictDetail.do", "medium",
			__resourcecode__);
}

function modifyDistrict() {
	var districtId = getCheckedId();
	if (districtId) {
		openIFrame(basePath
				+ "/DistrictController/toDistrictDetail.do?districtId="
				+ districtId, "medium", __resourcecode__);
	} else {
		alert("请选择记录！");
	}
}

function deleteDistrict() {
	var districtId = getCheckedId();
	if (districtId) {
		if (confirm("是否确认删除？")) {
			var delLayer = layer.load("正在删除...");
			$.ajax({
				url : basePath + "/DistrictController/deleteDistrict.do",
				type : "post",
				data : {
					districtId : districtId
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
	submitWithParam("districtListForm", "districtList");
}

function getCheckedId() {
	return $("[name='_districtId']:checked").val();
}