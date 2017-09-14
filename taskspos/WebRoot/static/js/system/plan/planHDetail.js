$(function(){
	$("#save").on("click", saveOrUpdatePlanH);
	$("#cancel").on("click", closePage);

	// 表单校验
	$("#planHForm").validator({
		stopOnError: true,
		timely: true,
		focusCleanup: true,
		fields: {
			"#operateDesc": "specialchar",
			"#operateEmp": "specialchar",
			"#operateTime": "specialchar",
			"#operateType": "specialchar",
			"#planId": "specialchar",
			"#status": "specialchar"
		}
	});
});

/**
 * 保存或更新系统计划历史
 *
 * @version V1.0  2014-07-16 PM 06:25:57 Wed
 * @author ly(ly@chinazrbc.com)
 */
function saveOrUpdatePlanH() {
	submitWithValidator("planHForm", function(){
		$.ajax({
			url: basePath + "/PlanHController/saveOrUpdatePlanH.do?r=" + new Date().getTime(),
			type: "post",
			data: $("#planHForm").serialize(),
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
