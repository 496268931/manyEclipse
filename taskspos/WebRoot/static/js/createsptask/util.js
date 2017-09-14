function myParamGenerator(paramrule, successResult, selectedVal, showName){
	var datafromfield = ""; //存来自父任务out字段的下拉菜单html
    var paramdivnum = 0;
	var paramObjToHtml = function(paramObj, initVal){
        paramdivnum++;
		var rethtml = "";
		rethtml +="<div name='paramdiv' class='search' style='vertical-align: middle;' paramdivnum='"+paramdivnum+"' col_type='"+paramObj.col_type+"' code='"+paramObj.name+"' ><span style='text-align:right;width:70px;display:inline-block;'>"+paramObj.label+"：</span>";
		alert(paramObj.col_type);
		switch(parseInt(paramObj.col_type, 10)){
			case SE_TYPE_STRING:
			case SE_TYPE_INT32:
				alert(paramObj.name);
                var tmp = initVal ? initVal[paramObj.name] : "";
                rethtml += "<input id='urltpl_"+paramdivnum+"_"+paramObj.name+"_"+paramObj.col_type+"' code='"+paramObj.name+"'  value='"+tmp+"'/>";
                alert(rethtml);
				break;
            case SE_TYPE_TIMESTAMP:
                var tmp = initVal ? initVal[paramObj.name] : "";
                rethtml += '<input id="urltpl_'+paramdivnum+'_'+paramObj.name+'_'+paramObj.col_type+'" code="'+paramObj.name+'" value="'+formatTime('yyyy-MM-dd hh:mm', tmp)+'" class="Wdate" type="text" readonly="readonly" style="width:150px;" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\'})" />';
                break;
            case SE_TYPE_ARRAY:
                //rethtml += "<textarea id='urltpl"+paramObj.name+"' name='urltpl"+paramObj.name+"' rows='15' cols='20'></textarea>";
				//数组容器
				//rethtml +="<div id='urltpl"+paramObj.name+"_div' name='urltpl"+paramObj.name+"_div' groupcount='1'>"
				rethtml += "<div id='urltpl_"+paramdivnum+"_"+paramObj.name+"_"+paramObj.col_type+"' code='"+paramObj.name+"' style='border:1px solid red;'>";
                paramObj.col_type_ex.ele_col.label = '数组';
                paramObj.col_type_ex.ele_col.name = 'ele_col';
                rethtml += paramObjToHtml(paramObj.col_type_ex.ele_col);
                rethtml += "<div><input type='button' id='urltpl_"+paramdivnum+"_"+paramObj.name+"_"+paramObj.col_type+"_btn' name='urltpl_"+paramObj.name+"_"+paramObj.col_type+"_btn' paramdivnum='"+paramdivnum+"' value='新增'/></div>";
                var valHtml = '';
                if(initVal && initVal[paramObj.name]){
                    for(var i=0,ilen=initVal[paramObj.name].length;i<ilen;i++){
                        valHtml += "<span class='selwordsbox'><span class='useritem' code='"+JSON.stringify(initVal[paramObj.name][i])+"'>"+initVal[paramObj.name][i]+"</span><a class='useritem_a' onclick='cancelSelected(this)'>×</a></span>";
                    }
                }
                rethtml += "<div>已添加的值:<span id='urltpl_"+paramdivnum+"_"+paramObj.name+"_"+paramObj.col_type+"_added'>"+valHtml+"</span></div>";
				rethtml += "</div>";
				//rethtml +="</div>";
                $("body").undelegate("input[name=urltpl_"+paramObj.name+"_"+paramObj.col_type+"_btn]", "click");
                $("body").delegate("input[name=urltpl_"+paramObj.name+"_"+paramObj.col_type+"_btn]", "click", function(){
                    var valdivnum = $(this).parent().parent().parent().attr("paramdivnum");
                    var addeddivnum = $(this).attr("paramdivnum");
                    //var tmparr = [];
                    var tmpobj = {};
                    var paramNodes = [];
                    getUserParamVal(valdivnum, paramObj.col_type, paramObj.name, tmpobj, paramNodes);
                    var disname = [];
                    if(typeof tmpobj.ele_col == "string" || typeof tmpobj.ele_col == "number"){
                        disname =  tmpobj.ele_col;
                    }
                    else{
                        for(var titem in tmpobj.ele_col){
                            if(typeof tmpobj.ele_col[titem] == "string" || typeof tmpobj.ele_col[titem] == "number"){
                                disname.push(tmpobj.ele_col[titem]); 
                            }
                        }
                    }
                    var html = "<span class='selwordsbox'><span class='useritem' code='"+JSON.stringify(tmpobj.ele_col)+"' >"+disname+"</span><a class='useritem_a' onclick='cancelSelected(this)'>×</a></span>";
                    $("#urltpl_"+addeddivnum+"_"+paramObj.name+"_"+paramObj.col_type+"_added").append(html);
                });
                break;
			case SE_TYPE_OBJECT:
				//对象容器
				//rethtml +="<div id='urltpl_"+paramObj.name+"_"+paramObj.col_type+"' name='urltpl"+paramObj.name+"_div' groupcount='1'>"
				rethtml += "<div id='urltpl_"+paramdivnum+"_"+paramObj.name+"_"+paramObj.col_type+"' groupnum='1' style='border:1px solid red;'>";
                if(paramObj.col_type_ex.sc_map){
					for(var item in paramObj.col_type_ex.sc_map){
	                    paramObj.col_type_ex.sc_map[item].label = item; //添加上显示标签
	                    paramObj.col_type_ex.sc_map[item].name = item; //添加上显示标签
	                    var tmp = null;
	                    if(initVal){
	                        if(paramObj.col_type_ex.sc_map[item].col_type == SE_TYPE_OBJECT){
	                            tmp = initVal[item];
	                        }
	                        else{
	                            tmp = initVal;
	                        }
	                    }
	                    rethtml += paramObjToHtml(paramObj.col_type_ex.sc_map[item], tmp);
	                }
                }
				rethtml += "</div>";
				//rethtml +="</div>";
				break;
			default:
				break;
		}
		rethtml +="</div>";
		return rethtml;
	};
	var initParamGenerator = function(){
		if(showName == undefined){
			showName = "";
		}
		$("#urltplbox").remove();
		var urltplhtml = "";
		urltplhtml +="<fieldset>";
		urltplhtml +="<legend>"+showName+"</legend>";
		urltplhtml +="<div id='paramtpldiv'>";
        /*
        for(var item in paramrule){
            urltplhtml += paramObjToHtml(paramrule[item]);
        }
        */
        urltplhtml += paramObjToHtml(paramrule, selectedVal);
		urltplhtml +="</div>";
		urltplhtml +="</fieldset>";
		$("<div id='urltplbox' title='"+showName+"列表'></div>").insertAfter("body");
		$("#urltplbox").append(urltplhtml);
		$("#urltplbox").dialog({
			autoOpen: true,
			modal:true,
			width:550,
			buttons:{
				"确定":function(){
                    var tmpobj = {};
                    var col_type = $("#paramtpldiv").children('div[name=paramdiv]').attr("col_type");
                    var code = $("#paramtpldiv").children('div[name=paramdiv]').attr("code");
                    var num = $("#paramtpldiv").children('div[name=paramdiv]').attr("paramdivnum");
                    var paramNodes = [];
                    var ret = getUserParamVal(num, col_type, code, tmpobj, paramNodes);
                    if(ret){
                        successResult(tmpobj, paramNodes);
                        $("#urltplbox").dialog("close");
                    }
				},
				"取消":function(){
					$("#urltplbox").dialog("close");
				}
			},
			close:function(){
			}
		});
	};
    var getUserParamVal = function(paramdivnum, col_type, code, tmpobj, paramNodes){
        var ret = true;
        $("#urltpl_"+paramdivnum+"_"+code+"_"+col_type+"").children("div[name=paramdiv]").each(function(i, item){
            var code = $(item).attr("code");
            var col_type = $(item).attr("col_type");
            var num = $(item).attr("paramdivnum");
            switch(parseInt(col_type, 10)){
                case SE_TYPE_STRING:
                    var id = "urltpl_"+num+"_"+code+"_"+col_type+"";
                    var code = $("#"+id+"").attr("code");
                    tmpobj[code] = $("#"+id+"").val();
                    var tmpnodes = {};
                    tmpnodes.name = $("#"+id+"").val();
                    paramNodes.push(tmpnodes);
                    break;
                case SE_TYPE_INT32:
                    var id = "urltpl_"+num+"_"+code+"_"+col_type+"";
                    var code = $("#"+id+"").attr("code");
                    var tmpval = parseInt($("#"+id+"").val(), 10);
                    if(isNaN(tmpval)){
                        //alert('请输入数字!');
                        $("#"+id+"").val('请输入数字!');
                        ret = false;
                        return false;
                    }
                    tmpobj[code] = tmpval;
                    var tmpnodes = {};
                    tmpnodes.name = tmpval;
                    paramNodes.push(tmpnodes);
                    break;
                case SE_TYPE_TIMESTAMP:
                    var id = "urltpl_"+num+"_"+code+"_"+col_type+"";
                    var code = $("#"+id+"").attr("code");
                    tmpobj[code] = getTimeSec($("#"+id+"").val());
                    var tmpnodes = {};
                    tmpnodes.name = $("#"+id+"").val();
                    paramNodes.push(tmpnodes);
                    break;
                case SE_TYPE_ARRAY:
                    var id = "urltpl_"+num+"_"+code+"_"+col_type+"";
                    var code = $("#"+id+"").attr("code");
                    tmpobj[code] = [];
                    var cid = $($("#"+id+"").children().get(2)).children('span').attr("id");
                    $("#"+cid+"").find(".useritem").each(function(i, item){
                        var itemobj = jQuery.parseJSON($(item).attr("code"));
                        tmpobj[code].push(itemobj);
                    });
                    var tmpnodes = {};
                    tmpnodes.name = tmpobj[code].join(',');
                    paramNodes.push(tmpnodes);
                    break;
                case SE_TYPE_OBJECT:
                    var id = "urltpl_"+num+"_"+code+"_"+col_type+"";
                    tmpobj[code] = {};
                    var tmpnodes = {};
                    tmpnodes.name = '父级';
                    tmpnodes.children = [];
                    paramNodes.push(tmpnodes);
                    getUserParamVal(num, col_type, code, tmpobj[code], paramNodes[paramNodes.length-1].children);
                    break;
            }
        });
        return ret;
    };
	initParamGenerator();
}

//choiceVal 可选数据,来自传入静态数组,或使用url动态获取
//successResult 选择后回调函数
//selectVal 已选择数据
//isnot 标签是否有"不"选择按钮
//showselectallbtn 是否显示 "选择全部"按钮
//显示名称
function myAccountSelect(successResult, selectVal, choiceVal, isemo, isnot, batchquery, showselectallbtn, isgo, showName){
	var _this = this;
	var page; //第几页
	var pagesize=20; //每页显示条数
	var curpage2;//当前页码
	var username = "";
	if(isgo == undefined){
		isgo = true;
	}
	var sltVal;
	if(selectVal == undefined){
		sltVal = []; 
	}
	else{
		sltVal = selectVal;
	}
	var isemotype = false;
	if(isnot == undefined){
		isnot = false;
	}
	if(showName == undefined){
		showName = "用户";
	}

	var initAccountSelect = function(){
		$("#accountsbox").remove();
		$("#batchuserdiv").remove();
		var accounthtml ="搜索"+showName+":<input type='text' id='accounttxt' name='accounttxt' /> <input type='button' name='sac' id='sac' value='搜索' />(使用*模糊搜索)";
		if(batchquery){
			accounthtml += "<input type='button' name='batchquery' id='batchquery' value='批量查询' />";
		}
		accounthtml += "<fieldset>"; 
		accounthtml +="<legend>"+showName+"列表</legend>";
		accounthtml +="<div id='groupaccounts'><img src='"+config.imagePath+"wait.gif'  style='padding:10px;padding-left:215px;' id='waitimg'/></div>";
		accounthtml +="<div id='accountpage' style='margin-top:2px;float:left;width:100%;margin:5px;'></div>";
		if(showselectallbtn){
			accounthtml +="<div class='search' id='accountselectallbtndiv' style='float:left;width:100%;'><input id='accountselectallbtn' name='accountselectallbtn' type='button' value='全选' /> <input id='accountunselectallbtn' name='accountunselectallbtn' type='button' value='反选' /> <input id='accountunselectnonebtn' name='accountunselectnonebtn' type='button' value='清空' /></div>";
		}
		accounthtml +="</fieldset>";
		accounthtml += "<fieldset>"; 
		accounthtml +="<legend>已选择的"+showName+"</legend>";
		accounthtml +="<div id='selectedaccounts'></div>";
		accounthtml +="</fieldset>";
		accounthtml +="<div class='search'><input id='accsubmit' name='accsubmit' type='button' value='选择完毕' /></div>";

		$("<div id='accountsbox' title='"+showName+"列表'></div>").insertAfter("body");
		$("#accountsbox").append(accounthtml);
		//批量添加用户
		var batchhtml ='';
			batchhtml+='<table class="formtable">';
			batchhtml+='<tr>';
			batchhtml+='<td class="tdleft">'+showName+'昵称：</td>';
			batchhtml+='<td>';
			batchhtml+='<textarea rows="25" cols="15" id="batch_screen_name"></textarea>';
			batchhtml+='</td>';
			batchhtml+='<td class="tdtip"></td>';
			batchhtml+='</tr>';
			batchhtml+='<tr id="noexistnametr" style="display:none;">';
			batchhtml+='<td class="tdleft">右边'+showName+'昵称不存在请重新输入：</td>';
			batchhtml+='<td colspan="2">';
			batchhtml+='<textarea rows="8" cols="15" id="noexistname"></textarea>';
			batchhtml+='</td>';
			batchhtml+='</tr>';

			batchhtml+='<tr id="repeatusertr" style="display:none;">';
			batchhtml+='<td class="tdleft">右边'+showName+'昵称存在重复项,仅分别保留一项,点击"获取结果",以确认：</td>';
			batchhtml+='<td colspan="2">';
			batchhtml+='<textarea rows="5" cols="15" id="repeatuser"></textarea>';
			batchhtml+='</td>';
			batchhtml+='</tr>';

			batchhtml+='<tr id="systemrepeatusertr" style="display:none;">';
			batchhtml+='<td class="tdleft">系统中有多个'+showName+'分别拥有右边昵称,点击"获取结果"确认后,可分别选择：</td>';
			batchhtml+='<td colspan="2">';
			batchhtml+='<textarea rows="4" cols="15" id="systemrepeatuser"></textarea>';
			batchhtml+='</td>';
			batchhtml+='</tr>';

			batchhtml+='</table>';
			$("<div id='batchuserdiv' style='display:none;'></div>").insertAfter("body");
			$("#batchuserdiv").append(batchhtml);



		if(isemo !="" && isemo!=undefined){
			isemotype = isemo; 
		}
		$("#accountsbox").dialog({
			autoOpen: false,
			modal:true,
			width:550,
			height:360,
			close:function(){
				$("#accountemotiondiv").css("display", "none");
			}
		});
		selectedAccount(sltVal);

		searchAccount(pagesize, 1, username);
		//$("#groupaccounts").html("<img src='"+config.imagePath+"wait.gif'  style='padding:10px;padding-left:215px;' id='waitimg'/>");
		$("#accountpage").empty();
		$("#accountsbox").dialog("open");

		$("#accounttxt").unbind("change");

		$("#batchquery").bind("click", function(){
			$("#batch_screen_name").val("");
			$("#batch_screen_name").removeAttr("style");
			$("#noexistnametr").hide();
			$("#noexistname").val("");
			$("#repeatusertr").hide();
			$("#repeatuser").val("");
			$("#systemrepeatusertr").hide();
			$("#systemrepeatuser").val("");
			//$("#batchuserdiv").dialog("open");
			$("#batchuserdiv").dialog("destroy"); 
			$("#batchuserdiv").dialog({
				autoOpen: true,
				title:"查询"+showName+"是否存在",
				modal:true,
				height:560,
				width:573,
				buttons:{
					"查询":function(){
						var uhtml = "";
						var users = $("#batch_screen_name").val();
						var userArr = users.split('\n');
						var names = {};
						names.users_screen_name = [];
						var repeatUserArr = []; //存在的重复用户
						$.each(userArr, function(di, ditem){
							if(ditem != ""){
								var tmpitem = commonFun.trim(ditem);
								if(!names.users_screen_name.inArray(tmpitem)){
									names.users_screen_name.push(tmpitem);
								}
								else{
									if(!repeatUserArr.inArray(tmpitem)){
										repeatUserArr.push(tmpitem);
									}
								}
							}
						});
						var dataobj={type:"query_screen_name", names:names};
						$.ajax({
							type: "POST",
							contentType: "application/json",
							dataType: "json",
							async:false,
							url: config.modelUrl + "feature_model.php",
							data: JSON.stringify(dataobj),
							beforeSend:function(){
							},
							complete:function(){
							},
							success: function (msg) {
								if(msg.flag == 1) { //有不存在的用户
									$("#batch_screen_name").css({"width":"199px","height":"296px"});
									$("#noexistnametr").show();
									$("#repeatusertr").hide();
									$("#systemrepeatusertr").hide();
									$("#noexistname").val(msg.noexistname.join('\n'));
								} 
								else{
									$("#batch_screen_name").css({"width":"199px","height":"256px"});
									$("#noexistnametr").val("");
									$("#noexistnametr").hide();
									$("#repeatusertr").hide();
									$("#systemrepeatusertr").hide();
									if(repeatUserArr.length >0 || msg.flag == 2){
										//有重复用户昵称
										if(repeatUserArr.length >0){
											$("#repeatusertr").show();
											$("#repeatuser").val(repeatUserArr.join('\n'));
											$("#batch_screen_name").val(names.users_screen_name.join('\n'));
										}
										//系统中存在重复昵称
										if(msg.flag == 2){
											$("#systemrepeatusertr").show();
											$("#systemrepeatuser").val(msg.sysrepeatuser.join('\n'));
										}
										choiceVal =  msg.users;

										//显示获取结果按钮
										//$(".ui-dialog-buttonset button").eq(1).show(); //隐藏指定的button
										$("#batchuserdiv").dialog("option","buttons", [
												{
													text: "获取结果",
													click: function() {
														if(choiceVal != undefined && choiceVal.length>0){
															searchAccount(pagesize, 1);
															$("#batchuserdiv").dialog("close");
														}
													}
												}
											]);
									}
									else{
										choiceVal =  msg.users;
										searchAccount(pagesize, 1);
										$("#batchuserdiv").dialog("close");
									}
								}
							}
						});
					}/*,
					"获取结果":function(){
						if(choiceVal != undefined && choiceVal.length>0){
							searchAccount(pagesize, 1);
							$("#batchuserdiv").dialog("close");
						}
					}
					*/
				}
			});
		});
		//反选
		$("#accountunselectallbtn").bind("click", function(){
			$("#groupaccounts").find("a").each(function(i, item){
				sendAccount($(item));
			});
		});
		//全选
		$("#accountselectallbtn").bind("click", function(){
			$("#selectedaccounts").empty();
			$("#groupaccounts").find("a").each(function(i, item){
				sendAccount($(item));
			});
		});
		//清空
		$("#accountunselectnonebtn").bind("click", function(i, item){
			$("#selectedaccounts .cancleitem").each(function(i, m){
				cancleWord($(m));
			});
		});

		$("#sac").bind("click", function(){
			username = $("#accounttxt").val();
			if(choiceVal != undefined && choiceVal.length>0){
				choiceAccount(choiceVal, username);
				$("#waitimg").css({display:"none"});
			}
			else{
				searchAccount(pagesize, 1, username);
				$("#groupaccounts").html("<img src='"+config.imagePath+"wait.gif'  style='padding:10px;padding-left:215px;' id='waitimg'/>");
				$("#accountpage").empty();
			}
		});
		$("#accsubmit").unbind("click");
		$("#accsubmit").bind("click", function(){
			var resultArr = [];
			$("#selectedaccounts .selword").each(function(i,m){
				var accountObj ={};
				accountObj.code= $(this).attr("code"); 
				accountObj.name = $(this).text();
				if($(this).attr("emotype") != undefined){
					accountObj.emotype = $(this).attr("emotype");
				}
				if($(this).attr("exclude") != undefined){
					accountObj.exclude = $(this).attr("exclude");
				}
				resultArr.push(accountObj);
			});		
			/*存储最后结果,使用successResult()函数调用*/
			successResult(resultArr);
			$("#accountemotiondiv").css("display", "none");
			$("#accountsbox").dialog("close");
		});
	};
	this.emotionChk= function(){
		var emohtml = '<div id="accountemotiondiv" style="position:absolute;display:none;z-index:9999;background-color:white;border:1px solid blue;font-size: 12px;"><span id="accountemotionspan">';
			emohtml +='<input type="checkbox" name="accountemotion" id="accountemotion1" value="1" class="outborder" /><label for="accountemotion1">反对</label>';
			emohtml +='<input type="checkbox" name="accountemotion" id="accountemotion2" value="2" class="outborder" /><label for="accountemotion2">负面</label>';
			emohtml +='<input type="checkbox" name="accountemotion" id="accountemotion3" value="3" class="outborder" /><label for="accountemotion3">中性</label>';
			emohtml +='<input type="checkbox" name="accountemotion" id="accountemotion4" value="4" class="outborder" /><label for="accountemotion4">正面</label>';
			emohtml +='<input type="checkbox" name="accountemotion" id="accountemotion5" value="5" class="outborder" /><label for="accountemotion5">赞赏</label>';
			emohtml +='<input type="checkbox" name="accountemotion" id="accountemotionall" value="*" class="outborder" /><label for="accountemotionall">全部</label>';
			emohtml +='<br/><a id="itemaccountemo" style="cursor:pointer;">确定</a></span></div>';
			if($("#accountemotiondiv").length == 0){
				//$(emohtml).insertAfter("body");
				$("body").append(emohtml);
			}
			$("#accountemotiondiv input[name=accountemotion]").bind("click", function(){
				var itemid = $(this).attr("id");
				if(itemid == "accountemotionall"){
					if($(this).prop("checked") == true){
						$("#accountemotiondiv input[name=accountemotion]").attr("checked", true);
					}
					else{
						$("#accountemotiondiv input[name=accountemotion]").attr("checked", false);
					}
				}
				else{
					var chklen = $("#accountemotiondiv input[name=accountemotion]:checked").length;
					var alllen = $("#accountemotiondiv input[name=accountemotion]").length;
					if($(this).prop("checked") == true){
						if(chklen == alllen-1){
							$("#accountemotiondiv input[name=accountemotion]").attr("checked", true);
						}
					}
					else{
						$("#accountemotiondiv input[name=accountemotion][id=accountemotionall]").attr("checked", false);
					}
				}
			});
	}
	var selectedAccount = function(sltVal){
		var slthtml = "";
		$.each(sltVal, function(i,m){
			slthtml += createUtilSpanHtml('selword', m.name, m.code, isnot, m.exclude, 'cancleWord', undefined, m.emotype);
		});
		$("#selectedaccounts").append(slthtml);
	}
	var choiceAccount = function(choiceArr, username){
		var rethtml = "";
		$.each(choiceArr, function(i, m){
			var flag = false; //已选择
			var code = m.users_id == undefined ? m.code : m.users_id;
			var name = m.users_screen_name == undefined ? m.name : m.users_screen_name;
			if(sltVal!=undefined && sltVal.length>0){
				$.each(sltVal, function(k,v){
					if(m.emotype != undefined){
						if(v.emotype == m.emotype && v.code == code){
							flag = true;
							return false;
						}
					}
					else{
						if(v.code == code){
							flag = true;
							return false;
						}
					}
				});
			}
			var slco = "";
			if(flag){
				slco = "class='selected'";
			}
			else{
				slco = "class='notselected'";
			}
			//var search = username.replace(/^\*/g, "*.").replace(/\*$/g,".*");
			//var re = new RegExp("^"+search+"$", "g");
			var re = new RegExp("^"+username+".*", "g");
			if(re.test(name)){
				var emoattr = "";
				var bname = name;
				if(m.emotype != undefined){
					emoattr = "emotype='"+m.emotype+"'";
					bname = name+"("+emoval2text(m.emotype)+")";
				}
				var userurl = "";
				var icode = code;
				if(icode != undefined && icode !=null){
					var sourceid = 1;//新浪微博
					userurl = weiboUserurl(icode,sourceid);
				}
				var ahtml = '';
				if(isgo){
					ahtml = " <span href='"+userurl+"' style='color:#37547D;' repeatlink='true' >前往</span>";
				}
				rethtml += "<a "+slco+" onclick='sendAccount(this)' name='"+name+"' code='"+code+"' "+emoattr+" >"+bname+" "+ahtml+"</a>";
			}
		});				
		$("#groupaccounts").empty().append(rethtml);
		//阻止冒泡触发父级点击事件
		$("span[repeatlink]").unbind("click")
		$("span[repeatlink]").bind("click", function(event){
			var href = $(this).attr("href");
			window.open(href, "_blank")
			event.stopPropagation();
		});
	};
	this.sendAccount = function(ele){
		var acname = $(ele).attr("name");
		var acid = $(ele).attr("code");
		var acemo = $(ele).attr("emotype");
		if(isemotype){
			_this.emotionChk();
			var ex,ey;
			//ex = eve.offsetX;
			//ey = eve.offsetY;
			//ex = eve.clientX;
			//ey = eve.clientY;
			ex = $(ele).offset().left + 30;
			ey = $(ele).offset().top - $("#accountemotiondiv").height();
			$("#accountemotiondiv").css({top:ey+"px", left:ex+"px", display:"block"});
			//$("#itememo").attr({code:$(this).attr("code"), name:$(this).text()});;
			$("#accountemotionspan input[name=accountemotion]").attr("checked", false);
			$("#itemaccountemo").unbind("click");  //一次创建多次绑定时需要unbind 否则会多次添加
			$("#itemaccountemo").bind("click", function(){
				var chklen = $("#accountemotiondiv input[name=accountemotion]:checked").length;
				if(chklen==0){
					alert("请选择情感!");
					return false;
				}
				var rtArr = [];
				var rvArr = [];
				$("#accountemotiondiv input[name=accountemotion]:checked").each(function(i,m){
					var rtid = $(this).attr("id");
					if(rtid != "accountemotionall"){ //全部checkbox 不添加到数组
						var rt = $("#accountemotiondiv label[for="+rtid+"]").text();
						rtArr.push(rt);
						var rv = $(this).val();
						rvArr.push(rv);
					}
				});
				var alllen = $("#accountemotiondiv input[name=accountemotion]").length;
				var emotype = "";
				if(rvArr.length>0){
					if(rvArr.length == alllen-1){
						acname = acname+"(全部)";
						emotype = "*";
					}
					else{
						acname = acname+"("+rtArr.join(",")+")";
						emotype = rvArr.join(",");
					}
				}
				var wflag = true;
				$("#selectedaccounts .selword").each(function(i,m){
					var sltw = $(this).attr("code");
					if(acid == sltw){
						$(this).parent().remove();
						$(ele).removeClass().addClass("notselected");
						wflag = false;
					}
				});
				if(wflag){
					//$("#acc_"+account.userid).css("background-color", "red");
					$(ele).removeClass().addClass("selected");
					var spanutilhtml = createUtilSpanHtml('selword', acname, acid, isnot, undefined, 'cancleWord', undefined, emotype);
					$("#selectedaccounts").append(spanutilhtml);
				}
				else{
					//alert("选择相同了!");
				}
				$("#accountemotiondiv").css({display:"none"});
			});

		}
		else{
			acname = $(ele).attr("name");
			acid = $(ele).attr("code");
			acemo = $(ele).attr("emotype");
			var wflag = true;
			$("#selectedaccounts .selword").each(function(i,m){
				var sltw = $(this).attr("code");
				var sltemo = $(this).attr("emotype");
				if(acemo != undefined){
					if(acid == sltw && acemo == sltemo){
						$(this).parent().remove();
						$(ele).removeClass().addClass("notselected");
						wflag = false;
					}
				}
				else{
					if(acid == sltw){
						$(this).parent().remove();
						$(ele).removeClass().addClass("notselected");
						wflag = false;
					}
				}
			});
			if(wflag){
				$(ele).removeClass().addClass("selected");
				var dname = acname;
				if(acemo != undefined){
					dname = acname+"("+emoval2text(acemo)+")";
				}
				var spanutilhtml = createUtilSpanHtml('selword', dname, acid,  isnot, undefined, 'cancleWord', undefined, acemo);
				$("#selectedaccounts").append(spanutilhtml);
			}
			else{
				//alert("选择相同了!");
			}
		}
	};
	this.cancleWord = function(elm){
		$(elm).parent().remove();
		var delemo = $(elm).attr("emotype");
		if(delemo != undefined && !isemo){
			$("#groupaccounts a[code="+$(elm).attr("code")+"][emotype="+delemo+"]").removeClass().addClass("notselected");
		}
		else{
			$("#groupaccounts a[code="+$(elm).attr("code")+"]").removeClass().addClass("notselected");
		}
	};
	var searchAccount = function(pagesize, curpage, username){
		if(choiceVal == undefined || choiceVal.length==0){
			if(curpage == '' || curpage == null || curpage == undefined ){
				_this.curpage2 = 1;
			}
			else{
				_this.curpage2 = curpage;
			}
			if(username == undefined){
				username = $("#accounttxt").val();
			}

			//动态数据
			var searchnameUrl = config.solrData+"?type=searchname&blurname="+encodeURIComponent(username)+"&page="+_this.curpage2+"&pagesize="+pagesize;
			ajaxRequest(searchnameUrl, function(data){
				if(data[0].totalcount>0) {
					choiceAccount(data[0].datalist, "");
					//显示分页
					if(data[0].totalcount>pagesize){
						pageDisplay(data[0].totalcount, searchAccount, "accountpage", pagesize, _this.curpage2);
					}
				}
				else{
					$("#groupaccounts").text(""+showName+"查询暂无数据！");
				}
			}, "json", function(){}, function(){ 
				$("#groupaccounts").html("<img src='"+config.imagePath+"wait.gif'  style='padding:10px;padding-left:215px;' id='waitimg'/>");
			}, function(){$("#waitimg").css({display:"none"});});

		}
		else{
			$("#accountpage").empty();
			choiceAccount(choiceVal, "");
			$("#waitimg").css({display:"none"});
		}
	}
	//初始化调用方法
	initAccountSelect();
}
	
	