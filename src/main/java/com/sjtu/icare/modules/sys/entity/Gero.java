package com.sjtu.icare.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.sjtu.icare.common.persistence.DataEntity;

/**
 * 养老院Entity
 * @author jty
 * @version 2015-03-15
 */
public class Gero extends DataEntity<Role>{

    private static final long serialVersionUID = 6321792448424424931L;
    
    private String name; //养老院名
    private String cancelDate;//注销日期

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Length(min=2,max=5)
    public String getName() {
        return name;
    }
    
    public void setname(String name) {
        this.name = name;
    }
    
    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }

	public Gero(int id) {
		super();
		this.id = id;
	}

	public Gero() {
		super();
	}

	@Override
	public void preDelete() {
		// TODO Auto-generated method stub
		
	}
}