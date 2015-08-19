package com.loukou.css.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loukou.css.dao.MenuDao;
import com.loukou.css.entity.MenuEntity;
import com.loukou.css.service.MenuService;
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;
	
	@Override
	public MenuEntity saveMenu(MenuEntity menu) {
		// TODO Auto-generated method stub
		return menuDao.save(menu);
	}

	@Override
	public List<MenuEntity> getMenus(List<Integer> menuIds) {
		// TODO Auto-generated method stub
		return menuDao.findByMenuIdInAndIsUseOrderByShowIndexDesc(menuIds, 0);
	}

	@Override
	public MenuEntity getMenu(int menuId) {
		// TODO Auto-generated method stub
		return menuDao.findOne(menuId);
	}

	@Override
	public List<MenuEntity> getMenusByParent(int parentId) {
		// TODO Auto-generated method stub
		return menuDao.findByParentIdAndIsUseOrderByShowIndexDesc(parentId, 0);
	}

}
