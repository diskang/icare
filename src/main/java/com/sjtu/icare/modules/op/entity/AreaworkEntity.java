package com.sjtu.icare.modules.op.entity;

import java.io.Serializable;
import java.sql.Date;

import com.sjtu.icare.common.persistence.DataEntity;

public class AreaworkEntity extends DataEntity<AreaworkEntity> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer carerId;
	private String areaIds;
	private Integer geroId;
	private String endDate;
	private Integer status;
	
	private String reqStartDate;
	private String reqEndDate;
	
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
	 * @return the areaIds
	 */
	public String getAreaIds() {
		return areaIds;
	}
	/**
	 * @param areaIds the areaIds to set
	 */
	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}
	/**
	 * @return the geroId
	 */
	public Integer getGeroId() {
		return geroId;
	}
	/**
	 * @param geroId the geroId to set
	 */
	public void setGeroId(Integer geroId) {
		this.geroId = geroId;
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
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/* (non-Javadoc)
	 * @see com.sjtu.icare.common.persistence.BaseEntity#preDelete()
	 */
	@Override
	public void preDelete() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * @return the reqStartDate
	 */
	public String getReqStartDate() {
		return reqStartDate;
	}
	/**
	 * @param reqStartDate the reqStartDate to set
	 */
	public void setReqStartDate(String reqStartDate) {
		this.reqStartDate = reqStartDate;
	}
	/**
	 * @return the reqEndDate
	 */
	public String getReqEndDate() {
		return reqEndDate;
	}
	/**
	 * @param reqEndDate the reqEndDate to set
	 */
	public void setReqEndDate(String reqEndDate) {
		this.reqEndDate = reqEndDate;
	}
	

	
	
}
