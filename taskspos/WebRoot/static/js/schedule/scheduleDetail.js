
$(function(){
	$("#save").on("click", saveOrUpdateSchedule);
	$("#cancel").on("click", closePage);

	// 表单校验
	$("#scheduleForm").validator({
		stopOnError: true,
		timely: true,
		focusCleanup: true,
		fields: {
	        "#jobMethodName": "specialchar",
	        "#jobParamValue": "specialchar",
	        "#scheduleDesc": "specialchar",
	        "#scheduleName": "specialchar",
	        "#status": "specialchar",
	        "#triggerName": "specialchar"
		}
	});
});

/**
 * 保存或更新系统调度注册表
 *
 * @version V1.0  2014-05-09 下午 02:50:45 星期五
 * @author ms(ms@chinazrbc.com)
 */
function saveOrUpdateSchedule() {
    submitWithValidator("scheduleForm", function(){
		$.ajax({
			url: basePath + "/ScheduleController/saveOrUpdateSchedule.do",
			type: "post",
			data: $("#scheduleForm").serialize(),
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
