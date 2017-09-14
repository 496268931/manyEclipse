package com.bxt.sptask.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bxt.sptask.common.authorization.LoginUserUtils;
import com.bxt.sptask.user.vo.UserVO;

/**
 * Description: 获取session 中的 USER 对象 All Rights Reserved.
 * 
 * @version 1.0 2014年4月29日 上午11:34:37 by ms(ms@inter3i.com）创建
 */
public class SessionUtils {
	
	/**
	 * Description: 通过session查找用户信息
	 * 
	 * @Version1.0 2014年4月29日 上午11:50:16 by ms(ms@inter3i.com）创建
	 * @param session
	 * @return
	 */
	public static UserVO getSessionUserVO(HttpSession session) {
		UserVO uservo = null;
		if (session != null) {
			uservo = LoginUserUtils.getLoginUser(session);
		}
		if (uservo != null) {
			return uservo;
		} else {
			return null;
		}
	}
	
	/**
	 * Description: 通过request查找用户信息
	 * 
	 * @Version1.0 2014年4月29日 上午11:50:22 by ms(ms@inter3i.com）创建
	 * @param request
	 * @return
	 */
	public static UserVO getSessionUserVO(HttpServletRequest request) {
		return getSessionUserVO(request.getSession(true));
	}
	
	/**
	 * Description: 通过session查找用户ID
	 * 
	 * @Version1.0 2014年4月29日 上午11:50:16 by ms(ms@inter3i.com）创建
	 * @param session
	 * @return
	 */
	public static Long getSessionUserId(HttpSession session) {
		UserVO userVO = LoginUserUtils.getLoginUser(session);
		if (userVO != null) {
			return Long.valueOf(userVO.getUserid());
		} else {
			return null;
		}
	}
	
	/**
	 * Description: 通过request查找用户ID
	 * 
	 * @Version1.0 2014年4月29日 上午11:50:22 by ms(ms@inter3i.com）创建
	 * @param request
	 * @return
	 */
	public static Long getSessionUserId(HttpServletRequest request) {
		return getSessionUserId(request.getSession(true));
	}
}
