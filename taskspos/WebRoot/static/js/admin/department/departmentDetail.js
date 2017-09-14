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
//					"#chargeEmpName" : {
//						rule : "部门负责人名称:required;remote[" +
//						basePath + "/DepartmentController/checkChargePerson.do, id]",
//						target : "#errorForChargeEmpName"
//					},
					"#departmentDesc" : "描述:length[1~20]",
					"#departmentSel" : {
						rule : "上级部门名称:required;",
						target : "#errorForDepartmentSel"
					},
					"#cityId" : "城市:required;",
					"#provinceId" : "省份:required;",
                    "#departmentType": "部门类型:required"
				}
			});
});

/**
 * 选择员工
 */
function chooseEmployee() { 
	window.open(basePath + "/EmployeeController/getAllEmployee.do?isChoose=true&callbackFn=chooseCallback&flag=true&custFlag=true");
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



var str=$("#departmentCode").val();
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
	
	
	var checkId=$("#id").val();
	if(null!=checkId&&''!=checkId){
		if(parentCode==str){
			alert("上级部门不能选当前部门");
			return false;
		}
		var list=getChildrenDepartment(checkId);
		if(null!=list&&list.length>0){
			for(var j=0;j<list.length;j++){
				if(parentCode==list[j].departmentCode){
					alert("上级部门不能选当前部门的下级部门");
					return false;
				}
			}
		}
		
	}
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
	var flag=false;
	submitWithValidator("departmentForm", function() {
	//$("#save").attr({"disabled":"disabled"});
	var parentcode=$("#parentCode").val();
	var chargeemp=$("#chargeEmp").val();
	$.ajax({
			url: basePath + "/DepartmentController/checkDepartmentTitle.do?parentCode="+parentcode+"&chargeEmp="+chargeemp,
			type: "post",
			async: false,
			success: function(data) {
//				if(data.status==1){
					flag=true;
//				}else{
//					alert(data.message);
//				}
			}
		});
  if(flag){
		
	$.ajax({
		async:false,
		url : basePath + "/DepartmentController/saveDepartment.do",
		type : "post",
		data : $("#departmentForm").serialize(),
		dataType : "json",
		success : function(data) {
			if (data.status == 1) {
				alert(data.message);
				var ids=parent.document.getElementById('ids').value;
				var parentDepartmentId=getParentDepartmentId(data.defaultDepartmentId);
				if("1"==data.flag){
					if(ids.indexOf(parentDepartmentId)>=0){
						ids=data.defaultDepartmentId+','+ids;
					}
				}else{
					var idsArr=ids.split(",");
					var lastId=idsArr[idsArr.length-1];
					if(ids.indexOf(parentDepartmentId)<0&&(data.defaultDepartmentId!=lastId)){
						var list=getChildrenDepartment(data.defaultDepartmentId);
						var childrenIds='';
						var childIds='';
						if(null!=list&&list.length>0){
							for(var m=0;m<list.length;m++){
								childrenIds=childrenIds+","+list[m].id;
							}
						}
						childrenIds=childrenIds+","+data.defaultDepartmentId;
						childrenIds=childrenIds.substring(1, childrenIds.length);
						var str=childrenIds.split(",");
						var arr=ids.split(",");
						var ids='';
						for(var i=0;i<arr.length;i++){
								for(var j=0;j<str.length;j++){
									if(arr[i]==str[j]){
										break;
									}
									if(j==str.length-1){
										ids=ids+","+arr[i];
									}
								
					           	}
						}
							if(ids.length!=0){
							 ids=ids.substring(1, ids.length);
							}
					}
				
				}
				parent.document.getElementById('ids').value=ids;
				window.parent.parent.frames["leftDepartmentTree"].location.reload();
				parent.refresh();
			} else {
				alert(data.message);
			}
		  }
    	});
	 }
	});
}

function updateChildParentCode(childIds,departmentId){
	$.ajax({
		async:false,
		url : basePath + "/DepartmentController/updateChildParentCode.do?childIds="+childIds,
		type : "post",
		data : {id:departmentId},
		dataType : "json",
		success : function(data) {
			
		}
	});
}

//获取所有子孙部门
function getChildrenDepartment(id){
	var list=new Array();
	$.ajax({
		async:false,
		url : basePath + "/DepartmentController/getSonDepartment.do",
		type : "post",
		data : {id:id},
		dataType : "json",
		success : function(data) {
			list=data.list;
		}
	});
	
	return list;
}

//获取所有子部门
function getChildDepartment(id){
	var list=new Array();
	$.ajax({
		async:false,
		url : basePath + "/DepartmentController/getChildDepartment.do",
		type : "post",
		data : {id:id},
		dataType : "json",
		success : function(data) {
			if(data.status==1){
				
				list=data.childList;
			}
		}
	});
	
	return list;
}

function getParentDepartmentId(id){
	var parentDepartmentId='';
	$.ajax({
		async:false,
		url : basePath + "/DepartmentController/getParentDepartmentId.do",
		type : "post",
		data : {id:id},
		dataType : "json",
		success : function(data) {
			parentDepartmentId=data;
		}
	});
	
	return parentDepartmentId;
}


function closePage() {
	parent.layer.close(parent.layer.getFrameIndex());
}