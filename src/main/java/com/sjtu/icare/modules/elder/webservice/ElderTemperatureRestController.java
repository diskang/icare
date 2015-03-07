package com.sjtu.icare.modules.elder.webservice;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;

import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.elder.entity.ElderEntity;
import com.sjtu.icare.modules.elder.entity.ElderTemperatureEntity;
import com.sjtu.icare.modules.elder.service.IElderHealthDataService;
import com.sjtu.icare.modules.staff.entity.StaffEntity;
import com.sjtu.icare.modules.staff.service.IStaffDataService;

/**
 * @Description 老人体温数据处理
 * @author WangQi
 * @date 2015-03-05
 */


@RestController
@RequestMapping("/elder/{eid}/temperature")
public class ElderTemperatureRestController {
	private static Logger logger = Logger.getLogger(ElderTemperatureRestController.class);

	@Autowired
	private IElderHealthDataService elderHealthDataService;
	@Autowired
	private IStaffDataService staffDataService;
	
	// UNDONE URL参数是否是 required 还没确定
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getElderTemperature(
			@PathVariable("eid") int elderId,
			@RequestParam(value="start_day", required=false) Date startDay,
			@RequestParam(value="end_day", required=false) Date endDay
			) {
		
		// 参数检查
		if (startDay == null || endDay == null) {
			String message = "未指定 queryType 参数";
			logger.warn(message);
			throw new RestException(HttpStatus.NOT_FOUND, message);
		}
		

		ElderEntity elderEntity = elderHealthDataService.getElderEntity(elderId);
		List<ElderTemperatureEntity> elderTemperatureEntityList = elderHealthDataService.getElderTemperatureEntity(elderId, startDay, endDay);

		// TODO 构造返回的 JSON
		HashMap<String, Object> resultMap = new HashMap<String, Object>(); 
		resultMap.put("id", elderId); 
		resultMap.put("name", elderEntity.getName()); 
		resultMap.put("photo", elderEntity.getPhotoUrl()); 
		     
		List<Object> tempList = new ArrayList<Object>();
		for (ElderTemperatureEntity entity : elderTemperatureEntityList) {
			HashMap<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("id", entity.getId());
			tempMap.put("temperature", entity.getTemperature());
			tempMap.put("times", entity.getTime());
			
			StaffEntity doctorEntity = entity.getDoctorEntity();
			
			if (doctorEntity != null) {
				HashMap<String, Object> tempMap2 = new HashMap<String, Object>();
				tempMap2.put("id", doctorEntity.getId());
				tempMap2.put("name", doctorEntity.getName());
				tempMap2.put("photo", doctorEntity.getPhotoUrl());
				tempMap.put("doctor", tempMap2);
			}
			
			tempList.add(tempMap);
		}

		
		resultMap.put("tpList", tempList); 
		  
		return resultMap;
	}

//	/*用void 方法必须有ResponseStatus注解*/
//	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void delete(@PathVariable("id") int id) {
//		userMapper.delete(new User(id));
//	}
//	
}
