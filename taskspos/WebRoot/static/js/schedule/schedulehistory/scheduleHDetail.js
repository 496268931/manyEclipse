$(function(){
	$("#save").on("click", saveOrUpdateScheduleH);
	$("#cancel").on("click", closePage);

	// 表单校验
	$("#scheduleHForm").validator({
		stopOnError: true,
		timely: true,
		focusCleanup: true,
		fields: {
	        "#operateDesc": "specialchar",
	        "#operateEmp": "specialchar",
	        "#operateTime": "specialchar",
	        "#operateType": "specialchar",
	        "#scheduleId": "specialchar",
	        "#status": "specialchar"
		}
	});
});

/**
 * 保存或更新调度历史表
 *
 * @version V1.0  2014-05-09 下午 03:38:55 星期五
 * @author ms(ms@chinazrbc.com)
 */
function saveOrUpdateScheduleH() {
    submitWithValidator("scheduleHForm", function(){
		$.ajax({
			url: basePath + "/ScheduleHController/saveOrUpdateScheduleH.do",
			type: "post",
			data: $("#scheduleHForm").serialize(),
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
