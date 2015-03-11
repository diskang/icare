/**
 * @Package com.sjtu.icare.modules.gero.service.impl
 * @Description TODO
 * @date Mar 9, 2015 5:16:30 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.gero.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.elder.webservice.ElderTemperatureRestController;
import com.sjtu.icare.modules.gero.entity.GeroAreaEntity;
import com.sjtu.icare.modules.gero.persistence.GeroAreaDAO;
import com.sjtu.icare.modules.gero.service.IGeroAreaService;

public class GeroAreaService implements IGeroAreaService {
	private static Logger logger = Logger.getLogger(ElderTemperatureRestController.class);
	
	@Autowired
	GeroAreaDAO geroAreaDAO;
	
	@Override
	public List<GeroAreaEntity> getGeroAreas(int geroId) {
		return geroAreaDAO.getGeroAreaEntities(geroId);
	}

	@Override
	public void insertGeroAreaRecord(Integer parentId, Integer type,
			Integer level, String name) {
	}



}
