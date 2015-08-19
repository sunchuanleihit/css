package com.loukou.css.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// 验证
@Entity
@Table(name = "wh_api_authorization")
public class ApiAuthorizationEntity {
	// id
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int Id;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public int getIsUse() {
		return isUse;
	}

	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	// 应用key
	@Column(name = "app_key")
	private String appKey;

	// 安全key
	@Column(name = "secret_key")
	private String secretKey;

	// 是否在用，0在用，1停用
	@Column(name = "is_use")
	private int isUse;

	// 备注
	@Column(name = "remark")
	private String remark;

	// 创建时间
	@Column(name = "create_time")
	private Date createTime;
	
}
