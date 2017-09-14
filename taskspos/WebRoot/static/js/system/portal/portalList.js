$(function () {
	$("#add").on("click", addPortal);
	$("#modify").on("click", modifyPortal);
	$("#delete").on("click", deletePortal);
	$("#history").on("click", showHistory);
	$("#view").on("click", viewPortal);
	$("#search").on("click", search);
	$("#configuration").on("click", configuration);
	$("#default").on("click", configDefault);
});


function configDefault() {
	var id = getCheckedId();
	var now = new Date().getTime();
	if (id) {
		if (confirm("是否设为缺省？")) {
			var delLayer = layer.load("正在设置...");
			$.ajax({
				url     : basePath + "/PortalController/configDefault.do?r=" + now,
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

function search() {
	trimTextInput("portalListForm");
	$("#portalListForm").submit();
}
/**
 * 新增系统门户
 *
 * @version V1.0  2014-07-24 PM 09:44:47 Thu
 * @author ly(ly@chinazrbc.com)
 */
function addPortal() {
	openCustomIFrame(basePath + "/PortalController/toPortalDetail.do");
}

/**
 * 修改系统门户
 *
 * @version V1.0  2014-07-24 PM 09:44:47 Thu
 * @author ly(ly@chinazrbc.com)
 */
function modifyPortal() {
	var id = getCheckedId();
	if (id) {
		openCustomIFrame(basePath + "/PortalController/toPortalDetail.do?id=" + id);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 查看客户
 */
function viewPortal() {
	var id = getCheckedId();
	if (id) {
		viewPortalById(id);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 配置组件操作
 */
function configuration() {
	var id = getCheckedId();
	if (id) {
		openCustomIFrame(basePath + "/PortalController/searchConfigurationPortletList.do?portalId=" + id);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 查看客户
 * @param id
 */
function viewPortalById(id) {
	openCustomIFrame(basePath + "/PortalController/toPortalDetail.do?id=" + id + "&isView=true");
}

/**
 * 删除系统门户
 *
 * @version V1.0  2014-07-24 PM 09:44:47 Thu
 * @author ly(ly@chinazrbc.com)
 */
function deletePortal() {
	var id = getCheckedId();
	var now = new Date().getTime();
	if (id) {
		if (confirm("是否确认删除？")) {
			var delLayer = layer.load("正在删除...");
			$.ajax({
				url     : basePath + "/PortalController/deletePortal.do?r=" + now,
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
		window.open(basePath + "/PortalHController/searchPortalHList.do?portalId=" + id);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 刷新列表
 *
 * @version V1.0  2014-07-24 PM 09:44:47 Thu
 * @author ly(ly@chinazrbc.com)
 */
function refresh() {
	submitWithParam("portalListForm", "portalList");
}

function openCustomIFrame(src) {
	var area = {width: "1000px"};
	openIFrame(src, area);
}
/**
 * 获取选中id
 *
 * @version V1.0  2014-07-24 PM 09:44:47 Thu
 * @author ly(ly@chinazrbc.com)
 */
function getCheckedId() {
	return $("[name='_id']:checked").val();
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
