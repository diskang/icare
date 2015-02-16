package com.sjtu.icare.modules.sys.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

public class Gero implements Serializable {

    private static final long serialVersionUID = 6321792448424424931L;
    private int id;
    private String name;
    private String cancelDate;

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
}