$(function () {
	$("#add").on("click", addPortalH);
	$("#modify").on("click", modifyPortalH);
	$("#delete").on("click", deletePortalH);
	$("#history").on("click", showHistory);
	$("#view").on("click", viewPortalH);
	$("#search").on("click", search);
});

function search() {
	trimTextInput("portalHListForm");
	$("#portalHListForm").submit();
}
/**
 * 新增系统门户历史
 *
 * @version V1.0  2014-07-24 PM 09:51:05 Thu
 * @author ly(ly@chinazrbc.com)
 */
function addPortalH() {
	openCustomIFrame(basePath + "/PortalHController/toPortalHDetail.do");
}

/**
 * 修改系统门户历史
 *
 * @version V1.0  2014-07-24 PM 09:51:05 Thu
 * @author ly(ly@chinazrbc.com)
 */
function modifyPortalH() {
	var id = getCheckedId();
	if (id) {
		openCustomIFrame(basePath + "/PortalHController/toPortalHDetail.do?id=" + id);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 查看客户
 */
function viewPortalH() {
	var id = getCheckedId();
	if (id) {
		openCustomIFrame(basePath + "/PortalHController/toPortalHDetail.do?id=" + id + "&isView=true");
	} else {
		alert("请选择记录！");
	}
}

/**
 * 删除系统门户历史
 *
 * @version V1.0  2014-07-24 PM 09:51:05 Thu
 * @author ly(ly@chinazrbc.com)
 */
function deletePortalH() {
	var id = getCheckedId();
	var now = new Date().getTime();
	if (id) {
		if (confirm("是否确认删除？")) {
			var delLayer = layer.load("正在删除...");
			$.ajax({
				url     : basePath + "/PortalHController/deletePortalH.do?r=" + now,
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
		window.open(basePath + "/PortalHHController/searchPortalHHList.do?portalHId=" + id);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 刷新列表
 *
 * @version V1.0  2014-07-24 PM 09:51:05 Thu
 * @author ly(ly@chinazrbc.com)
 */
function refresh() {
	submitWithParam("portalHListForm", "portalHList");
}

function openCustomIFrame(src) {
	var area = {width: "1000px"};
	openIFrame(src, area);
}
/**
 * 获取选中id
 *
 * @version V1.0  2014-07-24 PM 09:51:05 Thu
 * @author ly(ly@chinazrbc.com)
 */
function getCheckedId() {
	return $("[name='_id']:checked").val();
}


/**
 * 查看客户
 * @param id
 */
function viewPortalById(id) {
	openCustomIFrame(basePath + "/PortalController/toPortalDetail.do?id=" + id + "&isView=true");
}