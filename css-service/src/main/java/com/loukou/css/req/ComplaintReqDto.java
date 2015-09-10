package com.loukou.css.req;

import java.io.Serializable;

public class ComplaintReqDto implements Serializable{
	private static final long serialVersionUID = 2632051005383958504L;
	private String orderSnMain;
	private String startTime;
	private String endTime;
	private Integer handleStatus;
	private Integer department;
	private Integer complaintType;
	private String cityCode;
	private Integer weic;
	
	public String getOrderSnMain() {
		return orderSnMain;
	}
	public void setOrderSnMain(String orderSnMain) {
		this.orderSnMain = orderSnMain;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getHandleStatus() {
		return handleStatus;
	}
	public void setHandleStatus(Integer handleStatus) {
		this.handleStatus = handleStatus;
	}
	public Integer getDepartment() {
		return department;
	}
	public void setDepartment(Integer department) {
		this.department = department;
	}
	public Integer getComplaintType() {
		return complaintType;
	}
	public void setComplaintType(Integer complaintType) {
		this.complaintType = complaintType;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public Integer getWeic() {
		return weic;
	}
	public void setWeic(Integer weic) {
		this.weic = weic;
	}

}
