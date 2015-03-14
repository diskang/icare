/**
 * @Package com.sjtu.icare.modules.staff.webservice
 * @Description TODO
 * @date Mar 13, 2015 9:34:36 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.staff.webservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.staff.entity.StaffEntity;
import com.sjtu.icare.modules.staff.entity.StaffSchedulePlanEntity;
import com.sjtu.icare.modules.staff.service.impl.StaffDataService;
import com.sjtu.icare.modules.sys.entity.User;

@RestController
@RequestMapping("/gero/{gid}/schedule")
public class GeroStaffScheduleRestController {
	private static Logger logger = Logger.getLogger(GeroStaffScheduleRestController.class);
	
	@Autowired
	StaffDataService staffDataService;
	
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getStaffSchedulePlans(
			@PathVariable("gid") int geroId,
			@RequestParam(value="start_date", required=false) String startDate,
			@RequestParam(value="end_date", required=false) String endDate,
			@RequestParam(value="role", required=false) String role
			) {
		
		// 参数检查
		if ((startDate != null && !ParamValidator.isDate(startDate)) || (endDate != null && !ParamValidator.isDate(endDate))) {
			String otherMessage = "start_date 或 end_date 不符合日期格式:" +
					"[start_date=" + startDate + "]" +
					"[end_date=" + endDate + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_STAFF_SCHEDULE_PLAN_GET_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		try {
			// 参数预处理
			Map<String, String> tempMap = ParamUtils.getDateOfStartDateAndEndDate(startDate, endDate);
			startDate = tempMap.get("startDate");
			endDate = tempMap.get("endDate");
			
			// 获取基础的 JSON返回
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
			
			StaffSchedulePlanEntity queryStaffSchedulePlanEntity = new StaffSchedulePlanEntity();
			queryStaffSchedulePlanEntity.setGeroId(geroId);
			
			
			List<StaffSchedulePlanEntity> allStaffSchedulePlans = null;
			if (role == null)
				allStaffSchedulePlans = staffDataService.getAllStaffPlansByGeroId(queryStaffSchedulePlanEntity, startDate, endDate);
			else
				allStaffSchedulePlans = staffDataService.getAllStaffPlansByGeroId(queryStaffSchedulePlanEntity, startDate, endDate, role);

			// 构造返回的 JSON
			Map<Integer, Map<String, Object>> staffPlanMap = new HashMap<Integer, Map<String, Object>>();
			
			for (StaffSchedulePlanEntity entity : allStaffSchedulePlans) {
				
				Integer staffId = entity.getStaffId();
				if (staffPlanMap.containsKey(staffId)) {
					Map<String, Object> tempMap2 = (Map<String, Object>) staffPlanMap.get(staffId);
					List<String> tempList = (List<String>) tempMap2.get("work_date");
					tempList.add(entity.getWorkDate());
				} else {
					Map<String, Object> tempMap2 = new HashMap<String, Object>();
					tempMap2.put("staff_id", staffId);
					
					List<String> tempList = new ArrayList<String>();
					tempList.add(entity.getWorkDate());
					tempMap2.put("work_date", tempList);
					
					StaffEntity queryStaffEntity = new StaffEntity();
					queryStaffEntity.setId(staffId);
					User user = staffDataService.getUserEntityOfStaff(queryStaffEntity);
					if (user == null)
						throw new Exception("内部错误，找不到 staff 对应的 user");
					tempMap2.put("id", user.getId());
					tempMap2.put("name", user.getName());
					
					staffPlanMap.put(staffId, tempMap2);
				}
			}
			
			for (Integer key : staffPlanMap.keySet())
			  	basicReturnedJson.addEntity(staffPlanMap.get(key));
			
			return basicReturnedJson.getMap();
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_STAFF_SCHEDULE_PLAN_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	}
}
