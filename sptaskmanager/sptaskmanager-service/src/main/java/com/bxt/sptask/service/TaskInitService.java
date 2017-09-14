package com.bxt.sptask.service;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.web.context.WebApplicationContext;

public interface TaskInitService {
	void initTaskHashmap();
	void waitTaskKeyRead();
	void waitTaskKeyHandle();
	void handleOutTimeTask();
	
	
	
}
