$(document).ready(function(){
    
	$.each(["constants", "paramsDef", "runTimeParam", "parentParam", "outData", "g_global", "URLCache", "TaskCache", "AppCache", "g_collect", "CurrPageCache", "g_current"], function(i, item){
	    //参数定义
	    if($("#tp_"+item+"_def_btn").length > 0){
	        $("#tp_"+item+"_def_btn").unbind("click");
	        $("#tp_"+item+"_def_btn").bind("click", function(){
	            var checkedVal = $("#tp_"+item+"_def_text").val();
	            if(checkedVal){
	                var treeObj = $.fn.zTree.getZTreeObj("tp_"+item+"_def_tree");
	                if(treeObj){
	                    var checkedNodes = treeObj.transformToArray(treeObj.getNodes());
	                    for(var i=0,ilen=checkedNodes.length;i<ilen;i++){
	                    		 checkedNodes[i].children = undefined;
	                    }
	                }
	            }
	            myParamDef.init(function(data, retNodes){
	                var zTreeObj;
	                var setting = {
	                    view: {
	                        selectedMulti: false
	                    },
	                    data: {
	                        simpleData: { //这里是用的简单数据
	                            enable: true,
	                            idKey: "id",
	                            pIdKey: "pId",
	                            rootPId: 0
	                        }
	                    }
	                };
	                var zTreeNodes = retNodes;
	                zTreeObj = $.fn.zTree.init($("#tp_"+item+"_def_tree"), setting, zTreeNodes);
	                $("#tp_"+item+"_def_text").val(JSON.stringify(data));
	                //$("#tp_"+item+"_assign_tree").empty();
	                //$("#tp_"+item+"_assign_text").val('');
	            }, false, checkedNodes, item);
	        });
	    }
	    if($("#tp_"+item+"_def_clear_btn").length > 0){
	        $("#tp_"+item+"_def_clear_btn").unbind("click");
	        $("#tp_"+item+"_def_clear_btn").bind("click", function(){
	            $("#tp_"+item+"_def_tree").empty();
	            $("#tp_"+item+"_def_text").val('');
	
	            $("#tp_"+item+"_assign_tree").empty();
	            $("#tp_"+item+"_assign_text").val('');
	
	            if(item == "outData"){
	                $("#tp_outData_path_tree").empty();
	                $("#tp_outData_path_text").val('');
	
	                $("#tp_userData_path_tree").empty();
	                $("#tp_userData_path_text").val('');
	            }
	        });
	    }
	    //参数赋值
	    if($("#tp_"+item+"_assign_btn").length > 0){
	        $("#tp_"+item+"_assign_btn").unbind("click");
	        $("#tp_"+item+"_assign_btn").bind("click", function(){
	            var paramdef = $("#tp_"+item+"_def_text").val();
	            alert("1" + paramdef);
	            if(paramdef){
	                var selectedVal = $("#tp_"+item+"_assign_text").val();
	                alert("2"+selectedVal);
	                myParamGenerator(jQuery.parseJSON(paramdef), function(data, paramNodes){
	                    var zTreeObj;
	                    var setting = {
	                        view: {
	                            selectedMulti: false
	                        }
	                    };
	                    var zTreeNodes = paramNodes;
	                    zTreeObj = $.fn.zTree.init($("#tp_"+item+"_assign_tree"), setting, zTreeNodes);
	                    $("#tp_"+item+"_assign_text").val(JSON.stringify(data));
	                }, jQuery.parseJSON(selectedVal));
	            }
	            else{
	                alert('请先进行参数定义');
	            }
	        });
	    }
	    if($("#tp_"+item+"_assign_clear_btn").length > 0){
	        $("#tp_"+item+"_assign_clear_btn").unbind("click");
	        $("#tp_"+item+"_assign_clear_btn").bind("click", function(){
	            $("#tp_"+item+"_assign_tree").empty();
	            $("#tp_"+item+"_assign_text").val('');
	        });
	    }
	});

	//数据入库
	 $("#tp_outData_path_btn").unbind("click");
     $("#tp_outData_path_btn").bind("click", function(){
         var choiceParam = {};
         //choiceParam.constants_def = jQuery.parseJSON($("#tp_constants_def_text").val());
         //choiceParam.paramsDef_def = jQuery.parseJSON($("#tp_paramsDef_def_text").val());
         //choiceParam.parentParam_def = jQuery.parseJSON($("#tp_parentParam_def_text").val());
         //choiceParam.runTimeParam_def = jQuery.parseJSON($("#tp_runTimeParam_def_text").val());
         choiceParam.outData_def = jQuery.parseJSON($("#tp_outData_def_text").val());
         if(choiceParam.outData_def){
             myParamPath.init(choiceParam, function(data){
                 var path = [];
                 getDataPath(data.paramPath, path);
                 $("#tp_outData_path_tree").text('outData'+path.join(''));
                 $("#tp_outData_path_text").val(JSON.stringify(data));
             });
         }
         else{
             alert('请先进行参数定义');
         }
     });
     
     if($("#tp_outData_path_clear_btn").length > 0){
         $("#tp_outData_path_clear_btn").unbind("click");
         $("#tp_outData_path_clear_btn").bind("click", function(){
             $("#tp_outData_path_tree").empty();
             $("#tp_outData_path_text").val('');
         });
     }
     
     $("#tp_userData_path_btn").unbind("click");
     $("#tp_userData_path_btn").bind("click", function(){
         var choiceParam = {};
         choiceParam.outData_def = jQuery.parseJSON($("#tp_outData_def_text").val());
         if(choiceParam.outData_def){
             myParamPath.init(choiceParam, function(data){
                 var path = [];
                 getDataPath(data.paramPath, path);
                 $("#tp_userData_path_tree").text('outData'+path.join(''));
                 $("#tp_userData_path_text").val(JSON.stringify(data));
             });
         }
         else{
             alert('请先进行参数定义');
         }
     });
     
     if($("#tp_userData_path_clear_btn").length > 0){
         $("#tp_userData_path_clear_btn").unbind("click");
         $("#tp_userData_path_clear_btn").bind("click", function(){
             $("#tp_userData_path_tree").empty();
             $("#tp_userData_path_text").val('');
         });
     }
     
     $("#tp_datafrom_btn").bind("click", function(){
         var choiceParam = {};
         choiceParam.constants_def = jQuery.parseJSON($("#tp_constants_def_text").val());
         choiceParam.paramsDef_def = jQuery.parseJSON($("#tp_paramsDef_def_text").val());
         choiceParam.parentParam_def = jQuery.parseJSON($("#tp_parentParam_def_text").val());
         choiceParam.runTimeParam_def = jQuery.parseJSON($("#tp_runTimeParam_def_text").val());
         choiceParam.outData_def = jQuery.parseJSON($("#tp_outData_def_text").val());

         choiceParam.g_global_def = jQuery.parseJSON($("#tp_g_global_def_text").val());
         choiceParam.URLCache_def = jQuery.parseJSON($("#tp_URLCache_def_text").val());
         choiceParam.TaskCache_def = jQuery.parseJSON($("#tp_TaskCache_def_text").val());
         choiceParam.AppCache_def = jQuery.parseJSON($("#tp_AppCache_def_text").val());
         choiceParam.g_collect_def = jQuery.parseJSON($("#tp_g_collect_def_text").val());
         choiceParam.CurrPageCache_def = jQuery.parseJSON($("#tp_CurrPageCache_def_text").val());
         choiceParam.g_current_def = jQuery.parseJSON($("#tp_g_current_def_text").val());
         if(choiceParam){
             myParamPath.init(choiceParam, function(data){
                 var taskurl = $("#tp_listweburl").val();
                 var paths_str = $("#tp_listweburl").attr("paths");
                 if(paths_str){
                     var paths_arr = paths_str ? jQuery.parseJSON(paths_str) : [];
                     var tmpobj = {};
                     tmpobj.pathid = data.paramPathId;
                     tmpobj.path = data.paramPath;
                     paths_arr.push(tmpobj);
                 }
                 else{
                     var paths_arr = [];
                     var tmpobj = {};
                     tmpobj.pathid =data.paramPathId;
                     tmpobj.path = data.paramPath;
                     paths_arr.push(tmpobj);
                 }
                 $("#tp_listweburl").attr("paths", JSON.stringify(paths_arr));
                 $("#tp_listweburl").val(taskurl+"|"+data.paramPathId+"|");
             });
         }else{
             alert('请先进行参数定义');
         }
     });
     $("#tp_datafrom_clear_btn").unbind("click");
     $("#tp_datafrom_clear_btn").bind("click", function(){
         $("#tp_listweburl").val("");
     });
     
   //设置模板
     $("#tp_grabsteps_btn").bind("click", function(){
         var checkedVal = [];
         $("#tp_grabsteps").find("span[name=step_span]").each(function(i, item){
             var tmpobj = {};
             tmpobj.tpl = $(item).attr("tpl");
             tmpobj.stepid = parseInt($(item).find('span[name=grabstep_id]').text(),10);
             tmpobj.steptpl = $(item).find('span[name=grabstep_tpl]').text();
             tmpobj.urlregex = $(item).find('span[name=grabstep_urlregex]').text();
             checkedVal.push(tmpobj);
         });
         myGrabSteps.init({
             checkedVal:checkedVal,
             successResult:function(data){
                 var spanhtml = "";
                 for(var i=0,ilen=data.length;i<ilen;i++){
                     var urltpl = data[i];
                     spanhtml += "<span class='selwordsbox'>";
                     spanhtml += "<span name='step_span' tpl='"+urltpl.tpl+"'><span><span>步骤ID：</span><span name='grabstep_id' >"+urltpl.stepid+"</span></span><br/><span><span>步骤模板：</span><span name='grabstep_tpl' >"+urltpl.steptpl+"</span></span><br/><span><span>匹配规则：</span><span name='grabstep_urlregex'>"+urltpl.urlregex+"</span></span></span>";
                     spanhtml += "</span>";
                 }
                 $("#tp_grabsteps").empty().append(spanhtml);
                 $("#tp_grabsteps").find("a[class=cancleitem]").unbind("click");
                 $("#tp_grabsteps").find("a[class=cancleitem]").bind("click", function(){
                     $(this).parent().remove();
                 });
             }
         });
     });
     
     $("#tp_paramMap_btn").unbind("click");
     $("#tp_paramMap_btn").bind("click", function(){
         var choiceParam = {};
         choiceParam.constants_def = jQuery.parseJSON($("#tp_constants_def_text").val());
         choiceParam.paramsDef_def = jQuery.parseJSON($("#tp_paramsDef_def_text").val());
         choiceParam.parentParam_def = jQuery.parseJSON($("#tp_parentParam_def_text").val());
         choiceParam.runTimeParam_def = jQuery.parseJSON($("#tp_runTimeParam_def_text").val());
         choiceParam.outData_def = jQuery.parseJSON($("#tp_outData_def_text").val());

         choiceParam.g_global_def = jQuery.parseJSON($("#tp_g_global_def_text").val());
         choiceParam.URLCache_def = jQuery.parseJSON($("#tp_URLCache_def_text").val());
         choiceParam.TaskCache_def = jQuery.parseJSON($("#tp_TaskCache_def_text").val());
         choiceParam.AppCache_def = jQuery.parseJSON($("#tp_AppCache_def_text").val());
         choiceParam.g_collect_def = jQuery.parseJSON($("#tp_g_collect_def_text").val());
         choiceParam.CurrPageCache_def = jQuery.parseJSON($("#tp_CurrPageCache_def_text").val());
         choiceParam.g_current_def = jQuery.parseJSON($("#tp_g_current_def_text").val());

         if(choiceParam){
             myParamPath.init(choiceParam, function(data){
                 var tmphtml = "<div name='paramMap'>";
                 tmphtml += "<span name='paramMap' code='"+JSON.stringify(data)+"'>"+data.paramPathId+"</span>";
                 tmphtml += "<a class='cancleitem' style='color:red;cursor:pointer;' >X</a>";
                 tmphtml += "</div>";
                 $("#tp_paramMap_params").append(tmphtml);
                 $("#tp_paramMap_params").find("a[class=cancleitem]").unbind("click");
                 $("#tp_paramMap_params").find("a[class=cancleitem]").bind("click", function(){
                     $(this).parent().remove();
                 });
             }, true);
         }
         else{
             alert('请先进行参数定义');
             return false;
         }
     });
     
     $("#tp_paramfilter_btn").unbind("click");
     $("#tp_paramfilter_btn").bind("click", function(){
         var choiceParam = {};
         //choiceParam.constants = jQuery.parseJSON($("#tp_constants_def_text").val());
         choiceParam.paramsDef_def = jQuery.parseJSON($("#tp_paramsDef_def_text").val());
         //choiceParam.parentParam = jQuery.parseJSON($("#tp_parentParam_def_text").val());
         //choiceParam.runTimeParam = jQuery.parseJSON($("#tp_runTimeParam_def_text").val());
         //choiceParam.outData = jQuery.parseJSON($("#tp_outData_def_text").val());
         if(choiceParam.paramsDef_def){
             myParamFilter.init(choiceParam, function(data){
                 var phtml = "<span class='selwordsbox'><span filter='"+JSON.stringify(data)+"'>"+data.fileterId+"</span><a class='useritem_a' onclick='cancelSelected(this)'>×</a></span>";
                 $("#tp_paramfilter_span").append(phtml);
             });
         }
         else{
             alert('请先进行参数定义');
         }
     });
     
     //选择源
     $("#tp_sourceid").bind("click", function(){
         var selectedVal = [];
         $("#tp_addedsourceid .useritem").each(function(i, item){
             var tmpobj = {};
             tmpobj.name = $(item).text();
             tmpobj.code = $(item).attr("code")
             selectedVal.push(tmpobj);
         });

         var choiceVal = [];
         if(config.allAccountSource.length == 0){
             //同步ajax方法
             getAccountSource(function(data){
                 if(data){
                     config.allAccountSource = data;
                 }
             });
         }
         $.each(config.allAccountSource, function(si, sitem){
             var tmpobj = {};
             tmpobj['name'] = sitem['name'];
             tmpobj['code'] = sitem['id'];
             choiceVal.push(tmpobj);
         });
         myAccountSelect(function(data){
             if(data.length > 0){
                 var dhtml = "";
                 var codeArr = [];
                 $.each(data, function(di, ditem){
                     codeArr.push(ditem.code);
                     dhtml += "<span class='selwordsbox'><span class='useritem' code='"+ditem.code+"' >"+ditem.name+"</span><a class='useritem_a' onclick='cancelSelected(this)'>×</a></span>";
                 });
                 $("#tp_addedsourceid").empty().append(dhtml);
                 $("#tp_hdaddedsourceid").val(codeArr.toString());
             }
         }, selectedVal, choiceVal, false, undefined, undefined, undefined, false, "来源");
     });
     //抓取账号
     $("#tp_accountid").bind("click", function(){
         var choiceValue = [];
         var selectedVal = [];
         $("#tp_addedaccountid .useritem").each(function(i, item){
             var tmpobj = {};
             tmpobj.name = $(item).text();
             tmpobj.code = $(item).attr("code")
             selectedVal.push(tmpobj);
         });
         var sourceid = $("#tp_hdaddedsourceid").val();
         if(!sourceid){
             alert("请选择帐号来源!");
             return false;
         }
         var searchnameUrl = config.modelUrl + "spideraccount_model.php";
         var senddataobj = {
             account_sourceid: sourceid,
         type: "selectaccountbysourceid"
         }
         $.ajax({
             type: "GET",
         dataType: "json",
         async:false,
         url: searchnameUrl,
         data: senddataobj,
         success:function(data){
             $.each(data.datalist, function(di, ditem){
                 var tmpobj = {};
                 tmpobj.name = ditem.username;
                 tmpobj.code = ditem.id;
                 choiceValue.push(tmpobj);
             });
         }
         });
         myCommonSelect(choiceValue, function(data){
             var switchdisplay = $("#s10-8").css("display");
             if(switchdisplay != "none"){
                 //当选择的帐号大于1个时需要选中切换帐号
                 if(data.length > 1){ //须使用切换帐号
                     $("input[name=tp_isswitch]").attr("checked", false);
                     $("input[name=tp_isswitch][value=1]").attr("checked", true);
                     $("#tp10_9").show();
                 }
                 else if(data.length == 0){ //不使用切换帐号
                     $("input[name=tp_isswitch]").attr("checked", false);
                     $("input[name=tp_isswitch][value=0]").attr("checked", true);
                     $("#tp10_9").hide();
                 }
             }
             var dhtml = "";
             $.each(data, function(di, ditem){
                 dhtml += "<span class='selwordsbox'><span class='useritem' code='"+ditem.code+"' >"+ditem.name+"</span><a class='useritem_a' onclick='cancelSelected(this)'>×</a></span>";
                 if(ditem.sourceid != undefined){
                     $("#tp_hdaddedsourceid").val(ditem.sourceid);
                 }
             });
             $("#tp_addedaccountid").empty().append(dhtml);
         }, selectedVal, "选择帐号", undefined, undefined, false, undefined, true);
     });
     
     var taskele;
   //用户选择的值还原
     if(taskele){
         if(taskele.taskID !== taskele.parentTaskID){
             $("input[name=isroottask]").attr("checked", false);
             $("#tp_isroottask0").attr("checked", true);
             $("#tp12_2").show();
             $("tr[id^=tp11_]").show();
             $("tr[id^=tp13_]").hide();
             var toption = '<option value="-1">未选择</option>';
             for(var i=0,ilen=_this.taskInstance.tasks.length;i<ilen;i++){
                 toption += '<option value="'+i+'">任务'+i+'</option>';
             }
             $("#tp_parenttask").empty().append(toption);
             $("#tp_parenttask").val(taskele.parentTaskID);
         }
         if(taskele.parentChoiceParam){
             $("#tp_fromPath_btn").attr('choiceparam', JSON.stringify(taskele.parentChoiceParam));
         }
         var taskp = taskele.param;
         var taskPro = taskp.taskPro;
         $("#tp_tasklevel").val(taskPro.tasklevel);
         //taskp.taskPro.local = 0;
         //taskp.taskPro.remote = 1;
         if(taskPro.iscommit !== undefined){
             $("input[name=iscommit]").attr("checked", false);
             $("input[name=iscommit][value="+taskPro.iscommit+"]").attr("checked", true);
         }
         //taskp.taskPro.source = 11;
         //taskp.taskPro.content_type = 0; //
         if(taskPro.duration !== undefined){
             $("#tp_duration").val(taskPro.duration); 
         }
         if(taskPro.conflictdelay){
             $("#tp_conflictdelay").val(taskPro.conflictdelay); 
         }
         if(taskPro.iscalctrend){
             $("input[name=iscalctrend]").attr("checked", false);
             $("input[name=iscalctrend][value="+taskPro.iscalctrend+"]").attr("checked", true);
         }
         if(taskPro.dataSave){
             $("input[name=dataSave]").attr("checked", false);
             $("input[name=dataSave][value="+taskPro.dataSave+"]").attr("checked", true);
         }
         if(taskPro.filPageTag){
             $("input[name=filPageTag]").attr("checked", false);
             $("input[name=filPageTag][value="+taskPro.filPageTag+"]").attr("checked", true);
         }
         if(taskPro.addUser){
             $("input[name=addUser]").attr("checked", false);
             $("input[name=addUser][value="+taskPro.addUser+"]").attr("checked", true);
         }
         if(taskPro.genCreatedAt){
             $("input[name=genCreatedAt]").attr("checked", false);
             $("input[name=genCreatedAt][value="+taskPro.genCreatedAt+"]").attr("checked", true);
         }
         if(taskPro.isGenChildTask){
             $("input[name=isGenChildTask]").attr("checked", false);
             $("input[name=isGenChildTask][value="+taskPro.isGenChildTask+"]").attr("checked", true);
         }

         if(taskPro.dictionaryPlan !== undefined && taskPro.dictionaryPlan){
             //数据放到全局数组里
             planArray=JSON.parse(taskPro.dictionaryPlan);
             var planHtml="";
             var count=1;
             $.each(planArray,function(di, itemPlan){
                 planHtml +="方案"+count+":(";
                 var t2="";
                 var count2=0;
                 if(itemPlan!=undefined&&itemPlan.length==0){
                     if(count2!=0){
                         t2=t2+","+"默认方案";
                     }
                     else{
                         t2="默认方案";
                     }
             count2++;
             planHtml+=t2+")";
             count++;
                 }
                 else {
                     $.each(itemPlan, function (dj, itemCategory){
                         if(count2!=0)
                     {
                         t2=t2+","+itemCategory.name;
                     }
                         else{
                             t2=itemCategory.name;
                         }
                     count2++;
                     });
                     planHtml+=t2+")";
                     count++;
                 }
             });
             $("#tp_dictionaryPlanText2").text(planHtml);	
         }
         if(taskPro.activatetime !== undefined){
             $("#tp_activatetime").val(timeToStr(taskPro.activatetime)); 
         }
         /*
         if(taskPro.template !== undefined){
             $("#tp_template").val(taskPro.template); 
         }
         */
         if(taskPro.remarks !== undefined){
             $("#tp_remarks").val(taskPro.remarks);
         }
         if(taskp.templMap){
             var spanhtml = "";
             for(var steptpl in taskp.templMap){
                 for(var i=0,ilen=taskp.templMap[steptpl].length;i<ilen;i++){
                     var stepNum = taskp.templMap[steptpl][i];
                     var tmpobj = {};
                     tmpobj.stepid = stepNum;
                     tmpobj.steptpl = steptpl;
                     var urltpl = {};
                     $.each(config.allSpiderConfig, function(ci, citem){
                         if(citem.id == taskPro.template){
                             urltpl = citem;
                             return false;
                         }
                     });
                     tmpobj.name = urltpl.name;
                     var reg = taskp.stepNumURLPatterns[stepNum];
                     tmpobj.urlregex = reg ? reg.join("\n") : null;
                     if(tmpobj.stepid){
                         spanhtml += "<span class='selwordsbox'>";
                         spanhtml += "<span name='step_span' tpl='"+taskPro.template+"'><span><span>步骤ID：</span><span name='grabstep_id' >"+tmpobj.stepid+"</span></span><br/><span><span>步骤模板：</span><span name='grabstep_tpl' >"+tmpobj.steptpl+"</span></span><br/><span><span>当前步骤校验规则：</span><span name='grabstep_urlregex'>"+tmpobj.urlregex+"</span></span></span>";
                         spanhtml += "</span>";
                     }
                 }
             }
             $("#tp_grabsteps").empty().append(spanhtml);
         }
         var constants = taskp.constants;
         if(constants){
             _this.initAssignText('constants', constants);
         }
         var paramsDef = taskp.paramsDef;
         if(paramsDef){
             _this.initAssignText('paramsDef', paramsDef);
         }
         var parentParam = taskp.parentParam;
         if(parentParam){
             _this.initAssignText('parentParam', parentParam);
         }
         var runTimeParam = taskp.runTimeParam;
         if(runTimeParam){
             _this.initAssignText('runTimeParam', runTimeParam);
         }
         var constants_def = taskp.constants_def;
         if(constants_def){
             _this.initDefText('constants', constants_def);
         }
         var paramsDef_def = taskp.paramsDef_def;
         if(paramsDef_def){
             _this.initDefText('paramsDef', paramsDef_def);
         }
         var parentParam_def = taskp.parentParam_def;
         if(parentParam_def){
             _this.initDefText('parentParam', parentParam_def);
         }
         var runTimeParam_def = taskp.runTimeParam_def;
         if(runTimeParam_def){
             _this.initDefText('runTimeParam', runTimeParam_def);
         }
         var outData_def = taskp.outData_def;
         if(outData_def){
             _this.initDefText('outData', outData_def);
         }

         var g_global_def = taskp.g_global_def;
         if(g_global_def){
             _this.initDefText('g_global', g_global_def);
         }

         var URLCache_def = taskp.URLCache_def;
         if(URLCache_def){
             _this.initDefText('URLCache', URLCache_def);
         }
         var TaskCache_def = taskp.TaskCache_def;
         if(TaskCache_def){
             _this.initDefText('TaskCache', TaskCache_def);
         }

         var AppCache_def = taskp.AppCache_def ;
         if(AppCache_def){
             _this.initDefText('AppCache', AppCache_def);
         }

         var g_collect_def = taskp.g_collect_def ;
         if(g_collect_def){
             _this.initDefText('g_collect', g_collect_def);
         }
         var CurrPageCache_def = taskp.CurrPageCache_def ;
         if(CurrPageCache_def){
             _this.initDefText('CurrPageCache', CurrPageCache_def);
         }
         var g_current_def = taskp.g_current_def ;
         if(g_current_def){
             _this.initDefText('g_current', g_current_def);
         }
         if(taskp.outData){
             if(taskp.outData.datasPath){
                 var outData_path = {};
                 outData_path.paramPathId = taskp.outData.datasPath.replace(/\|/g, ""); 
                 outData_path.paramPath = taskp.pathStructMap[outData_path.paramPathId]; 
                 $("#tp_outData_path_text").val(JSON.stringify(outData_path));
                 var path = [];
                 _this.getDataPath(outData_path.paramPath, path);
                 $("#tp_outData_path_tree").text('outData'+path.join(''));
             }
         }

         if(taskp.taskPro){
             if(taskp.taskPro.userPathId){
                 var userData_path = {};
                 userData_path.paramPathId = taskp.taskPro.userPathId.replace(/\|/g, ""); 
                 userData_path.paramPath = taskp.pathStructMap[userData_path.paramPathId]; 
                 $("#tp_userData_path_text").val(JSON.stringify(userData_path));
                 var path = [];
                 _this.getDataPath(userData_path.paramPath, path);
                 $("#tp_userData_path_tree").text('outData'+path.join(''));
             }
         }
         var loginAccounts = taskp.loginAccounts;
         if(loginAccounts.globalaccount !== undefined){
             $("input[name=tp_globalaccount]").attr("checked", false);
             $("input[name=tp_globalaccount][value="+loginAccounts.globalaccount+"]").attr("checked", true);
             if(parseInt(loginAccounts.globalaccount, 10)){
                 $("#tp10_8").hide();
                 $("#tp10_12").hide();
                 $("input[name=logoutfirst]").attr("checked", false);
                 $("input[name=logoutfirst][value=1]").attr("checked", true);
             }
         }
         if(loginAccounts.logoutfirst !== undefined){
             $("input[name=tp_logoutfirst]").attr("checked", false);
             $("input[name=tp_logoutfirst][value="+loginAccounts.logoutfirst+"]").attr("checked", true);
         }

         if(loginAccounts.isswitch !== undefined){
             $("input[name=tp_isswitch]").attr("checked", false);
             $("input[name=tp_isswitch][value="+loginAccounts.isswitch+"]").attr("checked", true);
             if(parseInt(loginAccounts.isswitch, 10)){
                 $("#tp10_9").show();
             }
         }
         if(loginAccounts.switchpage !== undefined){
             $("#tp_switchpage").val(loginAccounts.switchpage);
         }
         if(loginAccounts.switchtime !== undefined){
             $("#tp_switchtime").val(loginAccounts.switchtime);
         }
         if(loginAccounts.accounts != undefined && loginAccounts.accounts.length > 0){
             var uhtml = "";
             $.each(loginAccounts.accounts, function(di, ditem){
                 var accountname = getSpiderAccountName(loginAccounts.source, ditem);
                 uhtml += "<span class='selwordsbox'><span class='useritem' code='"+ditem+"' >"+accountname+"</span><a class='useritem_a' onclick='cancelSelected(this)'>×</a></span>";
             });
             $("#tp_addedaccountid").empty().append(uhtml);
         }
         if(loginAccounts.source !== undefined){
             var sourcename = getSourceName(loginAccounts.source);
             var uhtml = "";
             uhtml += "<span class='selwordsbox'><span class='useritem' code='"+loginAccounts.source+"' >"+sourcename+"</span><a class='useritem_a' onclick='cancelSelected(this)'>×</a></span>";
             $("#tp_addedsourceid").empty().append(uhtml);
         }
         //taskp.loginAccounts.switchstrategy = 1;
         //taskp.loginAccounts.globalaccounts = "";

         if(taskp.filters){
             $("#tp_paramfilter_span").empty();
             var phtml = "";
             for(var i=0;i<taskp.filters.length;i++){
                 var tmpFilter = taskp.filters[i];
                 phtml += "<span class='selwordsbox'><span filter='"+JSON.stringify(tmpFilter)+"'>"+tmpFilter.fileterId+"</span><a class='useritem_a' onclick='cancelSelected(this)'>×</a></span>";
             }
             $("#tp_paramfilter_span").append(phtml);
         }
         if(taskp.pathStructMap && taskp.pathStructMap.keys){
             for(var i=0,ilen=taskp.pathStructMap.keys.length;i<ilen;i++){
                 var pitem = taskp.pathStructMap.keys[i];
                 var pmap = taskp.pathStructMap[pitem];
                 var tmpobj = {};
                 tmpobj.paramPathId = pitem;
                 tmpobj.paramPath = pmap;
                 var tmphtml = "<div name='paramMap'>";
                 tmphtml += "<span name='paramMap' code='"+JSON.stringify(tmpobj)+"'>"+pitem+"</span>";
                 tmphtml += "<a class='cancleitem' style='color:red;cursor:pointer;' >X</a>";
                 tmphtml += "</div>";
                 $("#tp_paramMap_params").append(tmphtml);
                 $("#tp_paramMap_params").find("a[class=cancleitem]").unbind("click");
                 $("#tp_paramMap_params").find("a[class=cancleitem]").bind("click", function(){
                     $(this).parent().remove();
                 });
             }
         }
         var taskUrls = taskp.taskUrls;
         if(taskUrls){
             //gen:表示根据参数生成，consts表示直接赋值给爬虫
             $("input[name=urltype]").attr("checked", false);
             $("input[name=urltype][value="+taskUrls.type+"]").attr("checked", true);
             var urls = taskUrls.urlValues;
             var ids = [];
             var paths = [];
             for(var i=0;i<urls.length;i++){
                 var tmpid = urls[i];
                 ids.push(tmpid);
                 var structid = tmpid.replace(/\|/g, "");
                 var tmppath = {};
                 tmppath.pathid = structid;
                 tmppath.path = taskp.pathStructMap[structid];
                 paths.push(tmppath);
             }
             $("#tp_listweburl").val(ids.join(''));
             $("#tp_listweburl").attr("paths", JSON.stringify(paths));
         }
         var parentTask = {};
         for(var i=0, ilen=_this.taskInstance.tasks.length;i<ilen;i++){
             var tmpTask = _this.taskInstance.tasks[i];
             if(tmpTask.taskID == taskele.parentTaskID){
                 parentTask = tmpTask;
             }
         }
         if(parentTask.param.taskGenConf && parentTask.param.taskGenConf.length > 0){
             for(var i=0;i<parentTask.param.taskGenConf.length;i++){
                 var tgc = parentTask.param.taskGenConf[i];
                 if(tgc.childTaskDefId == taskele.taskID){
                     if(tgc.splitStep !== undefined){
                         $("#tp_splitStep").val(tgc.splitStep);
                     }
                     if(tgc.childTaskUrl){
                         $("input[name=genconf_urltype]").attr("checked", false);
                         $("input[name=genconf_urltype][value="+tgc.childTaskUrl.type+"]").attr("checked", true);
                         var urls = tgc.childTaskUrl.value;
                         if(!isEmptyObject(urls) && urls.length != 0){
                             $("#tp_genconf_listweburl").val(urls.paramValue);
                             var paths_arr = [];
                             var pathid = urls.paramValue.replace(/\|/g, "");
                             paths_arr.push({"pathid":pathid, "path":taskp.pathStructMap[pathid]});
                             var paths_str = JSON.stringify(paths_arr);
                             $("#tp_genconf_listweburl").attr("paths", paths_str);
                         }
                     }
                     if(tgc.params !== undefined && tgc.params.length > 0){
                         $("#tp_taskGenConf_params").empty();
                         for(var i=0,ilen=tgc.params.length;i<ilen;i++){
                             var tmpObj = tgc.params[i];
                             var tmp_from_path = {};
                             tmp_from_path.paramPathId = tmpObj.paramPath.replace(/\|/g, ""); 
                             tmp_from_path.paramPath = taskp.pathStructMap[tmp_from_path.paramPathId]; 
                             var targetTypeAttr = "";
                             if(tmpObj.targetType !== undefined){
                                 targetTypeAttr = "targetType='"+tmpObj.targetType+"'";
                             }
                             var tmp_to_path = {};
                             tmp_to_path.paramPathId = tmpObj.toParamPath.replace(/\|/g, ""); 
                             tmp_to_path.paramPath = taskp.pathStructMap[tmp_to_path.paramPathId]; 

                             var tmphtml = "<div name='paramPath'>";
                             tmphtml += "<span name='paramPathfrom' code='"+JSON.stringify(tmp_from_path)+"' "+targetTypeAttr+">"+tmp_from_path.paramPathId+"</span>";
                             tmphtml += "->";
                             tmphtml += "<span name='paramPathto' code='"+JSON.stringify(tmp_to_path)+"'>"+tmp_to_path.paramPathId+"</span>";
                             tmphtml += "<a class='cancleitem' style='color:red;cursor:pointer;' >X</a>";
                             tmphtml += "</div>";
                             $("#tp_taskGenConf_params").append(tmphtml);
                             $("#tp_taskGenConf_params").find("a[class=cancleitem]").unbind("click");
                             $("#tp_taskGenConf_params").find("a[class=cancleitem]").bind("click", function(){
                                 $(this).parent().remove();
                             });
                         }
                     }
                 }
             }
         }
     }
     else{
         currEditElement = null;
     }
     
     $("#addTaskParamBut").click(function(){
    	 addTaskParam();
     });
     
});

getDataPath = function(data, retPath){
    if(data.type){
        if(data.type == 2){
            retPath.push("."+data.data.chld_col_name);
        }
        else if(data.type ==1){
            retPath.push("["+data.data.arr_data.arr_idx+"]");
        }
    }
    if(data.col_name_ex){
        getDataPath(data.col_name_ex, retPath);
    }
    if(data.name_ex){
        getDataPath(data.name_ex, retPath);
    }
};

function addTaskParam(taskid){
	//首先判断是否存在taskid,是：新建子任务、否新建父任务
	 var taskele = {};
     var taskp = {};
	
     taskp.taskPro = {};
     taskp.taskPro.tasklevel = $("#tp_tasklevel").val();  //任务级别
     taskp.taskPro.local = 0;
     taskp.taskPro.remote = 1;
     taskp.taskPro.iscommit = $("input[name=iscommit]:checked").val();  //立即更新，每次立即更新检索引擎数据
     taskp.taskPro.source = 11;
     taskp.taskPro.content_type = 0;   //内容类型
     taskp.taskPro.duration = $("#tp_duration").val();      //任务超时时间
     taskp.taskPro.conflictdelay = $("#tp_conflictdelay").val(); //冲突延迟
     taskp.taskPro.iscalctrend = $("input[name=iscalctrend]:checked").val();  //是否计算轨迹
     taskp.taskPro.dataSave = $("input[name=dataSave]:checked").val();        //是否数据入库
     taskp.taskPro.filPageTag = $("input[name=filPageTag]:checked").val();   //过滤html标签
     taskp.taskPro.addUser = $("input[name=addUser]:checked").val();          //增加用户
     taskp.taskPro.genCreatedAt = $("input[name=genCreatedAt]:checked").val();  //默认采集时间，如果采集的数据没有created_at字段，系统会以采集时间作为文章的创建时间
     taskp.taskPro.isGenChildTask = $("input[name=isGenChildTask]:checked").val(); //是否派生子任务
     taskp.taskPro.dictionaryPlan = $("#tph_dictionaryPlan2").val();                 //分词方案
     taskp.taskPro.activatetime = $("#tp_activatetime").val() ? getTimeSec($("#tp_activatetime").val()) : "";  //启动时间
     //taskp.taskPro.template = $("#tp_template").val();
     taskp.taskPro.remarks = $("#tp_remarks").val();   //摘要信息
     var userpath = $("#tp_userData_path_text").val(); //用户入库数据路径
     taskp.taskPro.userPathId = userpath ? jQuery.parseJSON($("#tp_userData_path_text").val()).paramPathId : '';
	
     //步骤
     taskp.templMap = {};
     taskp.stepNumURLPatterns = {};
     $("#tp_grabsteps").find("span[name=step_span]").each(function(i, item){
         var tmpobj = {};
         tmpobj.tpl = $(item).attr("tpl");
         tmpobj.stepid = parseInt($(item).find('span[name=grabstep_id]').text(), 10);
         tmpobj.steptpl = $(item).find('span[name=grabstep_tpl]').text();
         tmpobj.urlregex = $(item).find('span[name=grabstep_urlregex]').text();
         if(taskp.templMap[tmpobj.steptpl]){
             taskp.templMap[tmpobj.steptpl].push(tmpobj.stepid);
         }
         else{
             taskp.templMap[tmpobj.steptpl] = [];
             taskp.templMap[tmpobj.steptpl].push(tmpobj.stepid);
         }
         taskp.stepNumURLPatterns[tmpobj.stepid] = tmpobj.urlregex.split("\n");
         taskp.taskPro.template = tmpobj.tpl;
     });

     //获取自定义变量值
     taskp.paramsDef_def = jQuery.parseJSON($("#tp_paramsDef_def_text").val());
     taskp.parentParam_def = jQuery.parseJSON($("#tp_parentParam_def_text").val());
     taskp.runTimeParam_def = jQuery.parseJSON($("#tp_runTimeParam_def_text").val());
     taskp.constants_def = jQuery.parseJSON($("#tp_constants_def_text").val());
     taskp.outData_def = jQuery.parseJSON($("#tp_outData_def_text").val());

     taskp.g_global_def = jQuery.parseJSON($("#tp_g_global_def_text").val());
     taskp.URLCache_def = jQuery.parseJSON($("#tp_URLCache_def_text").val());
     taskp.TaskCache_def = jQuery.parseJSON($("#tp_TaskCache_def_text").val());
     taskp.AppCache_def = jQuery.parseJSON($("#tp_AppCache_def_text").val());
     taskp.g_collect_def = jQuery.parseJSON($("#tp_g_collect_def_text").val());
     taskp.CurrPageCache_def = jQuery.parseJSON($("#tp_CurrPageCache_def_text").val());
     taskp.g_current_def = jQuery.parseJSON($("#tp_g_current_def_text").val());
     
   //赋值
     taskp.paramsDef = jQuery.parseJSON($("#tp_paramsDef_assign_text").val());
     taskp.parentParam = jQuery.parseJSON($("#tp_parentParam_assign_text").val());
     taskp.runTimeParam = jQuery.parseJSON($("#tp_runTimeParam_assign_text").val());
     taskp.constants = jQuery.parseJSON($("#tp_constants_assign_text").val());

     taskp.loginAccounts = {};
     taskp.loginAccounts.globalaccount = $("input[name=tp_globalaccount]:checked").val();
     taskp.loginAccounts.logoutfirst = $("input[name=tp_logoutfirst]:checked").val();
     taskp.loginAccounts.isswitch = $("input[name=tp_isswitch]:checked").val();
     taskp.loginAccounts.switchpage = $("#tp_switchpage").val();
     taskp.loginAccounts.switchtime = $("#tp_switchtime").val();
     var accountsource = [];
     $("#tp_addedsourceid").find(".useritem").each(function(i, item){
         accountsource.push($(item).attr("code"));
     });
     taskp.loginAccounts.source = accountsource[0];
     taskp.loginAccounts.switchstrategy = 1;
     
   //抓取帐号
     var addaccountid = [];
     $("#tp_addedaccountid").find(".useritem").each(function(i, item){
         addaccountid.push($(item).attr("code"));
     });
     taskp.loginAccounts.accounts = addaccountid;
     taskp.loginAccounts.globalaccounts = "";
     taskp.pathStructMap = {};
     taskp.filters = [];
     $("#tp_paramfilter_span").find("span[filter]").each(function(i, item){  //过滤条件
         var filterStr = $(item).attr("filter");
         var filterObj = filterStr ? jQuery.parseJSON(filterStr) : null;
         if(filterObj.valueCfg.valueType == "var"){
             taskp.pathStructMap[filterObj.valueCfg.paramPathId] = {};
             taskp.pathStructMap[filterObj.valueCfg.paramPathId] = filterObj.valueCfg.paramPath;
             delete filterObj.valueCfg.paramPath;
         }
         taskp.filters.push(filterObj);
     });
     taskp.taskUrls = {};
     //{gen:表示根据参数生成，consts表示直接赋值给爬虫}
     taskp.taskUrls.type = $("input[name=urltype]:checked").val();
     taskp.taskUrls.urlValues = [];
     var urlvalue = $("#tp_listweburl").val();
     taskp.taskUrls.urlValues.push(urlvalue);
     var paths_str = $("#tp_listweburl").attr("paths");
     var paths_arr = paths_str ? jQuery.parseJSON(paths_str) : [];
     if(paths_arr.length > 0){
         for(var i=0;i<paths_arr.length;i++){
             var pitem = paths_arr[i];
             taskp.pathStructMap[pitem.pathid] = {};
             taskp.pathStructMap[pitem.pathid] = pitem.path;
         }
     }
     taskp.outData = {};
     var outDatastr= $("#tp_outData_path_text").val();
     var outDataPath = outDatastr ? jQuery.parseJSON(outDatastr) : null;
     if(outDataPath !== null){
         taskp.outData.datasPath = "|"+outDataPath.paramPathId+"|";
         taskp.pathStructMap[outDataPath.paramPathId] = outDataPath.paramPath;
     }
     
   //参数映射
     taskp.pathStructMap.keys = [];
     $("#tp_paramMap_params").children("div[name=paramMap]").each(function(i, item){
         var paramMapStr= $(item).children("span[name=paramMap]").attr("code");
         var paramMapObj = paramMapStr ? jQuery.parseJSON(paramMapStr) : null;
         taskp.pathStructMap[paramMapObj.paramPathId] = paramMapObj.paramPath;
         taskp.pathStructMap.keys.push(paramMapObj.paramPathId);
     });
//     //参数传递
//     var parentChoiceParam = $("#tp_fromPath_btn").attr('choiceparam') ? jQuery.parseJSON($("#tp_fromPath_btn").attr('choiceparam')) : null;
//     if(parentChoiceParam){
//         taskele.parentChoiceParam = parentChoiceParam;
//     }
//     var isroottask = parseInt($("input[name=tp_isroottask]:checked").val(), 10);
//     if(isroottask === 1){
//         taskele.parentTaskID = taskele.taskID;
//     }
//     else{//子任务
//         taskele.parentTaskID = $("#tp_parenttask").val();
//     }
//
//     if(!taskp.taskGenConf){
//         taskp.taskGenConf = [];
//     } 
//     else{ //复制或单独编辑一个有子任务的任务时,点击确定,子任务的路径定义需要从新赋值
//         for(var i=0, ilen=taskp.taskGenConf.length;i<ilen;i++){
//             var tgc = taskp.taskGenConf[i];
//             if(tgc && tgc.childTaskDefId != undefined){
//                 for(var i=0, ilen=_this.taskInstance.tasks.length;i<ilen;i++){
//                     var tmpTask = _this.taskInstance.tasks[i];
//                     if(tmpTask.taskID == tgc.childTaskDefId){
//                         //keys keys中路径不应该添加到父任务中去.
//                         var ckeys = tmpTask.param.pathStructMap['keys'];
//                         if(!ckeys){
//                             ckeys = [];
//                         }
//                         for(var cpath in tmpTask.param.pathStructMap){
//                             if(!ckeys.inArray(cpath) && cpath != 'keys'){
//                                 taskp.pathStructMap[cpath] = tmpTask.param.pathStructMap[cpath];
//                             }
//                         }
//                         break;
//                     }
//                 }
//             }
//         }
//     }
//     if(isroottask === 0){ //当前任务为子任务
//
//         var parentTask = {};
//         for(var i=0, ilen=_this.taskInstance.tasks.length;i<ilen;i++){
//             var tmpTask = _this.taskInstance.tasks[i];
//             if(tmpTask.taskID == taskele.parentTaskID){
//                 parentTask = tmpTask;
//                 parentTask.param.taskGenConf = [];
//             }
//         }
//
//         var tmpConf = {};
//         tmpConf.dataPath = taskp.outData.datasPath ? taskp.outData.datasPath : '';
//         tmpConf.splitStep = $("#tp_splitStep").val();
//         tmpConf.childTaskDefId = taskele.taskID;
//         tmpConf.childTaskUrl = {};
//         var urltype = $("input[name=genconf_urltype]:checked").val();
//         tmpConf.childTaskUrl.type = urltype;
//         var urlvalue = $("#tp_genconf_listweburl").val();
//         if(urltype == 'consts'){
//             tmpConf.childTaskUrl.value = {};
//             var paths_str = $("#tp_genconf_listweburl").attr("paths");
//             var paths_arr = paths_str ? jQuery.parseJSON(paths_str) : [];
//             if(paths_arr.length > 0){
//                 for(var i=0;i<paths_arr.length;i++){
//                     var pitem = paths_arr[i];
//                     parentTask.param.pathStructMap[pitem.pathid] = {};
//                     parentTask.param.pathStructMap[pitem.pathid] = pitem.path;
//                     taskp.pathStructMap[pitem.pathid] = pitem.path;
//                     tmpConf.childTaskUrl.value.paramValue = "|"+pitem.pathid+"|";
//                     tmpConf.childTaskUrl.value.paramSource = pitem.path.paramSource;
//                 }
//             }
//         }
//         else{
//             tmpConf.childTaskUrl.templ = urlvalue;
//         }
//         taskp.taskUrls = null;
//         tmpConf.params = [];
//
//         var fromPath = {};
//         var toPath = {};
//         $("#tp_taskGenConf_params").children("div[name=paramPath]").each(function(i, item){
//             var tmp = {};
//             var pathFromStr= $(item).children("span[name=paramPathfrom]").attr("code");
//             var pathFrom = pathFromStr ? jQuery.parseJSON(pathFromStr) : null;
//             taskp.pathStructMap[pathFrom.paramPathId] = pathFrom.paramPath;
//             tmp.paramPath = "|"+pathFrom.paramPathId+"|";
//             tmp.targetType = pathFrom.paramPath.paramSource ? $(item).children("span[name=paramPathfrom]").attr("targetType") : undefined;
//
//             fromPath.id = pathFrom.paramPathId;
//             fromPath.path = pathFrom.paramPath;
//             parentTask.param.pathStructMap[fromPath.id] = fromPath.path;
//             var pathToStr= $(item).children("span[name=paramPathto]").attr("code");
//             var pathTo = pathToStr ? jQuery.parseJSON(pathToStr) : null;
//             taskp.pathStructMap[pathTo.paramPathId] = pathTo.paramPath;
//             tmp.toParamPath = "|"+pathTo.paramPathId+"|";
//             tmpConf.params.push(tmp);
//
//             toPath.id = pathTo.paramPathId;
//             toPath.path = pathTo.paramPath;
//             parentTask.param.pathStructMap[toPath.id] = toPath.path;
//
//         });
//         //tmpConf是子任务参数，需要放到对应的父级结构中
//         var has = false;
//         for(var i=0;i<parentTask.param.taskGenConf;i++){
//             if(JSON.stringify(parentTask.param.taskGenConf[i]) == JSON.stringify(tmpConf)){
//                 has = true;
//                 break;
//             }
//         }
//         if(!has){
//             parentTask.param.taskGenConf.push(tmpConf);
//         }
//     }
     var taskall = {};
     taskall['root'] = taskp;
     
     var query = {};
     query.tasktype = 2;
     query.task = 20;
     query.tasklevel = taskall['root'].taskPro.tasklevel;
     query.local = taskall['root'].taskPro.local;
     query.remote = taskall['root'].taskPro.remote;
     query.activatetime = taskall['root'].taskPro.activatetime;
     query.conflictdelay = taskall['root'].taskPro.conflictdelay;
     query.remarks = taskall['root'].taskPro.remarks;
     query.taskparams = JSON.stringify(taskall);
     
    
     $.ajax({
 		url:basePath + "/SpTaskController/saveTaskInfo.do",
 		type:"post",
 		data:$.param(query),
 		beforeSend:searchIng,
 		complete:function(){
 			endSearchIng();
 			$.removeAttr("disabled");
 		},
 		success:handleTaskSaved
 	});

}

function handleTaskSaved(){
	alert("保存成功！");
}


this.completeAddElement = function(ele){
    if(ele != undefined){
        var isEdit = false;
        if(_this.currEditElement == null){//新增
            _this.taskInstance.tasks.push(ele);
            _this.addModelLayer(ele);
            _this.maxTaskID++; //新增完把全局的taskID++;
        }
        else{ //修改
            isEdit = true;
            var changemodels = ele.taskID != _this.currEditElement.taskID;
            if(changemodels){ //复制
                ele.taskID = _this.currEditElement.taskID;
                //删除旧的element
                _this.delElement($("div[modellayerindex="+ele.taskID+"]").find("h1 a img"));
                //添加上新的element
                _this.taskInstance.tasks.push(ele);
                _this.addModelLayer(ele);
            }
            else{ //编辑
                for(var i=0,ilen=_this.taskInstance.tasks.length;i<ilen;i++){
                    var tmpTask = _this.taskInstance.tasks[i];
                    if(tmpTask.taskID == ele.taskID){
                        _this.taskInstance.tasks[i] = ele;
                    }
                }
                $("#divaddcommonspt").dialog('close');
            }
        }

        //移动modellayer
        var sourceoffset = $("#modellayer_"+ele.taskID+"").offset();
        if(sourceoffset != null){
            $("#modellayer_"+ele.taskID+"").css({left:parseInt(sourceoffset.left+1, 10)+"px"});
        }
        //连线
        if(ele.parentTaskID != ele.taskID){
            jsPlumbInstance.detachAllConnections("modellayer_"+ele.taskID+"");
            jsPlumbInstance.connect({ source: "modellayer_"+ele.parentTaskID+"", target: "modellayer_"+ele.taskID+"" });
        }
    }
    return true;
}

