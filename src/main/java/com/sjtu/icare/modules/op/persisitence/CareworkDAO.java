package com.sjtu.icare.modules.op.persisitence;

import java.util.List;
import java.util.Map;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.op.entity.CareworkEntity;;

/**
 * @Description 老人护工工作职责的 Mapper
 * @author lzl
 * @date 2015-03-13
 */

@MyBatisDao
public interface CareworkDAO {

	CareworkEntity getCareworkEntityById(Map<String, Object> CareworkEntity);
	
	List<CareworkEntity> getCareworkEntitiesByCarerid(Map<String, Object> CareworkEntity);
	
	CareworkEntity getCareworkEntityByElderid(Map<String, Object> CareworkEntity);
	
}