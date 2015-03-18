/**
 * @Package com.sjtu.icare.modules.staff.service
 * @Description Staff service interface
 * @date Mar 6, 2015 9:49:22 PM
 * @author Wang Qi
 * @version v1.0
 */
package com.sjtu.icare.modules.staff.service;

import java.util.List;

import com.sjtu.icare.modules.staff.entity.StaffEntity;
import com.sjtu.icare.modules.staff.entity.StaffSchedulePlanEntity;
import com.sjtu.icare.modules.sys.entity.User;


public interface IStaffDataService {
	
	StaffEntity getStaffEntity(StaffEntity staffEntity);

	User getUserEntityOfStaff(StaffEntity StaffEntity);

	List<StaffSchedulePlanEntity> getStaffSchedulePlans(
			StaffSchedulePlanEntity queryStaffSchedulePlanEntity,
			String startDate, String endDate);

	void insertStaffSchedulePlans(StaffSchedulePlanEntity staffSchedulePlanEntity, List<String> workDate);

	void deleteStaffSchedulePlans(StaffSchedulePlanEntity postEntity,
			List<String> noworkDate);

	List<StaffSchedulePlanEntity> getAllStaffPlansByGeroId(
			StaffSchedulePlanEntity staffSchedulePlanEntity, String startDate,
			String endDate);
	
	List<StaffSchedulePlanEntity> getAllStaffPlansByGeroId(
			StaffSchedulePlanEntity queryStaffSchedulePlanEntity,
			String startDate, String endDate, String role);

	/**
	 * @Title getAllStaffs
	 * @Description 该方法不能拿到 Role
	 * @param @param User
	 * @param @return
	 * @return List<User>
	 * @throws
	 */
	List<User> getAllStaffs(User user);
	
}
