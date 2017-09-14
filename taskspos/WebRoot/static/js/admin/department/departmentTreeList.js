$(function() {
	$.getScript(basePath + "/static/js/common/common_oper.js");
	var setting = {
		data : {
			key : {
				name : "departmentName"
			},
			simpleData : {
				enable : true,
				rootPId : "null",
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
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = treeObj.getNodes();
			treeObj.expandNode(nodes[0], true, false, false);
		},
		error : function() {
			// alert("error");
		}
	});
});
var node;
var viewNode;

function onClick(e, treeId, treeNode) {
	node = treeNode.departmentCode;
	viewNode = treeNode.departmentCode;
	
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var arr = treeObj.getNodesByFilter(function(node){return node;}, false, treeNode);
	arr.push(treeNode);
	var ids=new Array();
	for(var i=0;i<arr.length;i++){

		ids[i]=arr[i].id;
	}
	window.parent.document.getElementById("rightDepartmentList").src=basePath + '/DepartmentController/getAllDepartmentByIds.do?ids='+ids+"&&__resourcecode__=${param.__resourcecode__}"; 
	//window.parent.frames["rightDepartmentList"].location.reload();
}


function refresh() {
	submitWithParam("departmentListForm", "departmentList");
//	$("#departmentListForm").submit();
}
