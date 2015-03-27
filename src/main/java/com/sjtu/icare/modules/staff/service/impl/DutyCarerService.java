/**
 * @Package com.sjtu.icare.modules.staff.service.impl
 * @Description TODO
 * @date Mar 15, 2015 6:12:20 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.staff.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjtu.icare.common.utils.MapListUtils;
import com.sjtu.icare.modules.elder.entity.ElderEntity;
import com.sjtu.icare.modules.gero.entity.GeroAreaEntity;
import com.sjtu.icare.modules.op.persistence.AreaworkDAO;
import com.sjtu.icare.modules.op.persistence.CareworkDAO;
import com.sjtu.icare.modules.staff.entity.StaffEntity;
import com.sjtu.icare.modules.staff.service.IDutyCarerService;

@Service
public class DutyCarerService implements IDutyCarerService {

	@Autowired
	CareworkDAO careworkDao;
	@Autowired
	AreaworkDAO areaworkDao;

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.staff.service.IDutyCarerService#getDutyCarerByElderIdAndDate(com.sjtu.icare.modules.elder.entity.ElderEntity, java.lang.String)
	 */
	@Override
	public List<StaffEntity> getDutyCarerByElderIdAndDate(
			ElderEntity elderEntity, String date) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(elderEntity);
		paramMap.put("date", date);
		return careworkDao.getStaffEntitiesByElderId(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.staff.service.IDutyCarerService#getDutyCarerByAreaIdAndDate(com.sjtu.icare.modules.gero.entity.GeroAreaEntity, java.lang.String)
	 */
	@Override
	public List<StaffEntity> getDutyCarerByAreaIdAndDate(
			GeroAreaEntity geroAreaEntity, String date) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(geroAreaEntity);
		paramMap.put("date", date);
		return areaworkDao.getStaffEntitiesByAreaId(paramMap);
	}
	
}
