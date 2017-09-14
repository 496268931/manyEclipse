package com.bxt.sptask.dao;

import java.util.List;

import com.bxt.sptask.navgationtree.vo.NavigationVO;

public interface NavigationDao {
	List<NavigationVO> getNavigationInfo(String uid);
}
