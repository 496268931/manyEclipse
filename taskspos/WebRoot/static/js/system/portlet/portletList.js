$(function () {
	$("#add").on("click", addPortlet);
	$("#modify").on("click", modifyPortlet);
	$("#delete").on("click", deletePortlet);
	$("#history").on("click", showHistory);
	$("#view").on("click", viewPortlet);
	$("#search").on("click", search);
});

function search() {
	trimTextInput("portletListForm");
	$("#portletListForm").submit();
}
/**
 * 新增系统门户信息组件
 *
 * @version V1.0  2014-07-24 PM 10:07:54 Thu
 * @author ly(ly@chinazrbc.com)
 */
function addPortlet() {
	openCustomIFrame(basePath + "/PortletController/toPortletDetail.do");
}

/**
 * 修改系统门户信息组件
 *
 * @version V1.0  2014-07-24 PM 10:07:54 Thu
 * @author ly(ly@chinazrbc.com)
 */
function modifyPortlet() {
	var id = getCheckedId();
	if (id) {
		openCustomIFrame(basePath + "/PortletController/toPortletDetail.do?id=" + id);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 查看客户
 */
function viewPortlet() {
	var id = getCheckedId();
	if (id) {
		openCustomIFrame(basePath + "/PortletController/toPortletDetail.do?id=" + id + "&isView=true");
	} else {
		alert("请选择记录！");
	}
}

/**
 * 根据ID查看详情
 * @param id
 */
function viewPortletById(id) {
	openCustomIFrame(basePath + "/PortletController/toPortletDetail.do?id=" + id + "&isView=true");
}

/**
 * 删除系统门户信息组件
 *
 * @version V1.0  2014-07-24 PM 10:07:54 Thu
 * @author ly(ly@chinazrbc.com)
 */
function deletePortlet() {
	var id = getCheckedId();
	var now = new Date().getTime();
	if (id) {
		if (confirm("是否确认删除？")) {
			var delLayer = layer.load("正在删除...");
			$.ajax({
				url     : basePath + "/PortletController/deletePortlet.do?r=" + now,
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
		window.open(basePath + "/PortletHController/searchPortletHList.do?portletId=" + id);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 刷新列表
 *
 * @version V1.0  2014-07-24 PM 10:07:54 Thu
 * @author ly(ly@chinazrbc.com)
 */
function refresh() {
	submitWithParam("portletListForm", "portletList");
}

function openCustomIFrame(src) {
	var area = {width: "1000px"};
	openIFrame(src, area);
}
/**
 * 获取选中id
 *
 * @version V1.0  2014-07-24 PM 10:07:54 Thu
 * @author ly(ly@chinazrbc.com)
 */
function getCheckedId() {
	return $("[name='_id']:checked").val();
}
