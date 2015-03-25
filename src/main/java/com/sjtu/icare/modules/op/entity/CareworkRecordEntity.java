package com.sjtu.icare.modules.op.entity;

import java.io.Serializable;

import com.sjtu.icare.common.persistence.DataEntity;

public class CareworkRecordEntity extends DataEntity<CareworkRecordEntity> implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer carerId;		//护工ID
	private Integer elderId;		//老人ID
	private Integer elderItemId;	//老人护理项目ID
	private String itemName;	//项目名
	private String finishTime;	//完成时间
	private String elderName;	//完成时间
	private String carerName;	//完成时间
	
	// request params
	private String startDate;
	private String endDate;
	private Integer staffType;
	private Integer elderType;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return super.getId();
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
	/* (non-Javadoc)
	 * @see com.sjtu.icare.common.persistence.BaseEntity#preDelete()
	 */
	@Override
	public void preDelete() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the staffType
	 */
	public Integer getStaffType() {
		return staffType;
	}
	/**
	 * @param staffType the staffType to set
	 */
	public void setStaffType(Integer staffType) {
		this.staffType = staffType;
	}
	/**
	 * @return the elderType
	 */
	public Integer getElderType() {
		return elderType;
	}
	/**
	 * @param elderType the elderType to set
	 */
	public void setElderType(Integer elderType) {
		this.elderType = elderType;
	}
	

}
