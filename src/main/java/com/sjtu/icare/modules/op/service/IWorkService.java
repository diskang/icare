/**
 * @Package com.sjtu.icare.modules.op.service
 * @Description TODO
 * @date Mar 23, 2015 7:47:52 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.op.service;

import java.util.List;

import com.sjtu.icare.modules.op.entity.AreaworkEntity;
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

	/**
	 * @Title updateCarework
	 * @Description TODO
	 * @param @param requestCareworkEntity
	 * @return void
	 * @throws
	 */
	void updateCarework(CareworkEntity careworkEntity);

	/**
	 * @Title deleteCarework
	 * @Description TODO
	 * @param @param requestCareworkEntity
	 * @return void
	 * @throws
	 */
	void deleteCarework(CareworkEntity careworkEntity);

	/**
	 * @Title getAreaworkEntities
	 * @Description TODO
	 * @param @param requestAreaworkEntity
	 * @param @return
	 * @return List<AreaworkEntity>
	 * @throws
	 */
	List<AreaworkEntity> getAreaworkEntities(
			AreaworkEntity areaworkEntity);

	/**
	 * @Title insertAreawork
	 * @Description TODO
	 * @param @param areaworkEntity
	 * @return void
	 * @throws
	 */
	void insertAreawork(AreaworkEntity areaworkEntity);

	/**
	 * @Title updateAreawork
	 * @Description TODO
	 * @param @param areaworkEntity
	 * @return void
	 * @throws
	 */
	void updateAreawork(AreaworkEntity areaworkEntity);

	/**
	 * @Title deleteAreawork
	 * @Description TODO
	 * @param @param areaworkEntity
	 * @return void
	 * @throws
	 */
	void deleteAreawork(AreaworkEntity areaworkEntity);
	
}
