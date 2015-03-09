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
    private double temperature;
    private String time;
 
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
    
    public double getTemperature() {
    	return temperature;
    }
    
    public void setTemperature(double temperature) {
    	this.temperature = temperature;
    }
    
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public StaffEntity getDoctorEntity() {
		return (StaffEntity) doctorEntity.clone();
	}

	public void setDoctorEntity(StaffEntity doctorEntity) {
		this.doctorEntity = doctorEntity;
	}



}