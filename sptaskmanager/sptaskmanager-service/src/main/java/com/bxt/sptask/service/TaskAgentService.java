package com.bxt.sptask.service;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

public interface TaskAgentService {
	//获取任务
	
	String getAgentTask(HashMap<String,String> hmobj);
	String getPhpAgentTask(HashMap<String,String> hmobj);
}
