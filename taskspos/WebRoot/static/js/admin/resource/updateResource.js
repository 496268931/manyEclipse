var setting = {
		view : {
			dblClickExpand : false
		},
		/* 			check: {
		 enable: true,
		 chkStyle: "radio",
		 radioType: "all"
		 }, */
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
 		if($("#resourceCode").val()==id){
 			alert("不能选择自己作为自己的父级资源，请重新选择!");
 			return null;
 		}
 		
		
		var cityObj = $("#citySel");
		cityObj.attr("value", v);
		if(level>0){
			//为父ID赋值
			$("#parentCode").val(id);
			$("#subSystemCode").val('');
		}else{
			$("#subSystemCode").val(id);
			$("#parentCode").val('');
		}
		//为资源级别赋值
		$("#resourceLevel").val(level+1);
		//为子系统ID赋值
		$("#subSystemId").val(subSystemId);
		
		if($("#resourceLevel").val()=='3'){
			//在此级别下资源有可能是url/按钮，所以需要显示资源类型选项
			document.getElementById("resourceTypeTr").style.display="";
		}else{
			document.getElementById("resourceTypeTr").style.display="none";
		}
		if($("#resourceLevel").val()=='4'){
			//在此级别下资源一定是按钮，所以不需要显示 展开/关闭选项
			document.getElementById("isOpenTr").style.display="none";
		}
		//资源类型如果为按钮，则不显示展开关闭项
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

	$(document).ready(function() {
		
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
						"#status":"状态:required;",
						"#resourceDesc":"资源描述;length[1~200];specialchar;"
					},
					valid: function(form){
				        //表单验证通过，提交表单到服务器
						$.ajax({
							type : "post",//使用post方式访问后台 
							url : basePath+"/ResourceController/updateResource.do",//要访问的后台地址
							data:$('#resourceInfo').serialize(),// 你的formid
							async:false,
							success : function(data) {
								alert(data.message);
								if(data.status==1){
									parent.refresh($("#subSystemId").val());
									closePageNoRefresh();
								}
							},
							error : function(XMLHttpRequest, textStatus, errorThrown) {
								alert(errorThrown);
							}
						});
				       }
				});  
		
			//资源类型如果为按钮，则不显示展开关闭项
			if($("#resourceType").val()=='0'){
				document.getElementById("isOpenTr").style.display="none";
				//默认关闭(展开也可)
				$("#isOpen").val('0');
			}else{
				document.getElementById("isOpenTr").style.display="";
			}
	});

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
		parent.refreshAll();
		parent.layer.close(parent.layer.getFrameIndex());
	}
	//关闭页面,不刷新
	function closePageNoRefresh() {
		parent.layer.close(parent.layer.getFrameIndex());
	}