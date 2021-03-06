package com.sjtu.icare.modules.elder.entity;


import java.io.Serializable;

/**
 * @Description 老人数据 Entity
 * @author WangQi
 * @date 2015-03-05
 */


public class ElderEntity implements Serializable {
  
	private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private Integer geroId;
    private String nssfId;
    private String archiveId;
    private Integer areaId;
    private Integer careLevel;
    private String checkinDate;
    private String checkoutDate;
    private String applyUrl;
    private String surveyUrl;
    private String assessUrl;
	private String trackUrl;
    private String padMac;
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
	 * @return the areaId
	 */
	public Integer getAreaId() {
		return areaId;
	}
	/**
	 * @param areaId the areaId to set
	 */
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	/**
	 * @return the careLevel
	 */
	public Integer getCareLevel() {
		return careLevel;
	}
	/**
	 * @param careLevel the careLevel to set
	 */
	public void setCareLevel(Integer careLevel) {
		this.careLevel = careLevel;
	}
	/**
	 * @return the checkinDate
	 */
	public String getCheckinDate() {
		return checkinDate;
	}
	/**
	 * @param checkinDate the checkinDate to set
	 */
	public void setCheckinDate(String checkinDate) {
		this.checkinDate = checkinDate;
	}
	/**
	 * @return the checkoutDate
	 */
	public String getCheckoutDate() {
		return checkoutDate;
	}
	/**
	 * @param checkoutDate the checkoutDate to set
	 */
	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	/**
	 * @return the applyUrl
	 */
	public String getApplyUrl() {
		return applyUrl;
	}
	/**
	 * @param applyUrl the applyUrl to set
	 */
	public void setApplyUrl(String applyUrl) {
		this.applyUrl = applyUrl;
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
   
  

}