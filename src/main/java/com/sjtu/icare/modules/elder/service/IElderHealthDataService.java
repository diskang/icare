/**
 * @Package com.sjtu.icare.modules.elder.service
 * @Description TODO
 * @date Mar 5, 2015 7:45:02 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.elder.service;

import java.util.List;

import com.sjtu.icare.modules.elder.entity.ElderBloodPressureEntity;
import com.sjtu.icare.modules.elder.entity.ElderEntity;
import com.sjtu.icare.modules.elder.entity.ElderTemperatureEntity;


/**
 * @Description 老人体温对应的 service 类
 * @author WangQi
 * @date 2015-03-06
 */
public interface IElderHealthDataService {
	
	ElderEntity getElderEntity(int id);

	List<ElderTemperatureEntity> getElderTemperatureEntities(int elderId, String startDay, String endDay);

	void insertElderTemperatureRecord(int elderId, int doctorId,
			String temperature, String time);

	List<ElderBloodPressureEntity> getElderBloodPressureEntities(int elderId,
			String startDate, String endDate);

	void insertElderBloodPressureRecord(int elderId, Integer doctorId,
			String diastolicPressure, String systolicPressure, String time);

}
