package com.bxt.sptask.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bxt.sptask.service.SpSaveTaskDataService;
import com.bxt.sptask.service.SpTaskService;
import com.bxt.sptask.taskhandle.vo.TaskVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.SpGrabDataHandle;

@Service
@Transactional(rollbackFor={Throwable.class})
public class SpSaveTaskDataServiceImpl implements SpSaveTaskDataService {
	
	@Autowired
    private SpTaskService spTaskService;

	@Override
	public String saveSpGrabInfo(HashMap<String, String> hmobj) {
		
		JSONObject requsePostData_json = new JSONObject();
		String requsePostData = hmobj.get("sdata");
		
		JSONObject taskvo_json = new JSONObject();
		
		try{
			if(requsePostData == null && requsePostData.equals("")){
				//setErrorMsg(1, "提交数据为空!");  //返回错误
				return null;
			}
			
			/**需要进行处理requsePostData中特殊字符，不然生产json时会报错
			*requsePostData是爬虫直接拼接的json字符串，其value可能会有各种非法的字符，需要做处理，不然生产json时会报各种错误
			*解析提交的数据
			**/
			requsePostData_json = JSONObject.fromObject(requsePostData); //在转为json格式对象时会保存
			String taskinfo = hmobj.get("stask");
			if(taskinfo == null && taskinfo.equals("")){
				 // setErrorMsg(3, "task id can not null."); //返回错误
			}
			
			TaskVo taskConfig = null;
			JSONObject taskinfo_json = JSONObject.fromObject(taskinfo);
			if(taskinfo_json != null && taskinfo_json.size() > 0){
				 if(taskinfo_json.containsKey("id") &&  taskinfo_json.get("id") != null){
					 String taskid = taskinfo_json.getString("taskid");
					 taskConfig = spTaskService.searchTaskTestById(taskid);//通过ID获取任务信息
					 if(taskConfig == null){
						 //返回错误信息
						 //$logger->error(__FILE__ . __LINE__ . "处理通用任务:[remotecommtask_cache]--+-从数据库加载任务失败:[task id:" . $taskinfo->id . "].");
			             //setErrorMsg(4, "处理通用任务:[remotecommtask_cache]--+-从数据库加载任务失败:[task id:" . $taskinfo->id . "].");
					}
				 }else{
					//报错返回
			        //setErrorMsg(3, "task id can not is null.");
				 }
			}else{
				//报错返回
		        //setErrorMsg(3, "task params can not is null.");
			}
			//$logger->info(__FILE__ . __LINE__ . "处理通用任务:[remotecommtask_cache]--+-从数据库加载任务成功!");
			
	       //获取当前任务的原始配置
	       String taskParams_org = taskConfig.getTaskparams();
	       JSONObject taskparams_json = JSONObject.fromObject(taskParams_org);//任务json
	       JSONObject curRootParams_json = null;//父任务json
	       if(taskparams_json != null){
	    	   curRootParams_json = (JSONObject)taskparams_json.get("root");//父任务json
	    	   if(curRootParams_json == null){
	    		  //返回错误信息
	    		  //setErrorMsg(1, "CurrentTaskParam param is null. for key:[root]");
	    	   }
	       }else{
	    	   //返回错误信息
	    	   //setErrorMsg(1, "Task param is null for task:[" . $taskinfo->id . "].");
	       }
	       
	       //处理过滤信息
	       JSONObject taskPro_json  = null;
	       JSONObject postdata_json = null;
	       if(curRootParams_json.containsKey("taskPro") && curRootParams_json.get("taskPro") != null){
	    	   taskPro_json = curRootParams_json.getJSONObject("taskPro");
	       }
	       if(taskPro_json != null && taskPro_json.containsKey("filPageTag") && taskPro_json.get("filPageTag") != null){
	    	   if(taskPro_json.getString("filPageTag").equals("1")){
	    		  postdata_json = SpGrabDataHandle.formatPostdata(requsePostData_json);
	    		  if(postdata_json != null){
	    			  // setErrorMsg(1, "过滤Html标签失败.过滤后数据为空.");
	    		  }
	    	   }else{
	    		   //不需要处理，抓取到的数据直接能够使用
	    		   postdata_json = requsePostData_json;
	    		   //$logger->debug(__FILE__ . __LINE__ . "处理通用任务:[remotecommtask_cache]--+-不过滤Html标签.数据:[" . var_export($postdata, true) . 
	    	   }
	       }
	       
	      //获取运行时参数
	      JSONObject postdata_param_json = null;
	      JSONObject runTimeURLStr_json = new JSONObject();
	      if(postdata_json.containsKey("param") && postdata_json.get("param") != null){
	    	  postdata_param_json = postdata_json.fromObject("param");
	    	  if(postdata_param_json.containsKey("runTimeParam") && postdata_param_json.get("runTimeParam") != null){
	    		  Object runTimeURLStr = postdata_param_json.get("runTimeParam");
	    		  if(runTimeURLStr != null &&  !(runTimeURLStr instanceof JSONArray) && runTimeURLStr instanceof String){
	    			  runTimeURLStr_json = JSONObject.fromObject(runTimeURLStr);
	    			  curRootParams_json.put("runTimeParam", runTimeURLStr_json);
	    		  }
	    	  }
	      }
	      
	      String spage = "";
	      if(postdata_json.containsKey("page") && postdata_json.get("page") != null){
	    	  spage = postdata_json.getString("page");
	      }      
		
	      /*if (!empty($postdata['page_url'])) {
	            $curPageUrl = $postdata['page_url'];
	            $currentTaskParam["runTimeParam"][INNER_PARAM_CUR_PAGE_URL] = $curPageUrl;
	            $logger->debug(__FILE__ . __LINE__ . "处理通用任务:[remotecommtask_cache]--+-set current page url to runtimeParams success! curPageUrl:[" . $runTimeURLStr . "].");
	        }*/
	      
	      if(postdata_json.containsKey("page_url") && postdata_json.get("page_url") != null){
	    	  Object curPageUrl = postdata_json.get("page_url");
	    	  if (curPageUrl instanceof JSONObject) {
	    		  curPageUrl = (JSONObject) curPageUrl;
	    		  runTimeURLStr_json.put("curPageUrl", curPageUrl);
				} else if (curPageUrl instanceof JSONArray) {
					curPageUrl = (JSONArray) curPageUrl;
					runTimeURLStr_json.put("curPageUrl", curPageUrl);
				} else if (curPageUrl instanceof String) {
					runTimeURLStr_json.put("curPageUrl", curPageUrl);
				}else{
					runTimeURLStr_json.put("curPageUrl", curPageUrl);
				}
	      }
	      
	      JSONObject source_json = new JSONObject();
	      if(postdata_json.containsKey("sourceid") && postdata_json.get("sourceid") != null){
	    	  String sourceid  = postdata_json.getString("sourceid");
	    	  taskvo_json.put("tasource", sourceid);
	    	  runTimeURLStr_json.put("source", sourceid);
	      }else if(postdata_json.containsKey("page_url") && postdata_json.get("page_url") != null){
	    	 //此功能没有使用
	    	  /* String page_url = postdata_json.getString("page_url");
	    	  if(page_url != null && page_url.indexOf("")<0){
	    		  page_url  = "http://" + page_url;
	    	  }
	    	  */
	      }
	      
	      if(curRootParams_json.containsKey("outData") && curRootParams_json.get("outData") != null){
	    	  
	      }else{
	    	  
	    	  
	      }
	      
	      Integer allcount = 0;
	      //需要解析
	      /*//默认情况下 从结果中去data作为所有需要结果数据来处理
	        if (empty($currentTaskParam["outData"]) || empty($currentTaskParam["outData"]["datasPath"])) {
	            $resultDatas = $postdata['data'];
	        } else {
	            $dataPathId = $currentTaskParam["outData"]["datasPath"];
	            $dataPathId = substr($dataPathId, 1, strlen($dataPathId) - 2);
	            $pathStr = $currentTaskParam["pathStructMap"][$dataPathId];
	            $logger->debug(__FILE__ . __LINE__ . " " . __FUNCTION__ . " 处理通用任务:[remotecommtask_cache]--+-根据[out]路径获取抓取文章数据,dataPathId:[" . $dataPathId . "] pathStr:[" . var_export($pathStr, true) . "].");
	            $resultDatas = getValueFromObjWrap($postdata['data'], $pathStr);
	            $logger->debug(__FILE__ . __LINE__ . " " . __FUNCTION__ . " 处理通用任务:[remotecommtask_cache]--+-根据[out]路径获取抓取文章数据成功. 文章数据:[" . var_export($resultDatas, true) . "].");
	        }
	        $allcount = count($resultDatas);
	        $logger->info(__FILE__ . __LINE__ . " " . __FUNCTION__ . " 处理通用任务:[remotecommtask_cache]--+-根据[out]路径获取抓取文章数据成功. 本次抓取数据:[" . $allcount . "]条.");
*/

	      //********根据配置判读当前任务是否需要入库(即当前任务抓取到的数据是否需要插入到数据库中)*********//
	      //测试代码,以后需要从数据库中查询分词字典
	      //$dictionary_plan_new ="0001";
	      /* $saveData = $currentTaskParam["taskPro"]["dataSave"];*/
	      String saveData = taskPro_json.getString("dataSave");
	      if(saveData != null && !saveData.trim().equals("") && saveData.trim().equals("1")){
	    	  
	    	  
	    	  
	    	  
	      }else{
	    	  //不存盘
	      }

	      //子任务存盘
	      //taskPro下面的isGenChildTask
	      if(taskPro_json.containsKey("isGenChildTask") && taskPro_json.get("isGenChildTask") != null && 
	    		  (taskPro_json.getString("isGenChildTask").equals("1") || taskPro_json.getString("isGenChildTask").equals("true"))){
	    	  if(curRootParams_json.containsKey("taskGenConf") && curRootParams_json.get("isGenChildTask") != null){
	    		  if(allcount > 0){
	    			  String remarks = "任务:["  + taskinfo_json.getString("id") + "]###的子任务";
	    			  Object genTaskCfgs_obj = curRootParams_json.get("taskGenConf");
	    			  //$genTaskCfgs = $currentTaskParam["taskGenConf"];
	    			  if(genTaskCfgs_obj != null && genTaskCfgs_obj instanceof JSONArray){
	    				  JSONArray genTaskCfgs_arr = (JSONArray)genTaskCfgs_obj;
	    				  Integer taskNumPerChild = 1,genIdx =0;
	    				  String childParamId = "";
	    				  for(int i = 0;i< genTaskCfgs_arr.size();i++){
	    					  genIdx = i;
	    					  JSONObject genTaskConfig_json = genTaskCfgs_arr.getJSONObject(i);
	    					  if(genTaskConfig_json.containsKey("splitStep") && genTaskConfig_json.get("splitStep") != null){
	    						  try{
	    							  taskNumPerChild =  genTaskConfig_json.getInt("splitStep");
	    						  }catch(Exception e){
	    							  taskNumPerChild = 1;
	    						  }
	    					  }else{
	    						  taskNumPerChild = 1;//子任务步长
	    					  }
	    					
	    					  //子任务参数定义Id
	                          childParamId = genTaskConfig_json.getString("childTaskDefId");
	    					  //获取子任务
	                          JSONObject childTaskParam_json = taskparams_json.getJSONObject("childTaskDefId");
	                          if (childTaskParam_json != null) {
	                        	JSONObject resultDatas = (JSONObject)requsePostData_json.get("data");
	                        	 Boolean addResult = SpGrabDataHandle.chunkDeriveTask4Common(resultDatas, taskparams_json, curRootParams_json, childParamId, childTaskParam_json, taskNumPerChild, genIdx, remarks);
	                        	  
	                        	  
	                        	  //addResult = chunkDeriveTask4Common($resultDatas, $taskParams, $currentTaskParam, $childParamId, $childTaskParam, $taskNumPerChild, $genIdx, $remarks);
	                              /*if ($addResult) {
	                                  $logger->debug(__FILE__ . __LINE__ . " " . " 处理通用任务:[remotecommtask_cache]--+-数据缓存--+-生成子任务[第{$genIdx}个子任务]成功! 任务taskid:" . $taskinfo->id . "]!");
	                              } else {
	                                  throw new Exception("处理通用任务:[remotecommtask_cache]--+-数据缓存--+-生成子任务[第{$genIdx}个子任务]异常,taskId:[" . $taskinfo->id . "].");
	                              }*/
	                          }else{
	                        	  //$logger->error(__FILE__ . __LINE__ . " 处理通用任务:[remotecommtask_cache]--+-数据缓存--+-生成子任务[第{$genIdx}个子任务]异常:['childTaskDef null'] for childTaskDefId:[" . $childParamId . "] AllConfig:[" . var_export($genTaskCfg, true));
	                              //setErrorMsg(1, "处理通用任务:[remotecommtask_cache]--+-数据缓存--+-生成子任务[第{$genIdx}个子任务]异常:['childTaskDef null']");
	                          }
	    				  } 
	    			  }else{
	    				  //setErrorMsg(1, "处理通用任务:[remotecommtask_cache]--+-数据缓存--+-generate task exception:['genTaskCfgs must be array']");
	    				  //setErrorMsg(1, "处理通用任务:[remotecommtask_cache]--+-数据缓存--+-generate task exception:['taskGenConf null']");
	    			  }
	    		  }else {
	                   // $logger->info(__FILE__ . __LINE__ . " " . " 处理通用任务:[remotecommtask_cache]--+-数据缓存--+-生成子任务--+-不需要为当前任务taskid:" . $taskinfo->id . "]派生子任务，当前任务共有:[" . $allcount . "]条数据.");
	              }
	    	  }else{
	    		  // $logger->info(__FILE__ . __LINE__ . " " . "处理通用任务:[remotecommtask_cache]--+-数据缓存--+-生成子任务--+-子任务生成配置:[taskGenCo
	    	  }
	      }else {
	    	  //处理通用任务:[remotecommtask_cache]--+-数据缓存--+-生成子任务--+-不需要生成子任务. 当前任务taskid:[" . $taskinfo->id . "],属性[taskPro.isGenChildTask]为false,isGenChildTask
	          //$logger->info(__FILE__ . __LINE__ . " " . " 处理通用任务:[remotecommtask_cache]--+-数据缓存--+-生成子任务--+-不需要生成子任务. 当前任务taskid:[" . $taskinfo->id . "],属性[taskPro.isGenChildTask]为false,isGenChildTask:[" . var_export($currentTaskParam["taskPro"], true) . "].");
	      }
	      //$logger->info(__FILE__ . __LINE__ . " " . " 处理通用任务:[remotecommtask_cache]--+-数据缓存处理成功! 为当前任务taskid:[" . $taskinfo->id . "]");
		}catch(Exception e){
			 // setErrorMsg(4, "task id can not null."); //返回错误
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
}
