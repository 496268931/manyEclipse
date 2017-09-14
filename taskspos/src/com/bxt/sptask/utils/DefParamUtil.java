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
	public static String[] getUrlFromTaskParam(JSONObject curRootParamsJson) throws Exception {

		String urlValues = "", sUrlVal = "", paramValues = "";
		String[] arUrl = null, arrayUrlValue = null;
		int sq = 0, sh = 0;

		JSONObject taskUrlsJson = new JSONObject();
		JSONObject paramPathStruct = new JSONObject();
		JSONObject souceValues = new JSONObject();
		if (curRootParamsJson.containsKey("taskUrls") && curRootParamsJson.get("taskUrls") != null) {
			taskUrlsJson = (JSONObject) curRootParamsJson.get("taskUrls");
			if (taskUrlsJson.containsKey("type") && taskUrlsJson.get("type") != null) {
				String sType = taskUrlsJson.getString("type");
				if (sType.equals("consts")) {// 处理consts类型
					String[] result = null;
					if (taskUrlsJson.get("urlValues") instanceof JSONArray) {
						JSONArray values = taskUrlsJson.getJSONArray("urlValues");
						for (int i = 0; i < values.size(); i++) {
							Object ob = values.get(i);
							if (ob instanceof JSONObject) {
								ob = (JSONObject) ob;
							} else if (ob instanceof JSONArray) {
								ob = (JSONObject) ob;

							} else if (ob instanceof String) {
								sUrlVal = ob.toString();
								if (sUrlVal.indexOf("|") > -1) {
									sq = sUrlVal.indexOf("|");
									sh = sUrlVal.indexOf("|",sUrlVal.indexOf("|") + 1);
									paramValues = sUrlVal.substring(sq + 1, sh);
									if (curRootParamsJson.containsKey("pathStructMap") && curRootParamsJson.get("pathStructMap") != null
											&& curRootParamsJson.getJSONObject("pathStructMap").containsKey(paramValues)
											&& curRootParamsJson.getJSONObject("pathStructMap").get(paramValues) != null) {
										paramPathStruct = curRootParamsJson.getJSONObject("pathStructMap").getJSONObject(paramValues);

										souceValues = getSourceObj(curRootParamsJson, paramPathStruct);
										
										String curURls = getValueFromObjWrap(souceValues,paramPathStruct);
										//$curURls = getValueFromObjWrap($souceValues,$paramPathStruct);
										
										
										System.out.println(paramPathStruct.toString());
									}

								}
							}
						}
						
					} else if (taskUrlsJson.get("urlValues") instanceof String) {
						
						
						System.out.println(taskUrlsJson.get("urlValues").toString());
					}
					// //从其他地方的参数定义中提取参数
					// $curURls = getValueFromObjWrap($souceValues,
					// $paramPathStruct);
					// if (is_array($curURls)) {
					// foreach ($curURls as $curUrlValue) {
					// $result[] = $curUrlValue;
					// $logger->debug(SELF . " " . __FUNCTION__ .
					// " 生成当前任务的运行URLS,类型为list,增加一个URL:[" . $curUrlValue . "]");
					// }
					// } else {
					// $result[] = $curURls;
					// }
					// } else {
					// $result[] = $urlVar;
					// }
					// }
				} else if (sType.equals("gen")) {// 处理gen类型

				} else {
					System.out.println("taskUrls中type参数类型不被支持，不能生成任务URL!");
				}
			} else {
				System.out.println("taskUrls中type参数不存在或为空，不能生成任务URL!");
			}
		} else {
			System.out.println("URL中taskUrls不存在或内容为空，不能生成任务URL");
		}

		return arUrl;
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
				if (paramSource != null && paramSource.trim().equals("3")) {
					if (paramName != null && paramName.equals("cur_date_time")) {
						// 获取日期模版template,根据一下时间模板取得的时间没有使用，所以没有翻译此代码
						// if (!empty($pathStruct["template"])) {
						// $templt = $pathStruct["template"];
						// } else {
						// $templt = INNER_PARAM_DATE_TEMP;
						// }
						DateTimeUtil dtu = new DateTimeUtil();
						paramV = dtu.toDateStr(new Date());
						sourceObj.put(paramName, paramV);
					}
					return sourceObj.getString(paramName);
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
			valueJson = getValueFromObj((JSONObject)sourceObj.get(paramName), (JSONObject)pathStruct.get("col_name_ex"));
		}else{
			valueJson = sourceObj.getString(paramName);
		}
		
		return valueJson;
	}
	
	
	public static String getValueFromObj(JSONObject sourceObj, JSONObject pathStruct){
		
		if(pathStruct.containsKey("type") && pathStruct.get("type") != null){
			if(pathStruct.getString("type").trim().equals("1")){//对象取值路径对象定义常常量-属性数据类型-数组
				String arrayIdx = pathStruct.getJSONObject("data").getJSONObject("arr_data").getString("arr_idx");
				 if (arrayIdx != null && arrayIdx.equals("-1")) {
					 String[] valueArrays = null;
					 
				 }else{
					 
				 }
			}else if(pathStruct.getString("type").trim().equals("2")){//对象取值路径对象定义常常量-属性数据类型-对象
				
				
			}
		}
//		    if ($pathStruct["type"] === PROPERTY_DATA_TYPE_ARRAY) {
//		        $arrayIdx = $pathStruct["data"]["arr_data"]["arr_idx"];
//		        //取所有的元素
//		        if ($arrayIdx === -1) {
//		            //取每个索引的值
//		            $valueArrays = array();
//		            foreach ($sourceObj as $oneElement) {
//		                if (!empty($pathStruct["name_ex"])) {
//		                    $valueArrays[] = getValueFromObj($oneElement, $pathStruct["name_ex"]);
//		                } else {
//		                    $valueArrays[] = $oneElement;
//		                }
//		            }
//		            return $valueArrays;
//		        } else {
//		            //获取单个索引的值
//		            $value = $sourceObj[$arrayIdx];
//		            if (!empty($pathStruct["name_ex"])) {
//		                return getValueFromObj($value, $pathStruct["name_ex"]);
//		            } else {
//		                return $value;
//		            }
//		        }
//		        //return getValueFromObj($sourceObj->$curPath, $childPath);
//		    } else if ($pathStruct["type"] === PROPERTY_DATA_TYPE_OBJ) {
//		        $propName = $pathStruct["data"]["chld_col_name"];
//		        $value = $sourceObj[$propName];
//		        //需要递归获取下一级的数据
//		        if (!empty($pathStruct["name_ex"])) {
//		            return getValueFromObj($value, $pathStruct["name_ex"]);
//		        }
//		        return $value;
//		    } else {
//		        throw new Exception("illegal PathStruct . type:[" . $pathStruct["type"] . "].");
//		    }
		return null;
	}

}
