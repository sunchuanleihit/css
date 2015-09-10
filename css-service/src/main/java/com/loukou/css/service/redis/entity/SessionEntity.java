package com.loukou.css.service.redis.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.loukou.css.entity.RoleEntity;

public class SessionEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8643975009416892858L;
	
	private String sessionId;
	private int userId;
	private String userName;
	private int warehouseId;
	private String warehouseName;
	private Date loginDate = new Date();
	private List<RoleEntity> lstRole;
	private int isRoot;
	
	public int getIsRoot() {
		return isRoot;
	}
	public void setIsRoot(int isRoot) {
		this.isRoot = isRoot;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	
	public List<RoleEntity> getLstRole() {
		return lstRole;
	}
	public void setLstRole(List<RoleEntity> lstRole) {
		this.lstRole = lstRole;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	
}
