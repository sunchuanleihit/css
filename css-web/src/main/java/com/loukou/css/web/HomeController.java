
package com.loukou.css.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.loukou.auth.core.annotation.AuthCheck;

@Controller
@RequestMapping("/")
@AuthCheck(privileges = {"css.login"}, isRedirect = true)
public class HomeController extends BaseController {
	
	@RequestMapping("")
	public String home(){
		return "redirect:/main";
	}
	
}
