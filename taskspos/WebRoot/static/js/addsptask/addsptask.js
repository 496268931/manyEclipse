$(document).ready(function(){
	$("#addParamTreeBtn").click(function(){
		addParamDefAndValue();
	});
	
	$("#saveTaskBut").click(function(){
		saveTask();
	});
	
	/*$("#radObj").click(function(){
		radObjHandle();
	});*/
	
	//获取分类导航信息
	creatNavgationTree();
});

function addParamDefAndValue(){
	var paramValue = "",paramid = "",parentParamName = "",paramName = "",paramValue= "";
	var param_id = "",selArrType = "";
	paramid = $("#paramid").attr("value");
	if(paramid == null || paramid =="" || paramid == "undefined"){
		alert("请选择要定义参数的父参数！！");
		return "";
	}
	parentParamName = $("#parentParamName").attr("value");
	if(parentParamName == null || parentParamName =="" || parentParamName == "undefined"){
		alert("请选择要定义参数的父参数！！");
		return "";
	}
	paramName = $("#paramName").attr("value");
	if(paramName == null || paramName =="" || paramName == "undefined"){
		alert("请输入定义的参数名！！");
		return "";
	}
	paramValue = $("#paramContent").attr("value");
	var paramclass =$('input:radio[name="Fruit"]:checked').val();
	if(paramclass == null || paramclass =="" || paramclass == "undefined"){
		alert("请选择定义的变量对应的数据类型！！");
		return "";
	}
	
	if(paramclass == 23){
		$("#paramContent").attr("value","");
	}else if(paramclass == 18){
		paramValue = $("#seldatetime").attr("value");
		if(paramValue == null || paramValue =="" || paramValue == "undefined"){
			alert("请输入定义参数的值");
			return "";
		}
	}else if(paramclass == 24){//需要处理日期格式
		selArrType = $("#selArrType").attr("value");
		if(selArrType=="24"){
			alert("数组中不可再嵌套数组!!");
			return "";
		}
		if(selArrType == "23"){
			$("#paramContent").attr("value","");
		}else{
			paramValue = $("#paramContent").attr("value");
		}
	}else{
		paramValue = $("#paramContent").attr("value");
		if(paramValue == null || paramValue =="" || paramValue == "undefined"){
			alert("请输入定义参数的值");
			return "";
		}
	}

	if(paramobj != null && paramobj != "" && paramobj != "undefined"){
		param_id = getID();
		paramobj.push(new addParaTreeNode(param_id,paramName,paramid,paramValue,paramclass,selArrType));
	}
	reCreateNavgationTree(1);
	//alert("数据类型值：" + paramclass + "|id=" + paramid);
	
}

function radObjHandle(){
	var paramclass =$('input:radio[name="Fruit"]:checked').val();
	if(paramclass != 23){
		$("#paramContent").attr("readonly",false);
	}else{
		$("#paramContent").attr("value","");
		$("#paramContent").attr("readonly",true);
	}
	
}

function showParamName(nodeid,nodename){
	//alert("nodeid=" + nodeid + "|nodename=" + nodename);
	$("#paramid").attr("value",nodeid);
	$("#parentParamName").attr("value",nodename);
	$("#paramName").attr("value","");
	$("#paramContent").attr("value","");
	$("input[name=Fruit]").removeAttr("checked");
	
}


function creatNavgationTree(showNodeId){
	var NavgationTree = null;
	NavgationTree = new dTree('NavgationTree');
	$.ajax({
		url:basePath + "/SpiderconfigController/getParamesTaskTree.do",
		type: 'POST',
		dataType: 'json',
		data:"userid=wxd",
		error:function(){
			//alert('Error loading ');
		},
		success: function(data){
			paramobj = null;
 			var st=data.taskcfgtemplist;
 			paramobj = st;
 			NavgationTree.add("0",-1,"TaskParameter");
 			for( i=0;i<st.length;i++){
 				NavgationTree.add(st[i].nodeid,st[i].parnodeid,st[i].nodename,st[i].paramvalue,st[i].paramtype,st[i].paramextype,'javascript:showParamName(\''+st[i].nodeid+'\',\''+st[i].nodename+'\')','javascript:selMyClassify(\''+st[i].nodeid+'\'');
 			}
 			$("#taskParamesTree").html("");
 			$("#taskParamesTree").html(NavgationTree.toString());
 			if(showNodeId==-1)
 				NavgationTree.openTo(NavgationTree.aNodes.length-1);
 			if(showNodeId>0)
 			NavgationTree.openTo(showNodeId,true);
 	  	}
 	  });
}

function addParaTreeNode(nodeid,nodename,parnodeid,paramvalue,paramtype,paramextype,usrid){
	this.hitnum = 1;
	this.isextend = "Y";
	this.nodeid  =  nodeid;
	this.nodename = nodename;
	this.parnodeid = parnodeid;
	this.paramvalue = paramvalue;
	this.paramtype = paramtype;
	this.paramextype = paramextype;
	this.userid = "wxd";
}

function reCreateNavgationTree(showNodeId){
	var NavgationTree = null;
	NavgationTree = new dTree('NavgationTree');
	NavgationTree.add("0",-1,"TaskParameter");
	if(paramobj != null && paramobj != "" && paramobj != "undefined"){
		for( i=0;i<paramobj.length;i++){
			NavgationTree.add(paramobj[i].nodeid,paramobj[i].parnodeid,paramobj[i].nodename,paramobj[i].paramvalue,paramobj[i].paramtype,paramobj[i].paramextype,'javascript:showParamName(\''+paramobj[i].nodeid+'\',\''+paramobj[i].nodename+'\')','javascript:selMyClassify(\''+paramobj[i].nodeid+'\'');
		}
		$("#taskParamesTree").html(NavgationTree.toString());
		if(showNodeId==-1)
			NavgationTree.openTo(NavgationTree.aNodes.length-1);
		if(showNodeId>0)
		NavgationTree.openTo(showNodeId,true);
	}
}

function saveTask(){
	var query = {};
	 query.tasktype = 2;
     query.task = 20;
     query.taskid = $("#taskid").attr("value");
	 query.task_def=JSON.stringify(paramobj);
	 $.ajax({
	 	url:basePath + "/SpTaskController/saveTask.do",
 		type:"post",
 		data:$.param(query),
 		beforeSend:searchIng,
 		complete:function(){
 			endSearchIng();
 			$.removeAttr("disabled");
 		},
 		success:handleTaskSaved
 	});
}

function handleTaskSaved(data){
	var task_id = data.taskid;
	if(task_id !=null && task_id != "" && task_id != "undefined"){
		$("#taskid").attr("value",task_id);
		//生产父类和子类树
		var showNodeId = 1; 
		var taskNavgationTree = null;
		taskNavgationTree = new taskTree('taskNavgationTree');
		taskNavgationTree.add("0",-1,"tasktree");
		taskNavgationTree.add(task_id,0,"父任务",null,null,null,null,'javascript:selTaskTree(\''+task_id+'\',\''+0+'\'');
		$("#parenttochildtree").html("");
		$("#parenttochildtree").html(taskNavgationTree.toString());
		
		if(showNodeId==-1)
			taskNavgationTree.openTo(taskNavgationTree.aNodes.length-1);
		if(showNodeId>0)
			taskNavgationTree.openTo(showNodeId,true);
	}
}

function selTaskTree(taskid,childid,tagid){
	var selnodechk = $('#'+tagid).attr("checked");
	if(selnodechk == null || selnodechk == "" || selnodechk == "undefined"){
		$(".taskcx:checked").removeAttr("checked"); 
		$("#tagid").attr("value","");
	}else{
		$(".taskcx:checked").removeAttr("checked"); 
		$('#'+tagid).attr("checked","true"); 
		$("#taskid").attr("value",taskid);
		$("#childtaskid").attr("value",childid);
		$("#tagid").attr("value",tagid);
	}
	//调用选中任务的配置信息显示在右侧编辑框中
	
	
}

function newNavgationPage(){
	var taskid,childtaskid;
	var tagid = $("#tagid").attr("value");
	if(tagid == null || tagid == "" || tagid == "undefined"){
		alert("请选择新建子任务对应的父任务节点！");
		return "";
	}
	if(confirm("你确定要新建对应子任务吗？")){
		alert("创建子任务");
		taskid = $("#taskid").attr("value");
		childtaskid = $("#childtaskid").attr("value");
		if(taskid != null && taskid != "" && taskid != "undefined" && childtaskid != null && childtaskid != "" && childtaskid != "undefined"){
			//创建子任务,传递任务ID、当前任务ID，然后返回结果
			var querystr = "taskid="+taskid + "&childtaskid=" + childtaskid + "&userid=wxd";
			$.ajax({
				url:basePath + "/SpTaskController/saveChildTask.do",
				type: 'POST',
				dataType: 'json',
				data:querystr,
				error:function(){
					alert('Error loading');
				},
				success:handleChildTaskSaved
		 	  });
		}else{
			alert("请从该任务管理界面！");
		}
     }else{
         return "";
    }
	//$("#tagid").attr("value","");
}

function handleChildTaskSaved(data){
	var cp_st = data.cp_task;
	var showNodeId = 1;
	taskNavgationTree = null;
	taskNavgationTree = new taskTree('taskNavgationTree');
	taskNavgationTree.add("0",-1,"tasktree");
	for( i=0;i<cp_st.length;i++){
		taskNavgationTree.add(cp_st[i].nodeid,cp_st[i].parnodeid,cp_st[i].nodename,'javascript:showParamName(\''+cp_st[i].nodeid+'\',\''+cp_st[i].nodename+'\')',null,null,null,'javascript:selTaskTree(\''+cp_st[i].nodeid+'\',\cp_st'+cp_st[i].parnodeid+'\'');
	}
	$("#parenttochildtree").html("");
	$("#parenttochildtree").html(taskNavgationTree.toString());
	
	if(showNodeId==-1)
		taskNavgationTree.openTo(taskNavgationTree.aNodes.length-1);
	if(showNodeId>0)
		taskNavgationTree.openTo(showNodeId,true);
	alert("生产子任务成功！");
}

function delNavgationTree(){
	if(confirm("你确定要删除该任务和其对应的子任务吗，删除后将不可恢复？")){
		alert("删除子任务子任务");
		taskid = $("#taskid").attr("value");
		childtaskid = $("#childtaskid").attr("value");
     }else{
         return "";
    }
}
