$(function () {
	$("#add").on("click", addAnnouncementH);
	$("#modify").on("click", modifyAnnouncementH);
	$("#delete").on("click", deleteAnnouncementH);
	$("#history").on("click", showHistory);
	$("#view").on("click", viewAnnouncementH);
	$("#search").on("click", search);
});

function search() {
	trimTextInput("announcementHListForm");
	$("#announcementHListForm").submit();
}
/**
 * 新增系统通知历史
 *
 * @version V1.0  2014-07-06 AM 02:29:34 Sun
 * @author ly(ly@chinazrbc.com)
 */
function addAnnouncementH() {
	openCustomIFrame(basePath + "/AnnouncementHController/toAnnouncementHDetail.do");
}

/**
 * 修改系统通知历史
 *
 * @version V1.0  2014-07-06 AM 02:29:34 Sun
 * @author ly(ly@chinazrbc.com)
 */
function modifyAnnouncementH() {
	var id = getCheckedId();
	if (id) {
		openCustomIFrame(basePath + "/AnnouncementHController/toAnnouncementHDetail.do?id=" + id);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 查看客户
 */
function viewAnnouncementH() {
	var id = getCheckedId();
	if (id) {
		openCustomIFrame(basePath + "/AnnouncementHController/toAnnouncementHDetail.do?id=" + id + "&isView=true");
	} else {
		alert("请选择记录！");
	}
}

/**
 * 删除系统通知历史
 *
 * @version V1.0  2014-07-06 AM 02:29:34 Sun
 * @author ly(ly@chinazrbc.com)
 */
function deleteAnnouncementH() {
	var id = getCheckedId();
	var now = new Date().getTime();
	if (id) {
		if (confirm("是否确认删除？")) {
			var delLayer = layer.load("正在删除...");
			$.ajax({
				url     : basePath + "/AnnouncementHController/deleteAnnouncementH.do?r=" + now,
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
		window.open(basePath + "/AnnouncementHHController/searchAnnouncementHHList.do?announcementHId=" + id);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 刷新列表
 *
 * @version V1.0  2014-07-06 AM 02:29:34 Sun
 * @author ly(ly@chinazrbc.com)
 */
function refresh() {
	submitWithParam("announcementHListForm", "announcementHList");
}

function openCustomIFrame(src) {
	var area = {width: "1000px"};
	openIFrame(src, area);
}
/**
 * 获取选中id
 *
 * @version V1.0  2014-07-06 AM 02:29:34 Sun
 * @author ly(ly@chinazrbc.com)
 */
function getCheckedId() {
	return $("[name='_id']:checked").val();
}

