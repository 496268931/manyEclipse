$(function () {
	$("#save").on("click", saveOrUpdateAnnouncement);
	$("#cancel").on("click", closePage);

	// 表单校验
	$("#announcementForm").validator({
		stopOnError : true,
		timely      : true,
		focusCleanup: true,
		fields      : {

			"#announcementContent"      : "通知内容:required;",
			"#announcementTitle"        : "specialchar;通知标题:required;length[~100]",
			"#announcementRecipientName": {
				rule  : "收件人:required;",
				target: "#errorForAnnouncementRecipientName"
			},
			"#announcementSenderName"   : {
				rule  : "发件人:required;",
				target: "#errorForAnnouncementSenderName"
			},
			"#announcementType"         : "specialchar;通知类型:required;",

		}
	});

	status();
	type();
});

/**
 * 保存或更新系统通知
 *
 * @version V1.0  2014-07-04 下午 02:02:50 星期五
 * @author ly(ly@chinazrbc.com)
 */
function saveOrUpdateAnnouncement() {
	$('#announcementForm :input').removeAttr('disabled');
	$('#announcementForm :input').each(function (i, c) {
		$(c).val() == '' ? $(c).attr('disabled', 'disabled') : null;
	});
	submitWithValidator("announcementForm", function () {
		$.ajax({
			url     : basePath + "/AnnouncementController/saveOrUpdateAnnouncement.do?r=" + new Date().getTime(),
			type    : "post",
			data    : $("#announcementForm").serialize(),
			dataType: "json",
			success : function (data) {
				if (data.status == 1) {
					alert(data.message);
					//发送成功后 1、禁止所有的输入框
					$('#announcementForm :input').attr('disabled','disabled');
					//转向已发送标签
					parent.activeDiv(3);
				} else {
					alert(data.message);
				}
			}
		});
	});
}

/**
 * 关闭遮罩层
 */
function closePage() {
	parent.layer.close(parent.layer.getFrameIndex());
}


/**
 * 选择员工
 */
function chooseAnnouncementSender() {
	window.open(basePath + "/EmployeeController/getAllEmployee.do?isChoose=true&callbackFn=chooseAnnouncementSenderCallback&flag=true");
}

function chooseAnnouncementRecipient() {
	window.open(basePath + "/EmployeeController/getAllEmployee.do?isChoose=true&callbackFn=chooseAnnouncementRecipientCallback&flag=true");
}

/**
 * 选择回调函数
 * @param memberId
 */
function chooseAnnouncementSenderCallback(id) {
	var employee = getEmployeeById(id);
	$("#announcementSender").val(id);
	$("#announcementSenderName").val(employee["realName"]);
}
function chooseAnnouncementRecipientCallback(id) {
	var employee = getEmployeeById(id);
	$("#announcementRecipient").val(id);
	$("#announcementRecipientName").val(employee["realName"]);
}

function status() {
	var status = $('#status').val();
//	alert(status);
	if (status == ANNOUNCEMENTSTATUSENUM_UNREAD_VALUE) {
		//alert(ANNOUNCEMENTSTATUSENUM_UNREAD_NAME);
	} else if (status == ANNOUNCEMENTSTATUSENUM_FORWARD_VALUE) {
		//alert(ANNOUNCEMENTSTATUSENUM_FORWARD_NAME);
		$("#announcementSenderName").attr('disabled', 'disabled');
		$("#chooseAnnouncementSender").hide();
	} else if (status == ANNOUNCEMENTSTATUSENUM_REPLY_VALUE) {
		$("#announcementSenderName").attr('disabled', 'disabled');
		$("#chooseAnnouncementSender").hide();

		$("#announcementRecipientName").attr('disabled', 'disabled');
		$("#chooseAnnouncementRecipient").hide();
		//alert(ANNOUNCEMENTSTATUSENUM_REPLY_NAME);
	}
}

function type() {
	var type = $('#type').val();
	if (type && type == 'read') {
		$(':input').attr('disabled', 'disabled');
		$('#save').hide();
	}
}