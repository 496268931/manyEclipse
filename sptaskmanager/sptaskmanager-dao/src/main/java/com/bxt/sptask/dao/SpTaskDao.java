package com.bxt.sptask.dao;

import java.util.List;

import com.bxt.sptask.taskhandle.vo.SchTaskVO;
import com.bxt.sptask.taskhandle.vo.TaskVo;

/**
 * Description: TaskTestDao 任务管理DAO接口 
 
 * All Rights Reserved.
 * 
 * @version V1.0  2016-12-13 下午 12:23:52 星期二
 * @author wxd(wxd@inter3i.com)
 */
public interface SpTaskDao{
	 List<SchTaskVO> searchSpTask(SchTaskVO paramSchTaskVO);
	 Integer searchSpTaskCount(SchTaskVO paramSchTaskVO);
	 String insertTaskInfo(TaskVo paramTaskVo);
	 void updateTaskInfo(TaskVo taskvo);
	 TaskVo searchById(String paramString);
	 Integer updateTaskBySpRead(TaskVo taskvo);
}