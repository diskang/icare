package com.sjtu.icare.modules.elder.persistence;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.elder.entity.ElderHeartRateEntity;
/**
 * @Description 老人心率数据的 Mapper
 * @author lzl
 * @date 2015-03-08
 */

@MyBatisDao
public interface ElderHeartRateDAO {

	List<ElderHeartRateEntity> getElderHeartRateEntityByElderidStartdayEndday(Map<String, Object> paramMap);

	void insertElderHeartRateRecordWithElderidDoctoridRateTime(
			@Param("elderId") int elderId, 
			@Param("doctorId") int doctorId, 
			@Param("rate") String rate, 
			@Param("time") String time);

}