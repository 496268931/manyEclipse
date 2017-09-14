$(function () {
	$("#save").on("click", saveOrUpdatePortletPosition);
	$("#cancel").on("click", closePage);

	// 表单校验
	$("#portletPositionForm").validator({
		stopOnError : true,
		timely      : true,
		focusCleanup: true,
		fields      : {
			"#columnIndex": "specialchar",
			"#portalId"   : "specialchar",
			"#portletId"  : "specialchar",
			"#rowIndex"   : "specialchar"
		}
	});
});

/**
 * 保存或更新系统门户信息组件位置
 *
 * @version V1.0  2014-07-24 PM 10:22:28 Thu
 * @author ly(ly@chinazrbc.com)
 */
function saveOrUpdatePortletPosition() {
	submitWithValidator("portletPositionForm", function () {
		$.ajax({
			url     : basePath + "/PortletPositionController/saveOrUpdatePortletPosition.do?r=" + new Date().getTime(),
			type    : "post",
			data    : $("#portletPositionForm").serialize(),
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
