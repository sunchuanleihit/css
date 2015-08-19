package com.loukou.css.processor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loukou.css.entity.MenuEntity;
import com.loukou.css.service.MenuService;

@Service
public class MenuProcessor {
	@Autowired
	private MenuService menuService;
	//获取菜单列表
	public List<MenuEntity> getMenuListByParentId(int parentId){
		return menuService.getMenusByParent(parentId);
	}
}
