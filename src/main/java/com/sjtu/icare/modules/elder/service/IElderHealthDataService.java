/**
 * @Package com.sjtu.icare.modules.elder.service
 * @Description TODO
 * @date Mar 5, 2015 7:45:02 PM
 * @author Wang Qi
 * @version TODO
 */
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

	List<ElderHeartRateEntity> getElderHeartRateEntity(int elderId, Date startDay, Date endDay);
}
