$(function(){
	$("#add").on("click", addPlanH);
	$("#modify").on("click", modifyPlanH);
	$("#delete").on("click", deletePlanH);
	$("#history").on("click", showHistory);
	$("#view").on("click", viewPlanH);
	$("#search").on("click", search);
});

function search() {
	trimTextInput("planHListForm");
	$("#planHListForm").submit();
}
/**
 * 新增系统计划历史
 *
 * @version V1.0  2014-07-16 PM 06:25:57 Wed
 * @author ly(ly@chinazrbc.com)
 */
function addPlanH() {
	openCustomIFrame(basePath + "/PlanHController/toPlanHDetail.do");
}

/**
 * 修改系统计划历史
 *
 * @version V1.0  2014-07-16 PM 06:25:57 Wed
 * @author ly(ly@chinazrbc.com)
 */
function modifyPlanH() {
	var id = getCheckedId();
	if (id) {
		openCustomIFrame(basePath + "/PlanHController/toPlanHDetail.do?id=" + id);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 查看客户
 */
function viewPlanH() {
	var id = getCheckedId();
	if (id) {
		openCustomIFrame(basePath + "/PlanHController/toPlanHDetail.do?id=" + id + "&isView=true");
	} else {
		alert("请选择记录！");
	}
}

/**
 * 删除系统计划历史
 *
 * @version V1.0  2014-07-16 PM 06:25:57 Wed
 * @author ly(ly@chinazrbc.com)
 */
function deletePlanH() {
	var id = getCheckedId();
	var now = new Date().getTime();
	if (id) {
		if (confirm("是否确认删除？")) {
			var delLayer = layer.load("正在删除...");
			$.ajax({
				url: basePath + "/PlanHController/deletePlanH.do?r=" + now,
				type: "post",
				data: {id: id},
				dataType: "json",
				success: function(data) {
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
		window.open(basePath + "/PlanHHController/searchPlanHHList.do?planHId=" + id);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 刷新列表
 *
 * @version V1.0  2014-07-16 PM 06:25:57 Wed
 * @author ly(ly@chinazrbc.com)
 */
function refresh() {
	submitWithParam("planHListForm", "planHList");
}

function openCustomIFrame(src) {
	var area = {width: "1000px"};
	openIFrame(src, area);
}
/**
 * 获取选中id
 *
 * @version V1.0  2014-07-16 PM 06:25:57 Wed
 * @author ly(ly@chinazrbc.com)
 */
function getCheckedId() {
	return $("[name='_id']:checked").val();
}
