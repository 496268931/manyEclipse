package com.bxt.sptask.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.bxt.sptask.dao.SpiderconfigDao;
import com.bxt.sptask.sptmpconfig.vo.SchSpiderconfigVO;

/**
 * Description: SpiderconfigDaoImpl spiderconfigDAO实现类 
 
 * All Rights Reserved.
 * 
 * @version V1.0  2017-01-06 下午 05:09:36 星期五
 * @author wxd(wxd@inter3i.com)
 */
@Repository
public class SpiderconfigDaoImpl implements SpiderconfigDao {
	private static final String	 SPIDERCONFIG_SQL_NAMESPACE_	= "com.bxt.sptask.sptmpconfig.vo.";

	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public List<SchSpiderconfigVO> searchSpiderconfig(SchSpiderconfigVO schSpiderconfigVO) {
		List<SchSpiderconfigVO> listSchSpiderTpl = null;
		try{
			listSchSpiderTpl = sqlMapClientTemplate.queryForList(SPIDERCONFIG_SQL_NAMESPACE_+ "searchSpiderconfigList",schSpiderconfigVO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return listSchSpiderTpl;
	}

	@Override
	public Integer searchSpiderconfigCount(SchSpiderconfigVO schSpiderconfigVO) {
		Integer iSptmpCount  = (Integer)sqlMapClientTemplate.queryForObject(SPIDERCONFIG_SQL_NAMESPACE_+ "searchSpiderconfigCount",schSpiderconfigVO);
		return iSptmpCount;
	}

 
}