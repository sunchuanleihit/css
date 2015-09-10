package com.loukou.css.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.loukou.css.entity.RoleEntity;

public interface RoleService {
	/**
	 * 保存实体
	 * */
    public RoleEntity saveRole(RoleEntity entity);
    
    /**
     * 获取当前角色列表
     * */
    public List<RoleEntity> getAll();
    
    /**
     * 判断是否存在角色
     * */
    public boolean exitsRole(String roleName);
    
    /**
     * 删除角色
     * */
    public boolean closeRole(int roleId);
    
    /**
     * 根据角色ids获取角色列表
     * */
    public List<RoleEntity> getRolesByIds(List<Integer> roleIds);
    
    /**
     * 获取不在角色id中的所有角色
     * */
    public Page<RoleEntity> getRolesNotInRoleIds(final List<Integer> roleIds,final int pageIndex,final int pageSize);
}
