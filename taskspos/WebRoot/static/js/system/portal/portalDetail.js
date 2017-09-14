$(function () {
	$("#save").on("click", saveOrUpdatePortal);
	$("#cancel").on("click", closePage);
	$("#layoutType").on("change", layoutTypeOnChangeEvent);


	// 表单校验
	$("#portalForm").validator({
		stopOnError : true,
		timely      : true,
		focusCleanup: true,
		fields      : {
			"#columnNum"   : "required,specialchar",
			"#employeeName": {
				rule  : "required;",
				target: "#errorForEmployeeName"
			},
			"#layoutType"  : "required,specialchar",
			"#portalCode"  : "specialchar",
			"#rowNum"      : "specialchar"
		}
	});
});

/**
 * 保存或更新系统门户
 *
 * @version V1.0  2014-07-24 PM 09:44:47 Thu
 * @author ly(ly@chinazrbc.com)
 */
function saveOrUpdatePortal() {
	submitWithValidator("portalForm", function () {
		$.ajax({
			url     : basePath + "/PortalController/saveOrUpdatePortal.do?r=" + new Date().getTime(),
			type    : "post",
			data    : $("#portalForm").serialize(),
			dataType: "json",
			success : function (data) {
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

/**
 * 关闭遮罩层
 */
function closePage() {
	parent.layer.close(parent.layer.getFrameIndex());
}

/**
 * 选择员工
 */
function chooseEmployee() {
	window.open(basePath + "/EmployeeController/getAllEmployee.do?isChoose=true&callbackFn=chooseEmployeeCallback&flag=true");
}

/**
 * 选择员工回调事件
 * @param id
 */
function chooseEmployeeCallback(id) {
	var employee = getEmployeeById(id);
	$("#employeeId").val(id);
	$("#employeeName").val(employee["realName"]);
}


function layoutTypeOnChangeEvent() {
	var layoutType = $('#layoutType').val();

	var enums = com.chinazrbc.xz.common.enums.system.portal.PortalLayoutTypeEnum.values();
	var enumObj = null;
	$.each(enums, function () {
		if (this.value == layoutType) {
			enumObj = this;
			return false;
		}
	});

	if (enumObj) {
		var columnNum = enumObj.columnNum;
		var rowNum = enumObj.rowNum;
		$('#columnNum').val(columnNum);
		$('#rowNum').val(rowNum);
	}

}