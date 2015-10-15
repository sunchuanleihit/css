package com.loukou.css.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.loukou.css.resp.AchievementRespDto;
import com.loukou.css.service.CssService;

/**
 * 绩效考核
 * @author sunchuanlei
 *
 */

@Controller
@RequestMapping("/achievement")
public class AchievementController {
	@Autowired
	private CssService cssService;
	
	
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView("achievement/index");
		return mv;
	}
	
	@RequestMapping("/caculate")
	@ResponseBody
	public List<AchievementRespDto> caculate(HttpServletRequest request){
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		List<AchievementRespDto> achievementList = cssService.getAchievement(startDate, endDate);
		return achievementList;
	}
	
}