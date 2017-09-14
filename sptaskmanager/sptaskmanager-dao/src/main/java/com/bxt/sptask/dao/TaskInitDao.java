package com.bxt.sptask.dao;

import java.util.List;

import com.bxt.sptask.sptmpconfig.vo.SchSpiderconfigVO;
import com.bxt.sptask.taskhandle.vo.TaskVo;

public interface TaskInitDao {
	List<TaskVo> getEveryGroupTask(TaskVo taskvo);
	List<TaskVo> getEveryGroupTaskID(TaskVo taskvo);
	List<TaskVo> getTaskGroupInfo();
	List<SchSpiderconfigVO> getTaskTemplateConfig();
	SchSpiderconfigVO getTaskTemplateConfig(String tmpid);
	void handleOutTimeTask();
	
}
