$(function() {
	$("#reset").on("click", reset);
	$("#savePassword").on("click", savePassword);

	$("#employeeOwnForm").validator(
			{
				stopOnError : true,
				timely : true,
				focusCleanup : true,
				fields : {
					"#oldloginPassword" : "旧密码:required;length[1~20];remote[" + basePath
							+ "/EmployeeController/checkEmployeePassword.do]",
					"#loginPassword":"新密码:required;length[6~20]",
					"#reloginPassword":"确认密码:required;match[loginPassword]",
				}
			});
});

function reset(){
	$(':input','#employeeOwnForm')
	 .not(':button, :submit,:radio, :hidden')
	 .val('')
}


function savePassword(){
	submitWithValidator("employeeOwnForm", function() {
	$.ajax({
		url : basePath + "/EmployeeController/savePassword.do",
		type : "post",
		data : $("#employeeOwnForm").serialize(),
		dataType : "json",
		success : function(data) {
			if (data.status == 1) {
				alert(data.message);
				location.reload();
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







