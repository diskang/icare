package com.sjtu.icare.modules.elder.webservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.DateUtils;
import com.sjtu.icare.common.utils.ParamUtils;
import com.sjtu.icare.common.utils.ParamValidator;
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
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getElderTemperature(
			@PathVariable("eid") int elderId,
			@RequestParam(value="start_date", required=false) String startDate,
			@RequestParam(value="end_date", required=false) String endDate
			) {
		
		
		// 参数检查
		if ((startDate != null && !ParamValidator.isDate(startDate)) || (endDate != null && !ParamValidator.isDate(endDate))) {
			String message = "#" + ErrorConstants.ELDER_TEMPERATURE_GET_PARAM_INVALID + "#\n" + 
							"start_date 或 end_date 不符合日期格式:\n" +
							"[start_date=" + startDate + "]" +
							"[end_date=" + endDate + "]" +
							"\n";
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		try {
			// 参数预处理
			if (startDate == null && endDate == null) {
				Date today = new Date();
				startDate = DateUtils.formatDateTime(DateUtils.getDateStart(today));
				endDate = DateUtils.formatDateTime(DateUtils.getDateEnd(today));
			}
			if (startDate == null && endDate != null) {
				Date thatDay = ParamUtils.convertStringToDate(endDate);
				startDate = DateUtils.formatDateTime(DateUtils.getDateStart(thatDay));
				endDate = DateUtils.formatDateTime(DateUtils.getDateEnd(thatDay));
			}
			
			// 获取基础的 JSON返回
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
			
			ElderEntity elderEntity = elderHealthDataService.getElderEntity(elderId);
			List<ElderTemperatureEntity> elderTemperatureEntityList = elderHealthDataService.getElderTemperatureEntities(elderId, startDate, endDate);

			// 构造返回的 JSON
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
			resultMap.put("tp_list", tempList); 
			  
			basicReturnedJson.addEntity(resultMap);
			
			return basicReturnedJson.getMap();
			
		} catch(Exception e) {
			String message = "#" + ErrorConstants.ELDER_TEMPERATURE_GET_SERVICE_FAILED + "#\n" + 
					"获取老人温度数据失败：\n" +
					"[" + e.getMessage() + "]" +
					"\n";
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
	
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Object postElderTemperature(
			@PathVariable("eid") int elderId,
			@RequestBody String inJson
			) {	
		
		Map<String, Object> requestBodyParamMap = ParamUtils.getMapByJson(inJson, logger);

		Integer doctorId;
		String temperature;
		String time;
		
		// 参数检查
		try {
			doctorId = (Integer) requestBodyParamMap.get("doctor_id");
			temperature = (String) requestBodyParamMap.get("temperature");
			time = (String) requestBodyParamMap.get("time");
			
			if (doctorId == null || temperature == null || time == null)
				throw new Exception();
			
			// 参数详细验证
			if (!ParamValidator.isTemperature(temperature) || !ParamValidator.isDate(time)) 
				throw new Exception();
			
		} catch(Exception e) {
			String message = "#" + ErrorConstants.ELDER_TEMPERATURE_INSERT_PARAM_INVALID + "#\n" + 
					"非法参数:\n" +
					"[doctor_id=" + requestBodyParamMap.get("doctor_id") + "]" +
					"[temperature=" + requestBodyParamMap.get("temperature") + "]" +
					"[time=" + requestBodyParamMap.get("time") + "]" +
					"\n";
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}

		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			elderHealthDataService.insertElderTemperatureRecord(elderId, doctorId, temperature, time);
		} catch(Exception e) {
			String message = "#" + ErrorConstants.ELDER_TEMPERATURE_INSERT_SERVICE_FAILED + "#\n" + 
					"非法参数:\n" +
					"[" + e.getMessage() + "]" +
					"\n";
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}

}
