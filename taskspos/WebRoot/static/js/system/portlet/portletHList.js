$(function(){
	$("#add").on("click", addPortletH);
	$("#modify").on("click", modifyPortletH);
	$("#delete").on("click", deletePortletH);
	$("#history").on("click", showHistory);
	$("#view").on("click", viewPortletH);
	$("#search").on("click", search);
});

function search() {
	trimTextInput("portletHListForm");
	$("#portletHListForm").submit();
}
/**
 * 新增系统门户信息组件历史
 *
 * @version V1.0  2014-07-24 PM 10:16:29 Thu
 * @author ly(ly@chinazrbc.com)
 */
function addPortletH() {
	openCustomIFrame(basePath + "/PortletHController/toPortletHDetail.do");
}

/**
 * 修改系统门户信息组件历史
 *
 * @version V1.0  2014-07-24 PM 10:16:29 Thu
 * @author ly(ly@chinazrbc.com)
 */
function modifyPortletH() {
	var id = getCheckedId();
	if (id) {
		openCustomIFrame(basePath + "/PortletHController/toPortletHDetail.do?id=" + id);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 查看客户
 */
function viewPortletH() {
	var id = getCheckedId();
	if (id) {
		openCustomIFrame(basePath + "/PortletHController/toPortletHDetail.do?id=" + id + "&isView=true");
	} else {
		alert("请选择记录！");
	}
}

/**
 * 删除系统门户信息组件历史
 *
 * @version V1.0  2014-07-24 PM 10:16:29 Thu
 * @author ly(ly@chinazrbc.com)
 */
function deletePortletH() {
	var id = getCheckedId();
	var now = new Date().getTime();
	if (id) {
		if (confirm("是否确认删除？")) {
			var delLayer = layer.load("正在删除...");
			$.ajax({
				url: basePath + "/PortletHController/deletePortletH.do?r=" + now,
				type: "post",
				data: {id: id},
				dataType: "json",
				success: function(data) {
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
		window.open(basePath + "/PortletHHController/searchPortletHHList.do?portletHId=" + id);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 刷新列表
 *
 * @version V1.0  2014-07-24 PM 10:16:29 Thu
 * @author ly(ly@chinazrbc.com)
 */
function refresh() {
	submitWithParam("portletHListForm", "portletHList");
}

function openCustomIFrame(src) {
	var area = {width: "1000px"};
	openIFrame(src, area);
}
/**
 * 获取选中id
 *
 * @version V1.0  2014-07-24 PM 10:16:29 Thu
 * @author ly(ly@chinazrbc.com)
 */
function getCheckedId() {
	return $("[name='_id']:checked").val();
}

/**
 * 根据ID查看详情
 * @param id
 */
function viewPortletById(id) {
	openCustomIFrame(basePath + "/PortletController/toPortletDetail.do?id=" + id + "&isView=true");
}