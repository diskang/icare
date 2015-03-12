package com.sjtu.icare.modules.op.persisitence;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.op.entity.CareworkRecordEntity;;

/**
 * @Description 老人护理项目记录的 Mapper
 * @author lzl
 * @date 2015-03-12
 */

@MyBatisDao
public interface CareworkRecordDAO {

	CareworkRecordEntity getCareworkRecordEntityById(int id);
	
}