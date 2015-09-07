package com.loukou.css.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tcz_member_rate")
public class MemberRate {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "user_name")
	private String userName = "";
	
	@Column(name = "user_rate")
	private double userRate = 0;

	@Column(name = "type")
	private int type = 1;
	
	@Column(name = "operator")
	private String operator = "";
	
	@Column(name = "create_date")
	private Date createDate = new Date();

	@Column(name = "enabled")
	private String enabled = "Y";
	
	@Column(name = "real_name")
	private String realName = "";
	
	@Column(name = "tel")
	private String tel = "";

	@Column(name = "bumen")
	private String bumen = "";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getUserRate() {
		return userRate;
	}

	public void setUserRate(double userRate) {
		this.userRate = userRate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getBumen() {
		return bumen;
	}

	public void setBumen(String bumen) {
		this.bumen = bumen;
	}
}
