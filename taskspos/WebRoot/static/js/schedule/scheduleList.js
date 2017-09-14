$(function() {
	$("#add").on("click", addSchedule);
	$("#modify").on("click", modifySchedule);
	$("#delete").on("click", deleteSchedule);
	$("#history").on("click", searchHistory);
});
/**
 * 查询调度历史记录
 */
function searchHistory() {
	var scheduleId = getCheckedId();
	if (scheduleId) {
		openIFrame(basePath
				+ "/ScheduleHController/searchScheduleHList.do?scheduleId="
				+ scheduleId, null, __resourcecode__);
	} else {
		layer.alert("请选择记录！", 8);
	}
}

/**
 * 新增系统调度注册表
 * 
 * @version V1.0 2014-05-09 下午 02:50:45 星期五
 * @author ms(ms@chinazrbc.com)
 */
function addSchedule() {
	openIFrame(basePath + "/ScheduleController/toScheduleDetail.do", null,
			__resourcecode__);
}

/**
 * 修改系统调度注册表
 * 
 * @version V1.0 2014-05-09 下午 02:50:45 星期五
 * @author ms(ms@chinazrbc.com)
 */
function modifySchedule() {
	var scheduleId = getCheckedId();
	if (scheduleId) {
		openIFrame(basePath + "/ScheduleController/toScheduleDetail.do?id="
				+ scheduleId, null, __resourcecode__);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 删除系统调度注册表
 * 
 * @version V1.0 2014-05-09 下午 02:50:45 星期五
 * @author ms(ms@chinazrbc.com)
 */
function deleteSchedule() {
	var id = getCheckedId();
	if (id) {
		if (confirm("是否确认删除？")) {
			var delLayer = layer.load("正在删除...");
			$.ajax({
				url : basePath + "/ScheduleController/deleteSchedule.do",
				type : "post",
				data : {
					id : id
				},
				dataType : "json",
				success : function(data) {
					if (data.status == 1) {
						refresh();
						layer.close(delLayer);
						alert(data.message);
					} else {
						alert(data.message);
					}
				}
			});
		}
	} else {
		alert("请选择记录！");
	}
}

/**
 * 刷新列表
 * 
 * @version V1.0 2014-05-09 下午 02:50:45 星期五
 * @author ms(ms@chinazrbc.com)
 */
function refresh() {
	submitWithParam("scheduleListForm", "scheduleList");
}
