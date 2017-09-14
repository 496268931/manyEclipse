$(function () {
	$("#add").on("click", addPortletPosition);
	$("#modify").on("click", modifyPortletPosition);
	$("#delete").on("click", deletePortletPosition);
	$("#history").on("click", showHistory);
	$("#view").on("click", viewPortletPosition);
	$("#search").on("click", search);
});

function search() {
	trimTextInput("portletPositionListForm");
	$("#portletPositionListForm").submit();
}
/**
 * 新增系统门户信息组件位置
 *
 * @version V1.0  2014-07-24 PM 10:22:28 Thu
 * @author ly(ly@chinazrbc.com)
 */
function addPortletPosition() {
	openCustomIFrame(basePath + "/PortletPositionController/toPortletPositionDetail.do");
}

/**
 * 修改系统门户信息组件位置
 *
 * @version V1.0  2014-07-24 PM 10:22:28 Thu
 * @author ly(ly@chinazrbc.com)
 */
function modifyPortletPosition() {
	var id = getCheckedId();
	if (id) {
		openCustomIFrame(basePath + "/PortletPositionController/toPortletPositionDetail.do?id=" + id);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 查看客户
 */
function viewPortletPosition() {
	var id = getCheckedId();
	if (id) {
		openCustomIFrame(basePath + "/PortletPositionController/toPortletPositionDetail.do?id=" + id + "&isView=true");
	} else {
		alert("请选择记录！");
	}
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

function showHistory() {
	var id = getCheckedId();
	if (id) {
		window.open(basePath + "/PortletPositionHController/searchPortletPositionHList.do?portletPositionId=" + id);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 刷新列表
 *
 * @version V1.0  2014-07-24 PM 10:22:28 Thu
 * @author ly(ly@chinazrbc.com)
 */
function refresh() {
	submitWithParam("portletPositionListForm", "portletPositionList");
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


