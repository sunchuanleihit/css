package com.loukou.css.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tcz_invoice_goods")
public class InvoiceGoods {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "invoice_id")
	private int invoiceId;
	
	@Column(name = "goods_id")
	private int goodsId;
	
	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	
	@Column(name = "goods_name")
	private String goodsName = "";
	
	@Column(name = "specification")
	private String specification = "";
	
	@Column(name = "unit")
	private String unit = "";
	
	@Column(name = "num")
	private int num = 0;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getGoods_amount() {
		return goods_amount;
	}

	public void setGoods_amount(double goods_amount) {
		this.goods_amount = goods_amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Column(name = "goods_amount")
	private double goods_amount = 0;
	
	@Column(name = "price")
	private double price = 0;
}