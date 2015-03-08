package com.sjtu.icare.modules.elder.persistence;


import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.elder.entity.ElderHeartRateEntity;
/**
 * @Description 老人心率数据的 Mapper
 * @author lzl
 * @date 2015-03-08
 */

@MyBatisDao
public interface ElderHeartRateEntityDAO {

	List<ElderHeartRateEntity> getElderHeartRateEntityByElderidStartdayEndday(@Param("elderId") int elderId, @Param("startDay") Date startDay, @Param("endDay") Date endDay);


}