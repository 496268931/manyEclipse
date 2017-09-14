$(function() {
	$("#save").on("click", saveOrUpdateRole);
	$("#cancel").on("click", closePage);
	$("#menuBtn").on("click", showMenu);
	var setting = {
			view : {
				dblClickExpand : false
			},
			check:{
				enable:true
			},
			data : {
				key : {
					name : "name", //各级树显示的名字
					url : ""//点击不访问URL
				},
				simpleData : {
					enable : true,
					rootPId : null,
					idKey : "id",
					pIdKey : "pId"
				}
			},
			callback : {
				onCheck: onCheck
			}
		};
		$.ajax({
			type : "post",
			url : basePath + "/SubSystemController/getResourceTempList.do?flag=role&roleId="+$("#id").val(),
			dataType : "json",
			success : function(data) {
				$.fn.zTree.init($("#treeDemo"), setting, data);

			},
			error : function() {
				alert("加载失败！");
			}
		});

	$("#RoleForm").validator(
			{
				stopOnError : true,
				timely : true,
				focusCleanup : true,
				fields : {
					"#roleName" : "角色名称:required;loginname;length[1~20];remote[" + basePath
							+ "/RoleController/checkRoleName.do, id]",
					"#roleDesc":"角色描述:length[0~50];specialchar", 
					"#departmentSel":"资源名称:required", 
				}
			});
});


function beforeClick(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}
var resourceIds=[];
function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
	nodes = zTree.getCheckedNodes(true),
	v = "",resourceId="";
	resourceIds=[];
	for (var i=0, l=nodes.length; i<l; i++) {
		resourceId=nodes[i].resourceId;
		if(null!=resourceId){
			v += nodes[i].name + ",";
		}
		resourceIds[i]=resourceId;
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	var cityObj = $("#departmentSel");
	cityObj.attr("value", v);
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

function saveOrUpdateRole() {
	submitWithValidator("RoleForm", function() {
	$.ajax({
		url : basePath + "/RoleController/saveOrUpdateRole.do?resources="+resourceIds,
		type : "post",
		data : $("#RoleForm").serialize(),
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






