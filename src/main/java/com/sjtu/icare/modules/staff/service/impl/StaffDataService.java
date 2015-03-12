/**
 * @Package com.sjtu.icare.modules.staff.service.impl
 * @Description TODO
 * @date Mar 6, 2015 9:54:14 PM
 * @author Wang Qi
 * @version v1.0
 */
package com.sjtu.icare.modules.staff.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjtu.icare.common.config.CommonConstants;
import com.sjtu.icare.modules.elder.entity.ElderEntity;
import com.sjtu.icare.modules.staff.entity.StaffEntity;
import com.sjtu.icare.modules.staff.persistence.StaffDAO;
import com.sjtu.icare.modules.staff.service.IStaffDataService;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;

@Service
public class StaffDataService implements IStaffDataService {
	@Autowired
	private StaffDAO staffDAO;
	@Autowired
	private SystemService systemService;
	
	@Override
	public StaffEntity getStaffEntity(int id) {
		return staffDAO.getStaffEntityById(id);
	}
	@Override
	public User getUserEntityOfStaff(StaffEntity StaffEntity) {
		return systemService.getUserByUserTypeAndUserId(CommonConstants.ELDER_TYPE, StaffEntity.getId());
	}

}
