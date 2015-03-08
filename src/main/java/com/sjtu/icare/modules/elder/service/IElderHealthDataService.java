package com.sjtu.icare.modules.elder.service;

import java.sql.Date;
import java.util.List;

import com.sjtu.icare.modules.elder.entity.ElderEntity;
import com.sjtu.icare.modules.elder.entity.ElderTemperatureEntity;
import com.sjtu.icare.modules.elder.entity.ElderHeartRateEntity;

/**
 * @Description 老人体温对应的 service 类
 * @author WangQi
 * @date 2015-03-06
 */
public interface IElderHealthDataService {
	
	ElderEntity getElderEntity(int id);

	List<ElderTemperatureEntity> getElderTemperatureEntity(int elderId, Date startDay, Date endDay);

	void insertElderTemperatureRecord(int elderId, int doctorId,
			String temperature, String time);

	List<ElderHeartRateEntity> getElderHeartRateEntity(int elderId, Date startDay, Date endDay);
}