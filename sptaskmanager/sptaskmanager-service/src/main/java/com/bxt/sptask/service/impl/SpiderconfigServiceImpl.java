package com.bxt.sptask.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bxt.sptask.dao.SpiderconfigDao;
import com.bxt.sptask.service.SpiderconfigService;
import com.bxt.sptask.sptmpconfig.vo.SchSpiderconfigVO;

/**
 * Description: SpiderconfigServiceImpl spiderconfigSERVICE实现类 
 
 * All Rights Reserved.
 * 
 * @version V1.0  2017-01-06 下午 05:09:36 星期五
 * @author wxd(wxd@inter3i.com)
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class SpiderconfigServiceImpl implements SpiderconfigService {

    private static final long serialVersionUID = 732179675493074899L;

	private static Logger LOGGER = LoggerFactory.getLogger(SpiderconfigServiceImpl.class);

	@Autowired
    private SpiderconfigDao spiderconfigDao;
	
	@Override
	public List<SchSpiderconfigVO> searchSpiderconfig(SchSpiderconfigVO schSpiderconfigVO) {
		List<SchSpiderconfigVO> listSchSpiderTpl = null;
		try{
			listSchSpiderTpl = spiderconfigDao.searchSpiderconfig(schSpiderconfigVO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return listSchSpiderTpl;
	}

	@Override
	public Integer searchSpiderconfigCount(SchSpiderconfigVO schSpiderconfigVO) {
		Integer isptmpconfigCount =0;
		isptmpconfigCount = spiderconfigDao.searchSpiderconfigCount(schSpiderconfigVO);
		return isptmpconfigCount;
	}
     
    
}