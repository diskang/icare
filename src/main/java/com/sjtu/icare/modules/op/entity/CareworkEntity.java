package com.sjtu.icare.modules.op.entity;

import java.io.Serializable;
import java.sql.Date;

public class CareworkEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int carerId;		//护工ID
	private int elderId;		//老人ID
	private Date startDate;		//起始时间
	private Date endDate;		//终止时间
	
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
	public int getElderId() {
		return elderId;
	}
	public void setElderId(int elderId) {
		this.elderId = elderId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
