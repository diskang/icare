package com.sjtu.icare.modules.op.persisitence;

import java.util.List;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.op.entity.CareworkEntity;;

/**
 * @Description 老人护工工作职责的 Mapper
 * @author lzl
 * @date 2015-03-13
 */

@MyBatisDao
public interface CareworkDAO {

	CareworkEntity getCareworkEntityById(int id);
	
	List<CareworkEntity> getCareworkEntitiesByCarerid(int carerId);
	
	CareworkEntity getCareworkEntityByElderid(int elderId);
	
}