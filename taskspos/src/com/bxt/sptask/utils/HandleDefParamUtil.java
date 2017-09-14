package com.bxt.sptask.utils;

import java.io.PrintStream;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HandleDefParamUtil
{
  public String analyRootTreeParam(String sdevTaskParam)
  {
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
    }
    else if (taskParamFlag.equals("任务参数定义")) {
      if ((str_result != null) && (!str_result.equals("")))
        json_def_param.put("paramsDef_def", "{" + str_result + "}");
      else {
        json_def_param.put("paramsDef_def", "null");
      }
      if ((str_value != null) && (!str_value.equals("")))
        json_def_param.put("paramsDef", "{" + str_value + "}");
      else
        json_def_param.put("paramsDef", "null");
    }
    else if (taskParamFlag.equals("运行参数定义")) {
      if ((str_result != null) && (!str_result.equals("")))
        json_def_param.put("runTimeParam_def", "{" + str_result + "}");
      else {
        json_def_param.put("runTimeParam_def", "null");
      }
      if ((str_value != null) && (!str_value.equals("")))
        json_def_param.put("runTimeParam", "{" + str_value + "}");
      else
        json_def_param.put("runTimeParam", "null");
    }
    else if (taskParamFlag.equals("父任务参数定义")) {
      if ((str_result != null) && (!str_result.equals("")))
        json_def_param.put("parentParam_def", "{" + str_result + "}");
      else {
        json_def_param.put("parentParam_def", "null");
      }
      if ((str_value != null) && (!str_value.equals("")))
        json_def_param.put("parentParam", "{" + str_value + "}");
      else
        json_def_param.put("parentParam", "null");
    }
    else if (taskParamFlag.equals("抓取数据定义")) {
      if ((str_result != null) && (!str_result.equals("")))
        json_def_param.put("outData_def", "{" + str_result + "}");
      else
        json_def_param.put("outData_def", "null");
    }
    else if (!taskParamFlag.equals("入库数据路径"))
    {
      if (!taskParamFlag.equals("用户入库数据路径"))
      {
        if (taskParamFlag.equals("获取g_global变量")) {
          if ((str_result != null) && (!str_result.equals("")))
            json_def_param.put("g_global_def", "{" + str_result + "}");
          else
            json_def_param.put("g_global_def", "null");
        }
        else if (taskParamFlag.equals("获取URL_cache变量")) {
          if ((str_result != null) && (!str_result.equals("")))
            json_def_param.put("URLCache_def", "{" + str_result + "}");
          else
            json_def_param.put("URLCache_def", "null");
        }
        else if (taskParamFlag.equals("获取task_cache变量")) {
          if ((str_result != null) && (!str_result.equals("")))
            json_def_param.put("TaskCache_def", "{" + str_result + "}");
          else
            json_def_param.put("TaskCache_def", "null");
        }
        else if (taskParamFlag.equals("获取app_cache变量")) {
          if ((str_result != null) && (!str_result.equals("")))
            json_def_param.put("AppCache_def", "{" + str_result + "}");
          else
            json_def_param.put("AppCache_def", "null");
        }
        else if (taskParamFlag.equals("获取g_collect变量")) {
          if ((str_result != null) && (!str_result.equals("")))
            json_def_param.put("g_collect_def", "{" + str_result + "}");
          else
            json_def_param.put("g_collect_def", "null");
        }
        else if (taskParamFlag.equals("获取CurrPageCache变量")) {
          if ((str_result != null) && (!str_result.equals("")))
            json_def_param.put("CurrPageCache_def", "{" + str_result + "}");
          else
            json_def_param.put("CurrPageCache_def", "null");
        }
        else if (taskParamFlag.equals("获取g_current变量"))
          if ((str_result != null) && (!str_result.equals("")))
            json_def_param.put("g_current_def", "{" + str_result + "}");
          else
            json_def_param.put("g_current_def", "null");
      }
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
                sparamJson = sparamJson + "\"" + str_name + "\":{\"col_type\":\"24\",\"col_type_ex\":{\"ele_col\":{\"col_type\":\"5\",\"col_type_ex\":null}}}";
              else
                sparamJson = sparamJson + ",\"" + str_name + "\":{\"col_type\":\"24\",\"col_type_ex\":{\"ele_col\":{\"col_type\":\"5\",\"col_type_ex\":null}}}";
            }
            else if ((str_extype != null) && (str_extype.equals("13"))) {
              if (sparamJson.equals(""))
                sparamJson = sparamJson + "\"" + str_name + "\":{\"col_type\":\"24\",\"col_type_ex\":{\"ele_col\":{\"col_type\":\"13\",\"col_type_ex\":null}}}";
              else
                sparamJson = sparamJson + ",\"" + str_name + "\":{\"col_type\":\"24\",\"col_type_ex\":{\"ele_col\":{\"col_type\":\"13\",\"col_type_ex\":null}}}";
            }
            else if ((str_extype != null) && (str_extype.equals("18"))) {
              if (sparamJson.equals(""))
                sparamJson = sparamJson + "\"" + str_name + "\":{\"col_type\":\"24\",\"col_type_ex\":{\"ele_col\":{\"col_type\":\"18\",\"col_type_ex\":null}}}";
              else {
                sparamJson = sparamJson + ",\"" + str_name + "\":{\"col_type\":\"24\",\"col_type_ex\":{\"ele_col\":{\"col_type\":\"18\",\"col_type_ex\":null}}}";
              }
            }
            else if (sparamJson.equals(""))
              sparamJson = sparamJson + "\"" + str_name + "\":{\"col_type\":\"24\",\"col_type_ex\":{\"ele_col\":" + getParamJson(taskParamJsonarray, str_id, sparamJson) + "}}";
            else {
              sparamJson = sparamJson + ",\"" + str_name + "\":{\"col_type\":\"24\",\"col_type_ex\":{\"ele_col\":" + getParamJson(taskParamJsonarray, str_id, sparamJson) + "}}";
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
          }
          else if ((str_type.equals("13")) || (str_type.equals("18"))) {
            if (sparamValueJson.equals(""))
              sparamValueJson = sparamValueJson + "\"" + str_name + "\":\"" + str_value + "\"";
            else
              sparamValueJson = sparamValueJson + ",\"" + str_name + "\":\"" + str_value + "\"";
          }
          else if (str_type.equals("23")) {
            str_id = jobj.getString("nodeid");
            if (sparamValueJson.equals(""))
              sparamValueJson = sparamValueJson + "\"" + str_name + "\":{" + getParamValueJson(taskParamJsonarray, str_id, sparamValueJson) + "}";
            else
              sparamValueJson = sparamValueJson + ",\"" + str_name + "\":{" + getParamValueJson(taskParamJsonarray, str_id, sparamValueJson) + "}";
          }
          else if (str_type.equals("24")) {
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

  public static void main(String[] args)
  {
    String sdevTaskParam = "[{\"nodeid\":2017888,\"nodename\":\"常量定义\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":0,\"userid\":\"wxd\"},{\"nodeid\":2017889,\"nodename\":\"任务参数定义\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":0,\"userid\":\"wxd\"},{\"nodeid\":2017890,\"nodename\":\"运行参数定义\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":0,\"userid\":\"wxd\"},{\"nodeid\":2017891,\"nodename\":\"父任务参数定义\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":0,\"userid\":\"wxd\"},{\"nodeid\":2017892,\"nodename\":\"抓取数据定义\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":0,\"userid\":\"wxd\"},{\"nodeid\":2017893,\"nodename\":\"入库数据路径\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":0,\"userid\":\"wxd\"},{\"nodeid\":2017894,\"nodename\":\"用户入库数据路径\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":0,\"userid\":\"wxd\"},{\"nodeid\":2017895,\"nodename\":\"获取g_global变量\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":0,\"userid\":\"wxd\"},{\"nodeid\":2017896,\"nodename\":\"获取URL_cache变量\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":0,\"userid\":\"wxd\"},{\"nodeid\":2017897,\"nodename\":\"获取task_cache变量\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":0,\"userid\":\"wxd\"},{\"nodeid\":2017898,\"nodename\":\"获取app_cache变量\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":0,\"userid\":\"wxd\"},{\"nodeid\":2017899,\"nodename\":\"获取g_collect变量\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":0,\"userid\":\"wxd\"},{\"nodeid\":2017900,\"nodename\":\"获取CurrPageCache变量\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":0,\"userid\":\"wxd\"},{\"nodeid\":2017901,\"nodename\":\"获取g_current变量\",\"paramtype\":\"\",\"paramvalue\":\"\",\"parnodeid\":0,\"userid\":\"wxd\"},{\"hitnum\":1,\"isextend\":\"Y\",\"nodeid\":\"1485228197533272\",\"nodename\":\"con_str\",\"parnodeid\":\"2017888\",\"paramvalue\":\"http://localhost:8080/taskspos/SpTaskController/showTaskAddPage.do\",\"paramtype\":\"13\",\"userid\":\"wxd\"},{\"hitnum\":1,\"isextend\":\"Y\",\"nodeid\":\"1485244442971584\",\"nodename\":\"run_url\",\"parnodeid\":\"2017890\",\"paramvalue\":\"http://localhost:8080/taskspos/SpTaskController/showTaskAddPage.do\",\"paramtype\":\"13\",\"userid\":\"wxd\"},{\"hitnum\":1,\"isextend\":\"Y\",\"nodeid\":\"1485245387145675\",\"nodename\":\"con_obj1\",\"parnodeid\":\"2017888\",\"paramvalue\":\"\",\"paramtype\":\"23\",\"userid\":\"wxd\"},{\"hitnum\":1,\"isextend\":\"Y\",\"nodeid\":\"1485245398389690\",\"nodename\":\"con_obj2\",\"parnodeid\":\"2017888\",\"paramvalue\":\"\",\"paramtype\":\"23\",\"userid\":\"wxd\"},{\"hitnum\":1,\"isextend\":\"Y\",\"nodeid\":\"1485245410860923\",\"nodename\":\"obj1_str\",\"parnodeid\":\"1485245387145675\",\"paramvalue\":\"sssss\",\"paramtype\":\"13\",\"userid\":\"wxd\"},{\"hitnum\":1,\"isextend\":\"Y\",\"nodeid\":\"1485245430645838\",\"nodename\":\"obj2_str\",\"parnodeid\":\"1485245398389690\",\"paramvalue\":\"sljdlfjaslf\",\"paramtype\":\"13\",\"userid\":\"wxd\"}]";

    HandleDefParamUtil hdpu = new HandleDefParamUtil();
    hdpu.analyRootTreeParam(sdevTaskParam);
  }
}