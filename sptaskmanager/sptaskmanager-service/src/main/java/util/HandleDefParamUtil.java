package util;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bxt.sptask.sptmpconfig.vo.TaskcfgtempVO;
import com.bxt.sptask.utils.IDUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HandleDefParamUtil{
	/**
	 * 解析自定义的json数据为Tree对象
	 */
	public List<TaskcfgtempVO> analyJsonToTree(String sparamJsonData,String skey_json){
		String sparNodeID = "";
		List<TaskcfgtempVO> dvf_tasktemp_list = new ArrayList<TaskcfgtempVO>();
		try{
			JSONObject param_json = JSONObject.fromObject(sparamJsonData);
			JSONObject key_dev_json = param_json.getJSONObject(skey_json);
			sparNodeID = IDUtils.genItemId();
			dvf_tasktemp_list.add(getTaskcfgtempVO(sparNodeID,"常量定义","0","","",""));
			analyEvrDefJson(key_dev_json,"constants_def","constants",dvf_tasktemp_list,sparNodeID);//解析常量定义
			
			sparNodeID = IDUtils.genItemId();
			dvf_tasktemp_list.add(getTaskcfgtempVO(sparNodeID,"任务参数定义","0","","",""));
			analyEvrDefJson(key_dev_json,"paramsDef_def","paramsDef",dvf_tasktemp_list,sparNodeID);//解析常量定义
			
			sparNodeID = IDUtils.genItemId();
			dvf_tasktemp_list.add(getTaskcfgtempVO(sparNodeID,"运行参数定义","0","","",""));
			analyEvrDefJson(key_dev_json,"runTimeParam_def","runTimeParam",dvf_tasktemp_list,sparNodeID);//解析常量定义
			
			sparNodeID = IDUtils.genItemId();
			dvf_tasktemp_list.add(getTaskcfgtempVO(sparNodeID,"父任务参数定义","0","","",""));
			analyEvrDefJson(key_dev_json,"parentParam_def","parentParam",dvf_tasktemp_list,sparNodeID);//解析常量定义
			
			sparNodeID = IDUtils.genItemId();
			dvf_tasktemp_list.add(getTaskcfgtempVO(sparNodeID,"抓取数据定义","0","","",""));
			analyEvrDefJson(key_dev_json,"outData_def","outData",dvf_tasktemp_list,sparNodeID);//解析常量定义
			
			sparNodeID = IDUtils.genItemId();
			dvf_tasktemp_list.add(getTaskcfgtempVO(sparNodeID,"获取g_global变量","0","","",""));
			analyEvrDefJson(key_dev_json,"g_global_def","g_global",dvf_tasktemp_list,sparNodeID);//解析常量定义
			
			sparNodeID = IDUtils.genItemId();
			dvf_tasktemp_list.add(getTaskcfgtempVO(sparNodeID,"获取URL_cache变量","0","","",""));
			analyEvrDefJson(key_dev_json,"URLCache_def","URLCache",dvf_tasktemp_list,sparNodeID);//解析常量定义
			
			sparNodeID = IDUtils.genItemId();
			dvf_tasktemp_list.add(getTaskcfgtempVO(sparNodeID,"获取task_cache变量","0","","",""));
			analyEvrDefJson(key_dev_json,"TaskCache_def","TaskCache",dvf_tasktemp_list,sparNodeID);//解析常量定义
			
			sparNodeID = IDUtils.genItemId();
			dvf_tasktemp_list.add(getTaskcfgtempVO(sparNodeID,"获取app_cache变量","0","","",""));
			analyEvrDefJson(key_dev_json,"AppCache_def","AppCache",dvf_tasktemp_list,sparNodeID);//解析常量定义
			
			sparNodeID = IDUtils.genItemId();
			dvf_tasktemp_list.add(getTaskcfgtempVO(sparNodeID,"获取g_collect变量","0","","",""));
			analyEvrDefJson(key_dev_json,"g_collect_def","g_collect",dvf_tasktemp_list,sparNodeID);//解析常量定义
			
			sparNodeID = IDUtils.genItemId();
			dvf_tasktemp_list.add(getTaskcfgtempVO(sparNodeID,"获取CurrPageCache变量","0","","",""));
			analyEvrDefJson(key_dev_json,"CurrPageCache_def","CurrPageCache",dvf_tasktemp_list,sparNodeID);//解析常量定义
			
			sparNodeID = IDUtils.genItemId();
			dvf_tasktemp_list.add(getTaskcfgtempVO(sparNodeID,"获取g_current变量","0","","",""));
			analyEvrDefJson(key_dev_json,"g_current_def","g_current",dvf_tasktemp_list,sparNodeID);//解析常量定义
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return dvf_tasktemp_list;
	}
	
	/**
	 * 解析json中自定义的变量
	 * @param def_json_param
	 * @param sdef_key
	 */
	public void analyEvrDefJson(JSONObject def_json_param,String sdef_key,String sdef_value_key,List<TaskcfgtempVO> dvf_tasktemp_list,String sactNodeID){
		String skeyName = "",stypeName = "",sextypeName = "",sparNodeID = "",sdefParamValue = "";
		String sarr_value= "",iarr_value= "";
		JSONArray arr_value_json = null;
		JSONObject temp_json = null,temp_ex_json = null,value_json = null;
		JSONObject dev_json = def_json_param.getJSONObject(sdef_key);
		if(dev_json != null && !dev_json.isEmpty()){
			//需要取值
			Iterator it = dev_json.keys();
			while(it.hasNext()){
				skeyName = it.next().toString();
				temp_json = dev_json.getJSONObject(skeyName);
				if(temp_json != null){
					stypeName = temp_json.getString("col_type");
					//需要重构这段代码，太过冗余
					if(stypeName != null && !stypeName.equals("") && stypeName.equals("5")){
						TaskcfgtempVO taskcfgtempvo = new TaskcfgtempVO();
						sparNodeID = IDUtils.genItemId();
						taskcfgtempvo.setNodeid(sparNodeID);
						taskcfgtempvo.setNodename(skeyName);
						taskcfgtempvo.setParnodeid(sactNodeID);
						taskcfgtempvo.setParamtype("5");
						//取值为整数,sdef_key,sdef_value_key,skeyName,parNodeID,sdataType
					    value_json = def_json_param.getJSONObject(sdef_value_key);
						sdefParamValue = getDefParamValue(value_json, skeyName);
						taskcfgtempvo.setParamvalue(sdefParamValue);
						dvf_tasktemp_list.add(taskcfgtempvo);
						
					}else if(stypeName != null && !stypeName.equals("") && stypeName.equals("13")){
						//取字符串
						TaskcfgtempVO taskcfgtempvo = new TaskcfgtempVO();
						sparNodeID =IDUtils.genItemId();
						taskcfgtempvo.setNodeid(sparNodeID);
						taskcfgtempvo.setNodename(skeyName);
						taskcfgtempvo.setParnodeid(sactNodeID);
						taskcfgtempvo.setParamtype("13");
						//取值为整数,sdef_key,sdef_value_key,skeyName,parNodeID,sdataType
						value_json = def_json_param.getJSONObject(sdef_value_key);
						sdefParamValue = getDefParamValue(value_json, skeyName);
						taskcfgtempvo.setParamvalue(sdefParamValue);
						dvf_tasktemp_list.add(taskcfgtempvo);
						
					}else if(stypeName != null && !stypeName.equals("") && stypeName.equals("18")){
						//取字日期
						TaskcfgtempVO taskcfgtempvo = new TaskcfgtempVO();
						sparNodeID =IDUtils.genItemId();
						taskcfgtempvo.setNodeid(sparNodeID);
						taskcfgtempvo.setNodename(skeyName);
						taskcfgtempvo.setParnodeid(sactNodeID);
						taskcfgtempvo.setParamtype("18");
						value_json = def_json_param.getJSONObject(sdef_value_key);
						sdefParamValue = getDefParamValue(value_json, skeyName);
						taskcfgtempvo.setParamvalue(sdefParamValue);
						dvf_tasktemp_list.add(taskcfgtempvo);
						
					}else if(stypeName != null && !stypeName.equals("") && stypeName.equals("23")){
						//取字对象
						TaskcfgtempVO taskcfgtempvo = new TaskcfgtempVO();
						sparNodeID =IDUtils.genItemId();
						taskcfgtempvo.setNodeid(sparNodeID);
						taskcfgtempvo.setNodename(skeyName);
						taskcfgtempvo.setParnodeid(sactNodeID);
						taskcfgtempvo.setParamtype("23");
						dvf_tasktemp_list.add(taskcfgtempvo);
						
						temp_ex_json = temp_json.getJSONObject("col_type_ex");
						value_json = def_json_param.getJSONObject(sdef_value_key);
						value_json = value_json.getJSONObject(skeyName); 
						analyObjectTaskParams(temp_ex_json,value_json, dvf_tasktemp_list,sparNodeID);
						
						
					}else if(stypeName != null && !stypeName.equals("") && stypeName.equals("24")){
						//取字数组,取扩展数据类型
						sextypeName = temp_json.getString("col_extype");
						if(sextypeName != null && !sextypeName.equals("") && sextypeName.equals("5")){
							sparNodeID =IDUtils.genItemId();
							//取int类型数组值							
							if(def_json_param.containsKey(sdef_value_key)){
								value_json = def_json_param.getJSONObject(sdef_value_key);
								if(value_json.containsKey(skeyName)){
									arr_value_json = value_json.getJSONArray(skeyName);
									try{
										for (int i = 0; i < arr_value_json.size(); i++) {
											if(iarr_value !=null && !iarr_value.equals("")){
												iarr_value += "," + arr_value_json.getString(i) +"";
											}else{
												iarr_value = "" + arr_value_json.getString(i) +"";
											}
										}
									}catch(Exception e){e.printStackTrace();}
								}
							}
							dvf_tasktemp_list.add(getTaskcfgtempVO(sparNodeID,skeyName,sactNodeID,iarr_value,stypeName,sextypeName));
						}else if(sextypeName != null && !sextypeName.equals("") && sextypeName.equals("13")){
							sparNodeID =IDUtils.genItemId();
							//取string类型数组值
							value_json = def_json_param.getJSONObject(sdef_value_key);
							arr_value_json = value_json.getJSONArray(skeyName);
							try{
								for (int i = 0; i < arr_value_json.size(); i++) {
									if(sarr_value !=null && !sarr_value.equals("")){
										sarr_value += ",\"" + arr_value_json.getString(i) +"\"";
									}else{
										sarr_value = "\"" + arr_value_json.getString(i) +"\"";
									}
								}
							}catch(Exception e){e.printStackTrace();}
							dvf_tasktemp_list.add(getTaskcfgtempVO(sparNodeID,skeyName,sactNodeID,sarr_value,stypeName,sextypeName));
							
						}else if(sextypeName != null && !sextypeName.equals("") && sextypeName.equals("18")){
							sparNodeID =IDUtils.genItemId();
							//取date类型数组值
							value_json = def_json_param.getJSONObject(sdef_value_key);
							arr_value_json = value_json.getJSONArray(skeyName);
							try{
								for (int i = 0; i < arr_value_json.size(); i++) {
									if(sarr_value !=null && !sarr_value.equals("")){
										sarr_value += ",\"" + arr_value_json.getString(i) +"\"";
									}else{
										sarr_value = "\"" + arr_value_json.getString(i) +"\"";
									}
								}
							}catch(Exception e){e.printStackTrace();}
							dvf_tasktemp_list.add(getTaskcfgtempVO(sparNodeID,skeyName,sactNodeID,sarr_value,stypeName,sextypeName));
						}else if(sextypeName != null && !sextypeName.equals("") && sextypeName.equals("23")){
							sparNodeID =IDUtils.genItemId();
							dvf_tasktemp_list.add(getTaskcfgtempVO(sparNodeID,skeyName,sactNodeID,null,stypeName,sextypeName));
							
							//取object类型数组值
							temp_ex_json = temp_json.getJSONObject("col_type_ex");
							if(def_json_param.containsKey(sdef_value_key)){
								value_json = def_json_param.getJSONObject(sdef_value_key);
								value_json = value_json.getJSONObject(skeyName); 
							}
							analyObjectTaskParams(temp_ex_json,value_json, dvf_tasktemp_list,sparNodeID);
							
						}else if(sextypeName != null && !sextypeName.equals("") && sextypeName.equals("24")){
							//不存在嵌套
							System.out.println("不可以数组嵌套数组类型");
						}
					}
				}
			}
		}
		
	}
	
	//根据自定义的参数名取值
	public String getDefParamValue(JSONObject value_json,String skeyName){
		//"constants\":{\"con_str\":\"dafafads\"}
		//\"runTimeParam\":{\"run_obj\":{\"url_str\":\"http://localhost:8080/taskspos/SpTaskController/showTaskAddPage.do\",
		//\"conlum\":\"dd\",\"conlum1\":\"dd\"}}
		String sresult_value = "";
		if(value_json != null && value_json.size() >0){
			if(value_json.containsKey(skeyName)){
				sresult_value = value_json.getString(skeyName);
			}
		}
		System.out.println(sresult_value);
		return sresult_value;
	}
	
	public void analyObjectTaskParams(JSONObject temp_ex_json,JSONObject value_json,
			List<TaskcfgtempVO> dvf_tasktemp_list,String sparNodeID){
		//"runTimeParam_def\":{\"run_obj\":{\"col_type\":\"23\",\"col_type_ex\":
		//{\"sc_map\":{\"url_str\":{\"col_type\":\"13\",\"col_type_ex\":null},
		//\"conlum\":{\"col_type\":\"13\",\"col_type_ex\":null},\"conlum1\":
		//{\"col_type\":\"13\",\"col_type_ex\":null}}}}},
		JSONObject json_obj = null ,temp_json = null,temp_childex_json = null;
		JSONObject temp_value_json = null;
		String skey_name = "",sparChildNodeID = "",sdefParamValue = "";
		if(temp_ex_json != null && temp_ex_json.size() > 0){
			json_obj = temp_ex_json.getJSONObject("sc_map");
			if(json_obj != null && json_obj.size()>0){
				Iterator it = json_obj.keys();
				while(it.hasNext()){
					skey_name = it.next().toString();
					temp_json = json_obj.getJSONObject(skey_name);
					String type_name = temp_json.getString("col_type");
					if(type_name != null && type_name.equals("5")){
						TaskcfgtempVO taskcfgtempvo = new TaskcfgtempVO();
						sparChildNodeID =IDUtils.genItemId();
						taskcfgtempvo.setNodeid(sparChildNodeID);
						taskcfgtempvo.setNodename(skey_name);
						taskcfgtempvo.setParnodeid(sparNodeID);
						taskcfgtempvo.setParamtype("5");
						//取值
						sdefParamValue = getDefParamValue(value_json, skey_name);
						taskcfgtempvo.setParamvalue(sdefParamValue);
						dvf_tasktemp_list.add(taskcfgtempvo);
					
					}else if(type_name != null && type_name.equals("13")){
						TaskcfgtempVO taskcfgtempvo = new TaskcfgtempVO();
						sparChildNodeID =IDUtils.genItemId();
						taskcfgtempvo.setNodeid(sparChildNodeID);
						taskcfgtempvo.setNodename(skey_name);
						taskcfgtempvo.setParnodeid(sparNodeID);
						taskcfgtempvo.setParamtype("13");
						//取值
						sdefParamValue = getDefParamValue(value_json, skey_name);
						taskcfgtempvo.setParamvalue(sdefParamValue);
						dvf_tasktemp_list.add(taskcfgtempvo);
						
					}else if(type_name != null && type_name.equals("18")){
						TaskcfgtempVO taskcfgtempvo = new TaskcfgtempVO();
						sparChildNodeID =IDUtils.genItemId();
						taskcfgtempvo.setNodeid(sparChildNodeID);
						taskcfgtempvo.setNodename(skey_name);
						taskcfgtempvo.setParnodeid(sparNodeID);
						taskcfgtempvo.setParamtype("18");
						//取值
						sdefParamValue = getDefParamValue(value_json, skey_name);
						taskcfgtempvo.setParamvalue(sdefParamValue);
						dvf_tasktemp_list.add(taskcfgtempvo);
					}else if(type_name != null && type_name.equals("23")){
						TaskcfgtempVO taskcfgtempvo = new TaskcfgtempVO();
						sparChildNodeID =IDUtils.genItemId();
						taskcfgtempvo.setNodeid(sparChildNodeID);
						taskcfgtempvo.setNodename(skey_name);
						taskcfgtempvo.setParnodeid(sparNodeID);
						taskcfgtempvo.setParamtype("23");
						dvf_tasktemp_list.add(taskcfgtempvo);
						temp_childex_json = temp_json.getJSONObject("col_type_ex");
						temp_value_json = value_json.getJSONObject(skey_name);
						analyObjectTaskParams(temp_childex_json,temp_value_json, dvf_tasktemp_list,sparChildNodeID);
					}else if(type_name != null && type_name.equals("24")){
						
					}
					
					
				}
			}
			
			
		}
		
	}

  /**
   * 通过list集合中的对象生成json数据
   * @param sdevTaskParam
   * @return
   */
  public String analyRootTreeParam(String sdevTaskParam){
    String sparamJson = "";
    JSONObject json_def_param = new JSONObject();
    JSONArray jsonarray = JSONArray.fromObject(sdevTaskParam);
    analyEvrDefParam(jsonarray, json_def_param, "常量定义");
    analyEvrDefParam(jsonarray, json_def_param, "任务参数定义");
    analyEvrDefParam(jsonarray, json_def_param, "运行参数定义");
    analyEvrDefParam(jsonarray, json_def_param, "父任务参数定义");
    analyEvrDefParam(jsonarray, json_def_param, "抓取数据定义");
    analyEvrDefParam(jsonarray, json_def_param, "获取g_global变量");
    analyEvrDefParam(jsonarray, json_def_param, "获取URL_cache变量");
    analyEvrDefParam(jsonarray, json_def_param, "获取task_cache变量");
    analyEvrDefParam(jsonarray, json_def_param, "获取app_cache变量");
    analyEvrDefParam(jsonarray, json_def_param, "获取g_collect变量");
    analyEvrDefParam(jsonarray, json_def_param, "获取CurrPageCache变量");
    analyEvrDefParam(jsonarray, json_def_param, "获取g_current变量");

    System.out.println(json_def_param.toString());
    return json_def_param.toString();
  }

  public void analyEvrDefParam(JSONArray taskParamJsonarray, JSONObject json_def_param, String taskParamFlag) {
    String str_name = ""; String str_id = ""; String str_result = ""; String str_value = "";
    JSONObject jobj = null;
    if ((taskParamJsonarray != null) && (taskParamJsonarray.size() > 0)) {
      for (int i = 0; i < taskParamJsonarray.size(); i++) {
        jobj = (JSONObject)taskParamJsonarray.get(i);
        str_name = jobj.getString("nodename");
        if (str_name.equals(taskParamFlag)) {
          str_id = jobj.getString("nodeid");
          str_result = getParamJson(taskParamJsonarray, str_id, "");
          str_value = getParamValueJson(taskParamJsonarray, str_id, "");
          break;
        }
        jobj = null;
      }
    }
    if (taskParamFlag.equals("常量定义")) {
      if ((str_result != null) && (!str_result.trim().equals("")))
        json_def_param.put("constants_def", "{" + str_result + "}");
      else {
        json_def_param.put("constants_def", "null");
      }
      if ((str_value != null) && (!str_value.equals("")))
        json_def_param.put("constants", "{" + str_value + "}");
      else
        json_def_param.put("constants", "null");
    }else if (taskParamFlag.equals("任务参数定义")) {
      if ((str_result != null) && (!str_result.equals("")))
        json_def_param.put("paramsDef_def", "{" + str_result + "}");
      else {
        json_def_param.put("paramsDef_def", "null");
      }
      if ((str_value != null) && (!str_value.equals("")))
        json_def_param.put("paramsDef", "{" + str_value + "}");
      else
        json_def_param.put("paramsDef", "null");
    }else if (taskParamFlag.equals("运行参数定义")) {
      if ((str_result != null) && (!str_result.equals("")))
        json_def_param.put("runTimeParam_def", "{" + str_result + "}");
      else {
        json_def_param.put("runTimeParam_def", "null");
      }
      if ((str_value != null) && (!str_value.equals("")))
        json_def_param.put("runTimeParam", "{" + str_value + "}");
      else
        json_def_param.put("runTimeParam", "null");
    }else if (taskParamFlag.equals("父任务参数定义")) {
      if ((str_result != null) && (!str_result.equals("")))
        json_def_param.put("parentParam_def", "{" + str_result + "}");
      else {
        json_def_param.put("parentParam_def", "null");
      }
      if ((str_value != null) && (!str_value.equals("")))
        json_def_param.put("parentParam", "{" + str_value + "}");
      else
        json_def_param.put("parentParam", "null");
    }else if (taskParamFlag.equals("抓取数据定义")) {
      if ((str_result != null) && (!str_result.equals("")))
        json_def_param.put("outData_def", "{" + str_result + "}");
      else
        json_def_param.put("outData_def", "null");
    }else if (taskParamFlag.equals("入库数据路径")){
    	System.out.println("入库参数没有配置设置");
    }else if (taskParamFlag.equals("用户入库数据路径")){
    	System.out.println("用户入库数据路径设置");
    }else if(taskParamFlag.equals("获取g_global变量")){
       if ((str_result != null) && (!str_result.equals("")))
          json_def_param.put("g_global_def", "{" + str_result + "}");
       else
          json_def_param.put("g_global_def", "null");
    }else if (taskParamFlag.equals("获取URL_cache变量")) {
      if ((str_result != null) && (!str_result.equals("")))
        json_def_param.put("URLCache_def", "{" + str_result + "}");
      else
        json_def_param.put("URLCache_def", "null");
    }else if (taskParamFlag.equals("获取task_cache变量")) {
      if ((str_result != null) && (!str_result.equals("")))
        json_def_param.put("TaskCache_def", "{" + str_result + "}");
      else
        json_def_param.put("TaskCache_def", "null");
    }else if (taskParamFlag.equals("获取app_cache变量")) {
      if ((str_result != null) && (!str_result.equals("")))
        json_def_param.put("AppCache_def", "{" + str_result + "}");
      else
        json_def_param.put("AppCache_def", "null");
    }else if (taskParamFlag.equals("获取g_collect变量")) {
      if ((str_result != null) && (!str_result.equals("")))
        json_def_param.put("g_collect_def", "{" + str_result + "}");
      else
        json_def_param.put("g_collect_def", "null");
    }else if (taskParamFlag.equals("获取CurrPageCache变量")) {
      if ((str_result != null) && (!str_result.equals("")))
        json_def_param.put("CurrPageCache_def", "{" + str_result + "}");
      else
        json_def_param.put("CurrPageCache_def", "null");
    }else if (taskParamFlag.equals("获取g_current变量")){
      if ((str_result != null) && (!str_result.equals("")))
        json_def_param.put("g_current_def", "{" + str_result + "}");
      else
        json_def_param.put("g_current_def", "null");
    }
}

  public String getParamJson(JSONArray taskParamJsonarray, String nodeid, String sparamJson) {
    String str_id = ""; String str_parid = ""; String str_name = ""; String str_value = ""; String str_type = ""; String str_extype = "";

    JSONObject jobj = null;
    if ((taskParamJsonarray != null) && (taskParamJsonarray.size() > 0)) {
      for (int i = 0; i < taskParamJsonarray.size(); i++) {
        jobj = (JSONObject)taskParamJsonarray.get(i);
        str_parid = jobj.getString("parnodeid");
        if ((str_parid != null) && (!str_parid.trim().equals("")) && (str_parid.equals(nodeid))) {
          str_name = jobj.getString("nodename");
          str_type = jobj.getString("paramtype");
          if (str_type.equals("5")) {
            if (sparamJson.equals(""))
              sparamJson = sparamJson + "\"" + str_name + "\":{\"col_type\":\"5\",\"col_type_ex\":null}";
            else
              sparamJson = sparamJson + ",\"" + str_name + "\":{\"col_type\":\"5\",\"col_type_ex\":null}";
          }
          else if (str_type.equals("13")) {
            if (sparamJson.equals(""))
              sparamJson = sparamJson + "\"" + str_name + "\":{\"col_type\":\"13\",\"col_type_ex\":null}";
            else
              sparamJson = sparamJson + ",\"" + str_name + "\":{\"col_type\":\"13\",\"col_type_ex\":null}";
          }
          else if (str_type.equals("18")) {
            if (sparamJson.equals(""))
              sparamJson = sparamJson + "\"" + str_name + "\":{\"col_type\":\"18\",\"col_type_ex\":null}";
            else {
              sparamJson = sparamJson + ",\"" + str_name + "\":{\"col_type\":\"18\",\"col_type_ex\":null}";
            }
          }
          else if (str_type.equals("23")) {
            str_id = jobj.getString("nodeid");
            if (sparamJson.equals(""))
              sparamJson = sparamJson + "\"" + str_name + "\":{\"col_type\":\"23\",\"col_type_ex\":{\"sc_map\":{" + getParamJson(taskParamJsonarray, str_id, sparamJson) + "}}}";
            else
              sparamJson = sparamJson + ",\"" + str_name + "\":{\"col_type\":\"23\",\"col_type_ex\":{\"sc_map\":{" + getParamJson(taskParamJsonarray, str_id, sparamJson) + "}}}";
          }
          else if (str_type.equals("24")) {
            str_extype = jobj.getString("paramextype");
            str_id = jobj.getString("nodeid");
            if ((str_extype != null) && (str_extype.equals("5"))) {
              if (sparamJson.equals(""))
                sparamJson = sparamJson + "\"" + str_name + "\":{\"col_type\":\"24\",\"col_extype\":\"5\",\"col_type_ex\":null}";
              else
                sparamJson = sparamJson + ",\"" + str_name + "\":{\"col_type\":\"24\",\"col_extype\":\"5\",\"col_type_ex\":null}";
            }else if ((str_extype != null) && (str_extype.equals("13"))) {
              if (sparamJson.equals(""))
                sparamJson = sparamJson + "\"" + str_name + "\":{\"col_type\":\"24\",\"col_extype\":\"13\",\"col_type_ex\":null}";
              else
                sparamJson = sparamJson + ",\"" + str_name + "\":{\"col_type\":\"24\",\"col_extype\":\"13\",\"col_type_ex\":null}";
            }else if ((str_extype != null) && (str_extype.equals("18"))) {
              if (sparamJson.equals("")){
                sparamJson = sparamJson + "\"" + str_name + "\":{\"col_type\":\"24\",\"col_extype\":\"18\",\"col_type_ex\":null}";
              }else {
                sparamJson = sparamJson + ",\"" + str_name + "\":{\"col_type\":\"24\",\"col_extype\":\"18\",\"col_type_ex\":null}";
              }
            }else if (str_extype != null && str_extype.equals("23")){
            	if (sparamJson.equals(""))
                    sparamJson = sparamJson + "\"" + str_name + "\":{\"col_type\":\"24\",\"col_extype\":\"23\",\"col_type_ex\":{\"sc_map\":{" + getParamJson(taskParamJsonarray, str_id, sparamJson) + "}}}";
                  else {
                    sparamJson = sparamJson + ",\"" + str_name + "\":{\"col_type\":\"24\",\"col_extype\":\"23\",\"col_type_ex\":{\"sc_map\":{" + getParamJson(taskParamJsonarray, str_id, sparamJson) + "}}}";
                  }
            }else if (str_extype != null && str_extype.equals("24")){//不会是24，因为数组中不可以嵌套数组
            	if (sparamJson.equals(""))
            	    sparamJson = sparamJson + "\"" + str_name + "\":{\"col_type\":\"24\",\"col_type_ex\":{\"ele_col\":" + getParamJson(taskParamJsonarray, str_id, sparamJson) + "}}";
                else {
                    sparamJson = sparamJson + ",\"" + str_name + "\":{\"col_type\":\"24\",\"col_type_ex\":{\"ele_col\":" + getParamJson(taskParamJsonarray, str_id, sparamJson) + "}}";
                }
            }
          }
        }
      }
    }
    return sparamJson;
  }

  public String getParamValueJson(JSONArray taskParamJsonarray, String nodeid, String sparamValueJson) {
    String str_id = ""; String str_parid = ""; String str_name = ""; String str_value = ""; String str_type = ""; String str_extype = "";

    JSONObject jobj = null;
    if ((taskParamJsonarray != null) && (taskParamJsonarray.size() > 0)) {
      for (int i = 0; i < taskParamJsonarray.size(); i++) {
        jobj = (JSONObject)taskParamJsonarray.get(i);
        str_parid = jobj.getString("parnodeid");
        if ((str_parid != null) && (!str_parid.trim().equals("")) && (str_parid.equals(nodeid))) {
          str_name = jobj.getString("nodename");
          str_type = jobj.getString("paramtype");
          str_value = jobj.getString("paramvalue");
          if (str_type.equals("5")) {
            if (sparamValueJson.equals(""))
              sparamValueJson = sparamValueJson + "\"" + str_name + "\":" + str_value;
            else
              sparamValueJson = sparamValueJson + ",\"" + str_name + "\":" + str_value;
          }else if ((str_type.equals("13")) || (str_type.equals("18"))) {
            if (sparamValueJson.equals(""))
              sparamValueJson = sparamValueJson + "\"" + str_name + "\":\"" + str_value + "\"";
            else
              sparamValueJson = sparamValueJson + ",\"" + str_name + "\":\"" + str_value + "\"";
          }else if (str_type.equals("23")) {
            str_id = jobj.getString("nodeid");
            if (sparamValueJson.equals(""))
              sparamValueJson = sparamValueJson + "\"" + str_name + "\":{" + getParamValueJson(taskParamJsonarray, str_id, sparamValueJson) + "}";
            else
              sparamValueJson = sparamValueJson + ",\"" + str_name + "\":{" + getParamValueJson(taskParamJsonarray, str_id, sparamValueJson) + "}";
          }else if (str_type.equals("24")) {
            str_extype = jobj.getString("paramextype");
            str_id = jobj.getString("nodeid");
            if ((str_extype.equals("5")) || (str_extype.equals("13")) || (str_extype.equals("18"))) {
              if (sparamValueJson.equals(""))
                sparamValueJson = sparamValueJson + "\"" + str_name + "\":[" + str_value + "]";
              else
                sparamValueJson = sparamValueJson + ",\"" + str_name + "\":[" + str_value + "]";
            }
            else if (str_extype.equals("23")) {
              if (sparamValueJson.equals(""))
                sparamValueJson = sparamValueJson + "\"" + str_name + "\":{" + getParamValueJson(taskParamJsonarray, str_id, sparamValueJson) + "}";
              else {
                sparamValueJson = sparamValueJson + ",\"" + str_name + "\":{" + getParamValueJson(taskParamJsonarray, str_id, sparamValueJson) + "}";
              }
            }
          }
        }
      }
    }
    return sparamValueJson;
  }
  
  public TaskcfgtempVO getTaskcfgtempVO(String nodeid,String nodename,String parnodeid,
		  String paramvalue,String paramtype,String paramextype){
	    TaskcfgtempVO taskcfgtempvo = new TaskcfgtempVO();
		taskcfgtempvo.setNodeid(nodeid);
		taskcfgtempvo.setNodename(nodename);
		taskcfgtempvo.setParnodeid(parnodeid);
		taskcfgtempvo.setParamvalue(paramvalue);
		taskcfgtempvo.setParamtype(paramtype);
		taskcfgtempvo.setParamextype(paramextype);
	  return taskcfgtempvo;
  }

  public static void main(String[] args)
  {
    //String sdevTaskParam = "[{\"hitnum\":1,\"isextend\":\"N\",\"nodeid\":\"2017888\",\"nodename\":\"常量定义\",\"paramextype\":\"\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":\"0\",\"userid\":\"wxd\"},{\"hitnum\":191,\"isextend\":\"N\",\"nodeid\":\"2017889\",\"nodename\":\"任务参数定义\",\"paramextype\":\"\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":\"0\",\"userid\":\"wxd\"},{\"hitnum\":1,\"isextend\":\"N\",\"nodeid\":\"2017890\",\"nodename\":\"运行参数定义\",\"paramextype\":\"\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":\"0\",\"userid\":\"wxd\"},{\"hitnum\":1,\"isextend\":\"N\",\"nodeid\":\"2017891\",\"nodename\":\"父任务参数定义\",\"paramextype\":\"\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":\"0\",\"userid\":\"wxd\"},{\"hitnum\":1,\"isextend\":\"N\",\"nodeid\":\"2017892\",\"nodename\":\"抓取数据定义\",\"paramextype\":\"\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":\"0\",\"userid\":\"wxd\"},{\"hitnum\":1,\"isextend\":\"N\",\"nodeid\":\"2017893\",\"nodename\":\"入库数据路径\",\"paramextype\":\"\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":\"0\",\"userid\":\"wxd\"},{\"hitnum\":1,\"isextend\":\"N\",\"nodeid\":\"2017894\",\"nodename\":\"用户入库数据路径\",\"paramextype\":\"\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":\"0\",\"userid\":\"wxd\"},{\"hitnum\":1,\"isextend\":\"N\",\"nodeid\":\"2017895\",\"nodename\":\"获取g_global变量\",\"paramextype\":\"\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":\"0\",\"userid\":\"wxd\"},{\"hitnum\":1,\"isextend\":\"N\",\"nodeid\":\"2017896\",\"nodename\":\"获取URL_cache变量\",\"paramextype\":\"\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":\"0\",\"userid\":\"wxd\"},{\"hitnum\":1,\"isextend\":\"N\",\"nodeid\":\"2017897\",\"nodename\":\"获取task_cache变量\",\"paramextype\":\"\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":\"0\",\"userid\":\"wxd\"},{\"hitnum\":1,\"isextend\":\"N\",\"nodeid\":\"2017898\",\"nodename\":\"获取app_cache变量\",\"paramextype\":\"\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":\"0\",\"userid\":\"wxd\"},{\"hitnum\":1,\"isextend\":\"N\",\"nodeid\":\"2017899\",\"nodename\":\"获取g_collect变量\",\"paramextype\":\"\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":\"0\",\"userid\":\"wxd\"},{\"hitnum\":1,\"isextend\":\"N\",\"nodeid\":\"2017900\",\"nodename\":\"获取CurrPageCache变量\",\"paramextype\":\"\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":\"0\",\"userid\":\"wxd\"},{\"hitnum\":1,\"isextend\":\"N\",\"nodeid\":\"2017901\",\"nodename\":\"获取g_current变量\",\"paramextype\":\"\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":\"0\",\"userid\":\"wxd\"},{\"hitnum\":1,\"isextend\":\"Y\",\"nodeid\":\"1487671062311621\",\"nodename\":\"g_col_arr\",\"parnodeid\":\"2017899\",\"paramvalue\":\"\",\"paramtype\":\"24\",\"paramextype\":\"23\",\"userid\":\"wxd\"},{\"hitnum\":1,\"isextend\":\"Y\",\"nodeid\":\"1487671247463739\",\"nodename\":\"g_col_arrobj_str\",\"parnodeid\":\"1487671062311621\",\"paramvalue\":\"sss\",\"paramtype\":\"13\",\"paramextype\":\"\",\"userid\":\"wxd\"},{\"hitnum\":1,\"isextend\":\"Y\",\"nodeid\":\"1487671266799176\",\"nodename\":\"g_col_arrobjint\",\"parnodeid\":\"1487671062311621\",\"paramvalue\":\"dd\",\"paramtype\":\"5\",\"paramextype\":\"\",\"userid\":\"wxd\"},{\"hitnum\":1,\"isextend\":\"Y\",\"nodeid\":\"1487671300663516\",\"nodename\":\"g_col_arrobj_date\",\"parnodeid\":\"1487671062311621\",\"paramvalue\":\"2017-02-21 18:01:23\",\"paramtype\":\"18\",\"paramextype\":\"\",\"userid\":\"wxd\"}]";
    HandleDefParamUtil hdpu = new HandleDefParamUtil();
    //hdpu.analyRootTreeParam(sdevTaskParam);
   // String param_json = "{\"root\":{\"constants_def\":{\"con_str\":{\"col_type\":\"13\",\"col_type_ex\":null}},\"constants\":{\"con_str\":\"dafafads\"},\"paramsDef_def\":null,\"paramsDef\":null,\"runTimeParam_def\":{\"run_obj\":{\"col_type\":\"23\",\"col_type_ex\":{\"sc_map\":{\"url_str\":{\"col_type\":\"13\",\"col_type_ex\":null},\"conlum\":{\"col_type\":\"13\",\"col_type_ex\":null},\"conlum1\":{\"col_type\":\"13\",\"col_type_ex\":null}}}}},\"runTimeParam\":{\"run_obj\":{\"url_str\":\"http://localhost:8080/taskspos/SpTaskController/showTaskAddPage.do\",\"conlum\":\"dd\",\"conlum1\":\"dd\"}},\"parentParam_def\":null,\"parentParam\":null,\"outData_def\":null,\"g_global_def\":null,\"URLCache_def\":null,\"TaskCache_def\":null,\"AppCache_def\":null,\"g_collect_def\":null,\"CurrPageCache_def\":null,\"g_current_def\":null}}";
   String param_json2 = "{\"root\":{\"constants_def\":null,\"constants\":null,\"paramsDef_def\":{\"task_param_str\":{\"col_type\":\"24\",\"col_extype\":\"13\",\"col_type_ex\":null}},\"paramsDef\":{\"task_param_str\":[123,456,789]},\"runTimeParam_def\":null,\"runTimeParam\":null,\"parentParam_def\":null,\"parentParam\":null,\"outData_def\":null,\"g_global_def\":null,\"URLCache_def\":null,\"TaskCache_def\":null,\"AppCache_def\":null,\"g_collect_def\":{\"g_col_arr\":{\"col_type\":\"24\",\"col_extype\":\"23\",\"col_type_ex\":{\"sc_map\":{\"g_col_arrobj_str\":{\"col_type\":\"13\",\"col_type_ex\":null},\"g_col_arrobjint\":{\"col_type\":\"5\",\"col_type_ex\":null},\"g_col_arrobj_date\":{\"col_type\":\"18\",\"col_type_ex\":null}}}}},\"CurrPageCache_def\":null,\"g_current_def\":null}}";
    hdpu.analyJsonToTree(param_json2, "root");
    
    
  }
  
}