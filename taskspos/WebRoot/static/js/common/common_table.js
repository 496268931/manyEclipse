
//以下内容为原型专用,正式平台不需采用

	//页面下载完成之后初始化
	$(document).ready(function(){
		//列表着色
		if ($(".list_table") && $(".list_table").length > 0) {
			$(".list_table").renderTable();
		}
		if ($(".edit_table") && $(".edit_table").length > 0) {
			$(".edit_table").renderTable({type:"edit"});
		}
		$("a").each(function(i){
			if(($(this).attr("href") == "#" || $(this).attr("href") == "javascript:void(0)")){
				$(this).addClass("available_link");	
			}
		});
		
		$("#search_swith_btn").bind("click",function(){
				if($(".search_input").css("display") !=  "none"){					
					$(".search_input").hide();
					$(this).removeClass("search_swith_up");
					$(this).addClass("search_swith_down");
				}else{
					$(".search_input").show();
					$(this).removeClass("search_swith_down");
					$(this).addClass("search_swith_up");										
				}					
				
			});																 
		$(".btn_op").each(function(i){
			if($(this).html().length <= 3)
				$(this).addClass("btn_op2");	
			else
				$(this).addClass("btn_op4");	
		});
		//initTabs();			
		$(".grid_table").each(function(i){
			$(this).find("tbody td").hover(			
				function() {$(this).addClass("highlight"); },
				function() {$(this).removeClass("highlight");}
			);	
		});
	});;

//创建当前位置，根据navtreedata变量，
function createCurrPosition(menuid,title){
	var path= new Array();
	if(navtreedata){
		
	   for(var i=0; i<navtreedata.length; i++)  
	   {  
	   findnode(navtreedata[i],menuid,path);
	   } 		
	}
	var curr = "";

	for(var i=0; i<path.length; i++) { 		
		if(i == 0)
			curr =  path[i].text;
		else
			curr =  path[i].text + ">>" + curr;	
	}  

	if(!title)
		title = $("title").html();


	if(curr.indexOf(title) < 0)
		curr = curr + ">>" + title;	
	document.write("当前位置："+curr);
}

function findnode(node,id,path){	
	if(node.id == id){		
		path.push(node);
		return true;
	}else{
		if(node.ChildNodes){
			for(var i=0; i<node.ChildNodes.length; i++){
				if(findnode(node.ChildNodes[i],id,path)){
					path.push(node);
					return true;
				}
			}		
		}					
	}			
	return false;
}

function createPageHtml(totalcount,pagenum,pagesize){
	if(!totalcount) totalcount = 1 ; else totalcount = parseInt(totalcount);
	if(!pagenum) pagenum = 1 ; else pagenum = parseInt(pagenum);
	if(!pagesize) pagesize = 15 ; else pagesize = parseInt(pagesize);

	if(Math.round(totalcount % pagesize) == 0)
		var pagecount = Math.floor(totalcount / pagesize);		
	else
		var pagecount = Math.floor(totalcount / pagesize) + 1;

	var class_page_first = "page_first";
	var class_page_pre = "page_pre";
	var class_page_next = "page_next";
	var class_page_last = "page_last";


	if(pagenum < 2) class_page_first = "page_first_disabled";
	if(pagenum < 2) class_page_pre = "page_pre_disabled";
	if(pagenum >= pagecount) class_page_next = "page_next_disabled";
	if(pagenum >= pagecount) class_page_last = "page_last_disabled";
	
	document.write("    <div class='pages'>");
	document.write("	  	<ul>");
	document.write("				<li ><a href='#' class='"+class_page_first+"'></a></li>");
	document.write("				<li ><a href='#' class='"+class_page_pre+"'></a></li>");
	document.write("				<li class='currentpage'>第<span class='pagenum'>"+pagenum+"</span>页</li>");
	document.write("				<li ><a href='#' class='"+class_page_next+"'></a></li>");
	document.write("				<li ><a href='#' class='"+class_page_last+"'></a></li>");
	document.write("     	</ul>");
	document.write("    	<div class='pageinfo'>总共<span class='totalcount'>"+totalcount+"</span>条记录 <span class='pagecount'>"+pagecount+"</span>页</div>");
	document.write("   	</div>");


}


//以上内容为原型专用,正式平台不需采用
/////////////////////////////////////////


	function checkAll(chkallname,chkname){
		if ($("#"+chkallname).attr("checked") == true) {
			$("input[name='"+chkname+"']").each(function() {
				if(!$(this).attr("disabled")){
					$(this).attr("checked", true);
					$(this).parents(".list_table tbody tr").addClass("selected");
				}
			});
		} else {
			$("input[name='"+chkname+"']").each(function() {
				$(this).attr("checked", false);
				$(this).parents(".list_table tbody tr").removeClass("selected");
			});
		}
	}

	function isChecked(chkname){
		var t=0;
		$("input[name='"+chkname+"']").each(function() {
			if ($(this).attr("checked") == true){t=1;}
		});
		return t==1;
	}

	function CheckedCount(chkname){
		var t=0;
		$("input[name='"+chkname+"']").each(function() {
			if ($(this).attr("checked") == true){t++;}
		});
		return t;
	}
	
	function getAnchor(url){
		if(!url)
			url =  document.location.toString();
		index = url.indexOf('#');
		if(index >= 0){
		 	anchor = url.substring(index+1);
		  	return decodeURI(anchor);
		}else
			return "";
	}

	function getUrlParam(url){
		if(!url)
			url =  document.location.toString();
		index = url.indexOf('?');
		if(index >= 0){
			index2 = url.indexOf('#');
			if(index2 >= 0){
				alert(url.substring(index+1,index2-1));
			 	return decodeURI(url.substring(index+1,index2-1));
			}
		  	else
				return decodeURI(url.substring(index+1));
		}else
			return "";
	}
	
function portal_ajaxget(a){	
	if(a.getAttribute('ajaxtarget')){
		$('#'+a.getAttribute('ajaxtarget')).load(a.href);
	}else{
		window.location = a.href;
	}
}
	
	function checkForm(formData, jqForm){
		for (var i=0; i < formData.length; i++) { 		        
	  	var validate = $("#"+formData[i].name).attr("validate"); 
	    if(validate){	    
				label = $("label[for='"+formData[i].name+"']");
				label = (label.size() > 0) ? label.html() : formData[i].name;          	
				validate = eval("({"+validate+"})");		            			            	
				if(validate.required && !formData[i].value){
					alert(label + '不允许为空');	
					$("#"+formData[i].name)[0].focus();
					return false;
				}				
		  } 
		}
		return true;
	}
	


	
	function renderTextArea(id,cancel){			
		if(cancel)
			$("#"+id).lazy_xheditor(false);
		else{
			$("#"+id).lazy_xheditor(true,{tools:'GStart,Cut,Copy,Paste,Pastetext,GEnd,Separator,GStart,Blocktag,Fontface,FontSize,Bold,Italic,FontColor,Removeformat,GEnd,Separator,GStart,Align,List,Outdent,Indent,GEnd,Separator,GStart,Link,Img,GEnd,Separator,GStart,Fullscreen,About,GEnd'});
		}
	}
	
function initTabs(){
	$(".tab_content").hide();
	$("ul.tabs li").hover(
	  function () {
	  	$(this).addClass("hover");
	  },
	  function () {
	    $(this).removeClass("hover");
	  }
	);
	$("ul.tabs li").click(function() {
		showTab($(this));
		return false;
	});	 
	
}
function showTab(tab,reload){	
	$("ul.tabs li").removeClass("active");	
	$(tab).addClass("active");
	$(tab).show();
	$(".tab_content").hide();
	var activeTab = "#"+$(tab).attr("id")+"_c";
	$(activeTab).show();
	$(tab).find("a").blur();
	var href = $(tab).find("a").attr("href");
	if(href != '#'){			
		if(!$(activeTab).data("loaded") || reload){						
			var anchor = getAnchor(href);			
			if(anchor == "")
				$(activeTab).load(href);						
			else{
				$(activeTab).load(href,function(){					
					window.location.hash = '#'+anchor;														
				});
			}
			$(activeTab).data("loaded","1");
		}
	}	
}	

function pluginLoaded(plugin_id){
	if($('body').data('plugin_'+plugin_id))
		return true;
	else
		return false;
}

function loadPlugin(plugin_id,file,callback){
	if(!pluginLoaded(plugin_id)){
    var fileArray = [];
    typeof file == 'string' ? fileArray[0] = file: fileArray = file;
    var loading =  fileArray.length;
		//$.include(file,callback);
		$.include(file,function(){
			if(loading == 1)	
				callback();
			else
				loading--;
		});
		$('body').data('plugin_'+plugin_id,'1');
	}
}


(function($){	
	$.fn.renderTable = function(options){
		var type = "list";
		if(options)
			type = options.type;		
		$(this).find("tbody tr:odd").addClass('odd');
		$(this).find("tbody tr:even").addClass('even');
		$(this).find("tbody tr").hover(			
			function() {$(this).addClass("highlight"); },
			function() {$(this).removeClass("highlight");}
		);		
		if(type == "list"){
			$(this).find("tbody tr :checkbox[name$='array[]']").bind("change",function(){			
				if($(this).attr("checked"))
					$(this).parents(".list_table tbody tr").addClass("selected");
				else
					$(this).parents(".list_table tbody tr").removeClass("selected");
			})		
		}
	};		
	
	$.fn.lazy_dialog = function(options){		
		if(pluginLoaded('dialog')){
			$(this).dialog(options);		
		}else{
			var my = $(this);
			loadPlugin('dialog',['js/dialog/css/dialog.css','js/dialog/jquery.dialog.js'],function(data){
					my.dialog(options);			
			});			
		}
	};
	
	$.fn.lazy_xheditor = function(bInit,options){		
		//if(pluginLoaded('xheditor')){
		//	$(this).xheditor(bInit,options);				
		///}else{
		//	var my = $(this);
		//	loadPlugin('xheditor',['js/xheditor/xheditor_skin/default/ui.css','js/xheditor/xheditor.js'],function(data){			
		//			my.xheditor(bInit,options);			
		//	});			
		//}				
		$(this).xheditor(bInit,options);		
	}		
	
})(jQuery);
