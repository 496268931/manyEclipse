package com.bxt.sptask.taskmanager.dao;

import java.util.List;

import com.bxt.sptask.taskhandle.vo.TaskVo;
import com.bxt.sptask.taskmanager.vo.SchTaskVO;

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
	 TaskVo searchById(String paramString);
}