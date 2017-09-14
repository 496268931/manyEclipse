//ajax方法
	function ajaxDataForm() {
		$.ajax({
			type : "post",//使用post方式访问后台 
			url : basePath+"/SubSystemController/updateSystem.do",//要访问的后台地址
			data:$('#subSystemInfo').serialize(),// 你的formid
			async:false,
			success : function(data) {
//				console.log(data);
				closePage();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(errorThrown);
			}
		});
	}
	
	//关闭页面
	function closePage() {
		parent.layer.close(parent.layer.getFrameIndex());
	}