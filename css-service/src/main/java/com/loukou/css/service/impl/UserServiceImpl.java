package com.loukou.css.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.loukou.css.bo.BaseRes;
import com.loukou.css.bo.LoginUser;
import com.loukou.css.bo.PageListEntity;
import com.loukou.css.bo.tools.MD5;
import com.loukou.css.dao.UserDao;
import com.loukou.css.entity.UserEntity;
import com.loukou.css.service.UserService;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public List<UserEntity> getAllUsers() {
		// TODO Auto-generated method stub
		return userDao.getAllUser();
	}

	@Override
	public UserEntity findByUserCode(String userCode) {
		// TODO Auto-generated method stub
		return userDao.findByUserCode(userCode);
	}

	@Override
	public int updateUser(UserEntity user) {
		// TODO Auto-generated method stub
		return userDao.updateByUserId(user.getUserId(), user.getUserName(), user.getUserName());
	}

	@Override
	public UserEntity findByUserId(int userId) {
		// TODO Auto-generated method stub
		return userDao.findOne(userId);
	}

	@Override
	public LoginUser checkLoginUser(String userCode, String userPwd) {
		// TODO Auto-generated method stub
		LoginUser loginuser = new LoginUser();
				
		UserEntity user = userDao.findByUserCode(userCode);
		if(user == null){
			loginuser.setCode("201");
			loginuser.setMessage("用户不存在");
			return loginuser;
		}
		
		if(user.getIsUse() == 1){
			//该用户已停用
			loginuser.setCode("202");
			loginuser.setMessage("该用户已停用");
			return loginuser;
		}
		MD5 md5 =new MD5();
		if(!user.getUserPwd().equals(md5.GetMD5Code(userPwd))){
			loginuser.setCode("203");
			loginuser.setMessage("密码错误");
			return loginuser;
		}
		loginuser.setCode("200");
		loginuser.setMessage("验证通过");
		user.setUserPwd("");
		loginuser.setUser(user);
		
		return loginuser;
	}

	@Override
	public int closeOrOpenUser(int userId, boolean isOpen) {
		// TODO Auto-generated method stub
		if(isOpen){
			return userDao.closeOrOpenUser(userId, 0);//启用
		}else{
			return userDao.closeOrOpenUser(userId, 1);//停用
		}
	}

	@Override
	public int updateUserPwd(int userId, String oldPwd, String newPwd) {
		// TODO Auto-generated method stub
		UserEntity user = userDao.findOne(userId);
		if(user == null){return 0;}
		MD5 md5 = new MD5();
		oldPwd = md5.GetMD5Code(oldPwd);
		if(!oldPwd.equals(user.getUserPwd())){
			return 2;//原密码错误
		}
		newPwd = md5.GetMD5Code(newPwd);
		return userDao.updateUserPwd(userId, newPwd);
	}
	
	public List<UserEntity> findByUserIdIn(List<Integer> ids){
		return userDao.findByUserIdIn(ids);
	}

	@Override
	public UserEntity saveUser(UserEntity user) {
		// TODO Auto-generated method stub
		return userDao.save(user);
	}
	
	public List<UserEntity> findByUserNameLike(String userName){
		return userDao.findByUserNameLike("%" + userName + "%");
	}
}
