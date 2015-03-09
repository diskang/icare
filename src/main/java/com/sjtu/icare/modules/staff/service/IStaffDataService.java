/**
 * @Package com.sjtu.icare.modules.staff.service
 * @Description Staff service interface
 * @date Mar 6, 2015 9:49:22 PM
 * @author Wang Qi
 * @version v1.0
 */
package com.sjtu.icare.modules.staff.service;

import com.sjtu.icare.modules.staff.entity.StaffEntity;


public interface IStaffDataService {
	
	StaffEntity getStaffEntity(int id);
	
}
