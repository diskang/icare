/**
 * @Package com.sjtu.icare.modules.staff.entity
 * @Description TODO
 * @date Mar 13, 2015 10:32:58 AM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.staff.entity;

import java.io.Serializable;

public class StaffSchedulePlanEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int staffId;
	private int geroId;
	private String workDate;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the staffId
	 */
	public int getStaffId() {
		return staffId;
	}
	/**
	 * @param staffId the staffId to set
	 */
	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}
	/**
	 * @return the geroId
	 */
	public int getGeroId() {
		return geroId;
	}
	/**
	 * @param geroId the geroId to set
	 */
	public void setGeroId(int geroId) {
		this.geroId = geroId;
	}
	/**
	 * @return the workDate
	 */
	public String getWorkDate() {
		return workDate;
	}
	/**
	 * @param workDate the workDate to set
	 */
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	
	
	
}
