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
	
}