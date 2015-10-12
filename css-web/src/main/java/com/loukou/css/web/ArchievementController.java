package com.loukou.css.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 绩效考核
 * @author sunchuanlei
 *
 */

@Controller
@RequestMapping("/archievement")
public class ArchievementController {
	
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView("archievement/index");
		return mv;
	}
	
}
