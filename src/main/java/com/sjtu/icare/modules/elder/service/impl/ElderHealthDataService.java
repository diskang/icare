package com.sjtu.icare.modules.elder.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjtu.icare.modules.elder.entity.ElderEntity;
import com.sjtu.icare.modules.elder.entity.ElderTemperatureEntity;
import com.sjtu.icare.modules.elder.persistence.ElderEntityDAO;
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

	@Override
	public ElderEntity getElderEntity(int id) {
		return elderEntityDAO.getElderEntityById(id);
	}

	@Override
	public List<ElderTemperatureEntity> getElderTemperatureEntity(int elderId, String startDay, String endDay) {
		return elderTemperatureEntityDAO.getElderTemperatureEntityByElderidStartdayEndday(elderId, startDay, endDay);
	}

	@Override
	public void insertElderTemperatureRecord(int elderId, int doctorId, String temperature, String time) {
		elderTemperatureEntityDAO.insertElderTemperatureRecordWithElderidDoctoridTemperatureTime(elderId, doctorId, temperature, time);
	}


	
}