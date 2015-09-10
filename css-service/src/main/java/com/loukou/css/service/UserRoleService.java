package com.loukou.css.service;

import java.util.List;

import com.loukou.css.entity.UserRoleEntity;

public interface UserRoleService {
	/**
	 * 保存实体
	 * */
    public UserRoleEntity saveEntity(UserRoleEntity entity);
    
    /**
     * 删除用户角色
     * */
    public void delteUserRole(UserRoleEntity entity);
    
    /**
     * 通过用户id获取该用户的角色
     * */
    public List<UserRoleEntity> getUserRoleByUserId(int userId);
    
    /**
     * 通过角色id获取用户
     * */
    public List<UserRoleEntity> getUserRoleByRoleId(int roleId);
}
