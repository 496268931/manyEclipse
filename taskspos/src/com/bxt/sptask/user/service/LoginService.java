package com.bxt.sptask.user.service;

import java.io.Serializable;

import com.bxt.sptask.taskhandle.vo.TaskVo;
import com.bxt.sptask.user.vo.UserVO;

public interface LoginService extends Serializable {
	
	UserVO getUserLoginInfo(UserVO userVo);
}
