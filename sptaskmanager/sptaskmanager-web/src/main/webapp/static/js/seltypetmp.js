$(document).ready(function(){
	$("#spi_search").click(function(){
		schSpiderConfig(1);
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
	
	
});


function schSpiderConfig(currPage){
	var showpage = $("#vp").attr("value");//每页显示数
	var totalResult=$("#spanHitCount").text();//命中数
	var totalPage=$("#pageCount").text();//所有页
	var spi_name =$("#spi_name").attr("value");
	var spi_taskpagestyletype = $("#spi_taskpagestyletype").attr("value");
	var currentPage = "pageHandle.currentPage="+currPage+"&pageHandle.showCount="+showpage+"&pageHandle.totalResult="+totalResult+"&pageHandle.totalPage="+totalPage+"&name="
	+spi_name+"&templatetype="+spi_taskpagestyletype;
	$.ajax({
		url:basePath + "/SpiderconfigController/schSpiderConfig.do",
		type:"post",
		data:currentPage,
		beforeSend:searchIng,
		complete:function(){
			endSearchIng();
			$.removeAttr("disabled");
		},
		success:handlesptmpView
	});

}

function handlesptmpView(data){
	var taskarr = "",detailhtm = "",toatlResult = "",currentPage = "",totalPage = "";
	if(data != null){
		aptmparr = data.sptmplist;
		toatlResult = data.totalResult;
		currentPage = data.currentPage;
		totalPage = data.totalPage;
		for(var j=0;j<aptmparr.length;j++){
			detailhtm += "<tr><td><input type='checkbox' name='checklist' id='checksource" + aptmparr[j].id+ "' value='" + aptmparr[j].id+ "'></input></td>" +
					    "<td>" +aptmparr[j].name+ "</td>" +
						"<td><a href='javascript:void(0);' id='spi_content_"+j+"'>"+aptmparr[j].dcontent+"</a></td>" +
					    "<td><a href='javascript:void(0);' id='spi_urlconfigrule_"+j+"'>"+aptmparr[j].urlconfigrule+"</a></td>" +
					    "<td>"+aptmparr[j].name+"</td>" +
					    "<td><a href='javascript:void(0);' id='spi_urlregex_"+j+"' title='"+aptmparr[j].urlregex+"'>"+aptmparr[j].urlregex+"</a></td>" +
					    "<td><a href='javascript:void(0);' id='spi_detailurlregex_"+j+"' title='"+aptmparr[j].detailurlregex+"'>"+aptmparr[j].detailurlregex+"</a></td>" +
					    "<td><a href='javascript:void(0);' id='spi_task"+j+"' onclick='addSpTask("+aptmparr[j].id+")'>新建任务</a>" +
					    "&nbsp;<a href='javascript:void(0);' id='spi_task"+j+"' onclick='createTask("+aptmparr[j].id+")'>生成任务</a>" +
					    "&nbsp;<a href='javascript:void(0);' id='spi_modify"+j+"' onclick='addspiderconfigWordHtml("+aptmparr[j].id+")'>修改</a></td>" +
					    "</tr>";
		}
		$('#spanHitCount').html(toatlResult);  
		$('#pageNum').html(currentPage);       
		$('#pageCount').html(totalPage);       
		$('#spiderconfiginfo tbody').html(detailhtm);
	}
}

function firstPage(){
	var currPage = parseInt($("#pageNum").text());
	if( currPage >1 ){
		schSpiderConfig("1");
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
	schSpiderConfig(currPage);
}

function nextPage(){
	var currPage = parseInt($("#pageNum").text()) + 1;
	var pageCount = parseInt($("#pageCount").text());
	if( currPage > pageCount ){
		alert("已是尾页");
		return;
	}
	schSpiderConfig(currPage);
}

function lastPage(){
	var currPage = parseInt($("#pageNum").text());
	var pageCount = parseInt($("#pageCount").text());
	if( currPage < pageCount ){
		schSpiderConfig(pageCount);
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
	schSpiderConfig(currPage);
}

function changesel(str){
	schSpiderConfig("1");
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
 				NavgationTree.add(st[i].nodeid,st[i].parnodeid,st[i].nodename,'javascript:showNavgationData(\''+st[i].nodeid+'\')','javascript:selMyClassify(\''+st[i].nodeid+'\'');
 			}
 			$("#divNavgationTree").html(NavgationTree.toString());
 			if(showNodeId==-1)
 				NavgationTree.openTo(NavgationTree.aNodes.length-1);
 			if(showNodeId>0)
 			NavgationTree.openTo(showNodeId,true);
 	  	}
 	  });
}

function selMyClassify(cfid,nodeid){
	var selnodechk = $('#'+nodeid).attr("checked");
	if(selnodechk == null || selnodechk == "" || selnodechk == "undefined"){
		$(".cx:checked").removeAttr("checked"); 
		$("#myclassifyid").attr("value", "");
	}else{
		$(".cx:checked").removeAttr("checked"); 
		$('#'+nodeid).attr("checked","true"); 
		$("#myclassifyid").attr("value", cfid);
	}
}

function showNavgationData(nodeid){
	//alert(nodeid);
	 
}
//新增初始化
function addspiderconfigWordHtml(ditem, iscopy) {
	alert("调用！");
}

//创建模板
function createTask(tmpid){
	var myclsid = $("#myclassifyid").attr("value");
	$("#sptmpid").attr("value",tmpid);
	if(myclsid == null || myclsid =="" || myclsid == "undefined"){
		alert("请选择界面左侧任务所在的分类，然后再生成该任务！！！");
		return "";
	}
	//生产任务信息
	var url = basePath + "/SpTaskController/showTaskCreatePage.do";
	$("#taskmanager").attr('action', url);
	$("#taskmanager").submit();
	//alert(myclsid + "|"+tmpid);
}

function addSpTask(taskparams,tmpid){
	 window.taskcanvasobj = null;
     window.taskcanvasobj = new taskCanvas();
	 window.taskcanvasobj.show();
	
	/*var myclsid = $("#myclassifyid").attr("value");
	$("#sptmpid").attr("value",tmpid);
	if(myclsid == null || myclsid =="" || myclsid == "undefined"){
		alert("请选择界面左侧任务所在的分类，然后再生成该任务！！！");
		return "";
	}
	//生产任务信息
	var url = basePath + "/SpTaskController/showTaskAddPage.do";
	$("#taskmanager").attr('action', url);
	$("#taskmanager").submit();
	//alert(myclsid + "|"+tmpid);
*/}
