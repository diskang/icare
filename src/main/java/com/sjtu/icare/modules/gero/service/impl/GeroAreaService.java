/**
 * @Package com.sjtu.icare.modules.gero.service.impl
 * @Description TODO
 * @date Mar 9, 2015 5:16:30 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.gero.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjtu.icare.modules.gero.entity.GeroAreaEntity;
import com.sjtu.icare.modules.gero.persistence.GeroAreaDAO;
import com.sjtu.icare.modules.gero.service.IGeroAreaService;

@Service
public class GeroAreaService implements IGeroAreaService {
	
	@Autowired
	GeroAreaDAO geroAreaDAO;
	
	@Override
	public List<GeroAreaEntity> getGeroAreas(GeroAreaEntity geroAreaEntity) {
		return geroAreaDAO.getGeroAreaEntities(geroAreaEntity);
	}
	
	@Override
	public GeroAreaEntity getGeroArea(GeroAreaEntity geroAreaEntity) {
		return geroAreaDAO.getGeroAreaEntity(geroAreaEntity);
	}
	
	@Override
	public void insertGeroAreaRecord(GeroAreaEntity geroAreaEntity) {
		geroAreaDAO.insertGeroAreaEntity(geroAreaEntity);
	}





}
