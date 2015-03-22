/**
 * @Package com.sjtu.icare.modules.op.service.impl
 * @Description TODO
 * @date Mar 18, 2015 1:43:34 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.op.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjtu.icare.common.config.CommonConstants;
import com.sjtu.icare.common.utils.MapListUtils;
import com.sjtu.icare.modules.op.entity.AreaworkRecordEntity;
import com.sjtu.icare.modules.op.entity.CareworkRecordEntity;
import com.sjtu.icare.modules.op.persistence.AreaworkRecordDAO;
import com.sjtu.icare.modules.op.persistence.CareworkRecordDAO;
import com.sjtu.icare.modules.op.service.IItemRecordService;

@Service
public class ItemRecordService implements IItemRecordService {

	@Autowired
	private CareworkRecordDAO careworkRecordDAO;
	@Autowired
	private AreaworkRecordDAO areaworkRecordDAO;
	
	
	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.op.service.IItemRecordService#getCareworkRecords(com.sjtu.icare.modules.op.entity.CareworkRecordEntity, java.lang.String, java.lang.String)
	 */
	@Override
	public List<CareworkRecordEntity> getCareworkRecords(
			CareworkRecordEntity careworkRecordEntity, String startDate,
			String endDate) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(careworkRecordEntity);
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		paramMap.put("staffType", CommonConstants.STAFF_TYPE);
		paramMap.put("elderType", CommonConstants.ELDER_TYPE);
		return careworkRecordDAO.getCareworkRecords(paramMap);
	}


	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.op.service.IItemRecordService#insertCareworkRecords(java.util.List)
	 */
	@Override
	public void insertCareworkRecords(
			List<CareworkRecordEntity> careworkRecordEntities) {
		List<Map<String, Object>> paramList = MapListUtils.listOfBeanToListOfMap(careworkRecordEntities);
		careworkRecordDAO.insertCareworkRecords(paramList);
	}


	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.op.service.IItemRecordService#getAreaworkRecords(com.sjtu.icare.modules.op.entity.AreaworkRecordEntity, java.lang.String, java.lang.String)
	 */
	@Override
	public List<AreaworkRecordEntity> getAreaworkRecords(
			AreaworkRecordEntity areaworkRecordEntity, String startDate,
			String endDate) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(areaworkRecordEntity);
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		paramMap.put("staffType", CommonConstants.STAFF_TYPE);
		return areaworkRecordDAO.getAreaworkRecords(paramMap);
	}


	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.op.service.IItemRecordService#insertAreaworkRecords(java.util.List)
	 */
	@Override
	public void insertAreaworkRecords(
			List<AreaworkRecordEntity> areaworkRecordEntities) {
		List<Map<String, Object>> paramList = MapListUtils.listOfBeanToListOfMap(areaworkRecordEntities);
		areaworkRecordDAO.insertAreaworkRecords(paramList);
	}

}
