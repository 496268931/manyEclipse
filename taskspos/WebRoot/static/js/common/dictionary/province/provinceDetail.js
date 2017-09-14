$(function(){
	$("#save").on("click", saveProvince);
	$("#cancel").on("click", closePage);

	$("#provinceForm").validator({
		stopOnError: true,
		timely: true,
		focusCleanup: true,
		fields: {
			"#provinceName": "省份名称:required;specialchar;length[~20];remote[" + basePath + "/ProvinceController/checkProvince.do, provinceId]",
			"#provinceShortName": "省份简称:required;specialchar;length[~20];remote[" + basePath + "/ProvinceController/checkProvince.do, provinceId]",
			"#provinceCode": "省份编码:required;digits;specialchar;length[~20]",
			"#description": "描述:specialchar;length[~200]"
		}
	});
});

function saveProvince() {
	submitWithValidator("provinceForm", function(){
        $("#save").hide();
        setTimeout(function () {
            $("#save").show();
        }, 10000);
		$.ajax({
			url: basePath + "/ProvinceController/saveProvince.do",
			type: "post",
			data: $("#provinceForm").serialize(),
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