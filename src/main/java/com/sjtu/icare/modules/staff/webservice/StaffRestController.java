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
import com.sjtu.icare.modules.staff.entity.StaffEntity;
import com.sjtu.icare.modules.staff.entity.StaffSchedulePlanEntity;
import com.sjtu.icare.modules.staff.service.IStaffDataService;
import com.sjtu.icare.modules.sys.entity.Role;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;
import com.sjtu.icare.modules.sys.utils.UserUtils;

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
				users = null;
				//users = staffDataService.getAllStaffs(queryUser, role);
			
			
			for (User user : users) {
				Map<String, Object> resultMap = new HashMap<String, Object>(); 
				resultMap.put("id", user.getId()); 
				resultMap.put("name", user.getName()); 
				resultMap.put("phone", user.getPhoneNo()); 
				resultMap.put("phone", user.getPhoneNo()); 
				resultMap.put("email", user.getEmail()); 
				resultMap.put("identity_no", user.getIdentityNo()); 
				resultMap.put("birthday", user.getBirthday()); 
				resultMap.put("gender", user.getGender()); 
				resultMap.put("residence_address", user.getResidenceAddress()); 
				resultMap.put("household_address", user.getHouseholdAddress()); 
				
				StaffEntity queryStaffEntity = new StaffEntity();
				queryStaffEntity.setId(user.getUserId());
				StaffEntity staffEntity = staffDataService.getStaffEntity(queryStaffEntity);
				if (staffEntity == null)
					throw new Exception("内部错误： user 找不到对应的 staff");
				resultMap.put("nssf", staffEntity.getNssfId()); 
				resultMap.put("leave_date", staffEntity.getLeaveDate()); 
				resultMap.put("archive_id", staffEntity.getArchiveId()); 
				
				User tempUser = UserUtils.get(user.getId());
				List<Role> roleList = tempUser.getRoleList();
				List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
				for (Role tempRole : roleList) {
					Map<String, Object> tempMap = new HashMap<String, Object>();
					tempMap.put("role_id", tempRole.getId());
					tempMap.put("role_name", tempRole.getName());
					tempList.add(tempMap);
				}
				resultMap.put("role_list", tempList);
				
				basicReturnedJson.addEntity(resultMap);
			}
			
			return basicReturnedJson.getMap();
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.STAFF_DATA_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	}
	
}
