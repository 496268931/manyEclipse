$(function() {
	$("#menuBtn").on("click", showMenu);
	$("#save").on("click", saveDepartment);
	$("#cancel").on("click", closePage);
	$("#chooseEmployee").on("click", chooseEmployee);
	var setting = {
		view : {
			dblClickExpand : false
		},
		
		data : {
			key : {
				name : "departmentName"
			},
			simpleData : {
				enable : true,
				rootPId : "0",
				idKey : "departmentCode",
				pIdKey : "parentCode"
			}
		},
		callback : {
			onClick : onClick
		}
	};
	$.ajax({
		type : "post",
		url : basePath + "/DepartmentController/departmentJson.do",
		dataType : "json",
		success : function(data) {
			$.fn.zTree.init($("#treeDemo"), setting, data);

		},
		error : function() {
			alert("加载失败！");
		}
	});

	$("#departmentForm").validator(
			{
				stopOnError : true,
				timely : true,
				focusCleanup : true,
				fields : {
					"#departmentCode" : "部门编号:required;departmentcode;length[1~10];remote[" + basePath + 
					"/DepartmentController/checkDepartmentCode.do, id]",
					"#departmentName" : "部门名称:required;length[1~10];loginname;remote[" + basePath + "/DepartmentController/checkDepartmentName.do, id]",
					"#chargeEmpName" : "部门负责人名称:required;remote[" +
					basePath + "/DepartmentController/checkChargePerson.do, id]",
					"#departmentDesc" : "描述:length[1~20]",
					"#departmentSel" : "上级部门名称:required;",
					"#cityId" : "城市:required;",
					"#provinceId" : "省份:required;",
				}
			});
});

/**
 * 选择员工
 */
function chooseEmployee() { 
	window.open(basePath + "/EmployeeController/getAllEmployee.do?isChoose=true&callbackFn=chooseCallback&flag=true");
}

/**
 * 选择回调函数
 * @param memberId
 */
function chooseCallback(id) {
	var employee = getEmployeeById(id);
	$("#chargeEmp").val(id);
	$("#chargeEmpName").val(employee["realName"]);
}



function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
			.getSelectedNodes(), v = "", parentCode = "";
	nodes.sort(function compare(a, b) {
		return a.id - b.id;
	});
	for (var i = 0, l = nodes.length; i < l; i++) {
		v += nodes[i].departmentName + ",";
		parentCode += nodes[i].departmentCode;
	}
	if (v.length > 0)
		v = v.substring(0, v.length - 1);
	var departmentObj = $("#departmentSel");
	var code = $("#parentCode");
	departmentObj.attr("value", v);
	code.attr("value", parentCode);
}

function showMenu() {
	var departmentObj = $("#departmentSel");
	var departmentOffset = $("#departmentSel").offset();
	$("#menuContent").css({
		left : departmentOffset.left + "px",
		top : departmentOffset.top + departmentObj.outerHeight() + "px"
	}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
			event.target).parents("#menuContent").length > 0)) {
		hideMenu();
	}
}

function saveDepartment() {
	submitWithValidator("departmentForm", function() {
	$.ajax({
		url : basePath + "/DepartmentController/saveDepartment.do",
		type : "post",
		data : $("#departmentForm").serialize(),
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