package com.bxt.sptask.taskhandle.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bxt.sptask.taskhandle.dao.TaskAgentDao;
import com.bxt.sptask.taskhandle.service.TaskAgentService;
import com.bxt.sptask.taskhandle.vo.TaskVo;
import com.bxt.sptask.taskinit.statichm.StaticMapParams;
import com.bxt.sptask.utils.HttpRequestSender;
import com.bxt.sptask.utils.PropertyUtil;
import com.bxt.sptask.utils.TaskCommonHandle;
import com.bxt.sptask.utils.TaskJsonAnaly;
import com.bxt.sptask.utils.TaskUtil;
import com.bxt.sptask.utils.vo.EntityParams;

@Service
@Transactional(rollbackFor = Throwable.class)
public class TaskAgentServiceImpl implements TaskAgentService {
	
	
	@Autowired
	TaskAgentDao taskAgentDao;
	
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
		List<TaskVo> listTask = null;
		//处理hmobj中的对象
		String sHost = hmobj.get("host");
		String sTasktype = hmobj.get("tasktype");
		String sPecifiedtype = hmobj.get("specifiedtype");
		String sPecifiedMac = hmobj.get("mac");
		
		String tasktype = "",task = "";
	
		//$taskobj = NULL;
		//$task = TASK_KEYWORD;
		try{
			JSONArray resulJosn = JSONArray.fromObject(sTasktype);
			for(Object obj:resulJosn){
				JSONObject jobj = (JSONObject) obj;
				tasktype = jobj.getString("tasktype");
				task = jobj.getString("task");
				hmobj.put("tasktype", tasktype);
				hmobj.put("task", task);
				listTask = taskAgentDao.getAgentTask(hmobj);
				for(TaskVo taskVo :listTask){
					TaskVo tkvo = TaskCommonHandle.getInitCommonTask(taskVo);
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	
	
	

}
