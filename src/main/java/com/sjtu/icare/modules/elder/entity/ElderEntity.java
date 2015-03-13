package com.sjtu.icare.modules.elder.entity;


import java.io.Serializable;
import java.sql.Date;

import com.sjtu.icare.modules.sys.entity.User;

/**
 * @Description 老人数据 Entity
 * @author WangQi
 * @date 2015-03-05
 */


public class ElderEntity implements Serializable {
  
    private static final long serialVersionUID = 1L;
	
    private int id;
    private String name;
    private int geroId;
    private String nssfId;
    private String archiveId;
    private int areaId;
    private int careLevel;
    private Date checkinDate;
    private Date checkoutDate;
    private String applyUrl;
    private String surveyUrl;
    private String assessUrl;
	private String trackUrl;
    private String padMac;
   
    
    /**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
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
	 * @return the geroId
	 */
	public int getGeroId() {
		return geroId;
	}
	/**
	 * @param geroId the geroId to set
	 */
	public void setGeroId(int geroId) {
		this.geroId = geroId;
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
	/**
	 * @return the roomId
	 */
	public int getAreaId() {
		return areaId;
	}
	/**
	 * @param roomId the roomId to set
	 */
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	/**
	 * @return the careLevel
	 */
	public int getCareLevel() {
		return careLevel;
	}
	/**
	 * @param careLevel the careLevel to set
	 */
	public void setCareLevel(int careLevel) {
		this.careLevel = careLevel;
	}
	/**
	 * @return the checkinDate
	 */
	public Date getCheckinDate() {
		if (checkinDate != null)
			return (Date)checkinDate.clone();
		else
			return null;
	}
	/**
	 * @param checkinDate the checkinDate to set
	 */
	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}
	/**
	 * @return the checkoutDate
	 */
	public Date getCheckoutDate() {
		if (checkoutDate != null)
			return (Date)checkoutDate.clone();
		else
			return null;
	}
	/**
	 * @param checkoutDate the checkoutDate to set
	 */
	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	/**
	 * @return the apply_url
	 */
	public String getApplyUrl() {
		return applyUrl;
	}
	/**
	 * @param apply_url the apply_url to set
	 */
	public void setApplyUrl(String apply_url) {
		this.applyUrl = apply_url;
	}
	/**
	 * @return the surveyUrl
	 */
	public String getSurveyUrl() {
		return surveyUrl;
	}
	/**
	 * @param surveyUrl the surveyUrl to set
	 */
	public void setSurveyUrl(String surveyUrl) {
		this.surveyUrl = surveyUrl;
	}
	/**
	 * @return the assessUrl
	 */
	public String getAssessUrl() {
		return assessUrl;
	}
	/**
	 * @param assessUrl the assessUrl to set
	 */
	public void setAssessUrl(String assessUrl) {
		this.assessUrl = assessUrl;
	}
	/**
	 * @return the trackUrl
	 */
	public String getTrackUrl() {
		return trackUrl;
	}
	/**
	 * @param trackUrl the trackUrl to set
	 */
	public void setTrackUrl(String trackUrl) {
		this.trackUrl = trackUrl;
	}
	/**
	 * @return the padMac
	 */
	public String getPadMac() {
		return padMac;
	}
	/**
	 * @param padMac the padMac to set
	 */
	public void setPadMac(String padMac) {
		this.padMac = padMac;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}