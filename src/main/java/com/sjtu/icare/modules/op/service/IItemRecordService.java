/**
 * @Package com.sjtu.icare.modules.op.service
 * @Description TODO
 * @date Mar 18, 2015 1:42:15 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.op.service;

import java.util.List;

import com.sjtu.icare.modules.op.entity.AreaworkRecordEntity;
import com.sjtu.icare.modules.op.entity.CareworkRecordEntity;

public interface IItemRecordService {

	/**
	 * @Title getCareworkRecords
	 * @Description TODO
	 * @param @param careworkRecordEntity
	 * @param @param startDate
	 * @param @param endDate
	 * @param @return
	 * @return List<CareworkRecordEntity>
	 * @throws
	 */
	List<CareworkRecordEntity> getCareworkRecords(
			CareworkRecordEntity careworkRecordEntity, String startDate,
			String endDate);
	/**
	 * @Title getCareworkRecords
	 * @Description 获取最近有记录的那天的记录
	 * @param @param careworkRecordEntity
	 * @param @param startDate
	 * @param @param endDate
	 * @param @return
	 * @return List<CareworkRecordEntity>
	 * @throws
	 */
	List<CareworkRecordEntity> getLatestCareworkRecords(
			CareworkRecordEntity careworkRecordEntity);

	/**
	 * @Title insertCareworkRecords
	 * @Description TODO
	 * @param @param postEntities
	 * @return void
	 * @throws
	 */
	void insertCareworkRecords(List<CareworkRecordEntity> careworkRecordEntities);

	/**
	 * @Title getAreaworkRecords
	 * @Description TODO
	 * @param @param areaworkRecordEntity
	 * @param @param startDate
	 * @param @param endDate
	 * @param @return
	 * @return List<AreaworkRecordEntity>
	 * @throws
	 */
	List<AreaworkRecordEntity> getAreaworkRecords(
			AreaworkRecordEntity areaworkRecordEntity, String startDate,
			String endDate);

	/**
	 * @Title insertAreaworkRecords
	 * @Description TODO
	 * @param @param areaworkRecordEntities
	 * @return void
	 * @throws
	 */
	void insertAreaworkRecords(List<AreaworkRecordEntity> areaworkRecordEntities);

}
