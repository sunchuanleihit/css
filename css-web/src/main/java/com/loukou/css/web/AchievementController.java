package com.loukou.css.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.loukou.auth.core.annotation.AuthCheck;
import com.loukou.css.resp.AchievementRespDto;
import com.loukou.css.service.CssService;

/**
 * 绩效考核
 * @author sunchuanlei
 *
 */

@Controller
@RequestMapping("/achievement")
@AuthCheck(privileges = {"css.login"}, isRedirect = true)
public class AchievementController extends  BaseController{
	@Autowired
	private CssService cssService;
	
	@RequestMapping("/index")
	@AuthCheck(privileges = {"css.achievement"}, isRedirect = true)
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView("achievement/index");
		return mv;
	}
	
	@RequestMapping("/caculate")
	@AuthCheck(privileges = {"css.achievement"}, isRedirect = true)
	@ResponseBody
	public List<AchievementRespDto> caculate(HttpServletRequest request){
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		List<AchievementRespDto> achievementList = cssService.getAchievement(startDate, endDate);
		return achievementList;
	}
	
}
