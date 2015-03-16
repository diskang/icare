package com.sjtu.icare.modules.op.persistence;

import java.util.List;
import java.util.Map;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.op.entity.AreaworkEntity;

/**
 * @Description 房间护工工作职责的 Mapper
 * @author lzl
 * @date 2015-03-13
 */

@MyBatisDao
public interface AreaworkDAO {

	AreaworkEntity getCareworkEntityById(Map<String, Object> AreaworkEntity);
	
	List<AreaworkEntity> getCareworkEntitiesByCarerid(Map<String, Object> AreaworkEntity);
	
	AreaworkEntity getCareworkEntityByElderid(Map<String, Object> AreaworkEntity);
	
}