/**
 * @Package com.sjtu.icare.modules.gero.entity
 * @Description TODO
 * @date Mar 9, 2015 5:09:21 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.gero.entity;

import java.io.Serializable;

public class GeroAreaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer parentId;
	private String parentIds;
	private Integer geroId;
	private Integer type;
	private Integer level;
	private String name;
	private String delFlag;
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
	 * @return the parentId
	 */
	public Integer getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return the parentIds
	 */
	public String getParentIds() {
		return parentIds;
	}
	/**
	 * @param parentIds the parentIds to set
	 */
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
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
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
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



}
