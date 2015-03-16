/**
 * @Package com.sjtu.icare.modules.op.entity
 * @Description TODO
 * @date Mar 16, 2015 3:21:07 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.op.entity;

import java.io.Serializable;

public class CareworkEntity implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
    private Integer id;
    private Integer carerId;
    private Integer elderId;
    private String startDate;
    private String endDate;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
    
}
