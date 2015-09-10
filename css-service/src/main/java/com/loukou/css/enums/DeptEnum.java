package com.loukou.css.enums;

public enum DeptEnum {
	NONE(-1,""),
	WEIC(1,"微仓"),
	BUY(2,"采购"),
	STORE(3,"仓库"),
	SYS(4,"系统");
	private DeptEnum(int id, String name){
		this.id = id;
		this.name = name;
	}
	public static DeptEnum parseName(int id){
		for (DeptEnum e : DeptEnum.values()) {
			if (e.id == id) {
				return e;
			}
		}
		return NONE;
	}
	private int id;
	private String name;
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
