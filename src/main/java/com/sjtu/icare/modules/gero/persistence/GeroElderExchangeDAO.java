/**
 * @Package com.sjtu.icare.modules.gero.persistence
 * @Description TODO
 * @date Mar 22, 2015 7:10:04 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.gero.persistence;

import java.util.List;
import java.util.Map;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.gero.entity.GeroElderExchangeEntity;

@MyBatisDao
public interface GeroElderExchangeDAO {

	/**
	 * @Title getGeroElderExchangeEntities
	 * @Description TODO
	 * @param @param paramMap
	 * @param @return
	 * @return List<GeroElderExchangeEntity>
	 * @throws
	 */
	public List<GeroElderExchangeEntity> getGeroElderExchangeEntities(
			GeroElderExchangeEntity elderExchangeEntity);

	/**
	 * @Title getGeroElderExchangeEntity
	 * @Description TODO
	 * @param @param paramMap
	 * @param @return
	 * @return GeroElderExchangeEntity
	 * @throws
	 */
	public GeroElderExchangeEntity getGeroElderExchangeEntity(
			Map<String, Object> paramMap);

	/**
	 * @Title insertGeroElderExchange
	 * @Description TODO
	 * @param @param paramMap
	 * @param @return
	 * @return Object
	 * @throws
	 */
	public void insertGeroElderExchange(Map<String, Object> paramMap);

	/**
	 * @Title updateGeroElderExchange
	 * @Description TODO
	 * @param @param paramMap
	 * @return void
	 * @throws
	 */
	public void updateGeroElderExchange(Map<String, Object> paramMap);

	/**
	 * @Title deleteGeroElderExchange
	 * @Description TODO
	 * @param @param paramMap
	 * @return void
	 * @throws
	 */
	public void deleteGeroElderExchange(Map<String, Object> paramMap);

}
