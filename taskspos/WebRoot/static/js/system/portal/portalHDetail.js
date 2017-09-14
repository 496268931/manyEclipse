$(function () {
	$("#save").on("click", saveOrUpdatePortalH);
	$("#cancel").on("click", closePage);

	// 表单校验
	$("#portalHForm").validator({
		stopOnError : true,
		timely      : true,
		focusCleanup: true,
		fields      : {
			"#operateDesc": "specialchar",
			"#operateEmp" : "specialchar",
			"#operateTime": "specialchar",
			"#operateType": "specialchar",
			"#portalId"   : "specialchar",
			"#status"     : "specialchar"
		}
	});
});

/**
 * 保存或更新系统门户历史
 *
 * @version V1.0  2014-07-24 PM 09:51:05 Thu
 * @author ly(ly@chinazrbc.com)
 */
function saveOrUpdatePortalH() {
	submitWithValidator("portalHForm", function () {
		$.ajax({
			url     : basePath + "/PortalHController/saveOrUpdatePortalH.do?r=" + new Date().getTime(),
			type    : "post",
			data    : $("#portalHForm").serialize(),
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
