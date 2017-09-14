$(function () {
	initTab();
	startTime();
});

/**
 * 初始化tab
 */
function initTab() {
	$("[name='subSystem'][index='0']").addClass("current");
}

/**
 * 获取资源树
 *
 * @param subsystemid
 */
function getResourceTree(subsystemid) {
	parent.document.getElementById("menu").src = CONTEXT
		+ "/LoginController/getResourcePage.do?subSystemId=" + subsystemid;
	parent.document.getElementById("center").src = CONTEXT
		+ "/jsp/login/welcome.jsp";
	setTabStyle(subsystemid);
}
/**
 * 设置tab样式
 *
 * @param subsystemid
 */
function setTabStyle(subsystemid) {
	$("[name='subSystem']").removeClass("current");
	$("#subSystem_" + subsystemid).addClass("current");
}

/**
 * 短消息
 *
 * @param userId
 */
function announcement(userId) {
	window.parent.parent.announcement(userId);
}

/**
 * 短消息
 *
 * @param userId
 */
function help(userId) {
	window.parent.parent.help(userId);
}

/**
 * 日历
 *
 * @param userId
 */
function calendar(userId) {
	window.parent.parent.calendar(userId);
}

/**
 * 个人信息
 *
 * @param userId
 */
function personal(userId) {
	window.parent.parent.personal(userId);
}
