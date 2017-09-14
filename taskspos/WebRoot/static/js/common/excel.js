var swfu;

//console.log(sessionid);

window.onload = function() {	
		
	var settings = {
		flash_url : basePath+"/static/plugins/upload/swfupload.swf",
		upload_url : basePath+"/MemberExcelImportController/memberExcelImport.do",
//		post_params: {"swfupload_sessionid" : sessionid},		
		file_size_limit : "100MB",
		file_types : "*.xls;*.xlsx",
		file_types_description : "All Files",
		file_upload_limit : 0,
		file_queue_limit : 0,
		custom_settings : {
			progressTarget : "fsUploadProgress",
			cancelButtonId : "btnCancel"
		},
		debug : false,

		// Button settings
		button_image_url : staticPath
				+ "/static/images/TestImageNoText_65x29.png",
		button_width : "65",
		button_height : "23",
		button_placeholder_id : "spanButtonPlaceHolder",
		button_text : '<span class="theFont">上传</span>',
		button_text_style : ".theFont { font-size: 12}",
		button_text_left_padding : 12,
		button_text_top_padding : 3,
		// The event handler functions are defined in handlers.js ; font-color:#000000
		file_queued_handler : fileQueued,
		file_queue_error_handler : fileQueueError,
		file_dialog_complete_handler : fileDialogComplete,
		upload_start_handler : uploadStart,
		upload_progress_handler : uploadProgress,
		upload_error_handler : uploadError,
		upload_success_handler : uploadSuccess,
		upload_complete_handler : uploadComplete,
		queue_complete_handler : queueComplete
	// Queue plugin event
	};

	swfu = new SWFUpload(settings);
};
function uploadSuccess(file, serverData) {	
//	console.log(typeof serverData);
	serverData = eval("("+serverData+")");
	var vResult = document.getElementById("sResult");
	vResult.innerHTML="";
	if(serverData.status)
		vResult.innerHTML = serverData.message;	
	else{
		serverData = eval(serverData);
		var str = '';
		for(var k in serverData){
			str += serverData[k] + '<br/>';
		}
		vResult.innerHTML = str;
	}
		//vResult.innerHTML ="文件校验未通过，上传失败";
	
	/*if(typeof(jResult.status)=='undefined')
		vResult.innerHTML =serverData+",文件校验未通过，上传失败";	
	else	
		vResult.innerHTML = jResult.message;*/			
	
	
}

function uploadError(file, errorCode, message) {
	var vResult = document.getElementById("sResult");
	vResult.innerHTML = "文件上传失败!" + message +"错误码:"+ errorCode;
}

$(function() {
	$("#templateDownload").on("click", templateDownload);
	$("#cancelBtn").on("click", cancelBtn);
});

function templateDownload() {
	var url = basePath + "/MemberExcelExportController/downloadExcel.do";
	$("#form1").attr("action", url);
	$("#form1").submit();
}

function cancelBtn() {
	/*swfu.cancelQueue();
	var vResult = document.getElementById("sResult");
	vResult.innerHTML = "";*/
	parent.refresh();
	closePage();
}
/**
 * 关闭遮罩层
 */
function closePage() {
	if (parent.layer.getFrameIndex != undefined) {
		parent.layer.close(parent.layer.getFrameIndex());
	} else {
		self.close();
	}
}

