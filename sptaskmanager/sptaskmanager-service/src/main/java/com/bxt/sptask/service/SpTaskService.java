package com.bxt.sptask.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.bxt.sptask.taskhandle.vo.SchTaskVO;
import com.bxt.sptask.taskhandle.vo.TaskVo;

/**
 * Description: TaskTestService 任务管理SERVICE接口 
 * All Rights Reserved.
 * @version V1.0  2016-12-13 下午 12:23:52 星期二
 * @author wxd(wxd@inter3i.com)
 */
public interface SpTaskService extends Serializable {

	  void deleteTaskTest(Integer paramInteger);

	  void deleteTaskTest(TaskVo paramTaskVo);

	  Integer insertTaskTest(TaskVo paramTaskVo);

	  HashMap<String,String> insertTaskParam(String paramString,String taskid,String childtaskid);
	  
	  void insertTaskParam(TaskVo taskvo);

	  String saveChildTaskNode(String paramString1, String paramString2);

	  TaskVo searchTaskTestById(String paramString);

	  List<SchTaskVO> searchSpTask(SchTaskVO paramSchTaskVO);

	  Integer searchSpTaskCount(SchTaskVO paramSchTaskVO);

	  List<TaskVo> searchTaskTestList(TaskVo paramTaskVo);

	  Integer searchTaskTestCount(TaskVo paramTaskVo);

	  void updateTaskTest(TaskVo paramTaskVo);

}