$(function () {
	$("#add").on("click", addPortletPosition);
	$("#delete").on("click", deletePortletPosition);
	$("#prev").on("click", prevPortletPosition);
	$("#next").on("click", nextPortletPosition);
});

/**
 * 前移一位
 */
function prevPortletPosition() {
	var id = getCheckedId();
	var portalId = $("#portalId").val();
	var now = new Date().getTime();
	if (id) {
		if (confirm("是否确认前移？")) {
			var delLayer = layer.load("正在删除...");
			$.ajax({
				url     : basePath + "/PortletPositionController/prevPortletPosition.do?r=" + now,
				type    : "post",
				data    : {id: id, portalId: portalId},
				dataType: "json",
				success : function (data) {
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
 * 后退一位
 */
function nextPortletPosition() {
	var id = getCheckedId();
	var portalId = $("#portalId").val();
	var now = new Date().getTime();
	if (id) {
		if (confirm("是否确认后移？")) {
			var delLayer = layer.load("正在删除...");
			$.ajax({
				url     : basePath + "/PortletPositionController/nextPortletPosition.do?r=" + now,
				type    : "post",
				data    : {id: id, portalId: portalId},
				dataType: "json",
				success : function (data) {
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


function search() {
	trimTextInput("portletPositionListForm");
	$("#portletPositionListForm").submit();
}

/**
 * 选择组件
 *
 * @version V1.0  2014-07-24 PM 10:22:28 Thu
 * @author ly(ly@chinazrbc.com)
 */
function addPortletPosition() {
	var portalId = $('#portalId').val();
	openCustomIFrame(basePath + "/PortalController/toConfiguration.do?portalId=" + portalId);
}

/**
 * 删除系统门户信息组件位置
 *
 * @version V1.0  2014-07-24 PM 10:22:28 Thu
 * @author ly(ly@chinazrbc.com)
 */
function deletePortletPosition() {
	var id = getCheckedId();
	var now = new Date().getTime();
	if (id) {
		if (confirm("是否确认删除？")) {
			var delLayer = layer.load("正在删除...");
			$.ajax({
				url     : basePath + "/PortletPositionController/deletePortletPosition.do?r=" + now,
				type    : "post",
				data    : {id: id},
				dataType: "json",
				success : function (data) {
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

function openCustomIFrame(src) {
	var area = {width: "1000px"};
	openIFrame(src, area);
}
/**
 * 获取选中id
 *
 * @version V1.0  2014-07-24 PM 10:22:28 Thu
 * @author ly(ly@chinazrbc.com)
 */
function getCheckedId() {
	return $("[name='_id']:checked").val();
}

/**
 * 刷新列表
 *
 * @version V1.0  2014-07-24 PM 09:44:47 Thu
 * @author ly(ly@chinazrbc.com)
 */
function refresh() {
	submitWithParam("portletPositionListForm", "portletPositionList");
}
