package com.sjtu.icare.modules.staff.entity;


import java.io.Serializable;

import com.sjtu.icare.common.utils.StringUtils;

/**
 * @Description Staff Entity
 * @author WangQi
 * @date 2015-03-06
 */


public class StaffEntity implements Serializable {
  
    private static final long serialVersionUID = 1L;
	
    private Integer id;
    private String name;
    private String nssfId;
    private Integer geroId;
    private String basicUrl;
    private String leaveDate;
    private String archiveId;
    


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
	 * @return the nssfId
	 */
	public String getNssfId() {
		return nssfId;
	}



	/**
	 * @param nssfId the nssfId to set
	 */
	public void setNssfId(String nssfId) {
		this.nssfId = nssfId;
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
	 * @return the basicUrl
	 */
	public String getBasicUrl() {
		return basicUrl;
	}



	/**
	 * @param basicUrl the basicUrl to set
	 */
	public void setBasicUrl(String basicUrl) {
		this.basicUrl = basicUrl;
	}



	/**
	 * @return the leaveDate
	 */
	public String getLeaveDate() {
		return leaveDate;
	}



	/**
	 * @param leaveDate the leaveDate to set
	 */
	public void setLeaveDate(String leaveDate) {
		if (StringUtils.isBlank(leaveDate))
			this.leaveDate = null;
		else
			this.leaveDate = leaveDate;
	}



	/**
	 * @return the archiveId
	 */
	public String getArchiveId() {
		return archiveId;
	}



	/**
	 * @param archiveId the archiveId to set
	 */
	public void setArchiveId(String archiveId) {
		this.archiveId = archiveId;
	}



	public Object clone() {
		try {
			StaffEntity cloned = (StaffEntity) super.clone();
			return cloned;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	
}