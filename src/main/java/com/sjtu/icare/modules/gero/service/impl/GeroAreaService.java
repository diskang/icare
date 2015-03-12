/**
 * @Package com.sjtu.icare.modules.gero.service.impl
 * @Description TODO
 * @date Mar 9, 2015 5:16:30 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.gero.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjtu.icare.common.utils.CommonUtils;
import com.sjtu.icare.modules.gero.entity.GeroAreaEntity;
import com.sjtu.icare.modules.gero.persistence.GeroAreaDAO;
import com.sjtu.icare.modules.gero.service.IGeroAreaService;

@Service
public class GeroAreaService implements IGeroAreaService {
	
	@Autowired
	GeroAreaDAO geroAreaDAO;
	
	@Override
	public List<GeroAreaEntity> getGeroAreas(GeroAreaEntity geroAreaEntity) {
		Map<String, Object> paramMap = CommonUtils.beanToMap(geroAreaEntity);
		return geroAreaDAO.getGeroAreaEntities(paramMap);
	}
	
	@Override
	public GeroAreaEntity getGeroArea(GeroAreaEntity geroAreaEntity) {
		Map<String, Object> paramMap = CommonUtils.beanToMap(geroAreaEntity);
		return geroAreaDAO.getGeroAreaEntity(paramMap);
	}
	
	@Override
	public void insertGeroAreaRecord(GeroAreaEntity geroAreaEntity) {
		Map<String, Object> paramMap = CommonUtils.beanToMap(geroAreaEntity);
		geroAreaDAO.insertGeroAreaEntity(paramMap);
	}

	@Override
	public List<GeroAreaEntity> getGeroSubareas(
			GeroAreaEntity ancestorGeroAreaEntity, Integer subLevel) {
		Map<String, Object> paramMap = CommonUtils.beanToMap(ancestorGeroAreaEntity);
		paramMap.put("subLevel", subLevel);
		return geroAreaDAO.getGeroSubareas(paramMap);
	}

	@Override
	public void updateGeroAreaRecord(GeroAreaEntity postEntity) {
		Map<String, Object> paramMap = CommonUtils.beanToMap(postEntity);
		geroAreaDAO.updateGeroAreaEntity(paramMap);
	}





}
