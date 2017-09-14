package com.bxt.sptask.taskinit.dao;

import java.util.List;

import org.springframework.web.context.WebApplicationContext;

import com.bxt.sptask.taskhandle.vo.SpiderconfigVO;
import com.bxt.sptask.taskhandle.vo.TaskVo;

public interface TaskInitDao {
	List<TaskVo> getEveryGroupTask(WebApplicationContext wact,TaskVo taskvo);
	List<TaskVo> getEveryGroupTaskID(WebApplicationContext wact,TaskVo taskvo);
	List<TaskVo> getTaskGroupInfo(WebApplicationContext wact);
	//List<SpiderconfigVO> getTaskTemplateConfig(WebApplicationContext wact);
	void handleOutTimeTask(WebApplicationContext wact);
	
}
