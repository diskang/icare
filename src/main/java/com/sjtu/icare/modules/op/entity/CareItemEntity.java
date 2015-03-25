/**
 * @Package com.sjtu.icare.modules.gero.entity
 * @Description TODO
 * @date Mar 9, 2015 5:09:21 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.op.entity;

import java.io.Serializable;

import com.sjtu.icare.common.persistence.DataEntity;

public class CareItemEntity extends DataEntity<CareItemEntity> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer geroId;
	private String name;
	private String icon;
	private Integer level;			//护理等级
	private Integer period;			//周期（几天为一轮）
	private Integer frequency;		//频率（一天做几次）
	private String notes;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the frequency
	 */
	public Integer getFrequency() {
		return frequency;
	}
	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
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
