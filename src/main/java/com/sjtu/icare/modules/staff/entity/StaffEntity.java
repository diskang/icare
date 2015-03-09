package com.sjtu.icare.modules.staff.entity;


import java.io.Serializable;
import java.sql.Date;

/**
 * @Description Staff Entity
 * @author WangQi
 * @date 2015-03-06
 */


public class StaffEntity implements Serializable, Cloneable {
  
    private static final long serialVersionUID = 1L;
	
    private int id;
    private String name;
    private String phone;
    private String email;
    private String identityNo;
    private String nssfId;
    private int geroId;
    private Date birthday;
    private String gender;
    private String basicUrl;
    private String residenceAddress;
    private String householdAddress;
    private Date leaveDate;
    private String archiveId;
    private String photoUrl;
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
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the identityNo
	 */
	public String getIdentityNo() {
		return identityNo;
	}
	/**
	 * @param idno the idno to set
	 */
	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}
	/**
	 * @return the nssfId
	 */
	public String getNssfId() {
		return nssfId;
	}
	/**
	 * @param nssf the nssf to set
	 */
	public void setNssfId(String nssfId) {
		this.nssfId = nssfId;
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
	 * @return the birthday
	 */
	public Date getBirthday() {
		if (birthday != null)
			return birthday;
		else
			return null;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
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
	 * @return the residenceAddress
	 */
	public String getResidenceAddress() {
		return residenceAddress;
	}
	/**
	 * @param residenceAddress the residenceAddress to set
	 */
	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}
	/**
	 * @return the householdAddress
	 */
	public String getHouseholdAddress() {
		return householdAddress;
	}
	/**
	 * @param householdAddress the householdAddress to set
	 */
	public void setHouseholdAddress(String householdAddress) {
		this.householdAddress = householdAddress;
	}
	/**
	 * @return the leaveDate
	 */
	public Date getLeaveDate() {
		if (leaveDate != null)
			return leaveDate;
		else
			return null;
	}
	/**
	 * @param leaveDate the leaveDate to set
	 */
	public void setLeaveDate(Date leaveDate) {
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
	/**
	 * @return the photoUrl
	 */
	public String getPhotoUrl() {
		return photoUrl;
	}
	/**
	 * @param photoUrl the photoUrl to set
	 */
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Object clone() {
		try {
			StaffEntity cloned = (StaffEntity) super.clone();
			
			if (birthday != null)
				cloned.birthday = (Date) birthday.clone();
			if (leaveDate != null)
				cloned.leaveDate = (Date) leaveDate.clone();
			
			return cloned;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
    
    
}