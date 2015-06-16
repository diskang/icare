/**
 * @Package com.sjtu.icare.modules.elder.entity
 * @Description blood pressure entity
 * @date Mar 8, 2015 7:17:35 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.elder.entity;

import java.io.Serializable;

/**
 * @description 老人血压实体类
 */
public class ElderBloodPressureEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer elderId;
    private Integer doctorId;
    private Double diastolicPressure;
    private Double systolicPressure;
    private String time;
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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
	 * 获取老人ID
	 * @return the elderId
	 */
	public Integer getElderId() {
		return elderId;
	}
	/**
	 * 设置老人ID
	 * @param elderId the elderId to set
	 */
	public void setElderId(Integer elderId) {
		this.elderId = elderId;
	}
	/**
	 * 获取医生ID
	 * @return the doctorId
	 */
	public Integer getDoctorId() {
		return doctorId;
	}
	/**
	 * 设置医生ID
	 * @param doctorId the doctorId to set
	 */
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	/**
	 * 获取舒张压
	 * @return the diastolicPressure
	 */
	public Double getDiastolicPressure() {
		return diastolicPressure;
	}
	/**
	 * 设置舒张压
	 * @param diastolicPressure the diastolicPressure to set
	 */
	public void setDiastolicPressure(Double diastolicPressure) {
		this.diastolicPressure = diastolicPressure;
	}
	/**
	 * 获取收缩压
	 * @return the systolicPressure
	 */
	public Double getSystolicPressure() {
		return systolicPressure;
	}
	/**
	 * 设置收缩压
	 * @param systolicPressure the systolicPressure to set
	 */
	public void setSystolicPressure(Double systolicPressure) {
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
 

}
