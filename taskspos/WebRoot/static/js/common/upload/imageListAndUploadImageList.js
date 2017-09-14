var swfu;
window.onload = function() {
	
	var settings = {
		flash_url : basePath+"/static/plugins/upload/swfupload.swf",
		upload_url: basePath+"/UploadController/saveImagesToPath.do?belongType="+$("#belongType").val(),
//		post_params: {"swfupload_sessionid" : sessionid},
		file_size_limit : "100MB",
		file_types : "*.jpg;*.png",
		file_types_description : "All Files",
		file_upload_limit : 0,
		file_queue_limit : 0,
		custom_settings : {
			progressTarget : "fsUploadProgress",
			cancelButtonId : "btnCancel"
		},
		debug: false,

		// Button settings
		button_image_url: staticPath+"/static/images/TestImageNoText_65x29.png",
		button_width: "65",
		button_height: "23",
		button_placeholder_id: "spanButtonPlaceHolder",
		button_text: '<span class="theFont">上传</span>',
		button_text_style: ".theFont { font-size: 12; font-color:#000000}",
		button_text_left_padding: 12,
		button_text_top_padding: 3,
		
		// The event handler functions are defined in handlers.js
		file_queued_handler : fileQueued,
		file_queue_error_handler : fileQueueError,
		file_dialog_complete_handler : fileDialogComplete,
		upload_start_handler : uploadStart,
		upload_progress_handler : uploadProgress,
		upload_error_handler : uploadError,
//			upload_success_handler : uploadSuccess,
		upload_success_handler : function uploadSuccess(file,serverData){
		try {
             var progress = new FileProgress(file, this.customSettings.progressTarget);
             progress.setComplete();
             progress.setStatus("Complete.");
             progress.toggleCancel(false);
				//追加回调数据
				var imageList = $("#imageList").val();
				if(imageList!=null && imageList!=""){
					imageList = imageList+"|"+serverData;
				}else{
					imageList = serverData;
				}
				$("#imageList").val(imageList);
				console.log("imageList="+$("#imageList").val());
				
            } catch (ex) {
                    this.debug("debug======"+ex);
            }
		},  
		upload_complete_handler : uploadComplete,

// 		queue_complete_handler : queueComplete	// Queue plugin event
		queue_complete_handler : function(){

			 $.ajax({
				type : "post",//使用post方式访问后台 
				url : basePath+$("#sreachAllList").val(),//要访问的后台地址
				data: "imageList="+$('#imageList').val(),// 你的formid
				async:false,
				success : function(data) {
					cloneTr(data);
					$('#imageList').val('');
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(errorThrown);
				}
			}); 
			
		}	// Queue plugin event
	};

	swfu = new SWFUpload(settings);
 };



$(document).keypress(function(event) {
    if (event.keyCode == 13) {
        return false;
    }
});
	//删除整行
	function removeTr(obj){
		if (confirm("是否确认删除？")) {
			$(obj).next().val('0');
			var imageUrl = $(obj).next().next().val();
			var id = $(obj).next().next().next().val();
			param = id+","+imageUrl;
			 $.ajax({
					type : "post",//使用post方式访问后台 
					url : basePath+$("#delete").val(),//要访问的后台地址
					data: {param:param},
					async:false,
					success : function(data) {
						$(obj).parent().parent().remove();
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(errorThrown);
					}
				});  
		}
	}
	
	$(function() {
		
		  $("#0_imageTr").hide();

		  $("#upload").click(function(){
			  addLayer('/UploadController/uploadImagePage.do?path='+$("#sreachListInDatabase").val());
		  });
		  $("#close").click(function(){
//			  window.close();
              window.opener.refreshPart($("#divId").val(),$("#sreachListInDatabase").val()+'?uuid='+$('#uuid').val()+'&belongId='+$("#belongId").val()+'&divId='+$("#divId").val());
              self.close();
		  });
		  
		  $("#save").click(function(){
			  var allTr = $('tr[name=imageTr]');
			  var arrayObj='';
			  
			  for(var i=0;i<allTr.length;i++){
				  if(i==0) continue;
				 var tr =  allTr.eq(i);
				  var imageDesc = tr.find("[name='imageDesc']").val();
				  var imageUrl = tr.find("[name='imageUrl']").val();
				  var imageType = tr.find("[name='imageType']").val();
				  var flag = tr.find("[name='flag']").val();
				  var imageId = tr.find("[name='imageId']").val();
				  var belongId = $("#belongId").val();
				  var uuid = $("#uuid").val();
				  var thumbnail = tr.find("[name='thumbnail']").val();
					
				  if(imageDesc==null) imageDesc='';
				  if(imageUrl==null) imageUrl='';
				  if(imageType==null) imageType='';
				  if(flag==null) flag='';
				  if(imageId==null) imageId='';
				  if(belongId==null) belongId='';
				  if(uuid==null) uuid='';
				  if(thumbnail==null) thumbnail='';
                  imageDesc = imageDesc.replace(/(\n)/g, "").replace(/(\r)/g, "");

				  if(imageDesc.trim()==''){
					  alert('图片描述不能为空！');
					  return;
				  }
				  if(imageType==''){
					  alert('图片类型不能为空！');
					  return;
				  }

				  var imageObj = '{"imageDesc":"'+imageDesc+'","imageUrl":"'+imageUrl+'","imageType":"'+imageType+'","flag":"'+flag+'","imageId":"'+imageId+'","belongId":"'+belongId+'","uuid":"'+uuid+'","thumbnail":"'+thumbnail+'"}';
				  if(arrayObj==''){
				  		arrayObj = imageObj;
				  }else{
					  arrayObj =arrayObj+","+imageObj;
				  }
			  }
			  if(arrayObj){
				  
			  arrayObj = "["+arrayObj+"]";

                  $("#save").attr({"disabled":"disabled"});
				   $.ajax({
						type : "post",//使用post方式访问后台 
						url : basePath+$("#saveOrUpdate").val(),//要访问的后台地址
						data: "imageObjList="+arrayObj,// 你的formid
						async:false,
						success : function(data) {
							alert(data.message);
                            $("#save").removeAttr("disabled");
							if(data.status=='1'){
								window.opener.refreshPart($("#divId").val(),$("#sreachListInDatabase").val()+'?uuid='+$('#uuid').val()+'&belongId='+$("#belongId").val()+'&divId='+$("#divId").val());
								self.close();
							}
						},
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							alert(errorThrown);
						}
					});  
			  }else{
				  alert("没有可上传的图片！");
			  }
		  });
	});
	//关闭页面
	function closePage() {
		parent.layer.close(parent.layer.getFrameIndex());
	}
	
	function addLayer(url){
		openIFrame(basePath+url,null,__resourcecode__);
	}

	function cloneTr(json){
		for(var i=0;i<json.length;i++){
//			console.log(basePath);
			console.log(json[i].thumbnail);
			var trLast = $('tr[name=imageTr]:last');
			trAfter = trLast.after('<tr name=imageTr>'+trLast.clone(true).html()+'</tr>');
			trAfter = $('tr[name=imageTr]:last');
			trAfter.find("[name='imagepath']").attr("src",ftpPath+json[i].imageUrl);
			trAfter.find("[name='imageDesc']").val(json[i].imageDesc);
			trAfter.find("[name='close']").attr("onclick","removeTr('"+(i+1)+"')");
			trAfter.find("[name='imageUrl']").val(json[i].imageUrl);
			trAfter.find("[name='imageId']").val('');
			trAfter.find("[name='imageType']").attr("value",json[i].imageType);
//			trAfter.find("[name='belongId']").val($("#belongId").val());
//			trAfter.find("[name='uuid']").val($("#uuid").val());
			trAfter.find("[name='thumbnail']").val(json[i].thumbnail);
		}
	}