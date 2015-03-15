package com.sjtu.icare.modules.sys.entity;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Lists;
import com.sjtu.icare.common.persistence.DataEntity;
import com.sjtu.icare.common.config.Global;
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
	
//	private String roleType;// 权限类型
//	private String dataScope;// 数据范围
	
//	private String oldName; 	// 原角色名称
//	private String oldEnname;	// 原英文名称
//	private String sysData; 		//是否是系统数据
//	private String useable; 		//是否是可用
	
	private User user;		// 根据用户ID查询角色列表

//	private List<User> userList = Lists.newArrayList(); // 拥有用户列表
	private List<Privilege> privilegeList = Lists.newArrayList(); // 拥有菜单列表
//	private List<Office> officeList = Lists.newArrayList(); // 按明细设置数据范围

	// 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在部门及以下数据；5：所在部门数据；8：仅本人数据；9：按明细设置）
//	public static final String DATA_SCOPE_ALL = "1";
//	public static final String DATA_SCOPE_COMPANY_AND_CHILD = "2";
//	public static final String DATA_SCOPE_COMPANY = "3";
//	public static final String DATA_SCOPE_OFFICE_AND_CHILD = "4";
//	public static final String DATA_SCOPE_OFFICE = "5";
//	public static final String DATA_SCOPE_SELF = "8";
//	public static final String DATA_SCOPE_CUSTOM = "9";
	
	public Role() {
		super();
//		this.dataScope = DATA_SCOPE_SELF;
//		this.useable=Global.YES;
	}
	
	public Role(int id){
		super(id);
	}
	
	public Role(User user) {
		this();
		this.user = user;
	}

//	public String getUseable() {
//		return useable;
//	}

//	public void setUseable(String useable) {
//		this.useable = useable;
//	}

//	public String getSysData() {
//		return sysData;
//	}

//	public void setSysData(String sysData) {
//		this.sysData = sysData;
//	}
//
//	public Office getOffice() {
//		return office;
//	}
//
//	public void setOffice(Office office) {
//		this.office = office;
//	}

//	@Length(min=1, max=100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	@Length(min=1, max=100)
//	public String getEnname() {
//		return enname;
//	}
//
//	public void setEnname(String enname) {
//		this.enname = enname;
//	}
//	
//	@Length(min=1, max=100)
//	public String getRoleType() {
//		return roleType;
//	}
//
//	public void setRoleType(String roleType) {
//		this.roleType = roleType;
//	}
//
//	public String getDataScope() {
//		return dataScope;
//	}
//
//	public void setDataScope(String dataScope) {
//		this.dataScope = dataScope;
//	}
//
//	public String getOldName() {
//		return oldName;
//	}
//
//	public void setOldName(String oldName) {
//		this.oldName = oldName;
//	}
//
//	public String getOldEnname() {
//		return oldEnname;
//	}
//
//	public void setOldEnname(String oldEnname) {
//		this.oldEnname = oldEnname;
//	}

//	public List<User> getUserList() {
//		return userList;
//	}
//
//	public void setUserList(List<User> userList) {
//		this.userList = userList;
//	}
//	
//	public List<String> getUserIdList() {
//		List<String> nameIdList = Lists.newArrayList();
//		for (User user : userList) {
//			nameIdList.add(user.getId());
//		}
//		return nameIdList;
//	}
//
//	public String getUserIds() {
//		return StringUtils.join(getUserIdList(), ",");
//	}

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
	
//	public List<Office> getOfficeList() {
//		return officeList;
//	}
//
//	public void setOfficeList(List<Office> officeList) {
//		this.officeList = officeList;
//	}
//
//	public List<String> getOfficeIdList() {
//		List<String> officeIdList = Lists.newArrayList();
//		for (Office office : officeList) {
//			officeIdList.add(office.getId());
//		}
//		return officeIdList;
//	}
//
//	public void setOfficeIdList(List<String> officeIdList) {
//		officeList = Lists.newArrayList();
//		for (String officeId : officeIdList) {
//			Office office = new Office();
//			office.setId(officeId);
//			officeList.add(office);
//		}
//	}
//
//	public String getOfficeIds() {
//		return StringUtils.join(getOfficeIdList(), ",");
//	}
//	
//	public void setOfficeIds(String officeIds) {
//		officeList = Lists.newArrayList();
//		if (officeIds != null){
//			String[] ids = StringUtils.split(officeIds, ",");
//			setOfficeIdList(Lists.newArrayList(ids));
//		}
//	}
	
	/**
	 * 获取权限字符串列表
	 */
//	public List<String> getPermissions() {
//		List<String> permissions = Lists.newArrayList();
//		for (Menu menu : menuList) {
//			if (menu.getPermission()!=null && !"".equals(menu.getPermission())){
//				permissions.add(menu.getPermission());
//			}
//		}
//		return permissions;
//	}

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
	
}
