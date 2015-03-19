/**
 * @Package com.sjtu.icare.modules.gero.persistence
 * @Description TODO
 * @date Mar 9, 2015 5:18:57 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.op.persistence;

import java.util.List;
import java.util.Map;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.op.entity.CareItemEntity;

@MyBatisDao
public interface CareItemDAO {

	List<CareItemEntity> getCareItemEntitiesByGeroId(Map<String, Object> paramMap);

	/**
	 * @Title getCareItemEntityById
	 * @Description TODO
	 * @param @param paramMap
	 * @param @return
	 * @return CareItemEntity
	 * @throws
	 */
	CareItemEntity getCareItemEntityById(Map<String, Object> paramMap);

	/**
	 * @Title deleteCareItem
	 * @Description TODO
	 * @param @param paramMap
	 * @param @return
	 * @return Object
	 * @throws
	 */
	void deleteCareItem(Map<String, Object> paramMap);

	/**
	 * @Title insertCareItem
	 * @Description TODO
	 * @param @param paramMap
	 * @return void
	 * @throws
	 */
	void insertCareItem(Map<String, Object> paramMap);

	/**
	 * @Title updateCareItem
	 * @Description TODO
	 * @param @param paramMap
	 * @return void
	 * @throws
	 */
	void updateCareItem(Map<String, Object> paramMap);
	
}
