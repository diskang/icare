/**
 * @Package com.sjtu.icare.modules.gero.persistence
 * @Description TODO
 * @date Mar 9, 2015 5:18:57 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.gero.persistence;

import java.util.List;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.gero.entity.GeroAreaEntity;

@MyBatisDao
public interface GeroAreaDAO {

	/**
	 * @Title getGeroAreaEntities
	 * @Description TODO
	 * @param @param geroId
	 * @param @return
	 * @return List<GeroAreaEntity>
	 * @throws
	 */
	List<GeroAreaEntity> getGeroAreaEntities(int geroId);

	/**
	 * @Title insertGeroAreaEntity
	 * @Description TODO
	 * @param @param geroAreaEntity
	 * @param @return
	 * @return Object
	 * @throws
	 */
	void insertGeroAreaEntity(GeroAreaEntity geroAreaEntity);
	
}
