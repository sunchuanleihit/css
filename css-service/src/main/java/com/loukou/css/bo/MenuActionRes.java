package com.loukou.css.bo;

import com.loukou.css.entity.MenuActionEntity;
//菜单权限返回实体
public class MenuActionRes {
private MenuActionEntity menuAction;
private int isCheck;//当前动作是否有权限
public MenuActionEntity getMenuAction() {
	return menuAction;
}
public void setMenuAction(MenuActionEntity menuAction) {
	this.menuAction = menuAction;
}
public int getIsCheck() {
	return isCheck;
}
public void setIsCheck(int isCheck) {
	this.isCheck = isCheck;
}

}
