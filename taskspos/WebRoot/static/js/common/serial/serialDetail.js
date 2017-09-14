$(function(){
	$("#save").on("click", saveOrUpdateSerial);
	$("#cancel").on("click", closePage);
	$("#serialForm").validator({
		stopOnError: true,
		timely: true,
		focusCleanup: true,
		fields: {
			"#isEverydayClear": "specialchar",
			"#serialCount": "specialchar",
			"#serialDesc": "specialchar",
			"#serialType": "specialchar",
			"#status": "specialchar"
		}
	});
});

/**
 * 保存或更新序列号表
 *
 * @version V1.0  2014-05-08 下午 07:54:07 星期四
 * @author ms(ms@chinazrbc.com)
 */
function saveOrUpdateSerial() {
    submitWithValidator("serialForm", function(){
		$.ajax({
			url: basePath + "/SerialController/saveOrUpdateSerial.do",
			type: "post",
			data: $("#serialForm").serialize(),
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

/**
 * 关闭遮罩层
 */
function closePage() {
	parent.layer.close(parent.layer.getFrameIndex());
}
