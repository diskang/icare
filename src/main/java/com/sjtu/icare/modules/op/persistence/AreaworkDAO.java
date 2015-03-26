package com.sjtu.icare.modules.op.persistence;

import java.util.List;
import java.util.Map;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.op.entity.AreaworkEntity;
import com.sjtu.icare.modules.staff.entity.StaffEntity;

/**
 * @Description 房间护工工作职责的 Mapper
 * @author lzl
 * @date 2015-03-13
 */

@MyBatisDao
public interface AreaworkDAO {
	/**
	 * @Title getAreaworkEntities
	 * @Description TODO
	 * @param @param areaworkEntity
	 * @param @return
	 * @return List<AreaworkEntity>
	 * @throws
	 */
	List<AreaworkEntity> getAreaworkEntities(AreaworkEntity areaworkEntity);

	/**
	 * @Title insertAreawork
	 * @Description TODO
	 * @param @param areaworkEntity
	 * @return void
	 * @throws
	 */
	void insertAreawork(AreaworkEntity areaworkEntity);

	/**
	 * @Title updateAreawork
	 * @Description TODO
	 * @param @param areaworkEntity
	 * @return void
	 * @throws
	 */
	void updateAreawork(AreaworkEntity areaworkEntity);

	/**
	 * @Title deleteAreawork
	 * @Description TODO
	 * @param @param areaworkEntity
	 * @return void
	 * @throws
	 */
	void deleteAreawork(AreaworkEntity areaworkEntity);

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