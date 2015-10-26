package com.loukou.css.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.loukou.auth.core.annotation.AuthCheck;

@Controller
@RequestMapping("")
@AuthCheck(privileges = {"css.login"}, isRedirect = true)
public class MainController extends BaseController {
	
	@RequestMapping("main")
	public String Main(Model model) {
		//取出用户信息
		String userName = this.getAuthInfo().getUserName();
		model.addAttribute("userName", userName);
		return "main";
	}
}