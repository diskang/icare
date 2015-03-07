package com.sjtu.icare.modules.elder.entity;


import java.io.Serializable;
import java.sql.Date;

import com.sjtu.icare.modules.staff.entity.StaffEntity;

/**
 * @Description T_ELDER_TEMPERATURE 表对应的 Entity
 * @author WangQi
 * @date 2015-03-05
 */
public class ElderTemperatureEntity implements Serializable {
  
    private static final long serialVersionUID = 1L;
	
    private int id;
    private int elderId;
    private int doctorId;
    private float temperature;
    private Date time;
 
    private StaffEntity doctorEntity;
    
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
    
    public float getTemperature() {
    	return temperature;
    }
    
    public void setTemperature(float temperature) {
    	this.temperature = temperature;
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

	public StaffEntity getDoctorEntity() {
		return (StaffEntity) doctorEntity.clone();
	}

	public void setDoctorEntity(StaffEntity doctorEntity) {
		this.doctorEntity = doctorEntity;
	}

}