/**
 * @Package com.sjtu.icare.modules.elder.persistence
 * @Description elder blood pressure DAO
 * @date Mar 8, 2015 7:39:07 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.elder.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.elder.entity.ElderBloodPressureEntity;

@MyBatisDao
public interface ElderBloodPressureDAO {

	List<ElderBloodPressureEntity> getElderBloodPressureEntitiesByElderidStartdayEndday(
			@Param("elderId") int elderId, 
			@Param("startDate") String startDate, 
			@Param("endDate") String endDate);

	void insertElderTemperatureRecordWithElderidDoctoridDiastolicpressureSystolicpressure(
			@Param("elderId") int elderId, 
			@Param("doctorId") int doctorId, 
			@Param("diastolicPressure") String diastolicPressure,
			@Param("systolicPressure") String systolicPressure, 
			@Param("time") String time);

}
