$(function() {
	$("#cancel").on("click", closePage);
	$("#savePassword").on("click", savePassword);
	$("#employeeForm")
	.validator(
			{
				stopOnError : true,
				timely : true,
				focusCleanup : true,
				fields : {
					"#loginPassword" : "密码:required;length[6~20]",
					"#reloginPassword" : "确认密码:required;match[loginPassword]",
				}
			});
});

function savePassword() {
	submitWithValidator("employeeForm", function() {
		$.ajax({
			url : basePath + "/EmployeeController/savePassword.do",
			type : "post",
			data : $("#employeeForm").serialize(),
			dataType : "json",
			success : function(data) {
				if (data.status == 1) {
					alert(data.message);
					// parent.layer.close(parent.layer.getFrameIndex());
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
