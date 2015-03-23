/**
 * @Package com.sjtu.icare.modules.gero.service.impl
 * @Description TODO
 * @date Mar 22, 2015 7:02:20 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.gero.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjtu.icare.common.utils.MapListUtils;
import com.sjtu.icare.modules.gero.entity.GeroElderExchangeEntity;
import com.sjtu.icare.modules.gero.persistence.GeroElderExchangeDAO;
import com.sjtu.icare.modules.gero.service.IGeroElderExchangeService;

@Service
public class GeroElderExcahngeService implements IGeroElderExchangeService {

	@Autowired
	private GeroElderExchangeDAO geroElderExcahngeDAO;
	
	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.gero.service.IGeroExchangeService#getGeroElderExchangeEntities(com.sjtu.icare.modules.gero.entity.GeroElderExchangeEntity)
	 */
	@Override
	public List<GeroElderExchangeEntity> getGeroElderExchangeEntities(
			GeroElderExchangeEntity elderExchangeEntity) {
		return elderExchangeEntity.getPage().setList(geroElderExcahngeDAO.getGeroElderExchangeEntities(elderExchangeEntity)).getList();
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.gero.service.IGeroElderExchangeService#getGeroElderExchangeEntity(com.sjtu.icare.modules.gero.entity.GeroElderExchangeEntity)
	 */
	@Override
	public GeroElderExchangeEntity getGeroElderExchangeEntity(
			GeroElderExchangeEntity elderExchangeEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(elderExchangeEntity);
		return geroElderExcahngeDAO.getGeroElderExchangeEntity(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.gero.service.IGeroElderExchangeService#insertGeroElderExchange(com.sjtu.icare.modules.gero.entity.GeroElderExchangeEntity)
	 */
	@Override
	public void insertGeroElderExchange(
			GeroElderExchangeEntity geroElderExchangeEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(geroElderExchangeEntity);
		geroElderExcahngeDAO.insertGeroElderExchange(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.gero.service.IGeroElderExchangeService#updateGeroElderExchange(com.sjtu.icare.modules.gero.entity.GeroElderExchangeEntity)
	 */
	@Override
	public void updateGeroElderExchange(
			GeroElderExchangeEntity geroElderExchangeEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(geroElderExchangeEntity);
		geroElderExcahngeDAO.updateGeroElderExchange(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.gero.service.IGeroElderExchangeService#deleteGeroElderExchangeEntity(com.sjtu.icare.modules.gero.entity.GeroElderExchangeEntity)
	 */
	@Override
	public void deleteGeroElderExchangeEntity(
			GeroElderExchangeEntity geroElderExchangeEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(geroElderExchangeEntity);
		geroElderExcahngeDAO.deleteGeroElderExchange(paramMap);
		
	}

}
