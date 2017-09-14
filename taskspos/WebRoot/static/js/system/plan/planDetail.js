$(function () {
	$("#save").on("click", saveOrUpdatePlan);
	$("#cancel").on("click", closePage);

	// 表单校验
	$("#planForm").validator({
		stopOnError : true,
		timely      : true,
		focusCleanup: true,
		fields      : {
			"#planCode"           : "required,specialchar",
			"#planContent"        : "required,specialchar",
			"#planEndTime"        : "required",
			"#planStartTime"      : "required",
			"#planRemindStartTime": "required",
			"#planRemindEmpName"  : {
				rule  : "required;",
				target: "#errorForPlanRemindEmpName"
			},
			"#planTitle"          : "required,specialchar",
			"#planType"           : "required",
			"#importanceDegree"   : "required"
		}
	});
});

/**
 * 保存或更新系统计划
 *
 * @version V1.0  2014-07-15 PM 11:17:54 Tue
 * @author ly(ly@chinazrbc.com)
 */
function saveOrUpdatePlan() {
	submitWithValidator("planForm", function () {
		$.ajax({
			url     : basePath + "/PlanController/saveOrUpdatePlan.do?r=" + new Date().getTime(),
			type    : "post",
			data    : $("#planForm").serialize(),
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


function choosePlanRemindEmp() {
	window.open(basePath + "/EmployeeController/getAllEmployee.do?isChoose=true&callbackFn=choosePlanRemindEmpCallback&flag=true");
}

/**
 * 选择回调函数
 * @param memberId
 */
function choosePlanRemindEmpCallback(id) {
	var employee = getEmployeeById(id);
	$("#planRemindEmp").val(id);
	$("#planRemindEmpName").val(employee["realName"]);
}