package com.bxt.sptask.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bxt.sptask.dao.NavigationDao;
import com.bxt.sptask.service.NavigationService;
import com.bxt.sptask.navgationtree.vo.NavigationVO;

import net.sf.json.JSONObject;

/**
 * Description: NavigationServiceImpl t_navigationSERVICE实现类 
 * All Rights Reserved.
 * @version V1.0  2017-01-03 下午 04:12:28 星期二
 * @author wxd(wxd@inter3i.com)
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class NavigationServiceImpl implements NavigationService {
	private static Logger LOGGER = LoggerFactory.getLogger(NavigationServiceImpl.class);

	@Autowired
    private NavigationDao navigationDao;

	@Override
	public String getNavigationInfo(String uid) {
		String navigationstr = "";
		JSONObject jsonobjNavgationVO = new JSONObject();
		
		List<NavigationVO> navigation_list = navigationDao.getNavigationInfo(uid);
		
		if(navigation_list != null && navigation_list.size()> 0){
			jsonobjNavgationVO.put("navigationlist", navigation_list);
			navigationstr = jsonobjNavgationVO.toString();
		}
		return navigationstr;
	}
	
    
}