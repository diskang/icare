/**
 * @Package com.sjtu.icare.modules.staff.webservice
 * @Description TODO
 * @date Mar 15, 2015 4:06:42 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.staff.webservice;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.DateUtils;
import com.sjtu.icare.common.utils.ParamUtils;
import com.sjtu.icare.common.utils.ParamValidator;
import com.sjtu.icare.common.web.rest.BasicController;
import com.sjtu.icare.common.web.rest.GeroBaseController;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.elder.entity.ElderEntity;
import com.sjtu.icare.modules.gero.entity.GeroAreaEntity;
import com.sjtu.icare.modules.staff.entity.StaffEntity;
import com.sjtu.icare.modules.staff.service.IDutyCarerService;
import com.sjtu.icare.modules.staff.service.IStaffDataService;
import com.sjtu.icare.modules.sys.entity.User;

@RestController
@RequestMapping({"${api.web}", "${api.service}"})
public class DutyCarerRestContorller extends GeroBaseController{
	private static Logger logger = Logger.getLogger(DutyCarerRestContorller.class);
	
	@Autowired
	IDutyCarerService dutyCarerService;
	@Autowired
	IStaffDataService staffDataService;
	
	@RequestMapping(value="/gero/{gid}/elder/{eid}/duty_carer", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getElderDutyCarer(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@PathVariable("eid") int elderId,
			@RequestParam(value="date", required=false) String date
			) {
		checkApi(request);
		checkGero(geroId);
		
		// 参数检查
		if (date != null && !ParamValidator.isDate(date)) {
			String otherMessage = "date 不符合日期格式:" +
					"[date=" + date + "]";
			String message = ErrorConstants.format(ErrorConstants.DUTY_CARER_ELDER_GET_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		try {
			// 参数预处理
			if (date == null) {
				Date today = new Date();
				date = DateUtils.formatDate(DateUtils.getDateStart(today));
			}
			if (date == null) {
				Date thatDay = ParamUtils.convertStringToDate(date);
				date = DateUtils.formatDate(DateUtils.getDateEnd(thatDay));
			}
			
			// 获取基础的 JSON返回
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
			
			ElderEntity queryElderEntity = new ElderEntity();
			queryElderEntity.setId(elderId);
			List<StaffEntity> dutyCarers = dutyCarerService.getDutyCarerByElderIdAndDate(queryElderEntity, date);
			
			// 构造返回的 JSON
			for (StaffEntity staffEntity : dutyCarers) {
				Map<String, Object> resultMap = new HashMap<String, Object>(); 
				resultMap.put("id", staffEntity.getId());
				
				User user = staffDataService.getUserEntityOfStaff(staffEntity);
				resultMap.put("gero_id", user.getGeroId());
				resultMap.put("name", user.getName());
				resultMap.put("phone", user.getPhoneNo());
				resultMap.put("gender", user.getGender());
				resultMap.put("photo_url", user.getPhotoUrl());
				resultMap.put("work_date", date);
				  
				basicReturnedJson.addEntity(resultMap);
			}
			
			return basicReturnedJson.getMap();
			
		} catch(Exception e) {
			e.printStackTrace();
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.DUTY_CARER_ELDER_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	}
	
	@RequestMapping(value="/gero/{gid}/area/{aid}/duty_carer", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getAreaDutyCarer(
			HttpServletRequest request,
			@PathVariable("aid") int geroId,
			@PathVariable("aid") int areaId,
			@RequestParam(value="date", required=false) String date
			) {
		checkApi(request);
		checkGero(geroId);
		
		// 参数检查
		if (date != null && !ParamValidator.isDate(date)) {
			String otherMessage = "date 不符合日期格式:" +
					"[date=" + date + "]";
			String message = ErrorConstants.format(ErrorConstants.DUTY_CARER_AREA_GET_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		try {
			// 参数预处理
			if (date == null) {
				Date today = new Date();
				date = DateUtils.formatDate(DateUtils.getDateStart(today));
			}
			if (date == null) {
				Date thatDay = ParamUtils.convertStringToDate(date);
				date = DateUtils.formatDate(DateUtils.getDateEnd(thatDay));
			}
			
			// 获取基础的 JSON返回
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
			
			GeroAreaEntity queryGeroAreaEntity = new GeroAreaEntity();
			queryGeroAreaEntity.setId(areaId);
			List<StaffEntity> dutyCarers = dutyCarerService.getDutyCarerByAreaIdAndDate(queryGeroAreaEntity, date);
			
			// 构造返回的 JSON
			for (StaffEntity staffEntity : dutyCarers) {
				Map<String, Object> resultMap = new HashMap<String, Object>(); 
				resultMap.put("id", staffEntity.getId());
				
				User user = staffDataService.getUserEntityOfStaff(staffEntity);
				resultMap.put("gero_id", user.getGeroId());
				resultMap.put("name", user.getName());
				resultMap.put("phone", user.getPhoneNo());
				resultMap.put("gender", user.getGender());
				resultMap.put("photo_url", user.getPhotoUrl());
				resultMap.put("work_date", date);
				  
				basicReturnedJson.addEntity(resultMap);
			}
			
			return basicReturnedJson.getMap();
			
		} catch(Exception e) {
			e.printStackTrace();
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.DUTY_CARER_ELDER_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	}
	
	
}
