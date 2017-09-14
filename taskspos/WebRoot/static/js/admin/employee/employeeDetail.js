$(function() {
	$("#menuBtn").on("click", showMenu);
	$("#save").on("click", saveEmployee);
	$("#cancel").on("click", closePage);
	$("#savePassword").on("click", savePassword);
    $("#chooseTitle").on("click", function() {
        chooseSomething('chooseTitleCallback', ChooseUrlEnum.TITLE);
    });
    $("#clearTitle").on("click", function() {
        $("#titleId").val("");
        $("#titleLevelName").val("");
    });
    $("#clearParent").on("click", function() {
        $("#parentId").val("");
        $("#parentName").val("");
    });
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
		}
	});

	$("#employeeForm")
			.validator(
					{
						stopOnError : true,
						timely : true,
						focusCleanup : true,
						fields : {
							"#employeeCode" : "员工编号:required;departmentcode;length[1~20];remote["+ 
							basePath+ "/EmployeeController/checkEmployeeCode.do, id]",
							"#loginName" : "登陆名:required;username;length[1~20];remote["+ 
							basePath+ "/EmployeeController/checkLoginName.do, id]",
							"#loginPassword" : "密码:required;length[6~20]",
							"#reloginPassword" : "确认密码:required;match[loginPassword]",
							"#realName" : "姓名:required;realname;length[1~20]",
							"#telphoneNo" : "电话号码:tel;length[1~20]",
							"#mobilePhoneNo" : "手机号:required;mobile;length[1~20]",
							"#email" : "邮箱:required;email;length[1~50]",
							"#identityNumber" : "身份证号:required;ID_card;length[1~20];remote["+ 
							basePath+ "/EmployeeController/checkEmployeeIdentityNumber.do, id]",
							"#roledepartmentSel" : {
								rule : "角色名称:required;",
								target : "#errorForRoledepartmentSel"
							},
							"#departmentSel" : {
								rule : "部门名称:required;",
								target : "#errorForDepartmentSel"
							}
						}
					});
});

function chooseTitleCallback(id) {
    var title = getTitleById(id);
    $("#titleId").val(id);
    $("#titleLevelName").val(title['levelName']);
}

var node;
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
	node = treeNode.departmentCode;
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

function saveEmployee() {
	submitWithValidator("employeeForm", function() {
	var titleId=$("#titleId").val();
	var flag=false;
//	if(null!=titleId&&''!=titleId){
//		flag=false;
		var departmentId=$("#departmentId").val();
//		$.ajax({
//			url: basePath + "/EmployeeController/checkDepartmentChargeEmpTitle.do?titleId="+titleId+"&departmentCode="+node+"&departmentId="+departmentId,
//			type: "post",
//			async: false,
//			data : $("#employeeForm").serialize(),
//			success: function(data) {
//				if(data.status==1){
//					flag=true;
//				}else{
//					alert(data.message);
//				}
//			}
//		});
//	}
    
	if(flag || true){
		
		$.ajax({
			url : basePath + "/EmployeeController/saveEmployee.do?departmentCode="
					+ node + "&roleIds=" + roleIds,
			type : "post",
			data : $("#employeeForm").serialize(),
			dataType : "json",
			success : function(data) {
				if (data.status == 1) {
					alert(data.message);
				    $("#save").attr({"disabled":"disabled"});
					// parent.layer.close(parent.layer.getFrameIndex());
					parent.refresh();
				} else {
					alert(data.message);
				}
			}
		});
	  }	
	});
	
}

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
