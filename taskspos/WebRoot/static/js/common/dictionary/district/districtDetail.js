$(function(){
	$("#save").on("click", saveDistrict);
	$("#cancel").on("click", closePage);

	$("#districtForm").validator({
		stopOnError: true,
		timely: true,
		focusCleanup: true,
		fields: {
			"#districtName": "行政区域名称:required;specialchar;remote[" + basePath + "/DistrictController/checkDistrict.do, districtId]",
			"#districtCode": "行政区域编码:required;digits;specialchar;remote[" + basePath + "/DistrictController/checkDistrict.do, districtId]",
			"#description": "描述:specialchar;length[~50]"
		}
	});
});

function saveDistrict() {
	submitWithValidator("districtForm", function(){
		$.ajax({
			url: basePath + "/DistrictController/saveDistrict.do",
			type: "post",
			data: $("#districtForm").serialize(),
			dataType: "json",
			success: function(data) {
				if (data.status == 1) {
					alert(data.message);
					parent.refresh();
				} else {
					alert(data.message);
				}
			}
		});
	});
}

function closePage() {
	parent.layer.close(parent.layer.getFrameIndex());
}