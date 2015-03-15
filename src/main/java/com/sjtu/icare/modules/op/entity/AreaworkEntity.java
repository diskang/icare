package com.sjtu.icare.modules.op.entity;

import java.io.Serializable;
import java.sql.Date;

public class AreaworkEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int carerId;		//护工ID
	private int areaId;			//区域ID
	private String startDate;		//起始时间
	private String endDate;		//终止时间
	
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
