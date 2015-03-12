/**
 * @Package com.sjtu.icare.modules.gero.persistence
 * @Description TODO
 * @date Mar 9, 2015 5:18:57 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.gero.persistence;

import java.util.List;
import java.util.Map;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.gero.entity.GeroAreaEntity;

@MyBatisDao
public interface GeroAreaDAO {

	/**
	 * @Title getGeroAreaEntitiesByGeroid
	 * @Description TODO
	 * @param @param geroId
	 * @param @return
	 * @return List<GeroAreaEntity>
	 * @throws
	 */
	List<GeroAreaEntity> getGeroAreaEntities(Map<String, Object> geroAreaEntity);
	
	/**
	 * @Title getGeroAreaEntitiy
	 * @Description TODO
	 * @param @param geroAreaEntity
	 * @param @return
	 * @return GeroAreaEntity
	 * @throws
	 */
	GeroAreaEntity getGeroAreaEntity(Map<String, Object> geroAreaEntity);
	
	/**
	 * @Title insertGeroAreaEntity
	 * @Description TODO
	 * @param @param geroAreaEntity
	 * @param @return
	 * @return Object
	 * @throws
	 */
	void insertGeroAreaEntity(Map<String, Object> geroAreaEntity);

	/**
	 * @Title getGeroSubareas
	 * @Description TODO
	 * @param @param paramMap
	 * @param @return
	 * @return List<GeroAreaEntity>
	 * @throws
	 */
	List<GeroAreaEntity> getGeroSubareas(Map<String, Object> paramMap);
	
}
