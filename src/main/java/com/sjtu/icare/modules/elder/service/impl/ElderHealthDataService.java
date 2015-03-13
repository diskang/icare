package com.sjtu.icare.modules.elder.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjtu.icare.common.config.CommonConstants;
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
	public ElderEntity getElderEntity(int id) {
		return elderDAO.getElderEntityById(id);
	}

	@Override
	public List<ElderTemperatureEntity> getElderTemperatureEntities(int elderId, String startDate, String endDate) {
		return elderTemperatureDAO.getElderTemperatureEntitiesByElderidStartdayEndday(elderId, startDate, endDate);
	}

	@Override
	public void insertElderTemperatureRecord(int elderId, int doctorId, String temperature, String time) {
		elderTemperatureDAO.insertElderTemperatureRecordWithElderidDoctoridTemperatureTime(elderId, doctorId, temperature, time);
	}

	@Override
	public List<ElderBloodPressureEntity> getElderBloodPressureEntities(
			int elderId, String startDate, String endDate) {
		return elderBloodPressureDAO.getElderBloodPressureEntitiesByElderidStartdayEndday(elderId, startDate, endDate);
	}

	@Override
	public void insertElderBloodPressureRecord(int elderId, Integer doctorId,
			String diastolicPressure, String systolicPressure, String time) {
		elderBloodPressureDAO.insertElderTemperatureRecordWithElderidDoctoridDiastolicpressureSystolicpressure(elderId, doctorId,
				diastolicPressure, systolicPressure, time);
	}

	@Override
	public List<ElderHeartRateEntity> getElderHeartRateEntity(int elderId, String startDate, String endDate) {
		return elderHeartRateDAO.getElderHeartRateEntityByElderidStartdayEndday(elderId, startDate, endDate);
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