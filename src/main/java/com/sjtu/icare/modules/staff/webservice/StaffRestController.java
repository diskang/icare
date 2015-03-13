/**
 * @Package com.sjtu.icare.modules.staff.webservice
 * @Description TODO
 * @date Mar 12, 2015 10:08:07 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.staff.webservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
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
import com.sjtu.icare.common.utils.CommonUtils;
import com.sjtu.icare.common.utils.DateUtils;
import com.sjtu.icare.common.utils.ParamUtils;
import com.sjtu.icare.common.utils.ParamValidator;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.staff.entity.StaffEntity;
import com.sjtu.icare.modules.staff.entity.StaffSchedulePlanEntity;
import com.sjtu.icare.modules.staff.service.IStaffDataService;

@RestController
@RequestMapping("/staff/{sid}/schedule")
public class StaffRestController {
	private static Logger logger = Logger.getLogger(StaffRestController.class);
	
	@Autowired
	IStaffDataService staffDataService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getStaffSchedulePlan(
			@PathVariable("sid") int staffId,
			@RequestParam(value="start_date", required=false) String startDate,
			@RequestParam(value="end_date", required=false) String endDate
			) {
		
		// 参数检查
		if ((startDate != null && !ParamValidator.isDate(startDate)) || (endDate != null && !ParamValidator.isDate(endDate))) {
			String otherMessage = "start_date 或 end_date 不符合日期格式:" +
					"[start_date=" + startDate + "]" +
					"[end_date=" + endDate + "]";
			String message = ErrorConstants.format(ErrorConstants.STAFF_SCHEDULE_PLAN_GET_PARAM_INVALID, otherMessage);
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
			
			StaffSchedulePlanEntity queryStaffSchedulePlanEntity = new StaffSchedulePlanEntity();
			queryStaffSchedulePlanEntity.setStaffId(staffId);
			List<StaffSchedulePlanEntity> staffSchedulePlanEntities = staffDataService.getStaffSchedulePlans(queryStaffSchedulePlanEntity, startDate, endDate);

			// 构造返回的 JSON
			Map<String, Object> resultMap = new HashMap<String, Object>(); 
			resultMap.put("id", staffId); 
			     
			List<Object> tempList = new ArrayList<Object>();
			for (StaffSchedulePlanEntity entity : staffSchedulePlanEntities) {
				tempList.add(entity.getWorkDate());
			}
			resultMap.put("work_date", tempList); 
			  
			basicReturnedJson.addEntity(resultMap);
			
			return basicReturnedJson.getMap();
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.STAFF_SCHEDULE_PLAN_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Object postStaffSchedulePlan(
			@PathVariable("sid") int staffId,
			@RequestBody String inJson
			) {	
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		tempRquestParamMap.put("staffId", staffId);
		Map<String, Object> requestParamMap = CommonUtils.convertMapToCamelStyle(tempRquestParamMap);
		
		List<String> workDate;
		List<String> noworkDate;
		
		try {
			workDate = (List<String>) requestParamMap.get("workDate");
			noworkDate = (List<String>) requestParamMap.get("noworkDate");
			
			// 参数详细验证
			// work_date, nowork_date 不能有交集
			HashMap<String, Boolean> bin = new HashMap<String, Boolean>();
			for (String date : workDate) {
				if (!ParamValidator.isDate(date))
					throw new Exception();
				bin.put(date.trim(), true);
			}
			for (String date : noworkDate) {
				if (!ParamValidator.isDate(date))
					throw new Exception();
				if (bin.get(date.trim()) != null)
					throw new Exception();
			}
		} catch(Exception e) {
			String otherMessage = "[work_date=" + requestParamMap.get("workDate") + "]" +
					"[nowork_date=" + requestParamMap.get("noworkDate") + "]";
			String message = ErrorConstants.format(ErrorConstants.STAFF_SCHEDULE_PLAN_POST_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			// 获取 geroId
			StaffEntity queryStaffEntity = new StaffEntity();
			queryStaffEntity.setId(staffId);
			StaffEntity staffEntity = staffDataService.getStaffEntity(queryStaffEntity);
			int geroId = staffEntity.getGeroId();
			
			StaffSchedulePlanEntity postEntity = new StaffSchedulePlanEntity(); 
			BeanUtils.populate(postEntity, requestParamMap);
			postEntity.setGeroId(geroId);
			if (!workDate.isEmpty())
				staffDataService.insertStaffSchedulePlans(postEntity, workDate);
			if (!noworkDate.isEmpty())
				staffDataService.deleteStaffSchedulePlans(postEntity, noworkDate);
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.STAFF_SCHEDULE_PLAN_POST_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
	
}
