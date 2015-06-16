package com.sjtu.icare.modules.elder.entity;


import java.io.Serializable;

/**
 * 老人心率实体类
 * @author lzl
 * @date 2015-03-08
 */
public class ElderHeartRateEntity implements Serializable {
  
	private static final long serialVersionUID = 1L;
	
    private Integer id;         //心率记录ID
    private Integer elderId;    //老人ID
    private Integer doctorId;   //医生ID
    private Double rate;     //心率
    private String time;      //测量时间
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
	 * 老人ID
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
	 * 医生ID
	 * @return the doctorId
	 */
	public Integer getDoctorId() {
		return doctorId;
	}
	/**
	 * @param doctorId the doctorId to set
	 */
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	/**
	 * 心率
	 * @return the rate
	 */
	public Double getRate() {
		return rate;
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}
	/**
	 * 录入时间
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