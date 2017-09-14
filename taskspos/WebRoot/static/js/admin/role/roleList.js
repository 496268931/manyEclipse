$(function() {
//	$.getScript(basePath + "/static/js/common/common_oper.js");
	$("#add").on("click", addRole);
	$("#modify").on("click", modifyRole);
	$("#delete").on("click", deleteRole);
	$("#view").on("click", view);
	$("#viewHistory").on("click", viewHistory);
	$("#cancel").on("click", closePage);
	$("#clearQuery").on("click", clearAll);
	$("#query").on("click", queryAll);
});

function queryAll(){
	trimTextInput("RoleListForm");
	$("#RoleListForm").submit();
}

function clearAll() {

	$(':input', '#RoleListForm').not(':button, :submit,:radio, :hidden')
			.val('').removeAttr('checked').removeAttr('selected');

}

function addRole() {
	openIFrame(basePath + "/RoleController/toRoleDetail.do", null,
			__resourcecode__);
}

function modifyRole() {
	var id = $("[name='radio']:checked").val();
	if (id) {
		openIFrame(basePath + "/RoleController/toRoleDetail.do?id=" + id, null,
				__resourcecode__);
	} else {
		alert("请选择角色再进行修改！");
		return false;
	}

}

function deleteRole() {
	var id = $("[name='radio']:checked").val();
	if (id) {
		if (confirm("是否确认删除？")) {
		$.ajax({
			url : basePath + "/RoleController/roleDelete.do?id=" + id,
			type : "post",
			dataType : "json",
			success : function(data) {
				if (data.status == 1) {
					refresh();
					// layer.close(delLayer);
					alert(data.message);
				} else {
					alert(data.message);
				}
			}
		});
		}	
	} else {
		alert("请选择角色再进行删除！");
		return false;
	}

}

function view() {
	var id = $("[name='radio']:checked").val();
	if (id) {
		openIFrame(basePath + "/RoleController/viewRole.do?id=" + id, null,
				__resourcecode__);
	} else {
		alert("请选择角色再进行查看！");
		return false;
	}
}


function viewRoleByLink(id){
	openIFrame(basePath + "/RoleController/viewRole.do?id=" + id, null,
			__resourcecode__);
}

function viewHistory() {
	var id = $("[name='radio']:checked").val();
	if (id) {
		openIFrame(basePath + "/RoleController/roleViewHistory.do?id=" + id,
				null, __resourcecode__);
	} else {
		alert("请选择角色再进行查看历史！");
		return false;
	}
}

function refresh() {
	// $("#RoleListForm").submit();
	submitWithParam("RoleListForm", "roleList");
}
function closePage() {
	parent.layer.close(parent.layer.getFrameIndex());
}
