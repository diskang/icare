/**
 * @Package com.sjtu.icare.modules.staff.persistence
 * @Description TODO
 * @date Mar 13, 2015 10:44:35 AM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.staff.persistence;

import java.util.List;
import java.util.Map;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.staff.entity.StaffSchedulePlanEntity;

@MyBatisDao
public interface StaffSchedulePlanDAO {

	List<StaffSchedulePlanEntity> getStaffSchedulePlans(
			Map<String, Object> paramMap);

	void insertStaffSchedulePlans(Map<String, Object> paramMap);

	void deleteStaffSchedulePlans(Map<String, Object> paramMap);

	List<StaffSchedulePlanEntity> getAllStaffSchedulePlansByGeroId(
			Map<String, Object> paramMap);

	List<StaffSchedulePlanEntity> getAllStaffSchedulePlansByGeroIdAndRole(
			Map<String, Object> paramMap);
	
}
