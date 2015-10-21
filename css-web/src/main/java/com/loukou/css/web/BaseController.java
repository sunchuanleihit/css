package com.loukou.css.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.loukou.auth.core.config.AuthConfigLoader;
import com.loukou.auth.core.entity.AuthInfo;

public class BaseController {


	@Autowired
	protected HttpSession session;

	@Autowired
	private AuthConfigLoader loader;
	
	public AuthInfo getAuthInfo(){
		AuthInfo info = (AuthInfo) session.getAttribute(loader.getSessionKey());
		return info;
	}
	
}
