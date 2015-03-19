package com.sjtu.icare.modules.staff.persistence;

import java.util.List;
import java.util.Map;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.staff.entity.StaffEntity;
import com.sjtu.icare.modules.sys.entity.User;
/**
 * @Description Staff Entity DAO
 * @author WangQi
 * @date 2015-03-06
 */

@MyBatisDao
public interface StaffDAO {

	StaffEntity getStaffEntity(Map<String, Object> paramMap);

	/**
	 * @Title getAllStaffs
	 * @Description TODO
	 * @param @param paramMap
	 * @param @return
	 * @return List<User>
	 * @throws
	 */
	List<User> getAllStaffs(Map<String, Object> paramMap);

	/**
	 * @Title getAllStaffsByRoles
	 * @Description TODO
	 * @param @param paramMap
	 * @param @return
	 * @return List<User>
	 * @throws
	 */
	List<User> getAllStaffsByRoles(Map<String, Object> paramMap);

	/**
	 * @Title insertStaff
	 * @Description TODO
	 * @param @param paramMap
	 * @param @return
	 * @return Map<String, Object>
	 * @throws
	 */
	Integer insertStaff(StaffEntity staffEntity);

	/**
	 * @Title insertUser
	 * @Description TODO
	 * @param @param paramMap
	 * @param @return
	 * @return Integer
	 * @throws
	 */
	Integer insertUser(Map<String, Object> paramMap);

	/**
	 * @Title updateStaff
	 * @Description TODO
	 * @param @param paramMap
	 * @param @return
	 * @return void
	 * @throws
	 */
	void updateStaff(Map<String, Object> paramMap);

	/**
	 * @Title updateUser
	 * @Description TODO
	 * @param @param paramMap
	 * @return void
	 * @throws
	 */
	void updateUser(Map<String, Object> paramMap);
	
}