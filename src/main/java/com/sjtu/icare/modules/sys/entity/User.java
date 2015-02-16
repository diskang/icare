package com.sjtu.icare.modules.sys.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

public class User implements Serializable {
    /*应该考虑加入手机号和邮箱，原来是手机号登录的*/
    private static final long serialVersionUID = 6321792448424424931L;
    private int id;
    private String username;
    private String password;
    private int userType;//超级管理员=0、管理员=1，员工=2、老人=3、家属=4
    private int userId;//-1，gero_id, staff_id, elder_id，family_id
    private String registerDate;
    private String cancelDate;

    public int getId() {
        return id;
    }

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
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }


    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
    
    public int getUserId(){
    	return userId;
    }
    
    public void setUserId(int userId){
    	this.userId = userId;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }
}