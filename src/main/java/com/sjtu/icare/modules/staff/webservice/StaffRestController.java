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

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.icare.common.config.CommonConstants;
import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.persistence.Page;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.DateUtils;
import com.sjtu.icare.common.utils.MapListUtils;
import com.sjtu.icare.common.utils.ParamUtils;
import com.sjtu.icare.common.web.rest.BasicController;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.staff.entity.StaffEntity;
import com.sjtu.icare.modules.staff.service.IStaffDataService;
import com.sjtu.icare.modules.sys.entity.Privilege;
import com.sjtu.icare.modules.sys.entity.Role;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;
import com.sjtu.icare.modules.sys.utils.UserUtils;

@RestController
@RequestMapping({"${api.web}/gero/{gid}/staff", "${api.service}/gero/{gid}/staff"})
public class StaffRestController extends BasicController {
	private static Logger logger = Logger.getLogger(StaffRestController.class);
	
	@Autowired
	IStaffDataService staffDataService;
	@Autowired
	SystemService systemService;
	
	// TODO Paging
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getStaffs(
			@PathVariable("gid") int geroId,
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="gender", required=false) String gender,
			@RequestParam(value="identity_no", required=false) String identityNo,
			@RequestParam(value="role", required=false) String role,
			@RequestParam("page") int page,
			@RequestParam("rows") int limit,
			@RequestParam("sort") String orderByTag
			) {
		
		Page<User> userPage = new Page<User>(page, limit);
		userPage = setOrderBy(userPage, orderByTag);
		
		// 参数检查
		if (gender != null && !(gender.equals("0") || gender.equals("1"))) {
			String otherMessage = "gender 不符合格式:" +
					"[gender=" + gender + "]";
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
			queryUser.setGeroId(geroId);
			queryUser.setPage(userPage);
			List<User> users;
			if (role == null)
				users = staffDataService.getAllStaffs(queryUser);
			else
				users = staffDataService.getAllStaffs(queryUser, role);
			
			for (User user : users) {
				Map<String, Object> resultMap = new HashMap<String, Object>(); 
				resultMap.put("id", user.getUserId()); 
				resultMap.put("name", user.getName()); 
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
					tempMap.put("id", tempRole.getId());
					tempMap.put("name", tempRole.getName());
					tempList.add(tempMap);
				}
				resultMap.put("role_list", tempList);
				
				basicReturnedJson.addEntity(resultMap);
			}
			
			basicReturnedJson.setTotal((int) queryUser.getPage().getCount());
			
			return basicReturnedJson.getMap();
			
		} catch(Exception e) {
			e.printStackTrace();
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.STAFF_DATA_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	}
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Object postStaff(
			@PathVariable("gid") int geroId,
			@RequestBody String inJson
			) {
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		tempRquestParamMap.put("geroId", geroId);
		tempRquestParamMap.put("registerDate", DateUtils.getDateTime());
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		
		try {
			
			if (requestParamMap.get("username") == null
				|| requestParamMap.get("name") == null
				|| requestParamMap.get("identityNo") == null
				|| requestParamMap.get("birthday") == null
				|| requestParamMap.get("phoneNo") == null
//				|| requestParamMap.get("gender") == null
//				|| requestParamMap.get("photoUrl") == null
//				|| requestParamMap.get("age") == null
//				|| requestParamMap.get("nationality") == null
//				|| requestParamMap.get("marriage") == null
//				|| requestParamMap.get("nativePlace") == null
//				|| requestParamMap.get("politicalStatus") == null
//				|| requestParamMap.get("education") == null
//				|| requestParamMap.get("zipCode") == null
//				|| requestParamMap.get("residenceAddress") == null
//				|| requestParamMap.get("householdAddress") == null
//				|| requestParamMap.get("wechatId") == null
				// for Staff
//				|| requestParamMap.get("nssfId") == null
//				|| requestParamMap.get("basicUrl") == null
//				|| requestParamMap.get("leaveDate") == null
//				|| requestParamMap.get("archiveId") == null
				)
				throw new Exception();
			
			// 参数详细验证
			
			// birthday
			// TODO
		} catch(Exception e) {
			String otherMessage = "[" + inJson + "]";
			String message = ErrorConstants.format(ErrorConstants.STAFF_DATA_POST_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			requestParamMap.put("password", CommonConstants.DEFAULT_PASSWORD);
			requestParamMap.put("geroId", geroId);
			// insert into Staff
			StaffEntity postStaffEntity = new StaffEntity(); 
			BeanUtils.populate(postStaffEntity, requestParamMap);
			Integer staffId = staffDataService.insertStaff(postStaffEntity);
			
			// insert into USER
			requestParamMap.put("userType", CommonConstants.STAFF_TYPE);
			requestParamMap.put("userId", staffId);
			
			User postUser = new User(); 
			BeanUtils.populate(postUser, requestParamMap);
			systemService.insertUser(postUser);
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.STAFF_DATA_POST_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
	
	@RequestMapping(value="/{sid}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getStaff(

			@PathVariable("gid") int geroId,
			@PathVariable("sid") int staffId
			) {
		
		try {
			// 获取基础的 JSON返回
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
			
			StaffEntity queryStaffEntity = new StaffEntity();
			queryStaffEntity.setId(staffId);
			StaffEntity staffEntity = staffDataService.getStaffEntity(queryStaffEntity);
			if (staffEntity == null)
				throw new Exception("找不到对应的 staff");
			User user = systemService.getUserByUserTypeAndUserId(CommonConstants.STAFF_TYPE, staffId);
			if (user == null)
				throw new Exception("内部错误：找不到对应的 user");
			
			Map<String, Object> resultMap = new HashMap<String, Object>(); 
			resultMap.put("user_id", user.getUserId()); 
			resultMap.put("id", user.getId()); 
			
			resultMap.put("age", user.getAge()); 
			resultMap.put("birthday", user.getBirthday()); 
			resultMap.put("cancel_date", user.getCancelDate()); 
			resultMap.put("education", user.getEducation()); 
			resultMap.put("email", user.getEmail()); 
			resultMap.put("gender", user.getGender()); 
			resultMap.put("gero_id", user.getGeroId()); 
			resultMap.put("household_address", user.getHouseholdAddress()); 
			resultMap.put("identity_no", user.getIdentityNo()); 
			resultMap.put("marriage", user.getMarriage()); 
			resultMap.put("name", user.getName()); 
			resultMap.put("nationality", user.getNationality()); 
			resultMap.put("native_place", user.getNativePlace()); 
			resultMap.put("notes", user.getNotes()); 
			resultMap.put("phone_no", user.getPhoneNo()); 
			resultMap.put("photo_url", user.getPhotoUrl()); 
			resultMap.put("political_status", user.getPoliticalStatus()); 
			resultMap.put("register_date", user.getRegisterDate()); 
			resultMap.put("residence_address", user.getResidenceAddress()); 
			resultMap.put("user_name", user.getUsername()); 
			resultMap.put("user_type", user.getUserType()); 
			resultMap.put("wechat_id", user.getWechatId()); 
			resultMap.put("zip_code", user.getZipCode()); 
			
			resultMap.put("nssf_id", staffEntity.getNssfId()); 
			resultMap.put("leave_date", staffEntity.getLeaveDate()); 
			resultMap.put("archive_id", staffEntity.getArchiveId()); 
			
			List<Role> roleList = user.getRoleList();
			List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
			for (Role tempRole : roleList) {
				Map<String, Object> tempMap = new HashMap<String, Object>();
				tempMap.put("role_id", tempRole.getId());
				tempMap.put("role_name", tempRole.getName());
				tempList.add(tempMap);
			}
			resultMap.put("role_list", tempList);
		
			List<Privilege> privilegeList = UserUtils.getPrivilegeList(user);
			List<Map<String, Object>> tempList2 = new ArrayList<Map<String, Object>>();
			for (Privilege privilege : privilegeList) {
				Map<String, Object> tempMap = new HashMap<String, Object>();
				tempMap.put("id", privilege.getId());
				tempMap.put("name", privilege.getName());
				tempMap.put("parent_id", privilege.getParentId());
				tempMap.put("parent_ids", privilege.getParentIds());
				tempMap.put("permission", privilege.getPermission());
				tempMap.put("href", privilege.getHref());
				tempMap.put("icon", privilege.getIcon());
				
				tempList2.add(tempMap);
			}
			resultMap.put("privilege_list", tempList2);
			
			basicReturnedJson.addEntity(resultMap);
			
			return basicReturnedJson.getMap();
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.STAFF_DATA_STAFF_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
		
	}
	
	@Transactional
	@RequestMapping(value="/{sid}", method = RequestMethod.PUT, produces = MediaTypes.JSON_UTF_8)
	public Object putStaff(
			@PathVariable("gid") int geroId,
			@PathVariable("sid") int staffId,
			@RequestBody String inJson
			) {
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		tempRquestParamMap.put("geroId", geroId);
		tempRquestParamMap.put("staffId", staffId);
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		
		try {
			/*
			if (requestParamMap.get("username") == null
				|| requestParamMap.get("name") == null
				|| requestParamMap.get("registerDate") == null
				|| requestParamMap.get("gender") == null
				|| requestParamMap.get("photoUrl") == null
				|| requestParamMap.get("identityNo") == null
				|| requestParamMap.get("age") == null
				|| requestParamMap.get("nationality") == null
				|| requestParamMap.get("marriage") == null
				|| requestParamMap.get("nativePlace") == null
				|| requestParamMap.get("birthday") == null
				|| requestParamMap.get("politicalStatus") == null
				|| requestParamMap.get("education") == null
				|| requestParamMap.get("phoneNo") == null
				|| requestParamMap.get("zipCode") == null
				|| requestParamMap.get("residenceAddress") == null
				|| requestParamMap.get("householdAddress") == null
				|| requestParamMap.get("wechatId") == null
				// for Staff
				|| requestParamMap.get("nssfId") == null
				|| requestParamMap.get("basicUrl") == null
				|| requestParamMap.get("leaveDate") == null
				|| requestParamMap.get("archiveId") == null
				)
				throw new Exception();
			*/
			// 参数详细验证
			// birthday
			// TODO
		} catch(Exception e) {
			String otherMessage = "[" + inJson + "]";
			String message = ErrorConstants.format(ErrorConstants.STAFF_DATA_STAFF_PUT_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			requestParamMap.put("geroId", geroId);
			// update Staff
			StaffEntity postStaffEntity = new StaffEntity(); 
			BeanUtils.populate(postStaffEntity, requestParamMap);
			postStaffEntity.setId(staffId);
			staffDataService.updateStaff(postStaffEntity);
			
			// update USER
			requestParamMap.put("userType", CommonConstants.STAFF_TYPE);
			requestParamMap.put("userId", staffId);
			User tempUser = systemService.getUserByUserTypeAndUserId(CommonConstants.STAFF_TYPE, staffId);
			User postUser = new User(); 
			BeanUtils.populate(postUser, requestParamMap);
			postUser.setId(tempUser.getId());
			systemService.updateUser(postUser);
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.STAFF_DATA_STAFF_PUT_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
	
	@Transactional
	@RequestMapping(value="/{sid}", method = RequestMethod.DELETE, produces = MediaTypes.JSON_UTF_8)
	public Object deleteStaff(
			@PathVariable("gid") int geroId,
			@PathVariable("sid") int staffId
			) {
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> requestParamMap = new HashMap<String, Object>();
		requestParamMap.put("geroId", geroId);
		requestParamMap.put("staffId", staffId);
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			Date now = new Date();
			
			String nowDateTime =  DateUtils.formatDateTime(now);
			String nowDate =  DateUtils.formatDate(now);
			
			// delete Staff
			StaffEntity postStaffEntity = new StaffEntity(); 
			BeanUtils.populate(postStaffEntity, requestParamMap);
			postStaffEntity.setId(staffId);
			postStaffEntity.setLeaveDate(nowDate);
			staffDataService.deleteStaff(postStaffEntity);
			
			// update USER
			requestParamMap.put("userType", CommonConstants.STAFF_TYPE);
			requestParamMap.put("userId", staffId);
			User tempUser = systemService.getUserByUserTypeAndUserId(CommonConstants.STAFF_TYPE, staffId);
			User postUser = new User(); 
			BeanUtils.populate(postUser, requestParamMap);
			postUser.setId(tempUser.getId());
			postUser.setCancelDate(nowDateTime);
			systemService.deleteUser(postUser);
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.STAFF_DATA_STAFF_DELETE_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
}
