/**
 * @Package com.sjtu.icare.modules.gero.entity
 * @Description TODO
 * @date Mar 9, 2015 5:09:21 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.gero.entity;

import java.io.Serializable;

/**
 * 养老院区域
 * @author sean_7
 */
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
	private String fullName;
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
	 * 父区域ID
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
	 * 所有祖先包括父亲
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
	 * 养老院ID
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
	 * 区域类型
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
	 * 级别
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
	 * 区域名
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
	 * 所有祖先以及对应区域名
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


}
