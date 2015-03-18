/**
 * @Package com.sjtu.icare.modules.staff.webservice
 * @Description TODO
 * @date Mar 18, 2015 4:18:59 PM
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
import com.sjtu.icare.modules.staff.entity.StaffSchedulePlanEntity;
import com.sjtu.icare.modules.staff.service.IStaffDataService;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;

@RestController
@RequestMapping("/gero/{gid}/staff")
public class StaffRestController {
	private static Logger logger = Logger.getLogger(StaffRestController.class);
	
	@Autowired
	IStaffDataService staffDataService;
	@Autowired
	SystemService systemService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getStaffList(
			@PathVariable("gid") int geroId,
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="gender", required=false) String gender,
			@RequestParam(value="identity_no", required=false) String identityNo,
			@RequestParam(value="role", required=false) String role
			) {
		
		// 参数检查
		if (gender != null && !(gender.equals("0") || gender.equals("1"))) {
			String otherMessage = "name 不符合格式:" +
					"[name=" + name + "]";
			String message = ErrorConstants.format(ErrorConstants.STAFF_DATA_GET_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		try {
			// 获取基础的 JSON返回
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
			
			
			User queryUser = new User();
			queryUser.setName(name);
			queryUser.setGender(gender);
			queryUser.setIdentityNo(identityNo);
			List<User> users;
			if (role == null)
				users = staffDataService.getAllStaffs(queryUser);
			else
				users = staffDataService.getAllStaffs(queryUser, role);
			
			for (User user : users) {
				
			}
			
			
			
			staffDataService.g
			
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
			String message = ErrorConstants.format(ErrorConstants.STAFF_DATA_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	}
	
}
