
package com.loukou.css.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController {
	
	@RequestMapping("")
	public String home(){
		return "redirect:/main";
	}
	
}
