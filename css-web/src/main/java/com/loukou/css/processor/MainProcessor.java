package com.loukou.css.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.loukou.css.bo.LoginUser;
import com.loukou.css.service.UserService;

@Service
public class MainProcessor {

	@Autowired
	private UserService userService;
	public  LoginUser Login(String userCode,String userPwd) {
		return userService.checkLoginUser(userCode,userPwd);
	}
}
