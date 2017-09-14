$(function () {
	$("#save").on("click", saveOrUpdatePortlet);
	$("#cancel").on("click", closePage);

	// 表单校验
	$("#portletForm").validator({
		stopOnError : true,
		timely      : true,
		focusCleanup: true,
		fields      : {
			"#iconType"    : "required",
			"#portletCode" : "required",
			"#portletTitle": "required,specialchar",
			"#refreshTime" : "required,integer[+]",
			"#src"         : "required"
		}
	});
});

/**
 * 保存或更新系统门户信息组件
 *
 * @version V1.0  2014-07-24 PM 10:07:54 Thu
 * @author ly(ly@chinazrbc.com)
 */
function saveOrUpdatePortlet() {
	submitWithValidator("portletForm", function () {
		$.ajax({
			url     : basePath + "/PortletController/saveOrUpdatePortlet.do?r=" + new Date().getTime(),
			type    : "post",
			data    : $("#portletForm").serialize(),
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
