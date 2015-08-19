package com.loukou.css.bo;

import com.loukou.css.entity.UserEntity;
/**
 * 用户登录返回
 * */
public class LoginUser {
	private String code;
	private String message;
    private UserEntity user;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
    
}
