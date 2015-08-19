package com.loukou.css.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//配货订单
@Entity
@Table(name = "wh_sa_pickingorder")
public class PickingOrderEntity {
	// 配货订单id
	@Id
	@GeneratedValue
	@Column(name = "order_id")
	private int orderId;

	// 配货订单号
	@Column(name = "order_code")
	private String orderCode;

	// 出库单id
	@Column(name = "stockoutorder_id")
	private int stockoutorderId;

	// 订单类型，0手工铺货，1自动补货，2微仓叫货
	@Column(name = "order_type")
	private int orderType;

	// 外部单号
	@Column(name = "out_ordercode")
	private String outOrderCode;

	// 订单状态，0已审核，1已通知仓库，2已发货，3已关闭
	@Column(name = "order_state")
	private int orderState;

	// 微仓id
	@Column(name = "miniwarehouse_id")
	private int miniwarehouseId;

	// 仓库id
	@Column(name = "warehouse_id")
	private int warehouseId;

	// 期望到达时间
	@Column(name = "expected_arrivetime")
	private Date expectedArriveTime;

	// 通知仓库用户id
	@Column(name = "notice_user_id")
	private int noticeUserId;

	// 通知时间
	@Column(name = "notice_time")
	private Date noticeTime;

	// 发货人id
	@Column(name = "delivery_user_id")
	private int deliveryUserId;

	// 发货时间
	@Column(name = "delivery_time")
	private Date deliveryTime;

	// 关闭人id
	@Column(name = "close_user_id")
	private int closeUserId;

	// 关闭时间
	@Column(name = "close_time")
	private Date closeTime;

	// 备注
	@Column(name = "remark")
	private String remark;

	// 创建时间
	@Column(name = "create_time")
	private Date createTime = new Date();

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

	public int getStockoutorderId() {
		return stockoutorderId;
	}

	public void setStockoutorderId(int stockoutorderId) {
		this.stockoutorderId = stockoutorderId;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public String getOutOrderCode() {
		return outOrderCode;
	}

	public void setOutOrderCode(String outOrderCode) {
		this.outOrderCode = outOrderCode;
	}

	public int getOrderState() {
		return orderState;
	}

	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}

	public int getMiniwarehouseId() {
		return miniwarehouseId;
	}

	public void setMiniwarehouseId(int miniwarehouseId) {
		this.miniwarehouseId = miniwarehouseId;
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Date getExpectedArriveTime() {
		return expectedArriveTime;
	}

	public void setExpectedArriveTime(Date expectedArriveTime) {
		this.expectedArriveTime = expectedArriveTime;
	}

	public int getNoticeUserId() {
		return noticeUserId;
	}

	public void setNoticeUserId(int noticeUserId) {
		this.noticeUserId = noticeUserId;
	}

	public Date getNoticeTime() {
		return noticeTime;
	}

	public void setNoticeTime(Date noticeTime) {
		this.noticeTime = noticeTime;
	}

	public int getDeliveryUserId() {
		return deliveryUserId;
	}

	public void setDeliveryUserId(int deliveryUserId) {
		this.deliveryUserId = deliveryUserId;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public int getCloseUserId() {
		return closeUserId;
	}

	public void setCloseUserId(int closeUserId) {
		this.closeUserId = closeUserId;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
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
	
	

}
