package com.sjtu.icare.modules.op.persistence;

import java.util.List;
import java.util.Map;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.op.entity.AreaworkRecordEntity;;

/**
 * @Description 区域护理项目记录的 Mapper
 * @author lzl
 * @date 2015-03-12
 */

@MyBatisDao
public interface AreaworkRecordDAO {

	/**
	 * @Title getAreaworkRecords
	 * @Description TODO
	 * @param @param paramMap
	 * @param @return
	 * @return List<AreaworkRecordEntity>
	 * @throws
	 */
	List<AreaworkRecordEntity> getAreaworkRecords(AreaworkRecordEntity areaworkRecordEntity);

	/**
	 * @Title insertAreaworkRecords
	 * @Description TODO
	 * @param @param paramList
	 * @return void
	 * @throws
	 */
	void insertAreaworkRecords(List<Map<String, Object>> paramList);
	
}