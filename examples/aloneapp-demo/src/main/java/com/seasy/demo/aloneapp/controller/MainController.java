package com.seasy.demo.aloneapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seasy.core.util.DatetimeUtil;
import com.seasy.web.MenuResource;
import com.seasy.web.security.shiro.SecurityConstants;
import com.seasy.web.security.shiro.controller.ShiroAbstractController;

@Controller
public class MainController extends ShiroAbstractController {
	@RequestMapping("/main")
	public String main(ModelMap model) {
		model.put("today", DatetimeUtil.getToday("yyyy年MM月dd日") + "(周" + DatetimeUtil.getSimpleWeekName() + ")");
		return "main.jsp";
	}
	
	@RequestMapping("/header")
	public String header(ModelMap model) {
		model.put("user", getUser());
		return "header.jsp";
	}
	
	@RequestMapping("/menu")
	public String menu(HttpServletRequest request, ModelMap model) {
		List<MenuResource>  topMenuList = (List<MenuResource>)request.getSession().getAttribute(SecurityConstants.SESSION_ATTR_KEY__MENULIST);
		model.put("topMenus", topMenuList);
		return "menu.jsp";
	}
	
	@RequestMapping("/personal")
	public String personal(ModelMap model) {
		return "personal.jsp";
	}
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request, ModelMap model) {
		return "login.jsp";
	}
	
}
