package com.loukou.css.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "lk_admin")
public class UserEntity {
	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "user_name")
	private String userCode;
	
	@Column(name = "user_realname")
	private String userName;
	
	@Column(name = "user_password")
	private String userPwd;
	
	@Column(name = "is_use")
	private int isUse;
	
	@Column(name = "is_root")
    private int isRoot;
	
	@Column(name = "create_time")
	private Date createTime;

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
	
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public int getIsUse() {
		return isUse;
	}

	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}

	public int getIsRoot() {
		return isRoot;
	}

	public void setIsRoot(int isRoot) {
		this.isRoot = isRoot;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
