$(function(){
	$("#save").on("click", saveBank);
	$("#cancel").on("click", closePage);

	$("#bankForm").validator({
		stopOnError: true,
		timely: true,
		focusCleanup: true,
		fields: {
			"#bankName": "银行名称:required;specialchar;remote[" + basePath + "/BankController/checkRepeat.do, bankId]",
			"#bankShortName": "银行简称:required;specialchar;remote[" + basePath + "/BankController/checkRepeat.do, bankId]",
			"#bankCode": "银行代码:digits;specialchar;remote[" + basePath + "/BankController/checkRepeat.do, bankId]",
			"#bankDesc": "描述:specialchar"
		}
	});
});

function saveBank() {
	submitWithValidator("bankForm", function(){
		$.ajax({
			url: basePath + "/BankController/saveBank.do",
			type: "post",
			data: $("#bankForm").serialize(),
			dataType: "json",
			success: function(data) {
				if (data.status == 1) {
					alert(data.message);
					parent.refresh();
				} else {
					alert(data.message);
				}
			}
		});
	});
}

function closePage() {
	parent.layer.close(parent.layer.getFrameIndex());
}