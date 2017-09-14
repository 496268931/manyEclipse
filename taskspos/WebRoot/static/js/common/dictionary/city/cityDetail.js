$(function(){
	$("#save").on("click", saveOrUpdateCity);
	$("#cancel").on("click", closePage);

	$("#cityForm").validator({
		stopOnError: true,
		timely: true,
		focusCleanup: true,
		fields: {
			"#cityName": "城市名称:required;specialchar;length[~20];remote[" + basePath + "/CityController/checkCity.do, id]",
			"#cityCode": "城市编码:required;digits;length[~20];remote[" + basePath + "/CityController/checkCity.do, id]",
			"#description": "描述:specialchar;length[~200]"
		}
	});
});

/**
 * 保存或更新城市(二级城市)
 *
 * @version V1.0  2014-05-07 上午 11:56:57 星期三
 * @author ff(ff@chinazrbc.com)
 */
function saveOrUpdateCity() {
	submitWithValidator("cityForm", function(){
		$.ajax({
			url: basePath + "/CityController/saveOrUpdateCity.do",
			type: "post",
			data: $("#cityForm").serialize(),
			dataType: "json",
			success: function(data) {
				if (data.status == 1) {
					alert(data.message);
					parent.refresh();
				} else {
					alert(data.message);
				}
			},
			error: function() {
				alert("error");
			}
		});
	});
}

/**
 * 关闭遮罩层
 */
function closePage() {
	parent.layer.close(parent.layer.getFrameIndex());
}