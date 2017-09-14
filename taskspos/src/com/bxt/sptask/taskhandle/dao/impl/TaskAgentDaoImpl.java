package com.bxt.sptask.taskhandle.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.bxt.sptask.taskhandle.dao.TaskAgentDao;
import com.bxt.sptask.taskhandle.vo.TaskVo;


@Repository
@SuppressWarnings("deprecation")
public class TaskAgentDaoImpl implements TaskAgentDao{
	private static final String	 TASKVO_SQL_NAMESPACE_	= "com.bxt.sptask.taskhandle.vo.";
	
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;

	@Override
	@SuppressWarnings("unchecked")
	public  List<TaskVo> getAgentTask(HashMap<String,String> hmobj) {
		String sPecifiedtype = hmobj.get("specifiedtype");
		String sTasktype = hmobj.get("tasktype");
		String sPecifiedMac = hmobj.get("mac");
		List<TaskVo> listTaskVo = null;
		TaskVo task = new TaskVo();
		
		if(sTasktype != null){
			task.setTasktype(Integer.parseInt(sTasktype));
		}
		String tasklx = hmobj.get("task");
		if(tasklx != null){
			task.setTask(Integer.parseInt(tasklx));	
		}
		
		
		//根据指定类型和指定MAC返回任务
		if (sPecifiedtype != null && sPecifiedMac != null) {
			listTaskVo = sqlMapClientTemplate.queryForList(TASKVO_SQL_NAMESPACE_+ "getPMTaskInfo",task);
			if(listTaskVo != null){
				listTaskVo = sqlMapClientTemplate.queryForList(TASKVO_SQL_NAMESPACE_+ "getPMTaskInfoOne",task);
				return listTaskVo;
			}
		 }
		 //根据指定MAC
		 if (sPecifiedMac != null) {
			 listTaskVo = sqlMapClientTemplate.queryForList(TASKVO_SQL_NAMESPACE_+ "getMTaskInfo",task);
			 if(listTaskVo != null){
				 listTaskVo = sqlMapClientTemplate.queryForList(TASKVO_SQL_NAMESPACE_+ "getMTaskInfoOne",task);
				 return listTaskVo;
			 }
		 }
		 
		 //根据指定类型
		 if (sPecifiedtype != null) {
			listTaskVo = sqlMapClientTemplate.queryForList(TASKVO_SQL_NAMESPACE_+ "getPTaskInfo",task);
			if(listTaskVo != null){
				listTaskVo = sqlMapClientTemplate.queryForList(TASKVO_SQL_NAMESPACE_+ "getPTaskInfoOne",task);
				return listTaskVo;
			}
		 }
		 //指定默认类型
		 listTaskVo = sqlMapClientTemplate.queryForList(TASKVO_SQL_NAMESPACE_+ "getEveryGroupTaskInfo",task);
//		 if(listTaskVo != null){
//			 listTaskVo = sqlMapClientTemplate.queryForList(TASKVO_SQL_NAMESPACE_+ "getTaskInfoOne",task);
//		 }
		
		return listTaskVo;
		
	}

	@Override
	public Integer upateTaskStatus(String taskid) {
		Integer iresult = sqlMapClientTemplate.update(TASKVO_SQL_NAMESPACE_ + "upTaskStatus",taskid);
		return iresult;
	}

}
