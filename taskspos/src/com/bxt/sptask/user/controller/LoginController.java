package com.bxt.sptask.user.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bxt.sptask.taskhandle.vo.TaskVo;
import com.bxt.sptask.user.service.LoginService;
import com.bxt.sptask.user.vo.UserVO;

@Controller
@RequestMapping(value = "/loginController")
public class LoginController {
	public static final Logger	LOGGER	= LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(String username, String password, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try{
			System.out.println("登录测试");
			UserVO user = new UserVO();
			user.setUsername(username);
			user.setPassword(password);
			UserVO db=loginService.getUserLoginInfo(user);
			if(db==null){
				request.getSession().setAttribute("errorInfo", "用户名密码错误!");
				modelAndView.setViewName("redirect:/jsp/login/login.jsp");
			}else{
				modelAndView.setViewName("redirect:/SpTaskController/showTaskPage.do");
			}
		}catch (Exception e) {
			LOGGER.error("登录错误!", e);
			request.getSession().setAttribute("errorInfo", "登录错误!");
			modelAndView.setViewName("redirect:/jsp/login/login.jsp");
		}
 		return modelAndView;
	}
}
