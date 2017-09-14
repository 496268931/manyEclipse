package com.bxt.sptask.utils;

import java.util.Date;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DefParamUtil {

	// 组装常量参数信息
	public static String defparamIntAndString(JSONObject contantObj) {
		Iterator it = contantObj.keys();
		String sResult = "", keyname = "";
		while (it.hasNext()) {
			keyname = it.next().toString();
			// System.out.println(contantObj.get(keyname).getClass().toString());
			if (contantObj.get(keyname).getClass().toString()
					.equals("class java.lang.Integer")
					|| contantObj.get(keyname).getClass().toString()
							.equals("class java.lang.Double")) {
				if (sResult != null && !sResult.equals("")) {
					sResult = sResult + ",\"" + keyname + "\"" + ":"
							+ contantObj.getInt(keyname);
				} else {
					sResult = "\"" + keyname + "\"" + ":"
							+ contantObj.getInt(keyname);
				}
			} else if (contantObj.get(keyname).getClass().toString()
					.equals("class java.lang.String")) {
				if (sResult != null && !sResult.equals("")) {
					sResult = sResult + ",\"" + keyname + "\"" + ":" + "\""
							+ contantObj.getString(keyname) + "\"";
				} else {
					sResult = "\"" + keyname + "\"" + ":" + "\""
							+ contantObj.getString(keyname) + "\"";
				}
			} else {
				System.out.println("此类型不存在"
						+ contantObj.get(keyname).getClass().toString());
			}

		}
		return sResult;
	}

	/**
	 * 设置爬虫任务抓取的地址规则;先判断有没有直接设置好值 wxd 2016.11.24 wxd@inter3i.com
	 * @throws Exception 
	 */
	public static String getUrlFromTaskParam(JSONObject curRootParamsJson) throws Exception {

		String sUrlVal = "", paramValues = "";
		String curURls = "";
		String[] arUrl = null, arrayUrlValue = null;
		int sq = 0, sh = 0;
		
		JSONArray result_json = new JSONArray();

		JSONObject taskUrls = new JSONObject();
		JSONObject paramPathStruct = new JSONObject();
		JSONObject souceValues = new JSONObject();
		if (curRootParamsJson.containsKey("taskUrls") && curRootParamsJson.get("taskUrls") != null) {
			taskUrls = (JSONObject) curRootParamsJson.get("taskUrls");
			if (taskUrls.containsKey("type") && taskUrls.get("type") != null) {
				String sType = taskUrls.getString("type");
				if (sType != null && (sType.equals("consts") ||  sType.equals("gen"))) {// 处理consts类型
					if (taskUrls.get("urlValues") instanceof JSONArray) {
						JSONArray values = taskUrls.getJSONArray("urlValues");
						for (int i = 0; i < values.size(); i++) {
							Object ob = values.get(i);
							if (ob instanceof JSONObject) {
								ob = (JSONObject) ob;
							} else if (ob instanceof JSONArray) {
								ob = (JSONArray) ob;

							} else if (ob instanceof String) {
								sUrlVal = ob.toString();
								if (sUrlVal.indexOf("|") > -1) {
									sq = sUrlVal.indexOf("|");
									sh = sUrlVal.indexOf("|",sUrlVal.indexOf("|") + 1);
									paramValues = sUrlVal.substring(sq + 1, sh);
									if (curRootParamsJson.containsKey("pathStructMap") && curRootParamsJson.get("pathStructMap") != null
											&& curRootParamsJson.getJSONObject("pathStructMap").containsKey(paramValues)
											&& curRootParamsJson.getJSONObject("pathStructMap").get(paramValues) != null) {
										//读取pathStructMap的JSON对象中paramValues的为key的值,
										//paramPathStruct={"col_name":"original_url","col_type":"13","col_name_ex":null,"paramSource":2}
										paramPathStruct = curRootParamsJson.getJSONObject("pathStructMap").getJSONObject(paramValues);
										//获取自定义参数的值
										souceValues = getSourceObj(curRootParamsJson, paramPathStruct);
										//"souceValues = {"original_url":"https://list.jd.com/list.html?cat=1318,2628,12123&ev=1107_70677&page=1&sort=sort_totalsales15_desc&trans=1&JL=4_2_0#J_main","floorNum":-1,"source_host":"电商","column":"冲锋衣","column1":"京东","dataClsfct":"trendAnalysis","updateFreq":"day"},
										curURls = getValueFromObjWrap(souceValues,paramPathStruct);
										//$curURls = getValueFromObjWrap($souceValues,$paramPathStruct);
									}
								}else{
									result_json.add(sUrlVal);
									return result_json.toString();
								}
							}
						}
					} else if (taskUrls.get("urlValues") instanceof String) {
						//如果是字符串的话，需要转为JSONArray数组，然后返回
						result_json.add(taskUrls.get("urlValues").toString());
						return result_json.toString();
					}
					return curURls;
				} else if (sType.equals("gen")) {// 处理gen类型
					if(taskUrls.containsKey("urlValues") && taskUrls.getJSONArray("urlValues").size()>1){
						throw new Exception("getUrlFromTaskParam exception,pathStructMap null!");	
					}else if(taskUrls.containsKey("urlValues")){
						if(curRootParamsJson.containsKey("pathStructMap") && curRootParamsJson.get("pathStructMap") != null){
							//JSONObject pathStructMap = curRootParamsJson.getJSONObject("pathStructMap");
							System.out.println("此处不做解析，任务配置里面没有使用该功能");
							//$taskUrls["urlValues"][0] = replaceParam4Url($taskUrls["urlValues"][0], $currentTaskParam, $pathStructMap, $grabDatas);
							//taskUrls.getJSONArray("urlValues").set(0, replaceParam4Url(taskUrls.getJSONArray("urlValues").get(0),curRootParamsJson,pathStructMap));
							
						}else{
							throw new Exception("getUrlFromTaskParam exception,pathStructMap null!");	
						}
					}else{
						throw new Exception("getUrlFromTaskParam exception,pathStructMap null!");
					}
				} else {
					throw new Exception("getUrlFromTaskParam exception,pathStructMap null!");
				}
			} else {
				throw new Exception("getUrlFromTaskParam exception,pathStructMap null!");
			}
		} else {
			throw new Exception("getUrlFromTaskParam exception,pathStructMap null!");
		}
		return "";
	}

	public static JSONObject getSourceObj(JSONObject taskParam,JSONObject pathStruct) {
		if (pathStruct.containsKey("paramSource") && pathStruct.get("paramSource") != null) {
			if (pathStruct.getString("paramSource").equals("1")) {
				return taskParam.getJSONObject("constants");
			} else if (pathStruct.getString("paramSource").equals("2")) {
				return taskParam.getJSONObject("paramsDef");
			} else if (pathStruct.getString("paramSource").equals("3")) {
				if (taskParam.containsKey("runTimeParam")) {
					taskParam.put("runTimeParam", (new JSONObject()));
				}
				return taskParam.getJSONObject("runTimeParam");
			} else if (pathStruct.getString("paramSource").equals("4")) {
				return taskParam.getJSONObject("parentParam");
			} else {
				System.out.println(" 不支持的变量源类型:["+ taskParam.getString("paramSource") + "].");
			}
		}
		return null;
	}

	public static String getValueFromObjWrap(JSONObject sourceObj,JSONObject pathStruct) throws Exception {
		String paramName = "", paramSource = "",paramV = "";
		if (pathStruct.containsKey("col_name") && pathStruct.get("col_name") != null) {
			paramName = pathStruct.getString("col_name");
			if (pathStruct.containsKey("paramSource") && pathStruct.get("paramSource") != null) {
				paramSource = pathStruct.getString("paramSource");
				//当paramSource值为3时，并且paramName为cur_date_time则使用时间来初始化运行参数
				if (paramSource != null && paramSource.trim().equals("3")) {
					if (paramName != null && paramName.equals("cur_date_time")) {
						DateTimeUtil dtu = new DateTimeUtil();
						paramV = dtu.toDateStr(new Date());
						sourceObj.put(paramName, paramV);
					}
					JSONArray cur_date_jsona = new JSONArray();
					cur_date_jsona.add(paramName);
					return cur_date_jsona.toString();
				}
			}
		}
		//如果sourceObj为空或sourceObj.get(paramName)值为空，则报异处理
		if(sourceObj == null){
			throw new Exception("sourceObj is null.getValueFromObjWrap excption!");
		}else if(!sourceObj.containsKey(paramName)){
			throw new Exception("paramName:[" + paramName + "] not exist in sourceObj");
		}
		String valueJson = null;
		if(pathStruct.containsKey("col_name_ex") && !pathStruct.getString("col_name_ex").equals("null")){
			valueJson = getValueFromObj(sourceObj.get(paramName), (JSONObject)pathStruct.get("col_name_ex"));
		}else{
			valueJson = sourceObj.getString(paramName);
		}
		
		return valueJson;
	}

	//调用JSONObject对象
	public static String getValueFromObj(Object sourceObj, JSONObject pathStruct) throws Exception{
		String result_value = null;
		JSONArray valueArrays =null, sourceObjArr = null;
		JSONObject sourceObj_json = null;
		Object obarr_value = null;
		if(pathStruct.containsKey("type") && pathStruct.get("type") != null){
			if(pathStruct.getString("type").trim().equals("1")){//对象取值路径对象定义常常量-属性数据类型-数组
				String arrayIdx = pathStruct.getJSONObject("data").getJSONObject("arr_data").getString("arr_idx");
				 if (arrayIdx != null && arrayIdx.equals("-1")) {
						if (sourceObj instanceof JSONArray) {
							sourceObjArr = (JSONArray) sourceObj;
							for (int i = 0; i < sourceObjArr.size(); i++) {
								obarr_value = sourceObjArr.get(i);
								if(pathStruct.containsKey("name_ex") && !pathStruct.getString("name_ex").equals("null")){
									result_value = getValueFromObj(obarr_value, pathStruct.getJSONObject("name_ex"));
								}else{
									if(obarr_value instanceof String){
										valueArrays.add(obarr_value.toString());
									}else if(obarr_value instanceof Integer){
										valueArrays.add(obarr_value);
									}
								}
							}
						}
					 return valueArrays.toString();
				 }else{
					 if(sourceObj instanceof JSONObject){
						 sourceObj_json = (JSONObject) sourceObj;
						 obarr_value = sourceObj_json.get(arrayIdx);
						 if(obarr_value instanceof String){
							 valueArrays.add(obarr_value.toString());
						 }else if(obarr_value instanceof Integer){
							 valueArrays.add(obarr_value);
						 }	 
					 }
					 if(pathStruct.containsKey("name_ex") && !pathStruct.getString("name_ex").equals("null")){
						 return getValueFromObj(obarr_value, pathStruct.getJSONObject("name_ex"));
					 }else{
						 return valueArrays.toString();
					 }
				 }
			}else if(pathStruct.getString("type").trim().equals("2")){//对象取值路径对象定义常常量-属性数据类型-对象
				String propName = pathStruct.getJSONObject("data").getString("chld_col_name");
				String svalue = "";
				if(sourceObj instanceof JSONObject){
					sourceObj_json = (JSONObject) sourceObj;
					obarr_value = sourceObj_json.get(propName);
					 if(obarr_value instanceof String){
						 valueArrays.add(obarr_value.toString());
					 }else if(obarr_value instanceof Integer){
						 valueArrays.add(obarr_value);
					 }
					if(pathStruct.containsKey("name_ex") && !pathStruct.getString("name_ex").equals("null")){
						return getValueFromObj(obarr_value, pathStruct.getJSONObject("name_ex"));
					}
				}
				return valueArrays.toString();
			}
		}else{
			throw new Exception("sourceObj is null.getValueFromObjWrap excption!");
		}
		return null;
	}
	
	public static Object replaceParam4Url(Object urlrule,JSONObject currentTaskParam,JSONObject pathStructMap){
		String[] matches = urlrule.toString().split("/(\\w*):Enum\\(\\|([^\\)]+)\\|\\)/");
		if (matches[0].length() > 0) {
			
			
		}
		return null;
	}

	
	
	
	

}
