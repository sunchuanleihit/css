package com.loukou.css.bo;

import java.util.Date;

public class PurchaseListBo {

	private int orderId;//订单Id
	private String orderCode;//订单号
	private int orderState;//状态
	private String orderStateName; //状态名
	private String purchaseName;//采购员
	private String supplierName;//供应商
	private String warehouseName;//收货仓
	private Date planArrivetime;//计划送达日期
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public int getOrderState() {
		return orderState;
	}
	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}
	public String getOrderStateName() {
		return orderStateName;
	}
	public void setOrderStateName(String orderStateName) {
		this.orderStateName = orderStateName;
	}
	public String getPurchaseName() {
		return purchaseName;
	}
	public void setPurchaseName(String purchaseName) {
		this.purchaseName = purchaseName;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public Date getPlanArrivetime() {
		return planArrivetime;
	}
	public void setPlanArrivetime(Date planArrivetime) {
		this.planArrivetime = planArrivetime;
	}
	
	
}
