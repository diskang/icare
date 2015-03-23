/**
 * @Package com.sjtu.icare.modules.gero.service
 * @Description TODO
 * @date Mar 22, 2015 5:35:06 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.gero.service;

import java.util.List;

import com.sjtu.icare.modules.gero.entity.GeroElderExchangeEntity;

/**
 * @author sean
 *
 */
public interface IGeroElderExchangeService {

	/**
	 * @Title getGeroElderExchangeEntities
	 * @Description TODO
	 * @param @param queryElderExchangeEntity
	 * @param @return
	 * @return List<GeroElderExchangeEntity>
	 * @throws
	 */
	List<GeroElderExchangeEntity> getGeroElderExchangeEntities(
			GeroElderExchangeEntity queryElderExchangeEntity);

	/**
	 * @Title getGeroElderExchangeEntity
	 * @Description TODO
	 * @param @param queryElderExchangeEntity
	 * @param @return
	 * @return GeroElderExchangeEntity
	 * @throws
	 */
	GeroElderExchangeEntity getGeroElderExchangeEntity(
			GeroElderExchangeEntity elderExchangeEntity);

	/**
	 * @Title insertGeroElderExchange
	 * @Description TODO
	 * @param @param geroElderExchangeEntity
	 * @return void
	 * @throws
	 */
	void insertGeroElderExchange(
			GeroElderExchangeEntity geroElderExchangeEntity);

	/**
	 * @Title updateGeroElderExchange
	 * @Description TODO
	 * @param @param geroElderExchangeEntity
	 * @return void
	 * @throws
	 */
	void updateGeroElderExchange(
			GeroElderExchangeEntity geroElderExchangeEntity);

	/**
	 * @Title deleteGeroElderExchangeEntity
	 * @Description TODO
	 * @param @param queryElderExchangeEntity
	 * @return void
	 * @throws
	 */
	void deleteGeroElderExchangeEntity(
			GeroElderExchangeEntity geroElderExchangeEntity);

}
