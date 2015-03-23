/**
 * @Package com.sjtu.icare.modules.op.service.impl
 * @Description TODO
 * @date Mar 23, 2015 8:00:38 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.op.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjtu.icare.modules.op.entity.CareworkEntity;
import com.sjtu.icare.modules.op.persistence.CareworkDAO;
import com.sjtu.icare.modules.op.service.IWorkService;

@Service
public class WorkService implements IWorkService {
	@Autowired
	private CareworkDAO careworkDAO;
	
	
	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.op.service.IWorkService#getCareworkEntities(com.sjtu.icare.modules.op.entity.CareworkEntity)
	 */
	@Override
	public List<CareworkEntity> getCareworkEntities(
			CareworkEntity careworkEntity) {
		return careworkEntity.getPage().setList(careworkDAO.getCareworkEntities(careworkEntity)).getList();
	}


	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.op.service.IWorkService#insertCarework(com.sjtu.icare.modules.op.entity.CareworkEntity)
	 */
	@Override
	public void insertCarework(CareworkEntity careworkEntity) {
		careworkDAO.insertCarework(careworkEntity);
	}


	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.op.service.IWorkService#updateCarework(com.sjtu.icare.modules.op.entity.CareworkEntity)
	 */
	@Override
	public void updateCarework(CareworkEntity careworkEntity) {
		careworkDAO.updateCarework(careworkEntity);
	}


	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.op.service.IWorkService#deleteCarework(com.sjtu.icare.modules.op.entity.CareworkEntity)
	 */
	@Override
	public void deleteCarework(CareworkEntity careworkEntity) {
		careworkDAO.deleteCarework(careworkEntity);
		
	}

}
