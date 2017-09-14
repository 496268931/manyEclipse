package com.bxt.sptask.taskinit.service;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.web.context.WebApplicationContext;

public interface TaskInitService {
	void initTaskHashmap(WebApplicationContext wact);
	void waitTaskKeyRead(WebApplicationContext wact);
	void waitTaskKeyHandle(WebApplicationContext wact);
	void handleOutTimeTask(WebApplicationContext wact);
	
	
	
}
