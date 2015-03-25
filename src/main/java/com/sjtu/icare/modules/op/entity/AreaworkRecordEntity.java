package com.sjtu.icare.modules.op.entity;

import java.io.Serializable;

import com.sjtu.icare.common.persistence.DataEntity;

public class AreaworkRecordEntity extends DataEntity<AreaworkRecordEntity> implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer carerId;		//护工ID
	private Integer areaId;			//区域ID
	private Integer areaItemId;		//区域护理项目ID
	private String itemName;	//项目名
	private String finishTime;	//完成时间
	private String carerName;	//完成时间
	private String areaFullName;	//完成时间
	
	// request params
	private String startDate;
	private String endDate;
	private Integer staffType;
	
	
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
	 * @return the areaId
	 */
	public Integer getAreaId() {
		return areaId;
	}
	/**
	 * @param areaId the areaId to set
	 */
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	/**
	 * @return the areaItemId
	 */
	public Integer getAreaItemId() {
		return areaItemId;
	}
	/**
	 * @param areaItemId the areaItemId to set
	 */
	public void setAreaItemId(Integer areaItemId) {
		this.areaItemId = areaItemId;
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
	 * @return the carerName
	 */
	public String getCarerName() {
		return carerName;
	}
	/**
	 * @param carerName the carerName to set
	 */
	public void setCarerName(String carerName) {
		this.carerName = carerName;
	}
	/**
	 * @return the areaFullName
	 */
	public String getAreaFullName() {
		return areaFullName;
	}
	/**
	 * @param areaFullName the areaFullName to set
	 */
	public void setAreaFullName(String areaFullName) {
		this.areaFullName = areaFullName;
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
	
}
