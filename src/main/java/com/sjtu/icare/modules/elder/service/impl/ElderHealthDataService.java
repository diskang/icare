package com.sjtu.icare.modules.elder.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjtu.icare.common.config.CommonConstants;
import com.sjtu.icare.common.utils.MapListUtils;
import com.sjtu.icare.modules.elder.entity.ElderBloodPressureEntity;
import com.sjtu.icare.modules.elder.entity.ElderEntity;
import com.sjtu.icare.modules.elder.entity.ElderHeartRateEntity;
import com.sjtu.icare.modules.elder.entity.ElderTemperatureEntity;
import com.sjtu.icare.modules.elder.persistence.ElderBloodPressureDAO;
import com.sjtu.icare.modules.elder.persistence.ElderDAO;
import com.sjtu.icare.modules.elder.persistence.ElderHeartRateDAO;
import com.sjtu.icare.modules.elder.persistence.ElderTemperatureDAO;
import com.sjtu.icare.modules.elder.service.IElderHealthDataService;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;

/**
 * @Description 老人信息的 service 类（接口如果有重载会在这一层做相应的封装）
 * @author WangQi
 * @date 2015-03-06
 */


@Service
public class ElderHealthDataService implements IElderHealthDataService {
	@Autowired
	private ElderDAO elderDAO;
	@Autowired
	private ElderTemperatureDAO elderTemperatureDAO;
	@Autowired
	private ElderBloodPressureDAO elderBloodPressureDAO;
	@Autowired
	private ElderHeartRateDAO elderHeartRateDAO;
	@Autowired
	private SystemService systemService;

	@Override
	public ElderEntity getElderEntity(ElderEntity elderEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(elderEntity);
		return elderDAO.getElderEntity(paramMap);
	}

	@Override
	public List<ElderTemperatureEntity> getElderTemperatureEntities(ElderTemperatureEntity elderTemperatureEntity, String startDate, String endDate) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(elderTemperatureEntity);
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		return elderTemperatureDAO.getElderTemperatureEntitiesByElderidStartdayEndday(paramMap);
	}

	@Override
	public void insertElderTemperatureRecord(int elderId, int doctorId, String temperature, String time) {
		elderTemperatureDAO.insertElderTemperatureRecordWithElderidDoctoridTemperatureTime(elderId, doctorId, temperature, time);
	}

	@Override
	public List<ElderBloodPressureEntity> getElderBloodPressureEntities(
			ElderBloodPressureEntity elderBloodPressureEntity, String startDate, String endDate) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(elderBloodPressureEntity);
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		return elderBloodPressureDAO.getElderBloodPressureEntitiesByElderidStartdayEndday(paramMap);
	}

	@Override
	public void insertElderBloodPressureRecord(int elderId, Integer doctorId,
			String diastolicPressure, String systolicPressure, String time) {
		elderBloodPressureDAO.insertElderTemperatureRecordWithElderidDoctoridDiastolicpressureSystolicpressure(elderId, doctorId,
				diastolicPressure, systolicPressure, time);
	}

	@Override
	public List<ElderHeartRateEntity> getElderHeartRateEntity(ElderHeartRateEntity elderHeartRateEntity, String startDate, String endDate) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(elderHeartRateEntity);
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		return elderHeartRateDAO.getElderHeartRateEntityByElderidStartdayEndday(paramMap);
	}

	@Override
	public void insertElderHeartRateRecord(int elderId, Integer doctorId,
			String heartRate, String time) {
		elderHeartRateDAO.insertElderHeartRateRecordWithElderidDoctoridRateTime(elderId, doctorId,
				heartRate, time);
	
	}

	@Override
	public User getUserEntityOfElder(ElderEntity elderEntity) {
		return systemService.getUserByUserTypeAndUserId(CommonConstants.ELDER_TYPE, elderEntity.getId());
	}
	
}