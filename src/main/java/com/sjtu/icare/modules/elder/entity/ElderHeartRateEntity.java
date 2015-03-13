package com.sjtu.icare.modules.elder.entity;


import java.io.Serializable;
import java.sql.Date;

import com.sjtu.icare.modules.staff.entity.StaffEntity;

/**
 * @Description T_ELDER_HEART_RATE 表对应的 Entity
 * @author lzl
 * @date 2015-03-08
 */
public class ElderHeartRateEntity implements Serializable {
  
    private static final long serialVersionUID = 1L;
	
    private int id;         //心率记录ID
    private int elderId;    //老人ID
    private int doctorId;   //医生ID
    private float rate;     //心率
    private Date time;      //测量时间
 
//    private StaffEntity doctorEntity;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getElderId() {
    	return elderId;
    }
    
    public void setElderId(int elderId) {
    	this.elderId = elderId;
    }
  
    public int getDoctorId() {
    	return doctorId;
    }

    public void setDoctorId(int doctorId) {
    	this.doctorId = doctorId;
    }
    
    public float getHeartRate() {
    	return rate;
    }
    
    public void setHeartRate(float rate) {
    	this.rate = rate;
    }
    
	public Date getTime() {
		if (time != null)
			return (Date) time.clone();
		else
			return null;
	}

	public void setTime(Date time) {
		this.time = time;
	}

//	public StaffEntity getDoctorEntity() {
//		return (StaffEntity) doctorEntity.clone();
//	}
//
//	public void setDoctorEntity(StaffEntity doctorEntity) {
//		this.doctorEntity = doctorEntity;
//	}

}