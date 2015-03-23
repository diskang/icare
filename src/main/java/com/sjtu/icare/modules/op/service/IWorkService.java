/**
 * @Package com.sjtu.icare.modules.op.service
 * @Description TODO
 * @date Mar 23, 2015 7:47:52 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.op.service;

import java.util.List;

import com.sjtu.icare.modules.op.entity.CareworkEntity;

public interface IWorkService {

	/**
	 * @Title getCareworkEntities
	 * @Description TODO
	 * @param @param careworkEntity
	 * @param @return
	 * @return List<CareworkEntity>
	 * @throws
	 */
	List<CareworkEntity> getCareworkEntities(
			CareworkEntity careworkEntity);

	/**
	 * @Title insertCarework
	 * @Description TODO
	 * @param @param requestCareworkEntity
	 * @return void
	 * @throws
	 */
	void insertCarework(CareworkEntity careworkEntity);
	
}
