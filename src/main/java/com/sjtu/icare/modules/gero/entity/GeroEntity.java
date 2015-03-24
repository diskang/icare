/**
 * @Package com.sjtu.icare.modules.gero.entity
 * @Description TODO
 * @date Mar 24, 2015 5:16:33 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.gero.entity;

import java.io.Serializable;

import com.sjtu.icare.common.persistence.DataEntity;

public class GeroEntity extends DataEntity<GeroEntity> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String city;
	private String district;
	private String address;
	private String contact;
	private String licence;
	private Integer scale;
	private Integer careLevel;
	private Integer contactId;
	private String logoUrl;
	private String photoUrl;
	private String registerDate;
	private String cancelDate;
	
	/* (non-Javadoc)
	 * @see com.sjtu.icare.common.persistence.BaseEntity#preDelete()
	 */
	@Override
	public void preDelete() {
		// TODO Auto-generated method stub
		
	}



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
	 * @return the city
	 */
	public String getCity() {
		return city;
	}



	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}



	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}



	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}



	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}



	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}



	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}



	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}



	/**
	 * @return the licence
	 */
	public String getLicence() {
		return licence;
	}



	/**
	 * @param licence the licence to set
	 */
	public void setLicence(String licence) {
		this.licence = licence;
	}



	/**
	 * @return the scale
	 */
	public Integer getScale() {
		return scale;
	}



	/**
	 * @param scale the scale to set
	 */
	public void setScale(Integer scale) {
		this.scale = scale;
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
	 * @return the contactId
	 */
	public Integer getContactId() {
		return contactId;
	}



	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}



	/**
	 * @return the logoUrl
	 */
	public String getLogoUrl() {
		return logoUrl;
	}



	/**
	 * @param logoUrl the logoUrl to set
	 */
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
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
	 * @return the registerDate
	 */
	public String getRegisterDate() {
		return registerDate;
	}



	/**
	 * @param registerDate the registerDate to set
	 */
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}



	/**
	 * @return the cancelDate
	 */
	public String getCancelDate() {
		return cancelDate;
	}



	/**
	 * @param cancelDate the cancelDate to set
	 */
	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}



	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
