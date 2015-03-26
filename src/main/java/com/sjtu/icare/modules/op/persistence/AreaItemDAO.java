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
import com.sjtu.icare.modules.op.entity.AreaItemEntity;

@MyBatisDao
public interface AreaItemDAO {
	
	/**
	 * @Title getAreaItems
	 * @Description TODO
	 * @param @param paramMap
	 * @param @return
	 * @return List<AreaItemEntity>
	 * @throws
	 */
	List<AreaItemEntity> getAreaItems(AreaItemEntity areaItemEntity);

	/**
	 * @Title insertAreaItem
	 * @Description TODO
	 * @param @param paramMap
	 * @return void
	 * @throws
	 */
	void insertAreaItem(Map<String, Object> paramMap);

	/**
	 * @Title getAreaItem
	 * @Description TODO
	 * @param @param paramMap
	 * @param @return
	 * @return AreaItemEntity
	 * @throws
	 */
	AreaItemEntity getAreaItem(Map<String, Object> paramMap);

	/**
	 * @Title updateAreaItem
	 * @Description TODO
	 * @param @param paramMap
	 * @return void
	 * @throws
	 */
	void updateAreaItem(Map<String, Object> paramMap);

	/**
	 * @Title deleteAreaItem
	 * @Description TODO
	 * @param @param paramMap
	 * @return void
	 * @throws
	 */
	void deleteAreaItem(Map<String, Object> paramMap);

	List<AreaItemEntity> getCareItemEntitiesByGeroId(
			AreaItemEntity areaItemEntity);
}
