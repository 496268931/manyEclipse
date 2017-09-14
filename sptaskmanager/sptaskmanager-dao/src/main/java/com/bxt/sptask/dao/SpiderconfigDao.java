package com.bxt.sptask.dao;

import java.util.List;

import com.bxt.sptask.sptmpconfig.vo.SchSpiderconfigVO;

public interface SpiderconfigDao {
	List<SchSpiderconfigVO> searchSpiderconfig(SchSpiderconfigVO schSpiderconfigVO);
	Integer searchSpiderconfigCount(SchSpiderconfigVO schSpiderconfigVO);
}
