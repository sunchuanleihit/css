package com.loukou.css.resp;

public class AchievementRespDto {
	private String name;
	private int cancelOrderNum = 0;//作废单数
	private int returnGoodsNum = 0;//退货单数
	private int returnMoneyNum = 0;//退款单数
	private int complaintNum = 0;//投诉统计数
	private int changeOrderNum = 0;//补发券次
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCancelOrderNum() {
		return cancelOrderNum;
	}
	public void setCancelOrderNum(int cancelOrderNum) {
		this.cancelOrderNum = cancelOrderNum;
	}
	public int getReturnGoodsNum() {
		return returnGoodsNum;
	}
	public void setReturnGoodsNum(int returnGoodsNum) {
		this.returnGoodsNum = returnGoodsNum;
	}
	public int getReturnMoneyNum() {
		return returnMoneyNum;
	}
	public void setReturnMoneyNum(int returnMoneyNum) {
		this.returnMoneyNum = returnMoneyNum;
	}
	public int getComplaintNum() {
		return complaintNum;
	}
	public void setComplaintNum(int complaintNum) {
		this.complaintNum = complaintNum;
	}
	public int getChangeOrderNum() {
		return changeOrderNum;
	}
	public void setChangeOrderNum(int changeOrderNum) {
		this.changeOrderNum = changeOrderNum;
	}

}
