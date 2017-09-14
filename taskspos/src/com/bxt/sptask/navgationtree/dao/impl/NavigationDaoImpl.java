package com.bxt.sptask.navgationtree.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.bxt.sptask.navgationtree.vo.NavigationVO;
import com.bxt.sptask.navgationtree.dao.NavigationDao;

/**
 * Description: NavigationDaoImpl t_navigationDAO实现类 
 
 * All Rights Reserved.
 * 
 * @version V1.0  2017-01-03 下午 04:08:24 星期二
 * @author wxd(wxd@chinazrbc.com)
 */
@Repository
public class NavigationDaoImpl implements NavigationDao {
	private static final String	 TASKVO_SQL_NAMESPACE_	= "com.bxt.sptask.navgationtree.vo.";
	
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public List<NavigationVO> getNavigationInfo(String uid) {
		List<NavigationVO> nvg_list = sqlMapClientTemplate.queryForList(TASKVO_SQL_NAMESPACE_ + "getNavigationByUserid",uid);
		return nvg_list;
	}
	

  
}