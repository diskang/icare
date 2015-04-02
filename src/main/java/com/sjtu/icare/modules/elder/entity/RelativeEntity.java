package com.sjtu.icare.modules.elder.entity;

import java.io.Serializable;

import com.sjtu.icare.common.utils.StringUtils;

public class RelativeEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	Integer id;
	Integer elderId;
	String name;
	Integer urgent;
	String relationship;
	String cancelDate;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getElderId() {
		return elderId;
	}
	public void setElderId(Integer elderId) {
		this.elderId = elderId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getUrgent() {
		return urgent;
	}
	public void setUrgent(Integer urgent) {
		this.urgent = urgent;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(String cancelDate) {
		if (StringUtils.isBlank(cancelDate))
			this.cancelDate = null;
		else
			this.cancelDate = cancelDate;
	}

}
