package com.sjtu.icare.modules.sys.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sjtu.icare.common.persistence.DataEntity;

/**
 * 权限Entity
 * @author jty
 * @version 2015-03-03
 */
public class Privilege extends DataEntity<Privilege>{
	
	private static final long serialVersionUID = 1L;
	private Privilege parent;	// 父级菜单
	private String parentIds; // 所有父级编号
	private String name; 	// 名称
	private String href; 	// 链接
	private String icon; 	// 图标
	private String notes;	//权限备注
	private String permission; // 权限标识
	private String api;
	
	private int userId;
	
	private int roleId;
	
	public Privilege(){
		super();
	}
	
	public Privilege(int id){
		super(id);
	}
	
	@JsonBackReference
	@NotNull
	public Privilege getParent() {
		return parent;
	}

	public void setParent(Privilege parent) {
		this.parent = parent;
	}

	@Length(min=1, max=2000)
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=1, max=100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min=0, max=2000)
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Length(min=0, max=100)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Length(min=0, max=200)
	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public int getParentId() {
		return parent.getId();
	}

	@JsonIgnore
	public static void sortList(List<Privilege> list, List<Privilege> sourcelist, int parentId, boolean cascade){
		for (int i=0; i<sourcelist.size(); i++){
			Privilege e = sourcelist.get(i);
			if (e.getParent()!=null && e.getParent().getId() == parentId){
				list.add(e);
				if (cascade){
					// 判断是否还有子节点, 有则继续获取子节点
					for (int j=0; j<sourcelist.size(); j++){
						Privilege child = sourcelist.get(j);
						if (child.getParent()!=null && child.getParent().getId() == e.getId()){
							sortList(list, sourcelist, e.getId(), true);
							break;
						}
					}
				}
			}
		}
	}

	@JsonIgnore
	public static String getRootId(){
		return "0";
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return name;
	}
	
	/**
	 * 插入之前执行方法
	 */
	public void preInsert() {
	}
	
	/**
	 * 更新之前执行方法
	 */
	public void preUpdate() {
	}
	
	/**
	 * 删除之前执行方法
	 */
	public void preDelete() {
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}
	
	
}