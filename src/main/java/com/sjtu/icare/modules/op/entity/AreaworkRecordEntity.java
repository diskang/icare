package com.sjtu.icare.modules.op.entity;

import java.io.Serializable;

public class AreaworkRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer carerId;		//护工ID
	private Integer areaId;			//区域ID
	private Integer areaItemId;		//区域护理项目ID
	private String itemName;	//项目名
	private String finishTime;	//完成时间
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
	
}
