package com.loukou.css.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loukou.css.dao.UserRoleDao;
import com.loukou.css.entity.UserRoleEntity;
import com.loukou.css.service.UserRoleService;
@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleDao userRoleDao;
	
	@Override
	public UserRoleEntity saveEntity(UserRoleEntity entity) {
		// TODO Auto-generated method stub
		return userRoleDao.save(entity);
	}

	@Override
	public void delteUserRole(UserRoleEntity entity) {
		// TODO Auto-generated method stub
		userRoleDao.delete(entity);
	}

	@Override
	public List<UserRoleEntity> getUserRoleByUserId(int userId) {
		// TODO Auto-generated method stub
		return userRoleDao.findByUserId(userId);
	}

	@Override
	public List<UserRoleEntity> getUserRoleByRoleId(int roleId) {
		// TODO Auto-generated method stub
		return userRoleDao.findByRoleId(roleId);
	}

}
