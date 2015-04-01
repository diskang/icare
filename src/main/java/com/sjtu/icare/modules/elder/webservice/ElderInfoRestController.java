package com.sjtu.icare.modules.elder.webservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.aliyun.oss.internal.OSSUtils;
import com.sjtu.icare.common.config.CommonConstants;
import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.persistence.Page;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.DateUtils;
import com.sjtu.icare.common.utils.MapListUtils;
import com.sjtu.icare.common.utils.OSSObjectUtils;
import com.sjtu.icare.common.utils.ParamUtils;
import com.sjtu.icare.common.utils.PinyinUtils;
import com.sjtu.icare.common.web.rest.GeroBaseController;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.elder.entity.ElderEntity;
import com.sjtu.icare.modules.elder.service.IElderInfoService;
import com.sjtu.icare.modules.gero.entity.GeroAreaEntity;
import com.sjtu.icare.modules.gero.service.IGeroAreaService;
import com.sjtu.icare.modules.staff.service.IStaffDataService;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;

@RestController
@RequestMapping({"${api.web}/gero/{gid}/elder", "${api.service}/gero/{gid}/elder"})
public class ElderInfoRestController extends GeroBaseController{
	
	private static Logger logger = Logger.getLogger(ElderInfoRestController.class);
	
	@Autowired
	private IElderInfoService elderInfoService;
	@Autowired
	IStaffDataService staffDataService;
	@Autowired
	SystemService systemService;
	@Autowired
	IGeroAreaService geroAreaService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getElders(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="gender", required=false) String gender,
			@RequestParam(value="age_min", required=false) Integer ageMin,
			@RequestParam(value="age_max", required=false) Integer ageMax,
			@RequestParam(value="care_level", required=false) Integer careLevel,
			@RequestParam(value="area_id", required=false) Integer areaId,
			@RequestParam("page") int page,
			@RequestParam("rows") int limit,
			@RequestParam("sort") String orderByTag
			) {
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":elder:info:read");
		checkPermissions(permissions);
		
		Page<User> userPage = new Page<User>(page, limit);
		userPage = setOrderBy(userPage, orderByTag);
		
		// 参数检查
		if (gender != null && !(gender.equals("0") || gender.equals("1"))) {
			String otherMessage = "gender 不符合格式:" +
					"[gender=" + gender + "]";
			String message = ErrorConstants.format(ErrorConstants.ELDER_INFO_GET_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		try {
			// 获取基础的 JSON返回
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
			
//			Map<String, Object> requestParamMap = new HashMap<String, Object>();
//			requestParamMap.put("name", name);
//			requestParamMap.put("gender", gender);
//			requestParamMap.put("ageMin", ageMin);
//			requestParamMap.put("ageMax", ageMax);
//			requestParamMap.put("careLevel", careLevel);
//			requestParamMap.put("areaId", areaId);
//			requestParamMap.put("userType", CommonConstants.ELDER_TYPE);
//			requestParamMap.put("geroId", geroId);
			
			User queryUser = new User();
			queryUser.setName(name);
			queryUser.setGender(gender);
			queryUser.setAgeMin(ageMin);
			queryUser.setAgeMax(ageMax);
			queryUser.setCareLevel(careLevel);
			queryUser.setUserType(CommonConstants.ELDER_TYPE);
			queryUser.setGeroId(geroId);
			queryUser.setPage(userPage);
			
			if (areaId == null)
				queryUser.setAreaId(areaId);
			else {
				
				List<Integer> areaIds = new ArrayList<Integer>();
				
				GeroAreaEntity queryGeroAreaEntity = new GeroAreaEntity();
				queryGeroAreaEntity.setGeroId(geroId);
				queryGeroAreaEntity.setId(areaId);
				GeroAreaEntity ancestorGeroAreaEntity = geroAreaService.getGeroArea(queryGeroAreaEntity);
				if (ancestorGeroAreaEntity == null)
					throw new Exception("找不到 area_id 对应的信息");
				Integer subLevel = 99999;
				List<GeroAreaEntity> descendantGeroAreaEntities = geroAreaService.getGeroSubareas(ancestorGeroAreaEntity, subLevel);
				
				if (descendantGeroAreaEntities == null)
					queryUser.setAreaId(null);
				else {
					queryUser.setAreaId(areaId);
					areaIds.add(ancestorGeroAreaEntity.getId());
					for (GeroAreaEntity geroAreaEntity : descendantGeroAreaEntities) {
						areaIds.add(geroAreaEntity.getId());
					}
					queryUser.setAreaIds(areaIds);
					
				}
				
			}
			
			List<User> users;
			users = elderInfoService.getAllElders(queryUser);
			
			for (User user : users) {
				
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
				resultMap.put("username", user.getUsername()); 
				resultMap.put("user_type", user.getUserType()); 
				resultMap.put("wechat_id", user.getWechatId()); 
				resultMap.put("zip_code", user.getZipCode()); 
				
				OSSObjectUtils ossObjectUtils = new OSSObjectUtils();
				resultMap.put("photo_src", ossObjectUtils.getDownloadUrl(user.getPhotoUrl())); 
				
				ElderEntity queryElderEntity = new ElderEntity();
				queryElderEntity.setId(user.getUserId());
				ElderEntity elder = elderInfoService.getElderEntity(queryElderEntity);
				if (elder == null)
					throw new Exception("内部错误： user 找不到对应的 elder");
				
				resultMap.put("care_level", elder.getCareLevel()); 
				resultMap.put("checkin_date", elder.getCheckinDate()); 
				resultMap.put("checkout_date", elder.getCheckoutDate()); 
				resultMap.put("pad_mac", elder.getPadMac()); 
				resultMap.put("nssf_id", elder.getNssfId()); 
				resultMap.put("archive_id", elder.getArchiveId()); 
				resultMap.put("area_id", elder.getAreaId()); 
				resultMap.put("elder_id", elder.getId()); 
				
				basicReturnedJson.addEntity(resultMap);
			}
			
			basicReturnedJson.setTotal((int) queryUser.getPage().getCount());
			
			return basicReturnedJson.getMap();
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.ELDER_INFO_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	}
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Object postElder(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@RequestBody String inJson
			) {
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":elder:info:add");
		checkPermissions(permissions);
		
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		requestParamMap.put("geroId", geroId);
		requestParamMap.put("registerDate", DateUtils.getDateTime());
		requestParamMap.put("password", CommonConstants.DEFAULT_PASSWORD);
		// TODO passworkd register date self gen
		try {
			
			if (requestParamMap.get("name") == null
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
				// for Elder
				|| requestParamMap.get("areaId") == null
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
			String message = ErrorConstants.format(ErrorConstants.ELDER_INFO_POST_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			
			// insert into Elder
			ElderEntity postElderEntity = new ElderEntity(); 
			BeanUtils.populate(postElderEntity, requestParamMap);
			if (postElderEntity.getCareLevel() == null)
				postElderEntity.setCareLevel(3);
			Integer elderId = elderInfoService.insertElder(postElderEntity);
			
			// insert into User
			requestParamMap.put("userType", CommonConstants.ELDER_TYPE);
			requestParamMap.put("userId", elderId);
			
			User postUser = new User(); 
			BeanUtils.populate(postUser, requestParamMap);
			postUser.setUsername(postUser.getIdentityNo());
			Integer userId = systemService.insertUser(postUser);
			String pinyinName = PinyinUtils.getPinyin(postUser.getName() + userId);
			postUser.setUsername(pinyinName);
			systemService.updateUser(postUser);
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.ELDER_INFO_POST_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
	
	@RequestMapping(value="/{eid}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getElder(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@PathVariable("eid") int elderId
			) {
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":elder:info:read");
		permissions.add("staff:"+getCurrentUser().getUserId()+":gero:"+geroId+":elder:read");
		
		
		try {
			// 获取基础的 JSON返回
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
			
			ElderEntity queryElderEntity = new ElderEntity();
			queryElderEntity.setId(elderId);
			ElderEntity elderEntity = elderInfoService.getElderEntity(queryElderEntity);
			if (elderEntity == null)
				throw new Exception("找不到对应的 elder");
			User user = systemService.getUserByUserTypeAndUserId(CommonConstants.ELDER_TYPE, elderId);
			if (user == null)
				throw new Exception("内部错误：找不到对应的 user");
			
			permissions.add("user:"+user.getId()+":info:read");
			checkPermissions(permissions);
			
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
			resultMap.put("username", user.getUsername()); 
			resultMap.put("user_type", user.getUserType()); 
			resultMap.put("wechat_id", user.getWechatId()); 
			resultMap.put("zip_code", user.getZipCode()); 
			
			OSSObjectUtils ossObjectUtils = new OSSObjectUtils();
			resultMap.put("photo_src", ossObjectUtils.getDownloadUrl(user.getPhotoUrl())); 
			
			resultMap.put("apply_url", elderEntity.getApplyUrl()); 
			resultMap.put("assess_url", elderEntity.getAssessUrl()); 
			resultMap.put("care_level", elderEntity.getCareLevel()); 
			resultMap.put("checkin_date", elderEntity.getCheckinDate()); 
			resultMap.put("checkout_date", elderEntity.getCheckoutDate());
			resultMap.put("nssf_id", elderEntity.getNssfId()); 
			resultMap.put("pad_mac", elderEntity.getPadMac()); 
			resultMap.put("survey_url", elderEntity.getSurveyUrl()); 
			resultMap.put("track_url", elderEntity.getTrackUrl()); 
			
			basicReturnedJson.addEntity(resultMap);
			
			return basicReturnedJson.getMap();
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.ELDER_INFO_ELDER_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
		
	}
	
	@Transactional
	@RequestMapping(value="/{eid}", method = RequestMethod.PUT, produces = MediaTypes.JSON_UTF_8)
	public Object putElder(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@PathVariable("eid") int elderId,
			@RequestBody String inJson
			) {
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":elder:info:update");
		
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		tempRquestParamMap.put("geroId", geroId);
		tempRquestParamMap.put("elderId", elderId);
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		
		try {
			// 参数详细验证
//			if (requestParamMap.get("areaId") != null && StringUtils.isBlank((CharSequence) requestParamMap.get("areaId")))
//				throw new Exception();
			
			
		} catch(Exception e) {
			String otherMessage = "[" + inJson + "]";
			String message = ErrorConstants.format(ErrorConstants.ELDER_INFO_ELDER_PUT_PARAM_INVALID , otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			// update Elder
			ElderEntity postElderEntity = new ElderEntity(); 
			BeanUtils.populate(postElderEntity, requestParamMap);
			postElderEntity.setId(elderId);
			elderInfoService.updateElder(postElderEntity);
			
			// update USER
			requestParamMap.put("userType", CommonConstants.ELDER_TYPE);
			requestParamMap.put("userId", elderId);
			User tempUser = systemService.getUserByUserTypeAndUserId(CommonConstants.ELDER_TYPE, elderId);
			
			permissions.add("user:"+tempUser.getId()+":info:read");
			checkPermissions(permissions);
			
			User postUser = new User(); 
			BeanUtils.populate(postUser, requestParamMap);
			postUser.setId(tempUser.getId());
			systemService.updateUser(postUser);
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.ELDER_INFO_ELDER_PUT_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
	
	@Transactional
	@RequestMapping(value="/{eid}", method = RequestMethod.DELETE, produces = MediaTypes.JSON_UTF_8)
	public Object deleteELder(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@PathVariable("eid") int elderId
			) {
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":elder:info:add");
		checkPermissions(permissions);
		
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> requestParamMap = new HashMap<String, Object>();
		requestParamMap.put("geroId", geroId);
		requestParamMap.put("elderId", elderId);
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			Date now = new Date();
			
			String nowDateTime =  DateUtils.formatDateTime(now);
			String nowDate =  DateUtils.formatDate(now);
			
			// delete Elder
			ElderEntity postElderEntity = new ElderEntity(); 
			BeanUtils.populate(postElderEntity, requestParamMap);
			postElderEntity.setId(elderId);
			postElderEntity.setCheckoutDate(nowDate);
			elderInfoService.deleteElder(postElderEntity);
			
			// delete USER
			requestParamMap.put("userType", CommonConstants.ELDER_TYPE);
			requestParamMap.put("userId", elderId);
			User tempUser = systemService.getUserByUserTypeAndUserId(CommonConstants.ELDER_TYPE, elderId);
			User postUser = new User(); 
			BeanUtils.populate(postUser, requestParamMap);
			postUser.setId(tempUser.getId());
			postUser.setCancelDate(nowDateTime);
			systemService.deleteUser(postUser);
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.ELDER_INFO_ELDER_DELETE_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
	
}
