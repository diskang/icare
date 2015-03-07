package com.sjtu.icare.modules.elder.persistence;


import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.elder.entity.ElderTemperatureEntity;
/**
 * @Description 老人体温数据的 Mapper
 * @author WangQi
 * @date 2015-03-05
 */

@MyBatisDao
public interface ElderTemperatureEntityDAO {

	List<ElderTemperatureEntity> getElderTemperatureEntityByElderidStartdayEndday(@Param("elderId") int elderId, @Param("startDay") Date startDay, @Param("endDay") Date endDay);


}