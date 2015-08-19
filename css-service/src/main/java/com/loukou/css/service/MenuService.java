package com.loukou.css.service;

import java.util.List;

import com.loukou.css.entity.MenuEntity;

public interface MenuService {
	/**
	 * 保存实体
	 * */
    public MenuEntity saveMenu(MenuEntity menu);
    
    /**
     * 根据菜单id获取菜单列表
     * */
    public List<MenuEntity> getMenus(List<Integer> menuIds);
    
    /**
     * 通过菜单id获取一个菜单
     * */
    public MenuEntity getMenu(int menuId);
    
    /**
     * 获取菜单列表
     * parentId:父菜单id
     * */
    public List<MenuEntity> getMenusByParent(int parentId);
}
