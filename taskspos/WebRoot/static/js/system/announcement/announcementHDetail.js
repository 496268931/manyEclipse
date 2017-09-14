$(function(){
	$("#save").on("click", saveOrUpdateAnnouncementH);
	$("#cancel").on("click", closePage);

	// 表单校验
	$("#announcementHForm").validator({
		stopOnError: true,
		timely: true,
		focusCleanup: true,
		fields: {
	        "#announcementId": "specialchar",
	        "#operateDesc": "specialchar",
	        "#operateEmp": "specialchar",
	        "#operateTime": "specialchar",
	        "#operateType": "specialchar",
	        "#status": "specialchar"
		}
	});
});

/**
 * 保存或更新系统通知历史
 *
 * @version V1.0  2014-07-06 AM 02:29:34 Sun
 * @author ly(ly@chinazrbc.com)
 */
function saveOrUpdateAnnouncementH() {
    submitWithValidator("announcementHForm", function(){
		$.ajax({
			url: basePath + "/AnnouncementHController/saveOrUpdateAnnouncementH.do?r=" + new Date().getTime(),
			type: "post",
			data: $("#announcementHForm").serialize(),
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
