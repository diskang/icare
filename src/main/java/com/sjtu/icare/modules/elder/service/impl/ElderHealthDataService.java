package com.sjtu.icare.modules.elder.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjtu.icare.modules.elder.entity.ElderBloodPressureEntity;
import com.sjtu.icare.modules.elder.entity.ElderEntity;
import com.sjtu.icare.modules.elder.entity.ElderHeartRateEntity;
import com.sjtu.icare.modules.elder.entity.ElderTemperatureEntity;
import com.sjtu.icare.modules.elder.persistence.ElderBloodPressureEntityDAO;
import com.sjtu.icare.modules.elder.persistence.ElderEntityDAO;
import com.sjtu.icare.modules.elder.persistence.ElderHeartRateEntityDAO;
import com.sjtu.icare.modules.elder.persistence.ElderTemperatureEntityDAO;
import com.sjtu.icare.modules.elder.service.IElderHealthDataService;

/**
 * @Description 老人信息的 service 类（接口如果有重载会在这一层做相应的封装）
 * @author WangQi
 * @date 2015-03-06
 */


@Service
public class ElderHealthDataService implements IElderHealthDataService {
	@Autowired
	private ElderEntityDAO elderEntityDAO;
	@Autowired
	private ElderTemperatureEntityDAO elderTemperatureEntityDAO;
	@Autowired
	private ElderBloodPressureEntityDAO elderBloodPressureEntityDAO;
	@Autowired
	private ElderHeartRateEntityDAO elderHeartRateEntityDAO;

	@Override
	public ElderEntity getElderEntity(int id) {
		return elderEntityDAO.getElderEntityById(id);
	}

	@Override
	public List<ElderTemperatureEntity> getElderTemperatureEntities(int elderId, String startDate, String endDate) {
		return elderTemperatureEntityDAO.getElderTemperatureEntitiesByElderidStartdayEndday(elderId, startDate, endDate);
	}

	@Override
	public void insertElderTemperatureRecord(int elderId, int doctorId, String temperature, String time) {
		elderTemperatureEntityDAO.insertElderTemperatureRecordWithElderidDoctoridTemperatureTime(elderId, doctorId, temperature, time);
	}

	@Override
	public List<ElderBloodPressureEntity> getElderBloodPressureEntities(
			int elderId, String startDate, String endDate) {
		return elderBloodPressureEntityDAO.getElderBloodPressureEntitiesByElderidStartdayEndday(elderId, startDate, endDate);
	}

	@Override
	public void insertElderBloodPressureRecord(int elderId, Integer doctorId,
			String diastolicPressure, String systolicPressure, String time) {
		elderBloodPressureEntityDAO.insertElderTemperatureRecordWithElderidDoctoridDiastolicpressureSystolicpressure(elderId, doctorId,
				diastolicPressure, systolicPressure, time);
	}

	@Override
	public List<ElderHeartRateEntity> getElderHeartRateEntity(int elderId, String startDate, String endDate) {
		return elderHeartRateEntityDAO.getElderHeartRateEntityByElderidStartdayEndday(elderId, startDate, endDate);
	}

	@Override
	public void insertElderHeartRateRecord(int elderId, Integer doctorId,
			String heartRate, String time) {
		elderHeartRateEntityDAO.insertElderHeartRateRecordWithElderidDoctoridRateTime(elderId, doctorId,
				heartRate, time);
	
	}
	
}