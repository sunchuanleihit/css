package com.loukou.css.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuRespDto implements Serializable{
	private static final long serialVersionUID = -7133457781205201838L;
	private int menuId;
	private String menuName;
	private String menuUrl;
	private int parentId;
	List<MenuRespDto> childMenus = new ArrayList<MenuRespDto>();
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public List<MenuRespDto> getChildMenus() {
		return childMenus;
	}
	public void setChildMenus(List<MenuRespDto> childMenus) {
		this.childMenus = childMenus;
	}

}
