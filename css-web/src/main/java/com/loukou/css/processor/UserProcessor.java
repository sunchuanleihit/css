package com.loukou.css.processor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loukou.css.entity.UserEntity;
import com.loukou.css.service.UserService;

@Service
public class UserProcessor {
	@Autowired
	private UserService userService;

	public List<UserEntity> getAll() {
		return userService.getAllUsers();
	}
	public UserEntity getUser(int userId){
		return userService.findByUserId(userId);
	}
	
	public UserEntity getUser(String userCode){
		return userService.findByUserCode(userCode);
	}
	
	public UserEntity saveUser(UserEntity user){
	    return userService.saveUser(user);
	}
	
	public int closeOrOpenUser(int userId,int userType){
		if(userType == 0){
			//当前启用改为停用
			return userService.closeOrOpenUser(userId, false);
		}
		return userService.closeOrOpenUser(userId, true);
	}
}
