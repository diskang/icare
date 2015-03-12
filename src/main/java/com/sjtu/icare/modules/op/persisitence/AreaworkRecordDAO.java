package com.sjtu.icare.modules.op.persisitence;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.op.entity.AreaworkRecordEntity;;

/**
 * @Description 区域护理项目记录的 Mapper
 * @author lzl
 * @date 2015-03-12
 */

@MyBatisDao
public interface AreaworkRecordDAO {

	AreaworkRecordEntity getAreaworkRecordEntityById(int id);
	
}