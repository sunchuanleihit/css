package com.loukou.css.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loukou.css.annotation.AuthPassport;
import com.loukou.css.processor.UserProcessor;
import com.loukou.css.service.redis.entity.SessionEntity;

@Controller
@RequestMapping("/main")
@AuthPassport
public class MainController extends BaseController {
    @Autowired 
    private UserProcessor userProcessor;
	
	@RequestMapping(method = RequestMethod.GET)
	public String Main(ModelMap modelMap) {
		//取出用户信息
		SessionEntity SessionEntity = sessionRedisService.getWhSessionEntity(getSessionId());
		String userName = userProcessor.getUser(SessionEntity.getUserId()).getUserName();
		SessionEntity.setUserName(userName);
		sessionRedisService.save(SessionEntity);
		modelMap.put("userId", SessionEntity.getUserId());
		modelMap.put("userName", SessionEntity.getUserName());
		modelMap.put("isRoot", SessionEntity.getIsRoot());
		return "main";
	}
}
	