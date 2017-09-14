package com.bxt.sptask.user.dao;

import java.io.Serializable;

import com.bxt.sptask.taskhandle.vo.TaskVo;
import com.bxt.sptask.user.vo.UserVO;

public interface LoginDao extends Serializable {
	
	UserVO getUserLoginInfo(UserVO user);

}
