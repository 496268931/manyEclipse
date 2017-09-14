package com.bxt.sptask.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.bxt.sptask.dao.TaskInitDao;
import com.bxt.sptask.sptmpconfig.vo.SchSpiderconfigVO;
import com.bxt.sptask.taskhandle.vo.TaskVo;

@Repository
@SuppressWarnings("deprecation")
public class TaskInitDaoImpl implements TaskInitDao{
	private static final String TEMPLETVO_SQL_NAMESPACE = "com.bxt.sptask.sptmpconfig.vo.";
	private static final String	 USERVO_SQL_NAMESPACE_	= "com.bxt.sptask.taskhandle.vo.";
	private static HashMap<String,List<String>> crmpTask =new HashMap<String,List<String>>();
	
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<TaskVo> getEveryGroupTask(TaskVo taskvo) {
		List<TaskVo> listTaskVo = null;
		try{
			listTaskVo = sqlMapClientTemplate.queryForList(USERVO_SQL_NAMESPACE_+ "getEveryGroupTaskInfo",taskvo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return listTaskVo;
	}

	@Override
	public List<TaskVo> getTaskGroupInfo() {
		List<TaskVo> listTaskVo = null;
		try{
			listTaskVo = sqlMapClientTemplate.queryForList(USERVO_SQL_NAMESPACE_+ "getGroupTaskZero");
		}catch(Exception e){
			e.printStackTrace();
		}
		return listTaskVo;
	}

	@Override
	public List<SchSpiderconfigVO> getTaskTemplateConfig() {
		List<SchSpiderconfigVO> listSpiderTpl = null;
		try{
			listSpiderTpl = sqlMapClientTemplate.queryForList(TEMPLETVO_SQL_NAMESPACE + "schSpiderConfList");
		}catch(Exception e){
			e.printStackTrace();
		}
		return listSpiderTpl;
	}

	@Override
	public List<TaskVo> getEveryGroupTaskID(TaskVo taskvo) {
		List<TaskVo> listTaskVo = null;
		try{
			listTaskVo = sqlMapClientTemplate.queryForList(USERVO_SQL_NAMESPACE_+ "getEveryGroupTaskID",taskvo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return listTaskVo;
	}

	@Override
	public void handleOutTimeTask() {
		try{
			sqlMapClientTemplate.update(USERVO_SQL_NAMESPACE_+ "upOutTimeTask");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public SchSpiderconfigVO getTaskTemplateConfig(String tmpid) {
		SchSpiderconfigVO schspvo = null;
		try{
			schspvo = (SchSpiderconfigVO)sqlMapClientTemplate.queryForObject(TEMPLETVO_SQL_NAMESPACE + "searchSpiderconfigById",tmpid);
		}catch(Exception e){
			e.printStackTrace();
		}
		return schspvo;
	}
	
	

}
