$(function() {
	$("#add").on("click", addEmployee);
	$("#modify").on("click", modifyEmployee);
	$("#delete").on("click", deleteEmployee);
	$("#startup").on("click", startupEmployee);
	$("#view").on("click", view);
	$("#reset").on("click", reset);
	$("#viewHistory").on("click", viewHistory);
	$("#choose").on("click", handleCallbackFn);
	$("#clearQuery").on("click", clearAll);
	$("#query").on("click", queryAll);
	$("#cancel").on("click", closeWindow);
});

function closeWindow(){
	window.close();
}

function queryAll(){
	trimTextInput("employeeListForm");
	$("#employeeListForm").submit();
}

// 清空查询条件
function clearAll() {

	$(':input', '#employeeListForm').not(':button, :submit,:radio, :hidden')
			.val('').removeAttr('checked').removeAttr('selected');
	$("#departmentId").val('');

}

function addEmployee() {
	openIFrame(basePath + "/EmployeeController/employeeDetail.do",  null, __resourcecode__);
}

function modifyEmployee() {
	var id = $("[name='radio']:checked").val();
	if (id) {
		openIFrame(basePath + "/EmployeeController/employeeDetail.do?id=" + id,
				null, __resourcecode__);
	} else {
		alert("请选择员工再进行修改！");
		return false;
	}

}

function handleCallbackFn() {
	var id = $("[name='radio']:checked").val();
	if (id) {
		var callbackFn = $("#callbackFn").val();
		if (callbackFn) {
			opener[callbackFn].call(opener, id);
			window.close();
		} else {
			alert("回调失败，没有制定回调函数！");
		}
	} else {
		alert("请选择记录！");
	}
}

function deleteEmployee() {
	var id = $("[name='radio']:checked").val();
	var employeeId = $("#employeeId").val();
	if (id == employeeId) {
		alert("不可以停用当前登录用户！！！");
		return false;
	}
	if (id) {
		$.ajax({
			async:false,
			url : basePath + "/EmployeeController/employeeDelete.do?id=" + id,
			type : "post",
			dataType : "json",
			success : function(data) {
				if (data.status == 1) {
					refresh();
					alert(data.message);
				} else {
					alert(data.message);
				}
			}
		});
	} else {
		alert("请选择员工再进行停用！");
		return false;
	}

}
function startupEmployee() {
	var id = $("[name='radio']:checked").val();
	if (id) {
		$.ajax({
			url : basePath + "/EmployeeController/employeeStartup.do?id=" + id,
			type : "post",
			dataType : "json",
			success : function(data) {
				if (data.status == 1) {
					refresh();
					alert(data.message);
				} else {
					alert(data.message);
				}
			}
		});
	} else {
		alert("请选择员工再进行启用！");
		return false;
	}

}

function view() {
	var id = $("[name='radio']:checked").val();
	if (id) {
		openIFrame(basePath + "/EmployeeController/viewEmployee.do?id=" + id, null, __resourcecode__);
	} else {
		alert("请选择员工再进行查看！");
		return false;
	}
}


function viewEmployeeByLink(id){
	openIFrame(basePath + "/EmployeeController/viewEmployee.do?id=" + id, null, __resourcecode__);
}



function reset() {

	var id = $("[name='radio']:checked").val();
	if (id) {
		openIFrame(basePath + "/EmployeeController/resetPassword.do?id=" + id,
				null, __resourcecode__);
	} else {
		alert("请选择员工再进行重置密码！");
		return false;
	}

}

function viewHistory() {
	var id = $("[name='radio']:checked").val();
	if (id) {
		openIFrame(basePath + "/EmployeeController/employeeViewHistory.do?id="
				+ id, null, __resourcecode__);
	} else {
		alert("请选择员工再查看历史！");
		return false;
	}
}

function refresh() {
	submitWithParam("employeeListForm", "employeeList");
}
