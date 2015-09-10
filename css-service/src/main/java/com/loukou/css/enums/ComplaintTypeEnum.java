package com.loukou.css.enums;

public enum ComplaintTypeEnum {
	NONE(-1,""),
	DELAY(1,"配送延迟"),
	FALSE(2,"虚假回单"),
	BROKEN(3,"商品破损"),
	MISSING(4,"配送缺发"),
	ATTITUDE(5,"态度问题"),
	NOTSAME(6,"实物不符"),
	QUALITY(7,"产品质量"),
	NOTENOUGH(8,"产品缺货"),
	PACKAGE(9,"包装问题"),
	PERIOD(10,"商品销期"),
	ORDER(11,"订单错误"),
	PAY(12,"支付错误"),
	ACCOUNT(13,"账户错误");
	private int id;
	private String name;
	
	public static ComplaintTypeEnum parseName(int id){
		for (ComplaintTypeEnum e : ComplaintTypeEnum.values()) {
			if (e.id == id) {
				return e;
			}
		}
		return NONE;
	}
	
	private ComplaintTypeEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
