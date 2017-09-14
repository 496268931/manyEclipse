$(function () {
	$("#save").on("click", saveOrUpdatePortletH);
	$("#cancel").on("click", closePage);

	// 表单校验
	$("#portletHForm").validator({
		stopOnError : true,
		timely      : true,
		focusCleanup: true,
		fields      : {
			"#operateDesc": "specialchar",
			"#operateEmp" : "specialchar",
			"#operateTime": "specialchar",
			"#operateType": "specialchar",
			"#portletId"  : "specialchar",
			"#status"     : "specialchar"
		}
	});
});

/**
 * 保存或更新系统门户信息组件历史
 *
 * @version V1.0  2014-07-24 PM 10:16:29 Thu
 * @author ly(ly@chinazrbc.com)
 */
function saveOrUpdatePortletH() {
	submitWithValidator("portletHForm", function () {
		$.ajax({
			url     : basePath + "/PortletHController/saveOrUpdatePortletH.do?r=" + new Date().getTime(),
			type    : "post",
			data    : $("#portletHForm").serialize(),
			dataType: "json",
			success : function (data) {
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
