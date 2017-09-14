package com.bxt.sptask.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.bxt.sptask.dao.SpideraccountDao;
import com.bxt.sptask.spideraccount.vo.SpideraccountVO;

@Repository
public class SpideraccountDaoImpl implements SpideraccountDao {
	private static final String	 SPACTVO_SQL_NAMESPACE	= "com.bxt.sptask.spideraccount.vo.";
	
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public SpideraccountVO searchSpActByID(Integer actid) {
		SpideraccountVO spact_vo = (SpideraccountVO)sqlMapClientTemplate.queryForObject(SPACTVO_SQL_NAMESPACE + "searchSpideraccountById",actid);
		return spact_vo;
	}

	@Override
	public SpideraccountVO searchSpActBySourceid(Integer sourceid) {
		SpideraccountVO spact_vo = (SpideraccountVO)sqlMapClientTemplate.queryForObject(SPACTVO_SQL_NAMESPACE + "searchSpideraccountBySourceid",sourceid);
		return spact_vo;
	}

	@Override
	public Integer updateSpActBySourceid(Integer sourceid) {
		Integer iresult = null;
		iresult = sqlMapClientTemplate.update(SPACTVO_SQL_NAMESPACE + "udpateSpideraccountBySourceid",sourceid);
		return iresult;
	}

	@Override
	public Integer updateSpActByID(SpideraccountVO spactvo) {
		Integer iresult = null;
		iresult = sqlMapClientTemplate.update(SPACTVO_SQL_NAMESPACE + "udpateSpideraccountByID",spactvo);
		return iresult;
	}
	
	

}
