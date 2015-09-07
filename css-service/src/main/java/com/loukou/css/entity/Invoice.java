package com.loukou.css.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tcz_invoice")
public class Invoice {
	@Id
	@GeneratedValue
	@Column(name = "invoice_id")
	private int invoiceId;
	
	@Column(name = "order_sn_main")
	private String orderSnMain = "";
	
	@Column(name = "tax")
	private double tax = 0;
	
	@Column(name = "order_amount")
	private double orderAmount = 0;
	
	@Column(name = "invoice_date")
	private Date invoiceDate = new Date();
	
	@Column(name = "mt_flg")
	private int mtFlg = 0;
	
	@Column(name = "valid")
	private int valid = 1;
	
	@Column(name = "invoice_no")
	private String invoiceNo = "";
	
	@Column(name = "freight")
	private int freight = 0;
	
	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getOrderSnMain() {
		return orderSnMain;
	}

	public void setOrderSnMain(String orderSnMain) {
		this.orderSnMain = orderSnMain;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public int getMtFlg() {
		return mtFlg;
	}

	public void setMtFlg(int mtFlg) {
		this.mtFlg = mtFlg;
	}

	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public int getFreight() {
		return freight;
	}

	public void setFreight(int freight) {
		this.freight = freight;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	@Column(name = "add_date")
	private Date addDate = new Date();
	
	@Column(name = "taxamount")
	private double taxAmount = 0;
}