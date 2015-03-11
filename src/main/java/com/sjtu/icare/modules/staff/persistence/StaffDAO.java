package com.sjtu.icare.modules.staff.persistence;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.staff.entity.StaffEntity;
/**
 * @Description Staff Entity DAO
 * @author WangQi
 * @date 2015-03-06
 */

@MyBatisDao
public interface StaffDAO {

	StaffEntity getStaffEntityById(int id);
	
}