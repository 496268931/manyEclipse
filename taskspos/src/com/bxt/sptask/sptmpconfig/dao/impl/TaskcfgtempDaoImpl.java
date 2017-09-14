package com.bxt.sptask.sptmpconfig.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.bxt.sptask.sptmpconfig.dao.TaskcfgtempDao;
import com.bxt.sptask.sptmpconfig.vo.TaskcfgtempVO;

@Repository
public class TaskcfgtempDaoImpl implements TaskcfgtempDao {
	private static final String	 TASKCFG_SQL_NAMESPACE_	= "com.bxt.sptask.sptmpconfig.vo.";
	
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	
	@Override
	public List<TaskcfgtempVO> getTaskcfgtempInfo() {
		
		List<TaskcfgtempVO>	taskcfgtemp_list = sqlMapClientTemplate.queryForList(TASKCFG_SQL_NAMESPACE_ + "getTaskcfgtempInfo");
		return taskcfgtemp_list;
		
	}
}
