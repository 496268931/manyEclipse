$(function() {
	$("#add").on("click", addScheduleH);
	$("#modify").on("click", modifyScheduleH);
	$("#delete").on("click", deleteScheduleH);
});

/**
 * 新增调度历史表
 * 
 * @version V1.0 2014-05-09 下午 03:38:55 星期五
 * @author ms(ms@chinazrbc.com)
 */
function addScheduleH() {
	openIFrame(basePath + "/ScheduleHController/toScheduleHDetail.do", null,
			__resourcecode__);
}

/**
 * 修改调度历史表
 * 
 * @version V1.0 2014-05-09 下午 03:38:55 星期五
 * @author ms(ms@chinazrbc.com)
 */
function modifyScheduleH() {
	var id = getCheckedId();
	if (scheduleHVO) {
		openIFrame(basePath + "/ScheduleHController/toScheduleHDetail.do?id="
				+ id, null, __resourcecode__);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 删除调度历史表
 * 
 * @version V1.0 2014-05-09 下午 03:38:55 星期五
 * @author ms(ms@chinazrbc.com)
 */
function deleteScheduleH() {
	var id = getCheckedId();
	if (id) {
		if (confirm("是否确认删除？")) {
			var delLayer = layer.load("正在删除...");
			$.ajax({
				url : basePath + "/ScheduleHController/deleteScheduleH.do",
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
 * @version V1.0 2014-05-09 下午 03:38:55 星期五
 * @author ms(ms@chinazrbc.com)
 */
function refresh() {
	submitWithParam("scheduleHListForm", "scheduleHList");
}

/**
 * 获取选中id
 * 
 * @version V1.0 2014-05-09 下午 03:38:55 星期五
 * @author ms(ms@chinazrbc.com)
 */
function getCheckedId() {
	return $("[name='_id']:checked").val();
}