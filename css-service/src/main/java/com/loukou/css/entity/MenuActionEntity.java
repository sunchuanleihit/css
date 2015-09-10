package com.loukou.css.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wh_menu_action")
public class MenuActionEntity {
	@Id
	@Column(name = "action_id")
	private int actionId;
	
	@Column(name = "action_name")
	private String actionName;
	
	@Column(name = "menu_id")
	private int menuId;
	
	@Column(name = "is_use")
	private int isUse;
	
	@Column(name = "create_time")
    private Date createTime;

	public int getActionId() {
		return actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public int getIsUse() {
		return isUse;
	}

	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
