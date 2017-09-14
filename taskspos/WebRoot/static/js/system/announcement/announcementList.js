$(function () {
	$("#add").on("click", addAnnouncement);
	$("#modify").on("click", modifyAnnouncement);
	$("#delete").on("click", deleteAnnouncement);
	$("#forward").on("click", forward);
	$("#reply").on("click", reply);
	$("#read").on("click", read);
	$("#history").on("click", showHistory);
	$("#view").on("click", viewAnnouncement);
	$("#search").on("click", search);
	$("#announcement").on("click", announcement);
});

function search() {
	trimTextInput("announcementListForm");
	$("#announcementListForm").submit();
}
/**
 * 新增系统通知
 *
 * @version V1.0  2014-07-04 下午 02:02:50 星期五
 * @author ly(ly@chinazrbc.com)
 */
function addAnnouncement() {
	openCustomIFrame(basePath + "/AnnouncementController/toAnnouncementDetail.do");
}

/**
 * 修改系统通知
 *
 * @version V1.0  2014-07-04 下午 02:02:50 星期五
 * @author ly(ly@chinazrbc.com)
 */
function modifyAnnouncement() {
	var id = getCheckedId();
	if (id) {
		openCustomIFrame(basePath + "/AnnouncementController/toAnnouncementDetail.do?id=" + id);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 查看通知
 */
function viewAnnouncement() {
	var id = getCheckedId();
	if (id) {
		openCustomIFrame(basePath + "/AnnouncementController/toAnnouncementDetail.do?id=" + id + "&type=view");
	} else {
		alert("请选择记录！");
	}
}


/**
 * 查看通知
 * @param id
 */
function viewAnnouncementById(id) {
	if (id) {
		openCustomIFrame(basePath + "/AnnouncementController/toAnnouncementDetail.do?id=" + id + "&type=view");
	} else {
		alert("请选择记录！");
	}
}


/**
 * 删除系统通知
 *
 * @version V1.0  2014-07-04 下午 02:02:50 星期五
 * @author ly(ly@chinazrbc.com)
 */
function deleteAnnouncement() {
	var id = getCheckedId();
	var now = new Date().getTime();
	if (id) {
		if (confirm("是否确认删除？")) {
			var delLayer = layer.load("正在删除...");
			$.ajax({
				url     : basePath + "/AnnouncementController/deleteAnnouncement.do?r=" + now,
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
		window.open(basePath + "/AnnouncementHController/searchAnnouncementHList.do?announcementId=" + id);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 刷新列表
 *
 * @version V1.0  2014-07-04 下午 02:02:50 星期五
 * @author ly(ly@chinazrbc.com)
 */
function refresh() {
	submitWithParam("announcementListForm", "announcementList");
}

function openCustomIFrame(src) {
	var area = {width: "1000px"};
	openIFrame(src, area);
}
/**
 * 获取选中id
 *
 * @version V1.0  2014-07-04 下午 02:02:50 星期五
 * @author ly(ly@chinazrbc.com)
 */
function getCheckedId() {
	return $("[name='_id']:checked").val();
}

/**
 * 转发
 */
function forward() {
	var id = getCheckedId();
	if (id) {
		var src = basePath + "/AnnouncementController/toAnnouncementDetail.do?id=" + id + "&type=forward";
		openCustomIFrame(src);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 回复
 */
function reply() {
	var id = getCheckedId();
	if (id) {
		var src = basePath + "/AnnouncementController/toAnnouncementDetail.do?id=" + id + "&type=reply";
		openCustomIFrame(src);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 阅读
 */
function read() {
	var id = getCheckedId();
	if (id) {
		readAnnouncement(id);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 阅读通知
 * @param id
 */
function readAnnouncement(id) {
	var now = new Date().getTime();
	$.ajax({
		url     : basePath + "/AnnouncementController/readAnnouncement.do?r=" + now,
		type    : "post",
		data    : {id: id},
		dataType: "json",
		success : function (data) {
			if (data.status == 1) {
				var src = basePath + "/AnnouncementController/toAnnouncementDetail.do?id=" + id + "&type=read";
				openCustomIFrame(src);
			} else {
				alert(data.message);
			}
		}
	});
}

function announcement() {
	var now = new Date().getTime();
	var src = basePath + "/AnnouncementController/toUsetAnnouncement.do?r=" + now;
	openCustomIFrame(src);
}