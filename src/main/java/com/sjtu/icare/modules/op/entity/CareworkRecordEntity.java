package com.sjtu.icare.modules.op.entity;

import java.io.Serializable;

public class CareworkRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer carerId;		//护工ID
	private Integer elderId;		//老人ID
	private Integer elderItemId;	//老人护理项目ID
	private String itemName;	//项目名
	private String finishTime;	//完成时间
	private String elderName;	//完成时间
	private String carerName;	//完成时间
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the carerId
	 */
	public Integer getCarerId() {
		return carerId;
	}
	/**
	 * @param carerId the carerId to set
	 */
	public void setCarerId(Integer carerId) {
		this.carerId = carerId;
	}
	/**
	 * @return the elderId
	 */
	public Integer getElderId() {
		return elderId;
	}
	/**
	 * @param elderId the elderId to set
	 */
	public void setElderId(Integer elderId) {
		this.elderId = elderId;
	}
	/**
	 * @return the elderItemId
	 */
	public Integer getElderItemId() {
		return elderItemId;
	}
	/**
	 * @param elderItemId the elderItemId to set
	 */
	public void setElderItemId(Integer elderItemId) {
		this.elderItemId = elderItemId;
	}
	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**
	 * @return the finishTime
	 */
	public String getFinishTime() {
		return finishTime;
	}
	/**
	 * @param finishTime the finishTime to set
	 */
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the elderName
	 */
	public String getElderName() {
		return elderName;
	}
	/**
	 * @param elderName the elderName to set
	 */
	public void setElderName(String elderName) {
		this.elderName = elderName;
	}
	/**
	 * @return the staffName
	 */
	public String getCarerName() {
		return carerName;
	}
	/**
	 * @param staffName the staffName to set
	 */
	public void setCarerName(String carerName) {
		this.carerName = carerName;
	}
	

}
