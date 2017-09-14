$(function() {
	$("#rolemenuBtn").on("click", roleshowMenu);
	var setting1 = {
		view : {
			dblClickExpand : false
		},
		check:{
			enable:true
		},
		data : {
			key : {
				name : "roleName"
			},
			simpleData : {
				enable : true,
				rootPId : "0",
				idKey : "roleName",
				pIdKey : "null"
			}
		},
		callback : {
			beforeClick: beforeClick,	
			onCheck: onCheck
		}
	
	};
	$.ajax({
		type : "post",
		url : basePath + "/EmployeeController/roleJson.do?employeeId="+$("#id").val(),
		dataType : "json",
		success : function(data) {
			$.fn.zTree.init($("#roletreeDemo"), setting1, data);

		},
		error : function() {
		}
	});

});


function beforeClick(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("roletreeDemo");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}
var roleIds=[];
function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("roletreeDemo"),
	nodes = zTree.getCheckedNodes(true),
	v = "",roleId="";
	roleIds=[];
	for (var i=0;i<nodes.length; i++) {
		v += nodes[i].roleName + ",";
		roleId=nodes[i].id;
		roleIds[i]=roleId;
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	var cityObj = $("#roledepartmentSel");
	cityObj.attr("value", v);
}

function roleshowMenu() {
	var departmentObj = $("#roledepartmentSel");
	var departmentOffset = $("#roledepartmentSel").offset();
	$("#rolemenuContent").css({
		left : departmentOffset.left + "px",
		top : departmentOffset.top + departmentObj.outerHeight() + "px"
	}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown1);
}
function hideMenu1() {
	$("#rolemenuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown1);
}
function onBodyDown1(event) {
	if (!(event.target.id == "rolemenuBtn" || event.target.id == "rolemenuContent" || $(
			event.target).parents("#rolemenuContent").length > 0)) {
		hideMenu1();
	}
}







