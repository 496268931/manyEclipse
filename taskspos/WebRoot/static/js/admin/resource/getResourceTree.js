var setting = {
	check : {
		enable : true,
		chkStyle : "checkbox",
		radioType : "all"
	},
	data : {
		key : {
			name : "resourceName", // 各级树显示的名字
			url : ""// 点击不访问URL
		},
		simpleData : {
			enable : true,
			rootPId : null,
			idKey : "resourceCode",
			pIdKey : "parentCode"

		}
	},
	callback : {
	// beforeClick: beforeClick,
	// onClick : onClick
	}
};

function updateResource(url) {
	var zTree = $.fn.zTree.getZTreeObj($(".active").attr("id").split("_")[0]
			+ "_tree");
	// var zTree = $.fn.zTree.getZTreeObj("${subSystemId}_tree");
	var nodes = zTree.getSelectedNodes();
	if (nodes) {
		if (nodes[0]) {
			var code = nodes[0].resourceCode;
			addLayer(url + "?flag=update&resourceCode=" + code);
		} else {
			alert("请选择资源！");
		}
	} else {
		alert("请选择资源！");
	}
}

function addLayer(url) {
//	openCustomIFrame(basePath + url);
	openIFrame2(basePath+url, ["30px",""],["700px","500px"], __resourcecode__);
}

function openCustomIFrame(src) {
	openIFrame(src, null, __resourcecode__);
}
// 页面加载时调用
$(document).ready(function() {
	// 根据子系统ID查询所属资源树
	ajaxData(subSystemId);
	$("#" + subSystemId + "_subSystem").removeClass().addClass("active");
	$("#" + subSystemId + "_content").show();

	$("#addSubSystem").on("click", function() {
		addLayer('/SubSystemController/addSubSystem.do');
	});

	$("#getSubSystem").on("click", function() {
		updateSuySystem('/SubSystemController/getSubSystemForUpdate.do');
	});

	$("#removeSubSystem").on("click", function() {
		removeSuySystem('/SubSystemController/removeSubSystem.do');
	});

	$("#querySubSystem").on("click", function() {
		getSuySystem('/SubSystemController/getSubSystem.do');
	});

	$("#addResource").on("click", function() {
//		var subSystemId = $(".active").attr("id").split("_")[0];
		addLayer('/ResourceController/addResource.do?');
	});

	$("#getResource").on("click", function() {
		updateResource('/ResourceController/getResourceByCode.do');
	});

	$("#removeResource").on("click", function() {
		removeResource('/ResourceController/removeResource.do');
	});

	$("#queryResource").on("click", function() {
		getResource('/ResourceController/getResourceByCode.do');
	});

	$("#queryResourceH").on("click", function() {
		getHistory('/ResourceController/getHistorty.do');
	});

});

// 查询资源树
function queryTree(subSystemId) {
	// 根据子系统ID查询所属资源树
	ajaxData(subSystemId);
	$("[id$='_content']").hide();// 所有以_content值结尾的属性为id的元素隐藏
	$("#" + subSystemId + "_content").show();
	$("[id$='_subSystem']").removeClass().addClass("nav");// 所有以_subSystem值结尾的属性为id的元素隐藏
	$("#" + subSystemId + "_subSystem").removeClass().addClass("active");
}

// ajax方法,用于请求tree
function ajaxData(subSystemId) {
	$.ajax({
		type : "post",// 使用post方式访问后台
		url : basePath + "/ResourceController/getResourceList.do",// 要访问的后台地址
		data : "subSystemId=" + subSystemId,// 要发送的数据
		dataType : "json", // 返回的数据类型
		success : function(data) {
			$.fn.zTree.init($("#" + subSystemId + "_tree"), setting, data);
			var treeObj = $.fn.zTree.getZTreeObj(subSystemId + "_tree");
			if (treeObj) {
				var nodes = treeObj.getNodes();
				recursionAllNodes(nodes, treeObj);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
}

// 递归遍历所有节点
function recursionAllNodes(nodes, treeObj) {
	if (nodes) {
		if (nodes.length > 0) {
			for (var i = 0; i < nodes.length; i++) {
				// 判断节点是否展开
				if (nodes[i].isOpen == 1) {
					treeObj.expandNode(nodes[i], true, false, false);
				}
				// 判断该节点的复选框是否失效
				if (nodes[i].status == 0) {
					nodes[i].nocheck = true;
				}
				recursionAllNodes(nodes[i], treeObj);
			}
		} else {
			if (nodes.children && nodes.children.length > 0) {
				for (var i = 0; i < nodes.children.length; i++) {
					// 判断节点是否展开
					if (nodes.children[i].isOpen == 1) {
						treeObj.expandNode(nodes.children[i], true, false,
								false);
					}
					// 判断该节点的复选框是否失效
					if (nodes.children[i].status == 0) {
						nodes.children[i].nocheck = true;
					}

					recursionAllNodes(nodes.children[i], treeObj);
				}
			}
		}
	}
}

// 更新子系统
function updateSuySystem(url) {
	// 获取当前子系统ID
	var subSystemId = $(".active").attr('id').split("_")[0];
	// 调用更新方法
	addLayer(url + "?subSystemId=" + subSystemId);
}
// 查看子系统
function getSuySystem(url) {
	// 获取当前子系统ID
	var subSystemId = $(".active").attr('id').split("_")[0];
	// 调用更新方法
	addLayer(url + "?subSystemId=" + subSystemId);
}
// 删除子系统
function removeSuySystem(url) {
	// 获取当前子系统ID
	var subSystemId = $(".active").attr('id').split("_")[0];
	// 调用更新方法
	if (confirm("是否确认删除？")) {
		$.ajax({
			type : "post",// 使用post方式访问后台
			url : basePath + url,// 要访问的后台地址
			data : "subSystemId=" + subSystemId,// 要发送的数据
			// dataType: "json", //返回的数据类型
			success : function(data) {
				// console.log(data);
				if (data.status == -1)
					alert(data.message);
				else
					refreshAll();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(errorThrown);
			}
		});
	}
}

// 删除资源
function removeResource(url) {
	var zTree = $.fn.zTree.getZTreeObj($(".active").attr("id").split("_")[0]
			+ "_tree");
	// var nodes = zTree.getCheckedNodes(true);
	// 获取所有全选状态的节点数据
	var preDeteleArr = zTree.getNodesByFilter(function(node) {
		return node.getCheckStatus().checked && !node.getCheckStatus().half;
	});

	// console.log(preDeteleArr);

	// 获取所有全选状态节点的资源编号
	var delNodes = [];
	for ( var i in preDeteleArr) {
		delNodes.push(preDeteleArr[i].resourceCode);
	}
	if (delNodes.length == "0") {
		alert("请选择资源！");
		return false;
	}
	if (confirm("是否确认删除？")) {
		var delLayer = layer.load("正在删除...");
		$.ajax({
			url : basePath + url + "?resourceCodes=" + delNodes,
			type : "post",
			dataType : "json",
			success : function(data) {
				if (data.status == 1) {
					refresh();
					layer.close(delLayer);
					// alert(data.message);
				} else {
					alert(data.message);
				}
			}
		});
	}

}

// 查看历史
function getHistory(url) {
	var zTree = $.fn.zTree.getZTreeObj($(".active").attr("id").split("_")[0]
			+ "_tree");
	var nodes = zTree.getSelectedNodes();
	var code = '';
	if (nodes) {
		if (nodes[0]) {
			if (nodes[0].resourceCode) {
				code = nodes[0].resourceCode;
			}
		}
	}
	if (code && code != '') {

		addLayer(url + "?resourceCode=" + code);
	} else {
		alert('请选择资源！');
	}
}

// 查看资源信息
function getResource(url) {

	var zTree = $.fn.zTree.getZTreeObj($(".active").attr("id").split("_")[0]
			+ "_tree");
	var nodes = zTree.getSelectedNodes();
	if (nodes) {
		if (nodes[0]) {
			var code = nodes[0].resourceCode;
			addLayer(url + "?flag=query&resourceCode=" + code);
		} else {
			alert("请选择资源！");
		}
	} else {
		alert("请选择资源！");
	}
}

// 刷新页面
function refresh(subSystemId) {
	if(subSystemId){
		ajaxData(subSystemId);
	}else{
//		console.log($(".active").attr("id").split("_")[0]);
		ajaxData($(".active").attr("id").split("_")[0]);
	}
}

// 刷新页面
function refreshAll() {
	location.reload(true);
}