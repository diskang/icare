/**
 * @Package com.sjtu.icare.modules.gero.entity
 * @Description TODO
 * @date Mar 22, 2015 6:04:41 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.gero.entity;

import java.io.Serializable;

import com.sjtu.icare.common.persistence.DataEntity;

public class GeroElderExchangeEntity extends DataEntity<GeroElderExchangeEntity> implements Serializable  {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String participants;
	private String mediators;
	private String description;
	private String result;
	private Integer recorder;
	private String time;
	private Integer geroId;
	/**
	 * @return the id
	 */
	public int getId() {
		return super.getId();
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the participants
	 */
	public String getParticipants() {
		return participants;
	}
	/**
	 * @param participants the participants to set
	 */
	public void setParticipants(String participants) {
		this.participants = participants;
	}
	/**
	 * @return the mediators
	 */
	public String getMediators() {
		return mediators;
	}
	/**
	 * @param mediators the mediators to set
	 */
	public void setMediators(String mediators) {
		this.mediators = mediators;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * @return the recorder
	 */
	public Integer getRecorder() {
		return recorder;
	}
	/**
	 * @param recorder the recorder to set
	 */
	public void setRecorder(Integer recorder) {
		this.recorder = recorder;
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
	 * @return the geroId
	 */
	public Integer getGeroId() {
		return geroId;
	}
	/**
	 * @param geroId the geroId to set
	 */
	public void setGeroId(Integer geroId) {
		this.geroId = geroId;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/* (non-Javadoc)
	 * @see com.sjtu.icare.common.persistence.BaseEntity#preDelete()
	 */
	@Override
	public void preDelete() {
		// TODO Auto-generated method stub
		
	}
	
	
}
