$(function() {
//	$.getScript(basePath + "/static/js/common/common_oper.js");
	$("#add").on("click", addDepartment);
	$("#modify").on("click", modifyDepartment);
	$("#delete").on("click", deleteDepartment);
	$("#view").on("click", viewDepartment);
	$("#viewHistory").on("click", viewHistoryList);
	$("#query").on("click", search);
	$("#clearQuery").on("click", clearAll);
	$("#chooseEmployee").on("click", chooseEmployee);
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
});

function viewDepartmentByLink(id){
	openIFrame(basePath + "/DepartmentController/viewDepartment.do?id="
			+ id, null, __resourcecode__);
}

//清空查询条件
function clearAll() {
	$(':input', '#departmentListForm').not(
			':button, :submit, :radio,:checkbox,:hidden').val('').removeAttr(
			'checked').removeAttr('selected');
}



/**
 * 选择员工
 */
function chooseEmployee() {
	window
			.open(basePath
					+ "/EmployeeController/getAllEmployee.do?isChoose=true&callbackFn=chooseCallback&flag=true&custFlag=true");
}

/**
 * 选择回调函数
 * 
 * @param memberId
 */
function chooseCallback(id) {
	var employee = getEmployeeById(id);
	$("#chargeEmpName").val(employee["realName"]);
}

function search() {
	trimTextInput("departmentListForm");
	$("#departmentListForm").submit();
}
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
	window.parent.document.getElementById("rightDepartmentList").src=basePath + '/DepartmentController/getAllDepartmentByIds.do?ids='+ids; 
	//window.parent.frames["rightDepartmentList"].location.reload();
}


function addDepartment() {
	openIFrame(basePath + "/DepartmentController/toDepartmentDetail.do", null, __resourcecode__);
}

function deleteDepartment() {
	
	var id = getCheckedId();
	if (id == null) {
		alert("请选择记录");
		return false;
	}
	
		$
		.ajax({
			url : basePath
					+ "/DepartmentController/getSonDepartment.do?id="
					+ id,
			type : "post",
			dataType : "json",
			success : function(data) {
				if(data.status == 1){
					  var names='';
					  for(var i=0;i<data.list.length;i++){
						  names=names+","+data.list[i].departmentName;
					  }
					 /* jQuery.each(data.list, function(i,item){  
						  
						  names=name+","+item.departmentName;
						  });*/
					  
					      names=names.substring(1,names.length);
					  alert("该部门存在子部门:"+names); 
					  if (confirm("是否确认删除？")) {
						  deleteSonDepartment(data.list,id);
					  }
				}else{
					 if (confirm("是否确认删除？")) {
					   deleteSonDepartment(data.list,id);
					 }
				}
				
				
			}
		});
}

function deleteSonDepartment(list,id){
	var arr=list;
	var ids='';
	if(null!=arr){
		for(var j=0;j<arr.length;j++){
			ids=ids+","+arr[j].id;
		}
		ids=ids.substring(1,ids.length);
	}
	$.ajax({
		async:false,
		url : basePath
				+ "/DepartmentController/deleteDepartment.do?id="
				+ id,
		type : "post",
		data : {ids:ids},
		success : function(data) {
			if(data.status == 1){
				
			  if(''!=data.rootMessage&&null!=data.rootMessage){
				  alert(data.rootMessage);
			  }
			  
			  if(ids==''&&''!=data.rootMessage){
				  
			  }else{
				  alert(data.message);
			  }
			 // parent.document.getElementById('ids').value=ids;
			  window.parent.frames["leftDepartmentTree"].location.reload();
			  refresh();
			}else{
				alert(data.message);
			}
			
		}
	});
}

function viewHistoryList() {
	var id = getCheckedId();
	if (id == null) {
		alert("请选择记录");
		return false;
	}
	openIFrame(basePath + "/DepartmentController/viewHistoryList.do?departmentId="
			+ id, null, __resourcecode__);

}

function modifyDepartment() {
	var id = getCheckedId();
	if (id == null) {
		alert("请选择记录");
		return false;
	}
	openIFrame(basePath
			+ "/DepartmentController/toDepartmentDetail.do?id="
			+ id, null, __resourcecode__);
}
function onDblClick(e, treeId, treeNode) {
	viewNode = treeNode.departmentCode;
	openIFrame(basePath + "/DepartmentController/viewDepartment.do?viewNode="
			+ viewNode, null, __resourcecode__);

}
function viewDepartment() {
	var id = getCheckedId();
	if (id == null) {
		alert("请选择记录");
		return false;
	}
	openIFrame(basePath + "/DepartmentController/viewDepartment.do?id="
			+ id, null, __resourcecode__);
}
function refresh() {
	submitWithParam("departmentListForm", "departmentList");
//	$("#departmentListForm").submit();
}

function getCheckedId() {
	return $("[name='_id']:checked").val();
}

