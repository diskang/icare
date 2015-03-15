package com.sjtu.icare.modules.op.entity;

import java.io.Serializable;
import java.sql.Date;

public class AreaworkRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int carerId;		//护工ID
	private int areaId;			//区域ID
	private int areaItemId;		//区域护理项目ID
	private String itemName;	//项目名
	private String finishTime;	//完成时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCarerId() {
		return carerId;
	}
	public void setCarerId(int carerId) {
		this.carerId = carerId;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public int getAreaItemId() {
		return areaItemId;
	}
	public void setAreaItemId(int areaItemId) {
		this.areaItemId = areaItemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
}
