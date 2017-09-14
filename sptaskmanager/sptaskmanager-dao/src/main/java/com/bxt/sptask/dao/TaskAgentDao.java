package com.bxt.sptask.dao;

import java.util.HashMap;
import java.util.List;

import com.bxt.sptask.taskhandle.vo.TaskVo;

public interface TaskAgentDao {
	
	List<TaskVo> getAgentTask(HashMap<String,String> hmobj);
	Integer upateTaskStatus(String taskid);
}
