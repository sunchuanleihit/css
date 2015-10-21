package com.loukou.css.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.loukou.auth.core.annotation.AuthCheck;
import com.loukou.css.processor.UserProcessor;
import com.loukou.css.service.redis.entity.SessionEntity;

@Controller
@RequestMapping("/main")
@AuthCheck(privileges = {"css.login"}, isRedirect = true)
public class MainController extends BaseController {
    @Autowired 
    private UserProcessor userProcessor;
	
	@RequestMapping("")
	public String Main(ModelMap modelMap) {
		//取出用户信息
		String userName = this.getAuthInfo().getUserName();
		modelMap.put("userName", userName);
		return "main";
	}
}
	