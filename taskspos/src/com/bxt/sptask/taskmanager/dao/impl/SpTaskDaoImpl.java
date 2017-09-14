package com.bxt.sptask.taskmanager.dao.impl;

import com.bxt.sptask.taskhandle.vo.TaskVo;
import com.bxt.sptask.taskmanager.dao.SpTaskDao;
import com.bxt.sptask.taskmanager.vo.SchTaskVO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SpTaskDaoImpl
  implements SpTaskDao
{
  private static final String TASKVO_SQL_NAMESPACE_ = "com.bxt.sptask.taskhandle.vo.";
  private static Logger LOGGER = LoggerFactory.getLogger(SpTaskDaoImpl.class);

  @Autowired
  private SqlMapClientTemplate sqlMapClientTemplate;

  public List<SchTaskVO> searchSpTask(SchTaskVO schTaskVO)
  {
    List listTaskVo = this.sqlMapClientTemplate.queryForList("com.bxt.sptask.taskhandle.vo.getTaskInfoByPage", schTaskVO);
    return listTaskVo;
  }

  public Integer searchSpTaskCount(SchTaskVO schTaskVO)
  {
    Integer itaskCount = (Integer)this.sqlMapClientTemplate.queryForObject("com.bxt.sptask.taskhandle.vo.getTaskCountByPage", schTaskVO);
    return itaskCount;
  }

  public String insertTaskInfo(TaskVo taskvo)
  {
    String taskid = (String)this.sqlMapClientTemplate.insert("com.bxt.sptask.taskhandle.vo.insertTaskInfo", taskvo);
    return taskid;
  }

  public TaskVo searchById(String taskid)
  {
    TaskVo taskvo = (TaskVo)this.sqlMapClientTemplate.queryForObject("com.bxt.sptask.taskhandle.vo.searchTaskByID", taskid);
    return taskvo;
  }
}