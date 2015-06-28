package com.sjtu.icare.modules.op.persistence;

import java.util.List;
import java.util.Map;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.op.entity.CareworkRecordEntity;;

/**
 * @Description 老人护理项目记录的 Mapper
 * @author lzl
 * @date 2015-03-12
 */

@MyBatisDao
public interface CareworkRecordDAO {

	/**
	 * @Title getCareworkRecords
	 * @Description TODO
	 * @param @param paramMap
	 * @param @return
	 * @return List<CareworkRecordEntity>
	 * @throws
	 */
	List<CareworkRecordEntity> getCareworkRecords(CareworkRecordEntity careworkRecordEntity);

	/**
	 * @Title insertCareworkRecords
	 * @Description TODO
	 * @param @param paramList
	 * @return void
	 * @throws
	 */
	void insertCareworkRecords(List<Map<String, Object>> paramList);

	/**
	 * @Title getLatestCareworkRecords
	 * @Description TODO
	 * @param @param paramMap
	 * @param @return
	 * @return List<CareworkRecordEntity>
	 * @throws
	 */
	List<CareworkRecordEntity> getLatestCareworkRecords(CareworkRecordEntity careworkRecordEntity);
	
}