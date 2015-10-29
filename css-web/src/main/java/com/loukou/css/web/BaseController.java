package com.loukou.css.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

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
	
	@ModelAttribute
	public void autoRun(Model model,HttpServletRequest request) {
        
        //privilege
        List<String> privileges = new ArrayList<String>();
        
        HttpSession session = request.getSession();
		if (session != null) {
			AuthInfo info = (AuthInfo) session.getAttribute(loader
					.getSessionKey());
			if (!CollectionUtils.isEmpty(info.getPrivileges())) {
				privileges.addAll(info.getPrivileges());
			}
		}
		model.addAttribute("privileges",privileges);
    }
}
