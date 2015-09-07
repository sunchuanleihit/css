package com.loukou.css.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tcz_invoice_action")
public class InvoiceAction {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "order_sn_main")
	private String orderSnMain = "";
	
	@Column(name = "action_name")
	private String actionName = "";
	
	@Column(name = "admin_name")
	private String adminName = "";
	
	@Column(name = "create_date")
	private Date createDate = new Date();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderSnMain() {
		return orderSnMain;
	}

	public void setOrderSnMain(String orderSnMain) {
		this.orderSnMain = orderSnMain;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}