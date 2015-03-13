/**
 * @Package com.sjtu.icare.modules.elder.persistence
 * @Description elder blood pressure DAO
 * @date Mar 8, 2015 7:39:07 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.elder.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import schemasMicrosoftComOfficeOffice.STInsetMode;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.elder.entity.ElderBloodPressureEntity;

@MyBatisDao
public interface ElderBloodPressureDAO {

	List<ElderBloodPressureEntity> getElderBloodPressureEntitiesByElderidStartdayEndday(Map<String, Object> paramMap);

	void insertElderTemperatureRecordWithElderidDoctoridDiastolicpressureSystolicpressure(
			@Param("elderId") int elderId, 
			@Param("doctorId") int doctorId, 
			@Param("diastolicPressure") String diastolicPressure,
			@Param("systolicPressure") String systolicPressure, 
			@Param("time") String time);

}
