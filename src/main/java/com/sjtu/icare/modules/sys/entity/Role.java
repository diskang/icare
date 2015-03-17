package com.sjtu.icare.modules.sys.entity;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.Lists;
import com.sjtu.icare.common.persistence.DataEntity;
import com.sjtu.icare.modules.sys.entity.Privilege;
import com.sjtu.icare.modules.sys.entity.User;

/**
 * 角色Entity
 * @author jty
 * @version 2015-03-03
 */
public class Role extends DataEntity<Role> {
	
	private static final long serialVersionUID = 1L;
	
	private int geroId;		//gero id
	private String name;	//角色名称
	private String notes;	//角色备注
	
	private User user;		// 根据用户ID查询角色列表
	
	private List<Integer> userIdList = Lists.newArrayList(); //权限用户列表

	private List<Privilege> privilegeList = Lists.newArrayList(); // 拥有权限列表
	
	public Role() {
		super();
	}
	
	public Role(int id){
		super(id);
	}
	
	public Role(User user) {
		this();
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Privilege> getPrivilegeList() {
		return privilegeList;
	}

	public void setPrivilegeList(List<Privilege> privilegeList) {
		this.privilegeList = privilegeList;
	}

	public List<Integer> getPrivilegeIdList() {
		List<Integer> privilegeIdList = Lists.newArrayList();
		for (Privilege privilege : privilegeList) {
			privilegeIdList.add(privilege.getId());
		}
		return privilegeIdList;
	}

	public void setPrivilegeIdList(List<Integer> privilegeIdList) {
		privilegeList = Lists.newArrayList();
		for (int privilegeId : privilegeIdList) {
			Privilege privilege = new Privilege();
			privilege.setId(privilegeId);
			privilegeList.add(privilege);
		}
	}

	public String getPrivilegeIds() {
		return StringUtils.join(getPrivilegeIdList(), ",");
	}
	
	public void setPrivilegeIds(String privilegeIds) {
		privilegeList = Lists.newArrayList();
		if (privilegeIds != null){
			String[] idsStrings = StringUtils.split(privilegeIds, ",");
			List<Integer> ids = Lists.newArrayList();
			for (String id : idsStrings){
				ids.add(Integer.parseInt(id));				
			}
			setPrivilegeIdList(ids);
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public int getGeroId() {
		return geroId;
	}

	public void setGeroId(int geroId) {
		this.geroId = geroId;
	}

	public List<Integer> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<Integer> userIdList) {
		this.userIdList = userIdList;
	}	
	
}
