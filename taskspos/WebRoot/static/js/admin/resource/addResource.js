$(function() {

		  $("#resourceInfo").validator(
				{
					stopOnError : true,
					timely : true,
					focusCleanup : true,
					rules:{
						myselfName: [/^[\w\u4e00-\u9fa5]+$/, "请输入中文 数字 字母 下划线"]
					},
					fields : {
						"#resourceName":"资源名称:required;length[1~25];specialchar;",
						"#resourceType":"资源类型:required;",
						"#url":"资源路径:length[1~200]",
						"#citySel":"所属资源:required;",
						"#status":"状态:required;",
						"#resourceDesc":"资源描述:length[1~200];specialchar;"
					},
					valid: function(form){
				        //表单验证通过，提交表单到服务器
						$.ajax({
							type : "post",//使用post方式访问后台 
							url : basePath+"/ResourceController/saveResource.do",//要访问的后台地址
							data:$('#resourceInfo').serialize(),// 你的formid
							async:false,
							success : function(data) {
								alert(data.message);
								if(data.status==1){
									parent.refresh($("#subSystemId").val());
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
	var setting = {
			view : {
				dblClickExpand : false
			},
			data : {
				key : {
					name : "name", //各级树显示的名字
					url : ""//点击不访问URL
				},
				simpleData : {
					enable : true,
					rootPId : null,
					idKey : "id",
					pIdKey : "pId"
	
				}
			},
			callback : {
				// 				beforeClick: beforeClick,
				onClick : onClick
			}
	};
	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
				.getSelectedNodes();
		nodes.sort(function compare(a, b) {
			return a.id - b.id;
		});
		var v = nodes[0].name;
		var id = nodes[0].id;
		var level = nodes[0].level;
		var subSystemId = nodes[0].subSystemId;
		if(level>=4){
			alert("不能选择层级大于或等于4的资源作为父节点，请重新选择!");
			return null;
		}
		
		var cityObj = $("#citySel");
		cityObj.attr("value", v);
		//如果level==0，则该节点为子系统
		if(level>0){
			//为父ID赋值
			$("#parentCode").val(id);
			//子系统编码置空,防止在页面多次选择，重复赋值
			$("#subSystemCode").val('');
		}else{
			$("#subSystemCode").val(id);
			//父节点编码置空,防止在页面多次选择，重复赋值
			$("#parentCode").val('');
		}
		//为资源级别赋值
		$("#resourceLevel").val(level+1);
		//为子系统ID赋值
		$("#subSystemId").val(subSystemId);
		//级别为3时才可选：按钮/url
		if($("#resourceLevel").val()=='3'){
//			$("#resourceTypeTr").style.display="";
			document.getElementById("resourceTypeTr").style.display="";
		}else{
			document.getElementById("resourceTypeTr").style.display="none";
			
		}
		
		//资源路径大于3级，则资源路径显示
		/*if($("#resourceLevel").val()>'3'&& $("#resourceType").val()!='0' ){
			document.getElementById("urlTr").style.display="";
		}else{
			document.getElementById("urlTr").style.display="none";
			
		}*/
		//如果资源级别为4，则该资源类型是按钮
		if($("#resourceLevel").val()=='4'){
			$("#resourceType").val('0');
			
		}
		//资源类型如果为按钮，则不显示展开关闭项，也不显示资源路径项
		if($("#resourceType").val()=='0'){
			document.getElementById("isOpenTr").style.display="none";
			//默认关闭(展开也可)
			$("#isOpen").val('0');
		}else{
			document.getElementById("isOpenTr").style.display="";
		}
	}

	//控制显示/隐藏  展开关闭按钮
	function isDispalyIsOpen(){
		if($("#resourceType").val()=='0'){
			document.getElementById("isOpenTr").style.display="none";
			//默认关闭(展开也可)
			$("#isOpen").val('0');
		}else{
			document.getElementById("isOpenTr").style.display="";
		}
	}
	
	function showMenu() {
		var cityObj = $("#citySel");
		var cityOffset = $("#citySel").offset();
		$("#menuContent").css({
			left : cityOffset.left + "px",
			top : cityOffset.top + cityObj.outerHeight() + "px"
		}).slideDown("fast");
		//访问后台资源地址，取得资源树JSON数据
		ajaxData();
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

	//ajax方法
	function ajaxData() {
		$.ajax({
			type : "post",//使用post方式访问后台 
			url : basePath + "/SubSystemController/getResourceTempList.do",//要访问的后台地址
			dataType : "json", //返回的数据类型
			success : function(data) {
//				console.log(data);
				$.fn.zTree.init($("#treeDemo"), setting, data);
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
	
