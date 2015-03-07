package com.sjtu.icare.modules.elder.persistence;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.elder.entity.ElderEntity;

/**
 * @Description 老人数据的 Mapper
 * @author WangQi
 * @date 2015-03-06
 */

@MyBatisDao
public interface ElderEntityDAO {

	ElderEntity getElderEntityById(int id);
	
}