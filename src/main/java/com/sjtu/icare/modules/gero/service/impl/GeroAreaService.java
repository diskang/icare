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

import com.sjtu.icare.modules.gero.entity.GeroAreaEntity;
import com.sjtu.icare.modules.gero.persistence.GeroAreaEntityDAO;
import com.sjtu.icare.modules.gero.service.IGeroAreaService;

public class GeroAreaService implements IGeroAreaService {

	@Autowired
	GeroAreaEntityDAO geroAreaEntityDAO;
	
	@Override
	public List<GeroAreaEntity> getGeroAreas(int geroId) {
		return geroAreaEntityDAO.getGeroEntityById(geroId);
	}

	@Override
	public void insertGeroAreaRecord(Integer parentId, Integer type,
			Integer level, String name) {
	}

}
