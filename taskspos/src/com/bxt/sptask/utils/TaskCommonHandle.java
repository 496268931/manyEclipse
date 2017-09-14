package com.bxt.sptask.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.bxt.sptask.taskhandle.vo.TaskVo;
import com.bxt.sptask.taskinit.statichm.StaticMapParams;

/**
 * 处理任务
 */
public class TaskCommonHandle {
	
	public static TaskVo getInitCommonTask(TaskVo taskinfo){
		try{
			String taskparams = taskinfo.getTaskparams();
			JSONObject allParamsJsonObj =  JSONObject.fromObject(taskparams);//转化为JSON数组
			JSONObject curRootParamsJsonObj = (JSONObject) allParamsJsonObj.get("root");
			
			//初始化爬虫运行时参数
			if(!curRootParamsJsonObj.containsKey("runTimeParam")){
				curRootParamsJsonObj.put("runTimeParam", new JSONObject());
			}else if(curRootParamsJsonObj.getString("runTimeParam").equals("null")){
				curRootParamsJsonObj.put("runTimeParam", new JSONObject());
			}
			
			System.out.println("runTimeParam:" + curRootParamsJsonObj.get("runTimeParam"));
			
			JSONObject runTimeParam = (JSONObject) curRootParamsJsonObj.get("runTimeParam");
			if(runTimeParam.containsKey("followpost")){
				runTimeParam.put("followpost", (new JSONArray()));
			}

			if(runTimeParam.containsKey("scene")){
				runTimeParam.remove("scene");
			}
			if(!runTimeParam.containsKey("scene")){
				runTimeParam.put("scene",(new JSONObject()));
			}
			
			JSONObject sceneJson = (JSONObject) runTimeParam.get("scene");
			if(!sceneJson.containsKey("isremote")){
				sceneJson.put("isremote",1);
			}else{
				sceneJson.put("isremote",1);
			}
			
			if(!sceneJson.containsKey("historystat")){
				sceneJson.put("historystat","");
			}
			 
			runTimeParam.put("scene", sceneJson);
   /**更新运行时参数*/
			curRootParamsJsonObj.put("runTimeParam", runTimeParam);
			
			//搜索引擎模板
	        //$config = $currentTaskParam["taskPro"]["template"];
			String tmpid = null;
			JSONObject taskProJson = null;
			if(curRootParamsJsonObj.containsKey("taskPro")){
				taskProJson = (JSONObject) curRootParamsJsonObj.get("taskPro");
				if(taskProJson.containsKey("template")){
					tmpid = taskProJson.get("template").toString();
					if(tmpid == null){
						return null;
					}else if(tmpid.trim().equals("")){
						return null;
					}
					Integer itmpid = null;
					try{
						itmpid = Integer.parseInt(tmpid);
					}catch(Exception e){
						return null;
					}
					//处理模板信息
					String sTaskTemplateInof = StaticMapParams.hmTemplate.get(itmpid);
					if(sTaskTemplateInof != null && !sTaskTemplateInof.equals("")){
						JSONObject sTaskTpiObj = JSONObject.fromObject(sTaskTemplateInof);
						String seol = "";
						if(sTaskTpiObj.getString("content").indexOf("\r\n") > 0){
							seol = "\r\n";
						}else{
							seol = "\n";
						}
		/**更新模板urlregex参数*/	
						JSONObject urlregexJson = new JSONObject();
						String urlregex = sTaskTpiObj.getString("urlregex");
						String[] arurlregex = null;
						if(urlregex != null && !urlregex.equals("")){
							 arurlregex = urlregex.split("[\\r\n,\\s]+");
							 urlregexJson.put("urlregex", arurlregex);
						}
		/**更新模板detailurlregex参数*/
						JSONObject detailurlregexJson = new JSONObject();
						String sdetailurlregex = sTaskTpiObj.getString("detailurlregex");
						String[] arDetailrulregex = null;
						if(sdetailurlregex != null && !sdetailurlregex.equals("")){
							arDetailrulregex = sdetailurlregex.split("[\\r\n,\\s]+");
							detailurlregexJson.put("detailurlregex", arDetailrulregex);
						}
			
						JSONObject configJson = new JSONObject();
						String constantsString = "";
						if(curRootParamsJsonObj.containsKey("constants") && curRootParamsJsonObj.get("constants") != null && 
								!curRootParamsJsonObj.getString("constants").equals("null")){
							JSONObject constantsJson = (JSONObject)curRootParamsJsonObj.get("constants");
							String sConstants = DefParamUtil.defparamIntAndString(constantsJson);
							if(sConstants != null && !sConstants.equals("")){
								constantsString = "#CONST:{" + sConstants + "}" + seol;
								configJson.put("config", constantsString);
							}else{
								configJson.put("config", "");
							}
						}else{
							configJson.put("config", "");
						}
		   /**更新模板参数*/			
						if(configJson.containsKey("config") && configJson.getString("config") != null){
							configJson.put("config", configJson.getString("config") + sTaskTpiObj.getString("content"));
						}else{
							configJson.put("config", sTaskTpiObj.getString("content"));
						}
						
	       /**考虑一个任务中配置了多个模版文件,设置任务中步骤和所使用的模版之间的映射关系*/
			            //$result['task']["templMap"] = json_encode($currentTaskParam["templMap"]);
			            //$result['task']["stepNumURLPatterns"] = json_encode($currentTaskParam["stepNumURLPatterns"]);
						JSONObject templMapJson = new JSONObject();
						if(curRootParamsJsonObj.containsKey("templMap") && (curRootParamsJsonObj.get("templMap") != null)){
							templMapJson = (JSONObject)curRootParamsJsonObj.get("templMap");
						}
						JSONObject stepNumUrlPatJson = new JSONObject();
						if(curRootParamsJsonObj.containsKey("stepNumURLPatterns") && (curRootParamsJsonObj.get("stepNumURLPatterns") != null)){
							stepNumUrlPatJson = (JSONObject)curRootParamsJsonObj.get("stepNumURLPatterns");
						}
						
						//$urls = getUrlFromTaskParam($currentTaskParam);
				        //$result['task']['url'] = $urls;
						JSONObject urlJson = new JSONObject();
						String[] sUrl = DefParamUtil.getUrlFromTaskParam(curRootParamsJsonObj);
						urlJson.put("url", sUrl);
						
						
						
						
					}else{
						return null;
					}
				}
				
			}
			
			
			
			allParamsJsonObj.put("root", curRootParamsJsonObj);
			taskinfo.setTaskparams(allParamsJsonObj.toString());
			//String tasklevel = JSONObject.fromObject(curRootParamsJsonObj.getString("taskPro")).getString("tasklevel");
			//System.out.println(tasklevel);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return taskinfo;
	}
	
	
	public static void main(String[] args) {
		
	}

}
