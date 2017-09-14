package com.bxt.sptask.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * Description: 用户登出 销毁session<br>
 * All Rights Reserved.
 */
@Controller
@RequestMapping(value = "/LoginoutController")
public class LoginoutController {
	
	@RequestMapping(value = "/loginout")
	public ModelAndView loginout(HttpServletRequest request, HttpSession session) {
		session.invalidate();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/jsp/login/login.jsp");
		return modelAndView;
	}
}
