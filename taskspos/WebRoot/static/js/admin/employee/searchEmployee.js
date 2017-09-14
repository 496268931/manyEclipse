$(function() {
	$("#menuBtn").on("click", showMenu);
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

});

var node;
function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
			.getSelectedNodes(), v = "", parentCode = "",sId="";
	nodes.sort(function compare(a, b) {
		return a.id - b.id;
	});
	for (var i = 0, l = nodes.length; i < l; i++) {
		v += nodes[i].departmentName + ",";
		parentCode += nodes[i].departmentCode;
		sId += nodes[i].id;
	}
	if (v.length > 0)
		v = v.substring(0, v.length - 1);
	var departmentObj = $("#departmentName");
	var code = $("#parentCode");
	var departmentId = $("#departmentId");
	departmentObj.attr("value", v);
	code.attr("value", parentCode);
	departmentId.attr("value", sId);
	node=treeNode.departmentCode;
}

function showMenu() {
	var departmentObj = $("#departmentName");
	var departmentOffset = $("#departmentName").offset();
	$("#menuContent").css({
		left : departmentOffset.left + "px",
		top : departmentOffset.top + departmentObj.outerHeight() + "px"
	}).slideDown("fast");

	$("#searchemp").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("#searchemp").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
			event.target).parents("#menuContent").length > 0)) {
		hideMenu();
	}
}









