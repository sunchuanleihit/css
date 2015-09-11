package com.loukou.css.resp;

import java.io.Serializable;

public class ComplaintRespDto implements Serializable{
	private static final long serialVersionUID = -825620845541218933L;
	
	private Integer id;
	
	private String userName;
	
	private String createTime;
	
	private String orderSnMain;
	
	private String whName;
	
	private String goodsName;
	
	private int departmentId;
	
	private String department;
	
	private String mobile;
	
	private String city;
	
	private int complaintTypeId;
	
	public int getComplaintTypeId() {
		return complaintTypeId;
	}

	public void setComplaintTypeId(int complaintTypeId) {
		this.complaintTypeId = complaintTypeId;
	}

	private String complaintType;
	
	private Integer handleStatus;
	
	private String addTime;
	
	private Integer status;
	
	private String content;

	private String actor;
	
	public String getActor() {
		return actor;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getWhName() {
		return whName;
	}

	public void setWhName(String whName) {
		this.whName = whName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getComplaintType() {
		return complaintType;
	}

	public void setComplaintType(String complaintType) {
		this.complaintType = complaintType;
	}

	public Integer getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(Integer handleStatus) {
		this.handleStatus = handleStatus;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrderSnMain() {
		return orderSnMain;
	}

	public void setOrderSnMain(String orderSnMain) {
		this.orderSnMain = orderSnMain;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
