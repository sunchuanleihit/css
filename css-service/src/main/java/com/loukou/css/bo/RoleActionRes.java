package com.loukou.css.bo;

import java.util.List;

import com.loukou.css.entity.MenuEntity;

//角色权限返回
public class RoleActionRes {
private MenuEntity menuEntity;//菜单
private List<MenuActionRes> menuActions;//菜单下的动作
public MenuEntity getMenuEntity() {
	return menuEntity;
}
public void setMenuEntity(MenuEntity menuEntity) {
	this.menuEntity = menuEntity;
}
public List<MenuActionRes> getMenuActions() {
	return menuActions;
}
public void setMenuActions(List<MenuActionRes> menuActions) {
	this.menuActions = menuActions;
}

}
