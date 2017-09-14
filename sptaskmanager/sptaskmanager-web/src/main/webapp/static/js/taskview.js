$(document).ready(function(){
	$("#schTaskBtn").click(function(){
		schTask(1);
	});
	
	$("#fp").click(function(){
		firstPage();		
	});
   	$("#pp").click(function(){
		prePage();		
	});
	$("#np").click(function(){
		nextPage();	
	});
	$("#lp").click(function(){		
		lastPage();
	});
	$("#btnGo").click(function(){
			goPage();
	});
    
	//获取分类导航信息
	creatNavgationTree();
	
	//跳转到任务配置
	$("#newTaskBtn").click(function(){
		creatNewTask();
	});
	
});

var deletebtn = "<input type='button' value='删除' name='deleteitem' style='background-color:#2E6E9E;color:#FFFFFF;' />";
var deleteselectedbtn = "<input type='button' value='取消' name='deleteitemselected' style='background-color:#EA3D3D;color:#FFFFFF;' />";

function schTask(currPage){
	var showpage = $("#vp").attr("value");//每页显示数
	var totalResult=$("#spanHitCount").text();//命中数
	var totalPage=$("#pageCount").text();//所有页
	
	var task = $("#seltask").attr("value");
	var taskpagestyletype = $("#seltaskpagestyletype").attr("value");
	var tasklevel = $("#seltasklevel").attr("value");
	var taskstatus = $("#seltaskstatus").attr("value");
	var machine = $("#selmachine").attr("value");

	var currentPage = "pageHandle.currentPage="+currPage+"&pageHandle.showCount="+showpage+"&pageHandle.totalResult="+totalResult+"&pageHandle.totalPage="+totalPage+"&task="
	+task+"&taskpagestyletype="+taskpagestyletype+"&tasklevel="+tasklevel+"&taskstatus="+taskstatus
	+"&machine="+machine;
	$.ajax({
		url:basePath + "/SpTaskController/schTaskInfo.do",
		type:"post",
		data:currentPage,
		beforeSend:searchIng,
		complete:function(){
			endSearchIng();
			$this.removeAttr("disabled");
		},
		success:handleTaskView
	});
}

function handleTaskView(data){
	var taskarr = "",taskstatus = "",tasklx = "",taskid = "";
	var detailhtm = "",toatlResult = "",currentPage = "",totalPage = "";
	var ormark = "",ormarkstr = "",taskparams = "",image = "";	
	
	if(data != null){
		taskarr = data.tasklist;
		toatlResult = data.totalResult;
		currentPage = data.currentPage;
		totalPage = data.totalPage;
		for(var j=0;j<taskarr.length;j++){
			taskid = taskarr[j].id;
			taskstatus = taskarr[j].taskstatus;
			tasklx = taskarr[j].task;
			//删除取消
			if (taskstatus != 1){
				detailhtm += "<tr><td><span id='"+taskid+"'>"+deletebtn+"</span></td>";
            }else{
            	detailhtm += "<tr><td></td>";
            }
			//id
			detailhtm += "<td>" + taskid + "</td>";
			//task
			detailhtm += "<td>" + tasklx + "</td>";
			//内容类型
			detailhtm += "<td>" + taskarr[j].taskpagestyletype + "</td>";
			//tasklevel
			detailhtm += "<td>" + taskarr[j].tasklevel + "</td>";
			detailhtm += "<td>" + getTaskStatus(taskarr[j].taskstatus) + "</td>";
			detailhtm += "<td>" + taskarr[j].datastatus + "</td>";
			detailhtm += "<td>" + timeToStr(taskarr[j].activatetime) + "</td>";
			detailhtm += "<td>" + timeToStr(taskarr[j].starttime) + "</td>";
			detailhtm += "<td>" + taskarr[j].tenantid + "</td>";
			detailhtm += "<td>" + taskarr[j].userid + "</td>";
			detailhtm += "<td>" + taskarr[j].machine + "</td>";
			detailhtm += "<td>" + taskarr[j].remarks + "</td>";

			//条件语句
			detailhtm += "<td><a href='javascript:void(0);' onclick='onDetailClick(this,"+taskid+","+taskarr[j].task+","+taskarr[j].datastatus+","+taskarr[j].taskparams+", "+taskarr[j].local+", "+taskarr[j].remote+", "+taskarr[j].conflictdelay+")'>详情</a>&nbsp;|&nbsp;";
			if(taskstatus == -1){
				detailhtm += '<a href="javascript:void(0);" onclick="onOPTButtonClick('+taskid+',1)">取消</a>';
			}else if(taskstatus == 0){
				detailhtm += '<a href="javascript:void(0);" onclick="onOPTButtonClick('+taskid+',2)">取消</a>';
			}else if (taskstatus == 1){
				detailhtm += '<a href="javascript:void(0);" onclick="onOPTButtonClick('+taskid+',-1)">停止</a>';
	            if (taskarr[j].timeout != null && taskarr[j].timeout != 0){
	            	detailhtm += '&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="onOPTButtonClick('+taskid+',0)">召回</a>';
	            }
			}else if(taskstatus == 4){
				detailhtm += '<a href="javascript:void(0);" onclick="onOPTButtonClick('+taskid+',2)">取消</a>';
			}else if(taskstatus == 7){
				detailhtm += '<a href="javascript:void(0);" onclick="onOPTButtonClick('+taskid+',-71)">继续</a>';
				detailhtm += '&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="onOPTButtonClick('+taskid+',-72)">重试</a>';
			}else if (taskstatus == 6){
				detailhtm += '<a href="javascript:void(0);" onclick="onOPTButtonClick('+taskid+',1)">已验证</a>';
				taskparams = JSON.parse(taskarr[j].taskparams);
				if (taskparams.scene && taskparams.scene.veriimage){
					image = taskparams.scene.veriimage.substring(taskparams.scene.veriimage.lastIndexOf('/') + 1);
					detailhtm += '&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="openVeriCodeDiv('+taskid+',\''+image+'\')">验证码</a>';
				}
			}else {
				detailhtm += '<a href="javascript:void(0);" onclick="onOPTButtonClick('+taskid+',0)">启动</a>';
			}
			
			if(tasklx == config.TASK_KEYWORD || tasklx == config.TASK_WEIBO || tasklx==config.TASK_WEBPAGE){ //抓取关键词, 抓取微博, 增加复制功能
				ormark = {"rm":taskarr[j].remarks};
				ormarkstr = JSON.stringify(ormark);
				detailhtm += "&nbsp;|&nbsp;<a href='javascript:void(0);' onclick='openTaskDiv(\"divaddspt\", "+taskarr[j].tasklevel+","+taskarr[j].task+","+taskarr[j].taskparams+","+taskarr[j].activatetime+" ,"+ormarkstr+", "+taskarr[j].local+", "+taskarr[j].remote+", "+taskarr[j].conflictdelay+", "+taskarr[j].taskpagestyletype+")'>复制</a>";
			}else if(tasklx == config.TASK_COMMON){
				detailhtm += "&nbsp;|&nbsp;<a href='javascript:void(0);' onclick='openCommonTaskDiv("+taskarr[j].taskparams+")'>复制</a>";
				detailhtm += "&nbsp;|&nbsp;<a href='javascript:void(0);' onclick='exportCommonTask("+taskid+", "+taskarr[j].taskparams+")'>导出</a>";
            }else if(tasklx == config.TASK_MIGRATEDATA){
				ormark = {"rm":taskarr[j].remarks};
				ormarkstr = JSON.stringify(ormark);
				detailhtm += "&nbsp;|&nbsp;<a href='javascript:void(0);' onclick='openTaskDiv(\"divaddmit\", "+taskarr[j].tasklevel+","+taskarr[j].task+","+taskarr[j].taskparams+","+taskarr[j].activatetime+" ,"+ormarkstr+", "+taskarr[j].local+", "+taskarr[j].remote+", "+taskarr[j].conflictdelay+")'>复制</a>";
			}
			detailhtm +="</td></tr>";
		}
		$('#spanHitCount').html(toatlResult);  
		$('#pageNum').html(currentPage);       
		$('#pageCount').html(totalPage);       
		$('#mytbl tbody').html(detailhtm);
	}
}


function firstPage(){
	var currPage = parseInt($("#pageNum").text());
	if( currPage >1 ){
		schTask("1");
	}else{
		alert("已是首页");
	}
}

function prePage(){
	var currPage = parseInt($("#pageNum").text()) - 1;
	if( currPage<1){
		alert("已是首页");
		return;
	}
	schTask(currPage);
}

function nextPage(){
	var currPage = parseInt($("#pageNum").text()) + 1;
	var pageCount = parseInt($("#pageCount").text());
	if( currPage > pageCount ){
		alert("已是尾页");
		return;
	}
	schTask(currPage);
}

function lastPage(){
	var currPage = parseInt($("#pageNum").text());
	var pageCount = parseInt($("#pageCount").text());
	if( currPage < pageCount ){
		schTask(pageCount);
	}else{
		alert("已是尾页");
	}
}

function goPage(){
	var currPage;
	if(!numCheck($("#txtPageNum"))){
		alert("请输入正确的页数！");
		return false;
	}
	try{
		currPage = parseInt($("#txtPageNum").attr("value").trim());
	}catch(err){
		alert("请输入正确的页数！");
		return;
	}
	var pageCount = parseInt($("#pageCount").text());
	if( currPage > pageCount ){
		alert("请输入正确的页数！");
			return;
	}
	schTask(currPage);
}

function changesel(str){
	schTask("1");
}

function creatNavgationTree(showNodeId){
	NavgationTree = new dTree('NavgationTree');
	$.ajax({
		url:basePath + "/NavigationInfo/getNavigation.do",
		type: 'POST',
		dataType: 'json',
		data:"userid=wxd",
		error:function(){
			//alert('Error loading ');
		},
		success: function(data){
 			var st=data.navigationlist;
 			NavgationTree.add("0",-1,"Navigation");
 			for( i=0;i<st.length;i++){
 				NavgationTree.add(st[i].nodeid,st[i].parnodeid,st[i].nodename,'javascript:showNavgationData(\''+st[i].nodeid+'\')');
 			}
 			$("#divNavgationTree").html(NavgationTree.toString());
 			if(showNodeId==-1)
 				NavgationTree.openTo(NavgationTree.aNodes.length-1);
 			if(showNodeId>0)
 			NavgationTree.openTo(showNodeId,true);
 	  	}
 	  });
}

function showNavgationData(){
	alert("search by my classify");
};

function creatNewTask(){
	var url = basePath + "/NavigationInfo/showNavigationPage.do";
	$("#taskmanager").attr('action', url);
	$("#taskmanager").submit();
}


