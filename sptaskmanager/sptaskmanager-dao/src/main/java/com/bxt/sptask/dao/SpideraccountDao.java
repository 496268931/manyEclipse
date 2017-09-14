package com.bxt.sptask.dao;

import com.bxt.sptask.spideraccount.vo.SpideraccountVO;

public interface SpideraccountDao {
	SpideraccountVO searchSpActByID(Integer actid);
	SpideraccountVO searchSpActBySourceid(Integer inuse);
	Integer updateSpActBySourceid(Integer sourceid);
	Integer updateSpActByID(SpideraccountVO spactvo);
}
 