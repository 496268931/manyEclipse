package com.bxt.sptask.service;

import java.util.List;

import com.bxt.sptask.sptmpconfig.vo.SchSpiderconfigVO;

public interface SpiderconfigService {

	List<SchSpiderconfigVO> searchSpiderconfig(SchSpiderconfigVO schSpiderconfigVO);
	Integer searchSpiderconfigCount(SchSpiderconfigVO schSpiderconfigVO);

}
