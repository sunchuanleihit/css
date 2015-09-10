package com.loukou.css.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.loukou.css.service.redis.entity.SessionEntity;
import com.loukou.css.service.SessionRedisService;
import com.loukou.css.util.SessionUtil;

public class BaseController {

	protected final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	protected SessionRedisService sessionRedisService;
	
	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected HttpSession session;

	protected String getSessionId(){
		return SessionUtil.getSessionKey(request,session);
	}
	
	protected SessionEntity getWhSessionEntity(){
		return sessionRedisService.getWhSessionEntity(getSessionId());
	}
	
}
