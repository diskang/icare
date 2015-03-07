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

import com.sjtu.icare.modules.staff.entity.StaffEntity;
import com.sjtu.icare.modules.staff.persistence.StaffEntityDAO;
import com.sjtu.icare.modules.staff.service.IStaffDataService;

@Service
public class StaffDataService implements IStaffDataService {
	@Autowired
	private StaffEntityDAO staffEntityDAO;
	
	@Override
	public StaffEntity getStaffEntity(int id) {
		return staffEntityDAO.getStaffEntityById(id);
	}
	

}
