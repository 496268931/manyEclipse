package com.bxt.sptask.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bxt.sptask.dao.SpTaskDao;
import com.bxt.sptask.dao.SpideraccountDao;
import com.bxt.sptask.dao.TaskAgentDao;
import com.bxt.sptask.dao.TaskInitDao;
import com.bxt.sptask.service.TaskAgentService;
import com.bxt.sptask.spideraccount.vo.SpideraccountVO;
import com.bxt.sptask.sptmpconfig.vo.SchSpiderconfigVO;
import com.bxt.sptask.staichm.StaticMapParams;
import com.bxt.sptask.taskhandle.vo.TaskVo;
import com.bxt.sptask.utils.HttpRequestSender;
import com.bxt.sptask.utils.PropertyUtil;
import com.fasterxml.jackson.core.JsonParser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.TaskCommonHandle;
import util.TaskJsonAnaly;
import util.TaskUtil;
import util.vo.EntityParams;

@Service
@Transactional(rollbackFor = Throwable.class)
public class TaskAgentServiceImpl implements TaskAgentService {
	
	
	@Autowired
	TaskAgentDao taskAgentDao;
	
	@Autowired
	SpideraccountDao spideraccountDao;
	
	@Autowired
	TaskInitDao taskDao;
	
	@Autowired
	private SpTaskDao spTaskDao;
	
	public String getPhpAgentTask(HashMap<String,String> hmobj){
		String sHost = hmobj.get("host");
		String sTasktype = hmobj.get("tasktype");
		String sPecifiedtype = hmobj.get("specifiedtype");
		String sPecifiedMac = hmobj.get("mac");
		String stoken = hmobj.get("stoken");
		String staskKey = "",staskstr = "",staskTypeStr = "",staskjson = "";
		String taskid = null;
		
		if(stoken != null && !stoken.equals("")){
			stoken = stoken.replace(" ", "+");
		}
		EntityParams etp = TaskJsonAnaly.getTaskAndType(sTasktype);
		if(etp != null){
			staskstr = etp.getStask();
			staskTypeStr = etp.getStaskType();
		}
		if(staskTypeStr!=null &&  staskstr != null && !staskTypeStr.equals("") && !staskstr.equals("")){
		    staskKey = TaskUtil.getTaskKey(staskTypeStr,sPecifiedMac,sPecifiedtype,staskstr);
			List<String> ltaskID = null;
			synchronized(StaticMapParams.hmTaskSchID){
				try{
					ltaskID = StaticMapParams.hmTaskSchID.get(staskKey);
					if(ltaskID != null && ltaskID.size()>0){
						taskid = ltaskID.get(0);
						if(taskid != null){
							ltaskID.remove(0);
						}
					}else{
						staskjson = "";
						//把当前KEY值写入List<String>
						if(!StaticMapParams.hmTempTaskWaitKey.containsKey(staskKey)){
							synchronized(StaticMapParams.hmTaskWaitKey){
								try{
									StaticMapParams.hmTempTaskWaitKey.put(staskKey, "1"); //启动线程去读取任务到hashmap中。
									StaticMapParams.hmTaskWaitKey.put(staskKey, "1"); //启动线程去读取任务到hashmap中。
								}catch(Exception e){
									e.printStackTrace();
								}
							}
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		if(taskid != null){
			//地址写配置文件写配置文件
			String requstUrl = PropertyUtil.getProperty("url");
			if(requstUrl != null){
				requstUrl = requstUrl + "type=javaget&task="+staskstr+"&host="+sHost+"&taskid=" + taskid + "&token="+stoken;
			}
			//System.out.println("================url:" + requstUrl);
			String charset = PropertyUtil.getProperty("charset");
			String stimeout = PropertyUtil.getProperty("timeout");
			Long itimeout = null;
			if(stimeout!=null){
				try{
					itimeout = Long.parseLong(stimeout);
				}catch(Exception e){
					itimeout = new Long(14000);
				}
			}
			//获得任务ID后去取任务
			//long lreadStart = System.currentTimeMillis();
			staskjson = HttpRequestSender.excuteTaskHttpGetReq(charset, requstUrl, itimeout);
			if(staskjson != null){
				taskAgentDao.upateTaskStatus(taskid);
			}
			//long lreadEnd = System.currentTimeMillis();
			//System.out.println("读取phpurl用时:" + (lreadEnd-lreadStart));
			//System.out.println(staskjson);
		}
		
		return staskjson;
		
	}
	
	
	
	

	@Override
	public String getAgentTask(HashMap<String,String> hmobj) {
		//begin定义一个返回的JSONObject
		JSONObject result_jsonobj = new JSONObject();
		JSONObject task_json = new JSONObject();
		JSONObject taskobj = null;
		
		result_jsonobj.put("result",true);
		result_jsonobj.put("msg", "");
		//end
		
		List<TaskVo> listTask = null;
		//处理hmobj中的对象
		String sHost = hmobj.get("host");
		String sTasktype = hmobj.get("tasktype");
		String sPecifiedtype = hmobj.get("specifiedtype");
		String sPecifiedMac = hmobj.get("mac");
		
		String tasktype = "",task = "";
	

		try{
			//准备爬虫配置模板
			List<SchSpiderconfigVO> listTmplate = taskDao.getTaskTemplateConfig();
			if(listTmplate != null){
				for(SchSpiderconfigVO spvo:listTmplate){
					StaticMapParams.hmTemplate.put(spvo.getId(), JSONObject.fromObject(spvo).toString());
				}
			}
			
			JSONArray resulJosn = JSONArray.fromObject(sTasktype);
			for(Object obj:resulJosn){
				JSONObject jobj = (JSONObject) obj;
				tasktype = jobj.getString("tasktype");
				task = jobj.getString("task");
				hmobj.put("tasktype", tasktype);
				hmobj.put("task", task);
				listTask = taskAgentDao.getAgentTask(hmobj);
				if(listTask != null && listTask.size() > 0){
					try{
						for(TaskVo taskVo :listTask){
							if(taskVo != null){
								//TaskVo tkvo = TaskCommonHandle.getInitCommonTask(taskVo,sHost);
								taskobj =  JSONObject.fromObject(taskVo);//需要更新数据库的json对象
								JSONObject taskparams_json = JSONObject.fromObject(taskobj.getString("taskparams"));//任务json
								JSONObject curRootParams_json = (JSONObject)taskparams_json.get("root");//父任务json
								//初始化运行参数
								curRootParams_json = TaskCommonHandle.initRunTimeParams(taskVo, curRootParams_json, sHost);
								//准备爬虫配置模板
							   if(curRootParams_json.containsKey("taskPro") && curRootParams_json.get("taskPro") != null){
								   JSONObject tmpid_json = curRootParams_json.getJSONObject("taskPro");
								   if(tmpid_json.containsKey("template") && tmpid_json.get("template") != null){
									   String tmpid = tmpid_json.getString("template");
									   SchSpiderconfigVO task_Tmplate = taskDao.getTaskTemplateConfig(tmpid);
									   JSONObject tmplate_json = JSONObject.fromObject(task_Tmplate);
									   //账号处理,并加载
									   JSONArray account_json= handleTaskAccounts(curRootParams_json,result_jsonobj);
									   HashMap<String,String> gaccount_hm = new HashMap<String,String>();
									   task_json = this.loadAccountInfo(account_json, curRootParams_json, task_json, gaccount_hm);
									   JSONObject gaccount_json = new JSONObject();
									   if(gaccount_hm != null && gaccount_hm.size() > 0){
										   gaccount_json.put("username", gaccount_hm.get("username"));
										   gaccount_json.put("password", gaccount_hm.get("password"));
										   result_jsonobj.put("gaccount",gaccount_json);  //设置返回爬虫全局账户
									   }
									   //连接模板
									   task_json = TaskCommonHandle.unionTaskTemplate(taskVo, curRootParams_json, tmplate_json,task_json);
									   //保存URL
									   task_json = TaskCommonHandle.getSpToCacheUrl(curRootParams_json,task_json);
									   //读取配置文件
									   task_json = TaskCommonHandle.getDefTaskParams(taskVo, curRootParams_json,task_json,sHost);
									   
									   /*
									    * 保存任务状态信息，并组装任务返回给爬虫
									    */
									   
									  //String task_seu = StringEscapeUtils.unescapeJavaScript(task_json.toString());
									   result_jsonobj.put("task", task_json.toString());
									   
									   spTaskDao.updateTaskBySpRead(taskVo);
								   }else{
									   result_jsonobj.put("result",false);
									   result_jsonobj.put("msg", "任务模板配置错误！");
								   }
							   }else{
								   result_jsonobj.put("result",false);
								   result_jsonobj.put("msg", "任务配置错误");
							   }
							   
							}
						}
					}catch(Exception e){
						result_jsonobj.put("result",false);
						result_jsonobj.put("msg", "读取任务报错，请检查配置的任务");
						e.printStackTrace();
					}
				}else{
					result_jsonobj.put("result",false);
					result_jsonobj.put("msg", "任务已经取完，请配置新任务！");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		//return StringEscapeUtils.escapeJava(result_jsonobj.toString());
		return result_jsonobj.toString();

	}
	
	public JSONArray handleTaskAccounts(JSONObject curRootParams_json,JSONObject result_jsonobj){
		//测试账号信息
		JSONArray spact_jsonarr = new JSONArray();
		JSONObject loginAct = null; 
		Integer actid = null;
		if(curRootParams_json.containsKey("loginAccounts")  && curRootParams_json.get("loginAccounts") != null){
			loginAct = curRootParams_json.getJSONObject("loginAccounts");
			if(loginAct.containsKey("accounts")  && loginAct.get("accounts") != null){
				if(loginAct.get("accounts") instanceof JSONArray){
					JSONArray actArr_json = loginAct.getJSONArray("accounts");
					if(actArr_json != null && actArr_json.size() > 0){
						for(int i = 0; i<actArr_json.size();i++){
							actid = actArr_json.getInt(i);
							//读取数据库
							SpideraccountVO spact = spideraccountDao.searchSpActByID(actid);
							if(spact != null ){
								JSONObject spact_json = new JSONObject();
								spact_json = JSONObject.fromObject(spact);
								spact_jsonarr.add(spact_json);
							}else{
								result_jsonobj.put("result",false);
								result_jsonobj.put("msg", "查询账号失败！");
							}
						}
					}
				}
			}
		}
		return spact_jsonarr;
	}
	
	public JSONObject loadAccountInfo(JSONArray spact_jsonarr,JSONObject curRootParams_json,JSONObject task_json,HashMap<String,String> gaccount_hm){
		JSONObject loginAct = null; 
		if(spact_jsonarr != null){
			if(spact_jsonarr.size()>0){
				task_json.put("accounts", spact_jsonarr);
			}else{
				System.out.println("加载的账号为空");
			}
		}else{
			Integer sourceid = null;
			if(curRootParams_json.containsKey("loginAccounts")  && curRootParams_json.get("loginAccounts") != null){
				loginAct = curRootParams_json.getJSONObject("loginAccounts");
				if(loginAct.containsKey("globalaccount")  && loginAct.get("globalaccount") != null){
					task_json.put("globalaccount", 1);
					 //获取帐号并返回
					 //$accountres = getNextSpiderAccount($currentTaskParam["loginAccounts"]["globalaccounts"]);
					if(loginAct.containsKey("globalaccounts")  && loginAct.get("globalaccounts") != null){
						try{
							sourceid = Integer.parseInt(loginAct.getString("globalaccounts"));
							SpideraccountVO spactvo = spideraccountDao.searchSpActBySourceid(sourceid); 
							if(spactvo != null){
								Integer MAX_INT_UNSIGNED = 2147483646; //mysql中 int(10) 无符号的最大值 4294967295
								Integer inuse = spactvo.getInuse();
								Integer icon = 0;
								if(inuse >= MAX_INT_UNSIGNED){
								    icon = spideraccountDao.updateSpActBySourceid(sourceid);
									if(icon == null || icon <= 0){
										//result_jsonobj.put("result",false);
										//result_jsonobj.put("msg", "inuse 清0失败");
									}
								}
								icon = spideraccountDao.updateSpActByID(spactvo);
								if(icon != null & icon > 0){
									gaccount_hm.put("username", spactvo.getUsername());
									gaccount_hm.put("password", spactvo.getPassword());
								}else{
									//result_jsonobj.put("result",false);
									//result_jsonobj.put("msg", "inuse 累加失败");
								}
							}else{
								//result_jsonobj.put("result",false);
								//result_jsonobj.put("msg", "未找到可用帐号！");
							}
						}catch(Exception e){
							e.printStackTrace();
						}
						//使用全局帐号的时候的帐号切换策略
						if(loginAct.containsKey("isswitch")  && loginAct.get("isswitch") != null){
							task_json.put("switchpage", loginAct.getString("switchpage"));
							task_json.put("switchtime", loginAct.getString("switchtime"));
						}
					}
					
				}
				
			}
		}
		
		return task_json;
	}
	
	
	public static void main(String[] args) {
//		JSONObject curRootParams_json = JSONObject.fromObject("{\"taskPro\":{\"tasklevel\":\"2\",\"local\":0,\"remote\":1,\"iscommit\":\"1\",\"source\":11,\"content_type\":0,\"duration\":\"3600\",\"conflictdelay\":\"\",\"iscalctrend\":\"0\",\"dataSave\":\"1\",\"filPageTag\":\"1\",\"addUser\":\"0\",\"genCreatedAt\":\"1\",\"isGenChildTask\":\"1\",\"dictionaryPlan\":\"[[]]\",\"cronstart\":\"\",\"specifiedType\":\"\",\"specifiedMac\":\"\",\"specifiedTypeForChild\":true,\"specifiedMacForChild\":true,\"cronend\":\"\",\"crontime\":\"\",\"crontimedes\":\"\",\"remarks\":\"站长BBS\",\"userPathId\":\"\",\"template\":\"118\"},\"templMap\":{\"zhanzhang-bbs-gentie-text\":[1]},\"stepNumURLPatterns\":{\"1\":[\"bbs.chinaz.com/\"]},\"paramsDef_def\":null,\"parentParam_def\":{\"type\":\"phyfield_add\",\"id\":1,\"label\":\"parentParam\",\"col_type\":\"23\",\"col_type_ex\":{\"sc_map\":{\"run_url\":{\"col_type\":\"13\",\"col_type_ex\":null},\"source_host\":{\"col_type\":\"13\",\"col_type_ex\":null},\"column\":{\"col_type\":\"13\",\"col_type_ex\":null},\"column1\":{\"col_type\":\"13\",\"col_type_ex\":null},\"floorNum\":{\"col_type\":\"5\",\"col_type_ex\":null},\"post_title\":{\"col_type\":\"13\",\"col_type_ex\":null}}},\"name\":\"parentParam\",\"open\":true},\"runTimeParam_def\":{\"type\":\"phyfield_add\",\"id\":1,\"label\":\"runTimeParam\",\"col_type\":\"23\",\"col_type_ex\":{\"sc_map\":{\"original_url\":{\"col_type\":\"13\",\"col_type_ex\":null},\"floorNum\":{\"col_type\":\"5\",\"col_type_ex\":null}}},\"name\":\"runTimeParam\",\"open\":true},\"constants_def\":null,\"outData_def\":null,\"g_global_def\":null,\"URLCache_def\":null,\"TaskCache_def\":null,\"AppCache_def\":null,\"g_collect_def\":{\"type\":\"phyfield_add\",\"id\":1,\"label\":\"g_collect\",\"col_type\":\"24\",\"col_type_ex\":{\"ele_col\":{\"col_type\":23,\"col_type_ex\":{\"sc_map\":{\"orinigal_url\":{\"col_type\":\"13\",\"col_type_ex\":null},\"source_host\":{\"col_type\":\"13\",\"col_type_ex\":null},\"column\":{\"col_type\":\"13\",\"col_type_ex\":null},\"column1\":{\"col_type\":\"13\",\"col_type_ex\":null},\"floor\":{\"col_type\":\"5\",\"col_type_ex\":null},\"post_title\":{\"col_type\":\"13\",\"col_type_ex\":null}}}}},\"name\":\"g_collect\",\"open\":true},\"CurrPageCache_def\":null,\"g_current_def\":null,\"paramsDef\":null,\"parentParam\":{\"source_host\":\"BBS\",\"column\":\"英特尔\",\"column1\":\"站长论坛\",\"post_title\":\"什么是河北智能车牌识别系统\"},\"runTimeParam\":{\"original_url\":\"http://bbs.chinaz.com/thread-8043981-1-1.html\"},\"constants\":null,\"loginAccounts\":{\"globalaccount\":\"0\",\"logoutfirst\":\"0\",\"isswitch\":\"0\",\"switchpage\":\"5\",\"switchtime\":\"0\",\"switchstrategy\":1,\"accounts\":[],\"globalaccounts\":\"\"},\"pathStructMap\":{\"keys\":[\"runTimeOriginalUrl\",\"collectDatasOrigUrl\",\"sourceHostParentParam\",\"collectDataSourceHost\",\"columnParentParam\",\"collectDataColumn\",\"column1ParentParam\",\"collectDataColumn1\",\"post_titleParentParam\",\"collectDatapost_title\"],\"runTimeOriginalUrl\":{\"col_name\":\"original_url\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":3},\"collectDatasOrigUrl\":{\"col_name\":\"g_collect\",\"col_type\":\"24\",\"col_name_ex\":{\"type\":1,\"data\":{\"arr_data\":{\"arr_idx\":-1,\"min_comb_eles\":null,\"max_comb_eles\":null}},\"name_ex\":{\"type\":2,\"data\":{\"chld_col_name\":\"original_url\"},\"name_ex\":null}},\"paramSource\":10},\"sourceHostParentParam\":{\"col_name\":\"source_host\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"collectDataSourceHost\":{\"col_name\":\"g_collect\",\"col_type\":\"24\",\"col_name_ex\":{\"type\":1,\"data\":{\"arr_data\":{\"arr_idx\":-1,\"min_comb_eles\":null,\"max_comb_eles\":null}},\"name_ex\":{\"type\":2,\"data\":{\"chld_col_name\":\"source_host\"},\"name_ex\":null}},\"paramSource\":10},\"columnParentParam\":{\"col_name\":\"column\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"collectDataColumn\":{\"col_name\":\"g_collect\",\"col_type\":\"24\",\"col_name_ex\":{\"type\":1,\"data\":{\"arr_data\":{\"arr_idx\":-1,\"min_comb_eles\":null,\"max_comb_eles\":null}},\"name_ex\":{\"type\":2,\"data\":{\"chld_col_name\":\"column\"},\"name_ex\":null}},\"paramSource\":10},\"column1ParentParam\":{\"col_name\":\"column1\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"collectDataColumn1\":{\"col_name\":\"g_collect\",\"col_type\":\"24\",\"col_name_ex\":{\"type\":1,\"data\":{\"arr_data\":{\"arr_idx\":-1,\"min_comb_eles\":null,\"max_comb_eles\":null}},\"name_ex\":{\"type\":2,\"data\":{\"chld_col_name\":\"column1\"},\"name_ex\":null}},\"paramSource\":10},\"post_titleParentParam\":{\"col_name\":\"post_title\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"collectDatapost_title\":{\"col_name\":\"g_collect\",\"col_type\":\"24\",\"col_name_ex\":{\"type\":1,\"data\":{\"arr_data\":{\"arr_idx\":-1,\"min_comb_eles\":null,\"max_comb_eles\":null}},\"name_ex\":{\"type\":2,\"data\":{\"chld_col_name\":\"post_title\"},\"name_ex\":null}},\"paramSource\":10},\"path281\":{\"col_name\":\"original_url\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":3},\"path166\":{\"col_name\":\"original_url\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":3},\"path485\":{\"col_name\":\"original_url\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":3},\"path323\":{\"col_name\":\"source_host\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path840\":{\"col_name\":\"source_host\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path977\":{\"col_name\":\"column\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path216\":{\"col_name\":\"column\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path992\":{\"col_name\":\"column1\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path991\":{\"col_name\":\"column1\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path496\":{\"col_name\":\"post_title\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":5},\"path737\":{\"col_name\":\"post_title\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4}},\"filters\":[],\"taskUrls\":{\"type\":\"consts\",\"urlValues\":\"taskurl$:\\\"$&lturl \\\"%s\\\">\\\"{url:Enum(http://bbs.chinaz.com/thread-8043981-1-1.html)}\"},\"outData\":[],\"taskGenConf\":[],\"parentTaskID\":\"1\"}");
//		TaskAgentServiceImpl tsi = new TaskAgentServiceImpl();
//		tsi.handleTaskAccounts(curRootParams_json);
	}

}
