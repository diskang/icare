/**
 * @Package com.sjtu.icare.modules.op.service
 * @Description TODO
 * @date Mar 17, 2015 3:44:14 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.op.service;

import java.util.List;

import com.sjtu.icare.modules.op.entity.CareItemEntity;

public interface IItemService {

	/**
	 * @Title getCareItems
	 * @Description TODO
	 * @param @param careItemEntity
	 * @param @return
	 * @return List<CareItemEntity>
	 * @throws
	 */
	List<CareItemEntity> getCareItems(CareItemEntity careItemEntity);

	/**
	 * @Title getCareItem
	 * @Description TODO
	 * @param @param careItemEntity
	 * @param @return
	 * @return CareItemEntity
	 * @throws
	 */
	CareItemEntity getCareItem(CareItemEntity careItemEntity);

	/**
	 * @Title deleteCareItem
	 * @Description TODO
	 * @param @param careItemEntity
	 * @return void
	 * @throws
	 */
	void deleteCareItem(CareItemEntity careItemEntity);

	/**
	 * @Title insertCareItem
	 * @Description TODO
	 * @param @param postEntity
	 * @return void
	 * @throws
	 */
	void insertCareItem(CareItemEntity careItemEntity);

	/**
	 * @Title updateCareItem
	 * @Description TODO
	 * @param @param careItemEntity
	 * @return void
	 * @throws
	 */
	void updateCareItem(CareItemEntity careItemEntity);

}
