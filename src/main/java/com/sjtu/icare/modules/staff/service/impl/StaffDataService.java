/**
 * @Package com.sjtu.icare.modules.staff.service.impl
 * @Description TODO
 * @date Mar 6, 2015 9:54:14 PM
 * @author Wang Qi
 * @version v1.0
 */
package com.sjtu.icare.modules.staff.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjtu.icare.common.config.CommonConstants;
import com.sjtu.icare.common.utils.CommonUtils;
import com.sjtu.icare.modules.staff.entity.StaffEntity;
import com.sjtu.icare.modules.staff.entity.StaffSchedulePlanEntity;
import com.sjtu.icare.modules.staff.persistence.StaffDAO;
import com.sjtu.icare.modules.staff.persistence.StaffSchedulePlanDAO;
import com.sjtu.icare.modules.staff.service.IStaffDataService;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;

@Service
public class StaffDataService implements IStaffDataService {
	@Autowired
	private StaffDAO staffDAO;
	@Autowired
	private SystemService systemService;
	@Autowired
	private StaffSchedulePlanDAO staffSchedulePlanDAO;
	
	@Override
	public StaffEntity getStaffEntity(StaffEntity staffEntity) {
		Map<String, Object> paramMap = CommonUtils.beanToMap(staffEntity);
		return staffDAO.getStaffEntity(paramMap);
	}
	
	@Override
	public User getUserEntityOfStaff(StaffEntity StaffEntity) {
		return systemService.getUserByUserTypeAndUserId(CommonConstants.STAFF_TYPE, StaffEntity.getId());
	}
	
	@Override
	public List<StaffSchedulePlanEntity> getStaffSchedulePlans(
			StaffSchedulePlanEntity queryCarerSchedulePlanEntity,
			String startDate, String endDate) {
		Map<String, Object> paramMap = CommonUtils.beanToMap(queryCarerSchedulePlanEntity);
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		return staffSchedulePlanDAO.getStaffSchedulePlans(paramMap);
	}

	@Override
	public void insertStaffSchedulePlans(
			StaffSchedulePlanEntity staffSchedulePlanEntity,
			List<String> workDate) {
		Map<String, Object> paramMap = CommonUtils.beanToMap(staffSchedulePlanEntity);
		paramMap.put("workDate", workDate);
		staffSchedulePlanDAO.insertStaffSchedulePlans(paramMap);
	}

	@Override
	public void deleteStaffSchedulePlans(StaffSchedulePlanEntity staffSchedulePlanEntity,
			List<String> noworkDate) {
		Map<String, Object> paramMap = CommonUtils.beanToMap(staffSchedulePlanEntity);
		paramMap.put("noworkDate", noworkDate);
		staffSchedulePlanDAO.deleteStaffSchedulePlans(paramMap);
	}



}
