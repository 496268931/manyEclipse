//关闭页面
	function closePage() {
		parent.layer.close(parent.layer.getFrameIndex());
	}
	
	$(document).ready(function() {
		
		  $("#subSystemInfo").validator(
				{
					stopOnError : true,
					timely : true,
					focusCleanup : true,
					rules:{
						myselfName: [/^[\w\u4e00-\u9fa5]+$/gi, "请输入中文 数字 字母 下划线"]
					},
					fields : {
						"#subSystemName":"子系统名称:required;length[1~16];specialchar;" +
								"remote["+basePath+ "/SubSystemController/checkSubSystemName.do, id]",
						"#subSystemDomain":"子系统域名:required;length[1~50];specialchar;",
						"#subSystemPort":"子系统端口:required;digits;length[1~6]",
						"#subSystemWeb":"子系统web名:required;length[1~50];specialchar;",
						"#isAdmin":"管理工具:required;",
						"#subSystemDesc":"子系统描述:length[1~50];specialchar;"
					},
					valid: function(form){
				        //表单验证通过，提交表单到服务器
						$.ajax({
							type : "post",//使用post方式访问后台 
							url : basePath+"/SubSystemController/saveSubSystem.do",//要访问的后台地址
							data:$('#subSystemInfo').serialize(),// 你的formid
							async:false,
							success : function(data) {
								alert(data.message);
								if(data.status==1){
									parent.refreshAll();
									closePage();
								}
							},
							error : function(XMLHttpRequest, textStatus, errorThrown) {
								alert(errorThrown);
							}
						});
				       }
				});  
		
	});