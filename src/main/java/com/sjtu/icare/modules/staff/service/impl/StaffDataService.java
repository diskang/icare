/**
 * @Package com.sjtu.icare.modules.staff.service.impl
 * @Description TODO
 * @date Mar 6, 2015 9:54:14 PM
 * @author Wang Qi
 * @version v1.0
 */
package com.sjtu.icare.modules.staff.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjtu.icare.common.config.CommonConstants;
import com.sjtu.icare.common.utils.MapListUtils;
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
		Map<String, Object> paramMap = MapListUtils.beanToMap(staffEntity);
		return staffDAO.getStaffEntity(paramMap);
	}
	
	/**
	 * 该方法无法的到 User 中的 Role
	 */
	@Override
	public User getUserEntityOfStaff(StaffEntity StaffEntity) {
		return systemService.getUserByUserTypeAndUserId(CommonConstants.STAFF_TYPE, StaffEntity.getId());
	}
	
	@Override
	public List<StaffSchedulePlanEntity> getStaffSchedulePlans(
			StaffSchedulePlanEntity queryStaffSchedulePlanEntity,
			String startDate, String endDate) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(queryStaffSchedulePlanEntity);
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		return staffSchedulePlanDAO.getStaffSchedulePlans(paramMap);
	}

	@Override
	public void insertStaffSchedulePlans(
			StaffSchedulePlanEntity staffSchedulePlanEntity,
			List<String> workDate) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(staffSchedulePlanEntity);
		paramMap.put("workDate", workDate);
		staffSchedulePlanDAO.insertStaffSchedulePlans(paramMap);
	}

	@Override
	public void deleteStaffSchedulePlans(StaffSchedulePlanEntity staffSchedulePlanEntity,
			List<String> noworkDate) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(staffSchedulePlanEntity);
		paramMap.put("noworkDate", noworkDate);
		staffSchedulePlanDAO.deleteStaffSchedulePlans(paramMap);
	}

	@Override
	public List<StaffSchedulePlanEntity> getAllStaffPlansByGeroId(
			StaffSchedulePlanEntity staffSchedulePlanEntity,
			String startDate, String endDate) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(staffSchedulePlanEntity);
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		return staffSchedulePlanDAO.getAllStaffSchedulePlansByGeroId(paramMap);
		
	}

	@Override
	public List<StaffSchedulePlanEntity> getAllStaffPlansByGeroId(
			StaffSchedulePlanEntity staffSchedulePlanEntity,
			String startDate, String endDate, String role) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(staffSchedulePlanEntity);
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		paramMap.put("role", role);
		paramMap.put("userType", CommonConstants.STAFF_TYPE);
		return staffSchedulePlanDAO.getAllStaffSchedulePlansByGeroIdAndRole(paramMap);
	
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.staff.service.IStaffDataService#getAllStaffs(com.sjtu.icare.modules.sys.entity.User)
	 */
	@Override
	public List<User> getAllStaffs(User user) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(user);
		paramMap.put("userType", CommonConstants.STAFF_TYPE);
		return staffDAO.getAllStaffs(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.staff.service.IStaffDataService#getAllStaffs(com.sjtu.icare.modules.sys.entity.User, java.lang.String)
	 */
	@Override
	public List<User> getAllStaffs(User user, String role) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(user);
		paramMap.put("userType", CommonConstants.STAFF_TYPE);
		List<String> roleList = Arrays.asList(role.split(","));
		for (int i=0; i<roleList.size(); ++i) {
			roleList.set(i, roleList.get(i).trim());
		}
		paramMap.put("roles", roleList);
		return staffDAO.getAllStaffsByRoles(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.staff.service.IStaffDataService#insertStaff(com.sjtu.icare.modules.staff.entity.StaffEntity)
	 */
	@Override
	public Integer insertStaff(StaffEntity staffEntity) {
		// Map<String, Object> paramMap = MapListUtils.beanToMap(staffEntity);
		staffDAO.insertStaff(staffEntity);
		return (Integer) staffEntity.getId();
	}


	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.staff.service.IStaffDataService#updateStaff(com.sjtu.icare.modules.staff.entity.StaffEntity)
	 */
	@Override
	public void updateStaff(StaffEntity staffEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(staffEntity);
		staffDAO.updateStaff(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.staff.service.IStaffDataService#deleteStaff(com.sjtu.icare.modules.staff.entity.StaffEntity)
	 */
	@Override
	public void deleteStaff(StaffEntity staffEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(staffEntity);
		staffDAO.deleteStaff(paramMap);
	}





}
