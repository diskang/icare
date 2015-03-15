package com.sjtu.icare.modules.sys.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.sjtu.icare.common.persistence.DataEntity;
import com.sjtu.icare.common.config.Global;
import com.sjtu.icare.common.utils.Collections3;
import com.sjtu.icare.modules.staff.entity.StaffEntity;
import com.sjtu.icare.modules.sys.entity.Role;

public class User extends DataEntity<User> {
    /*应该考虑加入手机号和邮箱，原来是手机号登录的*/
    private static final long serialVersionUID = 6321792448424424931L;
    private String username;
    private String password;
    private String name;
    private int userType;//超级管理员=0、管理员=1，员工=2、老人=3、家属=4
    private int userId;//-1，gero_id, staff_id, elder_id，family_id
    private String registerDate;
    private String cancelDate;
    private char gender;	//性别（男0，女1）
    private String photoUrl;	// 头像url
    private String identityNo;
    private int age;
    private String nationality;
    private int marriage; //0未婚，1已婚
    private String nativePlace; //籍贯
    private String birthday;
    private String politicalStatus; //政治面貌
    private String education;
    private String phoneNo;
    private String zipCode;
    private String residenceAddress; //户籍地址
    private String householdAddress; //居住地址
    private String email;
    private String wechatId; //微信账号
    private int geroId;
    
//    private String userType;// 用户类型
//	private String loginIp;	// 最后登陆IP
//	private Date loginDate;	// 最后登陆日期
//	private String loginFlag;	// 是否允许登陆
//	private String oldLoginName;// 原登录名
//	private String newPassword;	// 新密码
//	
//	private String oldLoginIp;	// 上次登陆IP
//	private Date oldLoginDate;	// 上次登陆日期
	
	private Role role;	// 根据角色查询用户条件
	
	private List<Role> roleList = Lists.newArrayList(); // 拥有角色列表

//    public int getId() {
//        return id;
//    }

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
    
//    public String getPassword() {
//        return password;
//    }
    
//    public void setPassword(String password) {
//        this.password = password;
//    }


//    public int getUserType() {
//        return userType;
//    }

//    public void setUserType(int userType) {
//        this.userType = userType;
//    }
    
//    public int getUserId(){
//    	return userId;
//    }
//    
//    public void setUserId(int userId){
//    	this.userId = userId;
//    }

//    public String getRegisterDate() {
//        return registerDate;
//    }

//    public void setRegisterDate(String registerDate) {
//        this.registerDate = registerDate;
//    }
//
//    public String getCancelDate() {
//        return cancelDate;
//    }
//
//    public void setCancelDate(String cancelDate) {
//        this.cancelDate = cancelDate;
//    }
    
    public User() {
		super();
//		this.loginFlag = Global.YES;
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
	
//	public String getPhoto() {
//		return photo;
//	}
//
//	public void setPhoto(String photo) {
//		this.photo = photo;
//	}
//
//	public String getLoginFlag() {
//		return loginFlag;
//	}
//
//	public void setLoginFlag(String loginFlag) {
//		this.loginFlag = loginFlag;
//	}

//	@SupCol(isUnique="true", isHide="true")
//	@ExcelField(title="ID", type=1, align=2, sort=1)
	public int getId() {
		return id;
	}

//	@JsonIgnore
//	@NotNull(message="归属公司不能为空")
//	@ExcelField(title="归属公司", align=2, sort=20)
//	public Office getCompany() {
//		return company;
//	}
//
//	public void setCompany(Office company) {
//		this.company = company;
//	}
//	
//	@JsonIgnore
//	@NotNull(message="归属部门不能为空")
//	@ExcelField(title="归属部门", align=2, sort=25)
//	public Office getOffice() {
//		return office;
//	}
//
//	public void setOffice(Office office) {
//		this.office = office;
//	}

	@Length(min=1, max=100, message="登录名长度必须介于 1 和 100 之间")
//	@ExcelField(title="登录名", align=2, sort=30)
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
//	@ExcelField(title="姓名", align=2, sort=40)
	public String getName() {
		return name;
	}
	
//	@Length(min=1, max=100, message="工号长度必须介于 1 和 100 之间")
////	@ExcelField(title="工号", align=2, sort=45)
//	public String getNo() {
//		return no;
//	}
//
//	public void setNo(String no) {
//		this.no = no;
//	}

	public void setName(String name) {
		this.name = name;
	}

//	@Email(message="邮箱格式不正确")
//	@Length(min=0, max=200, message="邮箱长度必须介于 1 和 200 之间")
//	@ExcelField(title="邮箱", align=1, sort=50)
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
	
//	@Length(min=0, max=200, message="电话长度必须介于 1 和 200 之间")
//	@ExcelField(title="电话", align=2, sort=60)
//	public String getPhone() {
//		return phone;
//	}
//
//	public void setPhone(String phone) {
//		this.phone = phone;
//	}
//
//	@Length(min=0, max=200, message="手机长度必须介于 1 和 200 之间")
//	@ExcelField(title="手机", align=2, sort=70)
//	public String getMobile() {
//		return mobile;
//	}
//
//	public void setMobile(String mobile) {
//		this.mobile = mobile;
//	}
//
//	@ExcelField(title="备注", align=1, sort=900)
//	public String getRemarks() {
//		return remarks;
//	}
	
//	@Length(min=0, max=100, message="用户类型长度必须介于 1 和 100 之间")
//	@ExcelField(title="用户类型", align=2, sort=80, dictType="sys_user_type")
	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

//	@ExcelField(title="创建时间", type=0, align=1, sort=90)
	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate){
		this.registerDate = registerDate;
	}
//	@ExcelField(title="最后登录IP", type=1, align=1, sort=100)
//	public String getLoginIp() {
//		return loginIp;
//	}

//	public void setLoginIp(String loginIp) {
//		this.loginIp = loginIp;
//	}

//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	@ExcelField(title="最后登录日期", type=1, align=1, sort=110)
//	public Date getLoginDate() {
//		return loginDate;
//	}

//	public void setLoginDate(Date loginDate) {
//		this.loginDate = loginDate;
//	}

//	public String getOldLoginName() {
//		return oldLoginName;
//	}

//	public void setOldLoginName(String oldLoginName) {
//		this.oldLoginName = oldLoginName;
//	}

//	public String getNewPassword() {
//		return newPassword;
//	}
//
//	public void setNewPassword(String newPassword) {
//		this.newPassword = newPassword;
//	}

//	public String getOldLoginIp() {
//		if (oldLoginIp == null){
//			return loginIp;
//		}
//		return oldLoginIp;
//	}
//
//	public void setOldLoginIp(String oldLoginIp) {
//		this.oldLoginIp = oldLoginIp;
//	}

//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	public Date getOldLoginDate() {
//		if (oldLoginDate == null){
//			return loginDate;
//		}
//		return oldLoginDate;
//	}
//
//	public void setOldLoginDate(Date oldLoginDate) {
//		this.oldLoginDate = oldLoginDate;
//	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@JsonIgnore
//	@ExcelField(title="拥有角色", align=1, sort=800, fieldType=RoleListType.class)
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
	
	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
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
	
	
}