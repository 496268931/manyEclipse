$(document).ready(
	function () {
		$("#deleteAnnouncement").on("click", deleteAnnouncement);
		$("#readAnnouncement").on("click", readAnnouncement);
	}
);


/**
 * 刷新IFRAME
 * @param iframeId
 * @param url
 */
function refreshIFrame(iframeId, url) {
	$("#" + iframeId).attr("src", url);
}

/**
 * 框架刷新
 * @param index
 */
function iframeOnRefresh(index) {
	var iframeIndex = $('#' + index + '_content').find('iframe');
	var src = iframeIndex.attr('src');
	var iframeId = index + '_iframe';
	refreshIFrame(iframeId, src);
}

/**
 * 激活标签栏
 * @param index
 */
function activeDiv(index) {
	showDiv(index);
	iframeOnRefresh(index);
}


function deleteAnnouncement() {
	var ids = getCheckedValue();
	if (ids) {
		deleteAnnouncements(ids);
	} else {
		alert("请选择删除对象");
	}
}

function readAnnouncement() {
	var ids = getCheckedValue();
	if (ids) {
		readAnnouncements(ids);
	} else {
		alert("请选择标记已读对象");
	}
}


/**
 * 删除通知
 * @param id
 */
function deleteAnnouncements(ids) {
	var now = new Date().getTime();
	$.ajax({
		url     : basePath + "/AnnouncementController/deleteAnnouncements.do?r=" + now,
		type    : "post",
		data    : {ids: ids},
		dataType: "json",
		success : function (data) {
			if (data.status == 1) {
				alert('删除成功');
				window.parent.iframeOnRefresh(1);
				window.parent.iframeOnRefresh(3);
			} else {
				alert(data.message);
			}
		}
	});
}

/**
 * 阅读通知
 * @param id
 */
function readAnnouncements(ids) {
	var now = new Date().getTime();
	$.ajax({
		url     : basePath + "/AnnouncementController/readAnnouncements.do?r=" + now,
		type    : "post",
		data    : {ids: ids},
		dataType: "json",
		success : function (data) {
			if (data.status == 1) {
				alert('标记阅读成功');
				window.parent.iframeOnRefresh(1);
				window.parent.iframeOnRefresh(3);
			} else {
				alert(data.message);
			}
		}
	});
}

/**
 * 获取选中的值
 * @returns {*}
 */
function getCheckedValue() {
	var iframeId = getActiveIframe();
	var ids = $('#' + iframeId)[0].contentWindow.getAnnouncementIds();
	if (ids) {
		return ids;
	}
	return null;
}

/**
 * 获取激活的IFRMAE的ID
 * @returns {*}
 */
function getActiveIframe() {
	var activeArch = $('.main_nav').find('.active');
	var id = activeArch.attr('id');
	var index = null;
	if (id && id.lastIndexOf('_info') > 0) {
		index = id.substring(0, id.lastIndexOf('_info'));
		return index + '_iframe';
	}
	return index;
}
