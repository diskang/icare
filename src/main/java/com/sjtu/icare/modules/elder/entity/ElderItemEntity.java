/**
 * @Package com.sjtu.icare.modules.elder.entity
 * @Description TODO
 * @date Mar 20, 2015 3:43:19 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.elder.entity;

import java.io.Serializable;

import com.sjtu.icare.common.persistence.DataEntity;
import com.sjtu.icare.modules.sys.entity.User;

public class ElderItemEntity extends DataEntity<ElderItemEntity> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer elderId;
	private Integer careItemId;
	private String careItemName;
	private String icon;
	private Integer level;			//护理等级
	private Integer period;			//周期（几天为一轮）
	private String startTime;			//周期（几天为一轮）
	private String endTime;			//周期（几天为一轮）
	private String delFlag;		//默认为0，删除为1
	
	
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
	 * @return the careItemId
	 */
	public Integer getCareItemId() {
		return careItemId;
	}
	/**
	 * @param careItemId the careItemId to set
	 */
	public void setCareItemId(Integer careItemId) {
		this.careItemId = careItemId;
	}
	/**
	 * @return the careItemName
	 */
	public String getCareItemName() {
		return careItemName;
	}
	/**
	 * @param careItemName the careItemName to set
	 */
	public void setCareItemName(String careItemName) {
		this.careItemName = careItemName;
	}
	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * @return the level
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	/**
	 * @return the period
	 */
	public Integer getPeriod() {
		return period;
	}
	/**
	 * @param period the period to set
	 */
	public void setPeriod(Integer period) {
		this.period = period;
	}
	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the delFlag
	 */
	public String getDelFlag() {
		return delFlag;
	}
	/**
	 * @param delFlag the delFlag to set
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
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
