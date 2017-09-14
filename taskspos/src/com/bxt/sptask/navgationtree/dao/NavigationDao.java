package com.bxt.sptask.navgationtree.dao;

import java.util.List;

import com.bxt.sptask.navgationtree.vo.NavigationVO;

public interface NavigationDao {
	List<NavigationVO> getNavigationInfo(String uid);
}
