$(function () {
	$("#save").on("click", saveOrUpdatePlan);
	$("#cancel").on("click", closePage);
	$("#delete").on("click", deletePlan);

	// 表单校验
	$("#planForm").validator({
		stopOnError : true,
		timely      : true,
		focusCleanup: true,
		fields      : {
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
					parent.refreshCalendar();
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

/**
 * 删除系统计划
 *
 * @version V1.0  2014-07-15 PM 11:17:54 Tue
 * @author ly(ly@chinazrbc.com)
 */
function deletePlan() {
	var id = $('#id').val();
	var now = new Date().getTime();
	if (id) {
		if (confirm("是否确认删除？")) {
			var delLayer = layer.load("正在删除...");
			$.ajax({
				url     : basePath + "/PlanController/deletePlan.do?r=" + now,
				type    : "post",
				data    : {id: id},
				dataType: "json",
				success : function (data) {
					if (data.status == 1) {
						layer.close(delLayer);
						alert(data.message);
						parent.refreshCalendar();
					} else {
						alert(data.message);
					}
				}
			});
		}
	} else {
		alert("无记录可供删除！");
	}
}
