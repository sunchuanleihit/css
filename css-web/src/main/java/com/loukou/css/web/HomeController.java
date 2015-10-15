package com.loukou.css.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.loukou.css.contains.PageUrls;
import com.loukou.css.service.redis.entity.SessionEntity;

@Controller
public class HomeController extends BaseController {
	@RequestMapping("/")
	public String home(){
		SessionEntity SessionEntity = sessionRedisService.getWhSessionEntity(getSessionId());
		if(SessionEntity!=null){
			return "redirect:/main";
		}
        return PageUrls.LOGIN_URL;
	}
	
}
