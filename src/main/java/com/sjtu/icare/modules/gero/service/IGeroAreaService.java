/**
 * @Package com.sjtu.icare.modules.gero.service
 * @Description TODO
 * @date Mar 9, 2015 5:16:11 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.gero.service;

import java.util.List;

import com.sjtu.icare.modules.gero.entity.GeroAreaEntity;

public interface IGeroAreaService {

	/**
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @Title getGeroAreas
	 * @Description TODO
	 * @param @param paramsMap
	 * @param @return
	 * @return Object
	 * @throws
	 */
	List<GeroAreaEntity> getGeroAreas(GeroAreaEntity geroAreaEntity);

	/**
	 * @Title insertGeroAreaRecord
	 * @Description TODO
	 * @param @param geroAreaEntity
	 * @return void
	 * @throws
	 */
	void insertGeroAreaRecord(GeroAreaEntity geroAreaEntity);

	/**
	 * @Title getGeroArea
	 * @Description TODO
	 * @param @param parentGeroAreaEntity
	 * @return void
	 * @throws
	 */
	GeroAreaEntity getGeroArea(GeroAreaEntity geroAreaEntity);

	/**
	 * @Title getGeroSubareas
	 * @Description TODO
	 * @param @param ancestorGeroAreaEntity
	 * @param @param subLevel
	 * @param @return
	 * @return List<GeroAreaEntity>
	 * @throws
	 */
	List<GeroAreaEntity> getGeroSubareas(GeroAreaEntity ancestorGeroAreaEntity,
			Integer subLevel);

	/**
	 * @Title updateGeroAreaRecord
	 * @Description TODO
	 * @param @param postEntity
	 * @return void
	 * @throws
	 */
	void updateGeroAreaRecord(GeroAreaEntity postEntity);

	/**
	 * @Title deleteGeroAreaRecord
	 * @Description TODO
	 * @param @param inputEntity
	 * @return void
	 * @throws
	 */
	void deleteGeroAreaRecord(GeroAreaEntity geroAreaEntity);
	
}
