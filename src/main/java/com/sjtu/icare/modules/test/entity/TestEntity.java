/**
 * @Package com.sjtu.icare.modules.test.entity
 * @Description TODO
 * @date Mar 10, 2015 4:35:06 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.test.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

public class TestEntity implements Serializable {
	  
    private static final long serialVersionUID = 1L;
    
    private int param_one;
    private int param_two;
	/**
	 * @return the param_one
	 */
    @Length(min=5,max=30)
	public int getParam_one() {
		return param_one;
	}

	/**
	 * @param param_one the param_one to set
	 */
	public void setParam_one(int param_one) {
		this.param_one = param_one;
	}

	/**
	 * @return the param_two
	 */
	public int getParam_two() {
		return param_two;
	}

	/**
	 * @param param_two the param_two to set
	 */
	public void setParam_two(int param_two) {
		this.param_two = param_two;
	}
    
	@Override
	public String toString() {
		return "TestEntity[" + param_one + ", " + param_two + "]";
	}
    
}
