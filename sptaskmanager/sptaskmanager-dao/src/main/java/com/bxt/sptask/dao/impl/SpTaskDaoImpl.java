package com.bxt.sptask.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.bxt.sptask.dao.SpTaskDao;
import com.bxt.sptask.taskhandle.vo.SchTaskVO;
import com.bxt.sptask.taskhandle.vo.TaskVo;

@Repository
public class SpTaskDaoImpl
  implements SpTaskDao
{
  private static final String TASKVO_SQL_NAMESPACE = "com.bxt.sptask.taskhandle.vo.";
  private static Logger LOGGER = LoggerFactory.getLogger(SpTaskDaoImpl.class);
 
  @Autowired
  private SqlMapClientTemplate sqlMapClientTemplate;

  public List<SchTaskVO> searchSpTask(SchTaskVO schTaskVO)
  {
    List listTaskVo = this.sqlMapClientTemplate.queryForList( TASKVO_SQL_NAMESPACE + "getTaskInfoByPage", schTaskVO);
    return listTaskVo;
  }

  public Integer searchSpTaskCount(SchTaskVO schTaskVO)
  {
    Integer itaskCount = (Integer)this.sqlMapClientTemplate.queryForObject(TASKVO_SQL_NAMESPACE + "getTaskCountByPage", schTaskVO);
    return itaskCount;
  }

  public String insertTaskInfo(TaskVo taskvo)
  {
    String taskid = (String)this.sqlMapClientTemplate.insert(TASKVO_SQL_NAMESPACE + "insertTaskInfo", taskvo);
    return taskid;
  }

  public TaskVo searchById(String taskid)
  {
    TaskVo taskvo = (TaskVo)this.sqlMapClientTemplate.queryForObject(TASKVO_SQL_NAMESPACE + "searchTaskByID", taskid);
    return taskvo;
  }

	@Override
	public void updateTaskInfo(TaskVo taskvo) {
		// TODO Auto-generated method stub
		sqlMapClientTemplate.update(TASKVO_SQL_NAMESPACE + "updateTaskInfoByID", taskvo);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Integer updateTaskBySpRead(TaskVo taskvo) {
		Integer icon = sqlMapClientTemplate.update(TASKVO_SQL_NAMESPACE + "updateTaskBySpRead", taskvo);
		return icon;
	}
}