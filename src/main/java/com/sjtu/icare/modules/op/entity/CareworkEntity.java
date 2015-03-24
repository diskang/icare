/**
 * @Package com.sjtu.icare.modules.op.entity
 * @Description TODO
 * @date Mar 16, 2015 3:21:07 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.op.entity;

import java.io.Serializable;

import com.sjtu.icare.common.persistence.DataEntity;

public class CareworkEntity extends DataEntity<CareworkEntity> implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
    private Integer id;
    private Integer carerId;
    private String elderIds;
    private String endDate;
    private Integer status;
    private Integer geroId;
    
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
	 * @return the elderIds
	 */
	public String getElderIds() {
		return elderIds;
	}
	/**
	 * @param elderIds the elderIds to set
	 */
	public void setElderIds(String elderIds) {
		this.elderIds = elderIds;
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
