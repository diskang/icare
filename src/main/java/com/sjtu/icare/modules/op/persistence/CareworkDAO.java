/**
 * @Package com.sjtu.icare.modules.op.persistence
 * @Description TODO
 * @date Mar 16, 2015 3:24:38 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.op.persistence;

import java.util.List;
import java.util.Map;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.op.entity.CareworkEntity;
import com.sjtu.icare.modules.staff.entity.StaffEntity;

/**
 * @Description 老人护工工作职责的 Mapper
 * @author lzl
 * @date 2015-03-13
 */

@MyBatisDao
public interface CareworkDAO {

	CareworkEntity getCareworkEntityById(Map<String, Object> paramMap);
	
	List<CareworkEntity> getCareworkEntitiesByCarerid(Map<String, Object> paramMap);
	
	CareworkEntity getCareworkEntityByElderid(Map<String, Object> paramMap);
	
	List<StaffEntity> getStaffEntitiesByElderId(Map<String, Object> paramMap);

	/**
	 * @Title getStaffEntitiesByAreaId
	 * @Description TODO
	 * @param @param paramMap
	 * @param @return
	 * @return List<StaffEntity>
	 * @throws
	 */
	List<StaffEntity> getStaffEntitiesByAreaId(Map<String, Object> paramMap);

}
