package com.sjtu.icare.modules.elder.webservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;

import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.DateUtils;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.modules.elder.entity.ElderEntity;
import com.sjtu.icare.modules.elder.entity.ElderTemperatureEntity;
import com.sjtu.icare.modules.elder.service.IElderHealthDataService;
import com.sjtu.icare.modules.staff.entity.StaffEntity;
import com.sjtu.icare.modules.staff.service.IStaffDataService;
import com.sun.org.apache.regexp.internal.recompile;
import com.alibaba.fastjson.JSON;

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
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getElderTemperature(
			@PathVariable("eid") int elderId,
			@RequestParam(value="start_date", required=false) String startDate,
			@RequestParam(value="end_date", required=false) String endDate
			) {
		
//		return DateUtils.getDate();
		// 参数检查
//		if (startDate == null || endDate == null) {
//			String message = "未指定 queryType 参数";
//			logger.warn(message);
//			throw new RestException(HttpStatus.NOT_FOUND, message);
//		}
		// 参数预处理
		if (startDate == null && endDate == null) {
			Date today = new Date();
			startDate = DateUtils.formatDateTime(DateUtils.getDateStart(today));
			endDate = DateUtils.formatDateTime(DateUtils.getDateEnd(today));
		}

		ElderEntity elderEntity = elderHealthDataService.getElderEntity(elderId);
		List<ElderTemperatureEntity> elderTemperatureEntityList = elderHealthDataService.getElderTemperatureEntity(elderId, startDate, endDate);

		// 构造返回的 JSON
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		Map<String, Object> resultMap = new HashMap<String, Object>(); 
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
		  
		basicReturnedJson.addEntity(resultMap);
		
		return basicReturnedJson.getMap();
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Object postElderTemperature(
			@PathVariable("eid") int elderId,
			@RequestBody String inJson
			) {	
		Map<String, Object> requestBodyParamMap = (Map<String, Object>) JSON.parse(inJson);
		
		Integer doctorId = (Integer) requestBodyParamMap.get("doctor_id");
		String tempearture = (String) requestBodyParamMap.get("temperature");
		String time = (String) requestBodyParamMap.get("time");
		
		// TODO 参数检查
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			elderHealthDataService.insertElderTemperatureRecord(elderId, doctorId, tempearture, time);
		} catch(Exception e) {
			e.printStackTrace();
			basicReturnedJson.setErrno(ErrorConstants.ELDER_TEMPERATURE_INSERT_ERROR);
			basicReturnedJson.setError("无法插入老人体温数据");
		}

		return basicReturnedJson.getMap();
		
	}

}
