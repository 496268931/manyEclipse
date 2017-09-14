$(function() {
	$.getScript(basePath + "/static/js/common/common_oper.js");
	$("#add").on("click", addDepartment);
	$("#modify").on("click", modifyDepartment);
	$("#delete").on("click", deleteDepartment);
	$("#view").on("click", viewDepartment);
	$("#history").on("click", viewHistoryList);
	$("#query").on("click", search);
	var setting = {
		check : {
			enable : true
		},
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
			onClick : onClick,
			onDblClick : onDblClick
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
function search() {
	trimTextInput("departmentListForm");
	$("#departmentListForm").submit();
}
var node;
var viewNode;
function onClick(e, treeId, treeNode) {
	node = treeNode.departmentCode;
	viewNode = treeNode.departmentCode;

}

function addDepartment() {
	openIFrame(basePath + "/DepartmentController/toDepartmentDetail.do", null, __resourcecode__);
}

function deleteDepartment() {
	// 获取所有全选状态的节点
	var zTreeObject = $.fn.zTree.getZTreeObj("treeDemo");
	var preDeteleArr = zTreeObject.getNodesByFilter(function(node) {
		return node.getCheckStatus().checked && !node.getCheckStatus().half;
	});
	// 获取所有全选状态节点的部门编号
	var delNodes = [];
	for ( var i in preDeteleArr) {
		delNodes.push(preDeteleArr[i].departmentCode);
	}

	if (delNodes.length == "0") {
		alert("请先选择部门的复选框再删除！！！");
		return false;
	}
	if (confirm("是否确认删除？")) {
		for (var i = 0; i < delNodes.length; i++) {
			if (delNodes[i] == "0") {
				alert("中融为根节点不给予删除");
			}
		}
		$
				.ajax({
					url : basePath
							+ "/DepartmentController/deleteDepartment.do?departmentCodes="
							+ delNodes,
					type : "post",
					dataType : "json",
					success : function(data) {
						if (data.status == 1) {
							if ('0' != data.message) {
								alert(data.message);
							}
							refresh();
							/* layer.close(delLayer); */

						} else {
							alert(data.message);
						}
					}
				});
	}
}
function viewHistoryList() {
	if (viewNode == null) {
		alert("请点击部门名称后再进行查看历史！！！");
		return false;
	}
	openIFrame(basePath + "/DepartmentController/viewHistoryList.do?viewNode="
			+ viewNode, null, __resourcecode__);

}

function modifyDepartment() {
	if (node == null) {
		alert("请点击部门名称后再进行修改部门信息！！！");
		return false;
	}
	openIFrame(basePath
			+ "/DepartmentController/toDepartmentDetail.do?departmentCode="
			+ node, null, __resourcecode__);
}
function onDblClick(e, treeId, treeNode) {
	viewNode = treeNode.departmentCode;
	openIFrame(basePath + "/DepartmentController/viewDepartment.do?viewNode="
			+ viewNode, null, __resourcecode__);

}
function viewDepartment() {
	if (viewNode == null) {
		alert("请先[双击]-部门名称-再查看部门信息或点击部门名称后点击查看按钮！！！");
		return false;
	}
	openIFrame(basePath + "/DepartmentController/viewDepartment.do?viewNode="
			+ viewNode, null, __resourcecode__);
}
function refresh() {
	$("#departmentListForm").submit();
}
