/**
 * @Package com.sjtu.icare.modules.elder.entity
 * @Description blood pressure entity
 * @date Mar 8, 2015 7:17:35 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.elder.entity;

import java.io.Serializable;

import com.sjtu.icare.modules.staff.entity.StaffEntity;

public class ElderBloodPressureEntity implements Serializable {
    private static final long serialVersionUID = 1L;
	
    private int id;
    private int elderId;
    private int doctorId;
    private double diastolicPressure;
    private double systolicPressure;
    private String time;
 
    private StaffEntity doctorEntity;

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
	 * @return the elderId
	 */
	public int getElderId() {
		return elderId;
	}

	/**
	 * @param elderId the elderId to set
	 */
	public void setElderId(int elderId) {
		this.elderId = elderId;
	}

	/**
	 * @return the doctorId
	 */
	public int getDoctorId() {
		return doctorId;
	}

	/**
	 * @param doctorId the doctorId to set
	 */
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	/**
	 * @return the diastolicPressure
	 */
	public double getDiastolicPressure() {
		return diastolicPressure;
	}

	/**
	 * @param diastolicPressure the diastolicPressure to set
	 */
	public void setDiastolicPressure(double diastolicPressure) {
		this.diastolicPressure = diastolicPressure;
	}

	/**
	 * @return the systolicPressure
	 */
	public double getSystolicPressure() {
		return systolicPressure;
	}

	/**
	 * @param systolicPressure the systolicPressure to set
	 */
	public void setSystolicPressure(double systolicPressure) {
		this.systolicPressure = systolicPressure;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the doctorEntity
	 */
	public StaffEntity getDoctorEntity() {
		return (StaffEntity) doctorEntity.clone();
	}

	/**
	 * @param doctorEntity the doctorEntity to set
	 */
	public void setDoctorEntity(StaffEntity doctorEntity) {
		this.doctorEntity = doctorEntity;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
