package com.bxt.sptask.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bxt.sptask.taskhandle.vo.TaskVo;
import com.bxt.sptask.user.dao.LoginDao;
import com.bxt.sptask.user.service.LoginService;
import com.bxt.sptask.user.vo.UserVO;

@Service
@Transactional(rollbackFor = Throwable.class)
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private LoginDao LoginDao;
	
	@Override
	public UserVO getUserLoginInfo(UserVO user) {
		UserVO rs = LoginDao.getUserLoginInfo(user);
		return rs;
	}

}
