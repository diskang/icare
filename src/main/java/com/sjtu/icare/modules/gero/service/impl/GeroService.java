package com.sjtu.icare.modules.gero.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjtu.icare.modules.gero.entity.GeroEntity;
import com.sjtu.icare.modules.gero.persistence.GeroDAO;
import com.sjtu.icare.modules.gero.service.IGeroService;
import com.sjtu.icare.modules.sys.entity.Gero;
import com.sjtu.icare.modules.sys.persistence.GeroMapper;

@Service
public class GeroService implements IGeroService {
	@Autowired
	private GeroDAO geroDAO;

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.gero.service.IGeroService#getGeros(com.sjtu.icare.modules.gero.entity.GeroEntity)
	 */
	@Override
	public List<GeroEntity> getGeros(GeroEntity geroEntity) {
		return geroEntity.getPage().setList(geroDAO.getGeros(geroEntity)).getList();
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.gero.service.IGeroService#insertGero(com.sjtu.icare.modules.gero.entity.GeroEntity)
	 */
	@Override
	public void insertGero(GeroEntity geroEntity) {
		geroDAO.insertGero(geroEntity);
		
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.gero.service.IGeroService#getGero(com.sjtu.icare.modules.gero.entity.GeroEntity)
	 */
	@Override
	public GeroEntity getGero(GeroEntity geroEntity) {
		return geroDAO.getGero(geroEntity);
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.gero.service.IGeroService#updateGero(com.sjtu.icare.modules.gero.entity.GeroEntity)
	 */
	@Override
	public void updateGero(GeroEntity geroEntity) {
		geroDAO.updateGero(geroEntity);
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.gero.service.IGeroService#deleteGero(com.sjtu.icare.modules.gero.entity.GeroEntity)
	 */
	@Override
	public void deleteGero(GeroEntity geroEntity) {
		geroDAO.deleteGero(geroEntity);
	}

}