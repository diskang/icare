package com.sjtu.icare.modules.elder.entity;


import java.io.Serializable;

public class ElderRelativeRelationshipEntity implements Serializable {
  
	private static final long serialVersionUID = 1L;

    private Integer elderUserId;
    private Integer relativeUserId;
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getElderUserId() {
		return elderUserId;
	}
	public void setElderUserId(Integer elderUserId) {
		this.elderUserId = elderUserId;
	}
	public Integer getRelativeUserId() {
		return relativeUserId;
	}
	public void setRelativeUserId(Integer relativeUserId) {
		this.relativeUserId = relativeUserId;
	}

	
   
  

}