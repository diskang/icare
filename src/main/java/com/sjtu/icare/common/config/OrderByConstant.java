package com.sjtu.icare.common.config;

public enum OrderByConstant {
	ID("id"),
	NAME("name"),
	REGISTER_DATE("register_date"),
	USERNAME("username")
	
	;
	private final String tag;
	public String getTag() {
		return tag;
	}
    //构造器默认也只能是private, 从而保证构造函数只能在内部使用 
	private OrderByConstant(String tag) {
    	this.tag = tag;
    }
}
