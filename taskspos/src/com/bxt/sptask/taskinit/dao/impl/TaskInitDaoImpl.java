package com.bxt.sptask.taskinit.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.bxt.sptask.taskhandle.vo.SpiderconfigVO;
import com.bxt.sptask.taskhandle.vo.TaskVo;
import com.bxt.sptask.taskinit.dao.TaskInitDao;

@SuppressWarnings("deprecation")
public class TaskInitDaoImpl implements TaskInitDao{
	private static final String	 USERVO_SQL_NAMESPACE_	= "com.bxt.sptask.taskhandle.vo.";
	private static HashMap<String,List<String>> crmpTask =new HashMap<String,List<String>>();
	
	private SqlMapClientTemplate sqlMapClientTemplate;
	//此处需要写个单类模式调用sqlMapClientTemplate,需要有静态代码块

	@SuppressWarnings("unchecked")
	@Override
	public List<TaskVo> getEveryGroupTask(WebApplicationContext wact,TaskVo taskvo) {
		List<TaskVo> listTaskVo = null;
		try{
			sqlMapClientTemplate =  (SqlMapClientTemplate)wact.getBean("sqlMapClientTemplate");
			listTaskVo = sqlMapClientTemplate.queryForList(USERVO_SQL_NAMESPACE_+ "getEveryGroupTaskInfo",taskvo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return listTaskVo;
	}

	@Override
	public List<TaskVo> getTaskGroupInfo(WebApplicationContext wact) {
		List<TaskVo> listTaskVo = null;
		try{
			sqlMapClientTemplate =  (SqlMapClientTemplate)wact.getBean("sqlMapClientTemplate");
			listTaskVo = sqlMapClientTemplate.queryForList(USERVO_SQL_NAMESPACE_+ "getGroupTaskZero");
		}catch(Exception e){
			e.printStackTrace();
		}
		return listTaskVo;
	}

//	@Override
//	public List<SpiderconfigVO> getTaskTemplateConfig(WebApplicationContext wact) {
//		List<SpiderconfigVO> listSpiderTpl = null;
//		try{
//			sqlMapClientTemplate =  (SqlMapClientTemplate)wact.getBean("sqlMapClientTemplate");
//			listSpiderTpl = sqlMapClientTemplate.queryForList(USERVO_SQL_NAMESPACE_+ "schSpiderConfList");
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return listSpiderTpl;
//	}

	@Override
	public List<TaskVo> getEveryGroupTaskID(WebApplicationContext wact,
			TaskVo taskvo) {
		List<TaskVo> listTaskVo = null;
		try{
			sqlMapClientTemplate =  (SqlMapClientTemplate)wact.getBean("sqlMapClientTemplate");
			listTaskVo = sqlMapClientTemplate.queryForList(USERVO_SQL_NAMESPACE_+ "getEveryGroupTaskID",taskvo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return listTaskVo;
	}

	@Override
	public void handleOutTimeTask(WebApplicationContext wact) {
		try{
			sqlMapClientTemplate =  (SqlMapClientTemplate)wact.getBean("sqlMapClientTemplate");
			sqlMapClientTemplate.update(USERVO_SQL_NAMESPACE_+ "upOutTimeTask");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	

}
