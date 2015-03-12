/**
 * @Package com.sjtu.icare.modules.elder.webservice
 * @Description TODO
 * @date Mar 8, 2015 6:59:49 PM
 * @author Wang Qi
 * @version TODO
 */
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
import com.sjtu.icare.modules.elder.entity.ElderBloodPressureEntity;
import com.sjtu.icare.modules.elder.entity.ElderEntity;
import com.sjtu.icare.modules.elder.service.IElderHealthDataService;
import com.sjtu.icare.modules.staff.entity.StaffEntity;
import com.sjtu.icare.modules.staff.service.IStaffDataService;

@RestController
@RequestMapping("/elder/{eid}/blood_pressure")
public class ElderBloodPressureRestController {
	private static Logger logger = Logger.getLogger(ElderTemperatureRestController.class);

	@Autowired
	private IElderHealthDataService elderHealthDataService;
	@Autowired
	private IStaffDataService staffDataService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getElderBloodPressure(
			@PathVariable("eid") int elderId,
			@RequestParam(value="start_date", required=false) String startDate,
			@RequestParam(value="end_date", required=false) String endDate
			) {

		// 参数检查
		if ((startDate != null && !ParamValidator.isDate(startDate)) || (endDate != null && !ParamValidator.isDate(endDate))) {
			String otherMessage = "start_date 或 end_date 不符合日期格式:\n" +
					"[start_date=" + startDate + "]" +
					"[end_date=" + endDate + "]";
			String message = ErrorConstants.format(ErrorConstants.ELDER_BLOOD_PRESSURE_GET_PARAM_INVALID, otherMessage);
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
			List<ElderBloodPressureEntity> elderBloodPressureEntityList = elderHealthDataService.getElderBloodPressureEntities(elderId, startDate, endDate);
	
			// 构造返回的 JSON
			Map<String, Object> resultMap = new HashMap<String, Object>(); 
			resultMap.put("id", elderId); 
			resultMap.put("name", elderEntity.getName()); 
			resultMap.put("photo", elderHealthDataService.getElderPhotoUrl()); 
			     
			List<Object> tempList = new ArrayList<Object>();
			for (ElderBloodPressureEntity entity : elderBloodPressureEntityList) {
				HashMap<String, Object> tempMap = new HashMap<String, Object>();
				tempMap.put("id", entity.getId());
				tempMap.put("diastolic_pressure", entity.getDiastolicPressure());
				tempMap.put("systolic_pressure", entity.getSystolicPressure());
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
			resultMap.put("bp_list", tempList); 
			  
			basicReturnedJson.addEntity(resultMap);
			
			return basicReturnedJson.getMap();
			
		} catch(Exception e) {
			String otherMessage = "获取老人血压数据失败：\n" +	 "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.ELDER_BLOOD_PRESSURE_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Object postElderBloodPressure(
			@PathVariable("eid") int elderId,
			@RequestBody String inJson
			) {	
		
		Map<String, Object> requestBodyParamMap = ParamUtils.getMapByJson(inJson, logger);
		
		Integer doctorId;
		String diastolicPressure;
		String systolicPressure;
		String time;
		
		// 参数检查
		try {
			doctorId = (Integer) requestBodyParamMap.get("doctor_id");
			diastolicPressure = (String) String.valueOf(requestBodyParamMap.get("diastolic_pressure"));
			systolicPressure = (String) String.valueOf(requestBodyParamMap.get("systolic_pressure"));
			time = (String) requestBodyParamMap.get("time");
						
			if (doctorId == null || diastolicPressure == null || systolicPressure == null || time == null)
				throw new Exception();
			
			// 参数详细验证
			if (!ParamValidator.isDecimal(diastolicPressure) || !ParamValidator.isDecimal(systolicPressure) || !ParamValidator.isDate(time)) 
				throw new Exception();
			
		} catch(Exception e) {
			String otherMessage = "非法参数:\n" +
					"[diastolic_pressure=" + requestBodyParamMap.get("diastolic_pressure") + "]" +
					"[systolic_pressure=" + requestBodyParamMap.get("systolic_pressure") + "]" +
					"[time=" + requestBodyParamMap.get("time") + "]";
			String message = ErrorConstants.format(ErrorConstants.ELDER_BLOOD_PRESSURE_POST_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			elderHealthDataService.insertElderBloodPressureRecord(elderId, doctorId, diastolicPressure, systolicPressure, time);
		} catch(Exception e) {
			String otherMessage = "非法参数:" + "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.ELDER_BLOOD_PRESSURE_POST_SERVICE_FAILED, otherMessage);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}

}
