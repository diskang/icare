/**
 * @Package com.sjtu.icare.modules.gero.service
 * @Description TODO
 * @date Mar 24, 2015 5:24:08 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.gero.service;

import java.util.List;

import com.sjtu.icare.modules.gero.entity.GeroEntity;

public interface IGeroService {

	/**
	 * @param geroEntity 
	 * @Title getGeros
	 * @Description TODO
	 * @param @return
	 * @return List<GeroEntity>
	 * @throws
	 */
	List<GeroEntity> getGeros(GeroEntity geroEntity);

	/**
	 * @Title insertGero
	 * @Description TODO
	 * @param @param geroEntity
	 * @return void
	 * @throws
	 */
	void insertGero(GeroEntity geroEntity);

	/**
	 * @Title getGero
	 * @Description TODO
	 * @param @param geroEntity
	 * @param @return
	 * @return GeroEntity
	 * @throws
	 */
	GeroEntity getGero(GeroEntity geroEntity);

	/**
	 * @Title updateGero
	 * @Description TODO
	 * @param @param geroEntity
	 * @return void
	 * @throws
	 */
	void updateGero(GeroEntity geroEntity);

	/**
	 * @Title deleteGero
	 * @Description TODO
	 * @param @param geroEntity
	 * @param @return
	 * @return void
	 * @throws
	 */
	void deleteGero(GeroEntity geroEntity);

}
