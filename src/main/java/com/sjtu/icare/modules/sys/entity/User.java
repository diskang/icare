package com.sjtu.icare.modules.sys.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.sjtu.icare.common.persistence.DataEntity;
import com.sjtu.icare.common.utils.Collections3;
import com.sjtu.icare.modules.sys.entity.Role;

public class User extends DataEntity<User> {
    /*应该考虑加入手机号和邮箱，原来是手机号登录的*/
    private static final long serialVersionUID = 6321792448424424931L;
    private String username;//用户名
    private String password;//密码
    private String name;//姓名
    private int userType;//超级管理员=0、管理员=1，员工=2、老人=3、家属=4
    private int userId;//-1，gero_id, staff_id, elder_id，family_id
    private String registerDate;//注册时间（系统加）
    private String cancelDate;//注销时间（系统加，逻辑删除）
    private String gender;	//性别（男0，女1）
    private String photoUrl;	// 头像url
    private String identityNo;//身份证号
    private int age;//年龄
    private String nationality;//国籍
    private int marriage; //0未婚，1已婚
    private String nativePlace; //籍贯
    private String birthday;//生日
    private String politicalStatus; //政治面貌
    private String education;//教育水平
    private String phoneNo;//电话号码
    private String zipCode;//邮编
    private String residenceAddress; //户籍地址
    private String householdAddress; //居住地址
    private String email;//邮箱
    private String wechatId; //微信账号
    private int geroId;//养老院ID
    
    // elders 查询参数
    private Integer ageMin;
    private Integer ageMax;
    private Integer areaId;
    private Integer careLevel;
    private List<Integer> areaIds;
    
    // relative 查询参数
    private Integer elderId;
    
    
    
	private Role role;	// 根据角色查询用户条件
	
	private List<Role> roleList = Lists.newArrayList(); // 拥有角色列表
	
	private List<String> roleNameList = new ArrayList<String>();

    public void setId(int id) {
        this.id = id;
    }
    
    @Length(min=5,max=30)
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public User() {
		super();
	}
	
	public User(int id){
		super(id);
	}

	public User(int id, String loginName){
		super(id);
		this.username = loginName;
	}
	
	public User(int id, int userType, int userId){
		super(id);
		this.userType = userType;
		this.userId = userId;
	}

	public User(Role role){
		super();
		this.role = role;
	}
	
	public int getId() {
		return id;
	}
	
	@Length(min=1, max=100, message="登录名长度必须介于 1 和 100 之间")
	public String getLoginName() {
		return username;
	}

	public void setLoginName(String loginName) {
		this.username = loginName;
	}

	@JsonIgnore
	@Length(min=1, max=100, message="密码长度必须介于 1 和 100 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Length(min=1, max=100, message="姓名长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate){
		this.registerDate = registerDate;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@JsonIgnore
	public List<Role> getRoleList() {
		return roleList;
	}
	
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	@JsonIgnore
	public List<Integer> getRoleIdList() {
		List<Integer> roleIdList = Lists.newArrayList();
		for (Role role : roleList) {
			roleIdList.add(role.getId());
		}
		return roleIdList;
	}

	public void setRoleIdList(List<Integer> roleIdList) {
		roleList = Lists.newArrayList();
		for (int roleId : roleIdList) {
			Role role = new Role();
			role.setId(roleId);
			roleList.add(role);
		}
	}
	
	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	public String getRoleNames() {
		return Collections3.extractToString(roleList, "name", ",");
	}
	
	public boolean isAdmin(){
		return userType == 0;
	}
	
	@Override
	public String toString() {
		return ""+id;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public int getMarriage() {
		return marriage;
	}

	public void setMarriage(int marriage) {
		this.marriage = marriage;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPoliticalStatus() {
		return politicalStatus;
	}

	public void setPoliticalStatus(String politicalStatus) {
		this.politicalStatus = politicalStatus;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getResidenceAddress() {
		return residenceAddress;
	}

	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

	public String getHouseholdAddress() {
		return householdAddress;
	}

	public void setHouseholdAddress(String householdAddress) {
		this.householdAddress = householdAddress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public int getGeroId() {
		return geroId;
	}

	public void setGeroId(int geroId) {
		this.geroId = geroId;
	}

	/**
	 * @return the ageMin
	 */
	public Integer getAgeMin() {
		return ageMin;
	}

	/**
	 * @param ageMin the ageMin to set
	 */
	public void setAgeMin(Integer ageMin) {
		this.ageMin = ageMin;
	}

	/**
	 * @return the ageMax
	 */
	public Integer getAgeMax() {
		return ageMax;
	}

	/**
	 * @param ageMax the ageMax to set
	 */
	public void setAgeMax(Integer ageMax) {
		this.ageMax = ageMax;
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
	 * @return the roleNameList
	 */
	public List<String> getRoleNameList() {
		return roleNameList;
	}

	/**
	 * @param roleNameList the roleNameList to set
	 */
	public void setRoleNameList(List<String> roleNameList) {
		this.roleNameList = roleNameList;
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
	 * @return the areaIds
	 */
	public List<Integer> getAreaIds() {
		return areaIds;
	}

	/**
	 * @param areaIds the areaIds to set
	 */
	public void setAreaIds(List<Integer> areaIds) {
		this.areaIds = areaIds;
	}

	public Integer getElderId() {
		return elderId;
	}

	public void setElderId(Integer elderId) {
		this.elderId = elderId;
	}
	
	
	
}