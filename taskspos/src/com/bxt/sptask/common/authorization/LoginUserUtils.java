package com.bxt.sptask.common.authorization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bxt.sptask.user.vo.UserVO;

/**
 * Description: 登录用户工具类<br>
 * All Rights Reserved.
 * 
 */
public class LoginUserUtils {
	
	/**
	 * Description: 获取登录对象
	 * 
	 * @param session
	 * @return
	 */
	public static UserVO getLoginUser(HttpSession session) {
		//UserVO userVO = (UserVO) session.getAttribute(EmployeeUserEnum.EMPLOYEE_NAME.getName());
		return null;
	}
	
	/**
	 * Description: 获取登录对象
	 * 
	 * @param request
	 * @return
	 */
	public static UserVO getLoginUser(HttpServletRequest request) {
		return getLoginUser(request.getSession());
	}
}
