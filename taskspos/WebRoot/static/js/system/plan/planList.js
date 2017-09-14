$(function () {
	$("#add").on("click", addPlan);
	$("#modify").on("click", modifyPlan);
	$("#delete").on("click", deletePlan);
	$("#history").on("click", showHistory);
	$("#view").on("click", viewPlan);
	$("#search").on("click", search);
});

function search() {
	trimTextInput("planListForm");
	$("#planListForm").submit();
}
/**
 * 新增系统计划
 *
 * @version V1.0  2014-07-15 PM 11:17:54 Tue
 * @author ly(ly@chinazrbc.com)
 */
function addPlan() {
	openCustomIFrame(basePath + "/PlanController/toPlanDetail.do");
}

/**
 * 修改系统计划
 *
 * @version V1.0  2014-07-15 PM 11:17:54 Tue
 * @author ly(ly@chinazrbc.com)
 */
function modifyPlan() {
	var id = getCheckedId();
	if (id) {
		openCustomIFrame(basePath + "/PlanController/toPlanDetail.do?id=" + id);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 查看计划
 */
function viewPlan() {
	var id = getCheckedId();
	viewPlanById(id);
}

/**
 * 查看计划
 */
function viewPlanById(id) {
	if (id) {
		openCustomIFrame(basePath + "/PlanController/toPlanDetail.do?id=" + id + "&isView=true");
	} else {
		alert("请选择记录！");
	}
}

/**
 * 删除系统计划
 *
 * @version V1.0  2014-07-15 PM 11:17:54 Tue
 * @author ly(ly@chinazrbc.com)
 */
function deletePlan() {
	var id = getCheckedId();
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
						refresh();
						layer.close(delLayer);
						alert(data.message);
					} else {
						alert(data.message);
					}
				}
			});
		}
	} else {
		alert("请选择记录！");
	}
}

function showHistory() {
	var id = getCheckedId();
	if (id) {
		window.open(basePath + "/PlanHController/searchPlanHList.do?planId=" + id);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 刷新列表
 *
 * @version V1.0  2014-07-15 PM 11:17:54 Tue
 * @author ly(ly@chinazrbc.com)
 */
function refresh() {
	submitWithParam("planListForm", "planList");
}

function openCustomIFrame(src) {
	var area = {width: "1000px"};
	openIFrame(src, area);
}
/**
 * 获取选中id
 *
 * @version V1.0  2014-07-15 PM 11:17:54 Tue
 * @author ly(ly@chinazrbc.com)
 */
function getCheckedId() {
	return $("[name='_id']:checked").val();
}


