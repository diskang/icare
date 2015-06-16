/**
 * @Package com.sjtu.icare.modules.elder.entity
 * @Description TODO
 * @date Mar 20, 2015 3:43:19 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.elder.entity;

/**
 * 老人项目实体类
 */
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
	 * 护理项目ID
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
	 * 护理项目名称
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
	 * 老人项目图标
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
	 * 护理等级
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
	 * 老人项目周期
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
	 * 老人项目起始时间
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
	 * 老人项目结束时间
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
	 * 逻辑删除开关
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
