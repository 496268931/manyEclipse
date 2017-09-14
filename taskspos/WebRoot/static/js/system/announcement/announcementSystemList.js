function openCustomIFrame(src) {
	var area = {width: "1000px"};
	openIFrame(src, area);
}

/**
 * 转发
 */
function forward(id) {
	if (id) {
		var src = basePath + "/AnnouncementController/toAnnouncementSystemDetail.do?id=" + id + "&type=forward";
		//页面激活中间"写邮件"页面
		window.parent.showDiv(2);
		//设置中间iframe的src并刷新
		window.parent.refreshIFrame('2_iframe', src);
		alert(src);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 回复
 */
function reply(id) {
	if (id) {
		var src = basePath + "/AnnouncementController/toAnnouncementSystemDetail.do?id=" + id + "&type=reply";
		//页面激活中间"写邮件"页面
		window.parent.showDiv(2);
		//设置中间iframe的src并刷新
		window.parent.refreshIFrame('2_iframe', src);
		alert(src);
	} else {
		alert("请选择记录！");
	}
}


/**
 * 查看通知
 * @param id
 */
function viewAnnouncementContentById(id, content) {
	if (id) {
		layer.alert(content, 14);
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
				//阅读成功后刷新表格
				if ('toUserAsSenderAnnouncementList' == purpose) {
					window.parent.iframeOnRefresh(1);
				}
				if ('toUserAsRecipientAnnouncementList' == purpose) {
					window.parent.iframeOnRefresh(3);
				}
			} else {
				alert(data.message);
			}
		}
	});
}

function getAnnouncementIds() {
	var checks = checks || [];
	$("input[type=checkbox][name=_id]").each(function () {
		if ($(this).attr("checked") == "checked") {
			checks.push($(this).val());
		}
	});
	return  checks.join(',');
}