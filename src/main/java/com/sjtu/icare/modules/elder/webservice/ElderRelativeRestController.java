/**
 * @Package com.sjtu.icare.modules.gero.webservice
 * @Description TODO
 * @date Mar 30, 2015 4:12:24 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.elder.webservice;

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

import com.sjtu.icare.common.config.CommonConstants;
import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.persistence.Page;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.DateUtils;
import com.sjtu.icare.common.utils.MapListUtils;
import com.sjtu.icare.common.utils.OSSObjectUtils;
import com.sjtu.icare.common.utils.ParamUtils;
import com.sjtu.icare.common.utils.PinyinUtils;
import com.sjtu.icare.common.web.rest.BasicController;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.elder.entity.ElderEntity;
import com.sjtu.icare.modules.elder.entity.RelativeEntity;
import com.sjtu.icare.modules.elder.service.IRelativeInfoService;
import com.sjtu.icare.modules.elder.service.impl.ElderInfoService;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;

@RestController
@RequestMapping({"${api.web}/gero/{gid}/relative", "${api.service}/gero/{gid}/relative"})
public class ElderRelativeRestController  extends BasicController {
	private static Logger logger = Logger.getLogger(ElderRelativeRestController.class);
	
	@Autowired
	private IRelativeInfoService relativeInfoService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private ElderInfoService elderInfoService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getRelatives(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@RequestParam(value="elder_id", required=false) Integer elderId,
			@RequestParam(value="name", required=false) String name,
			@RequestParam("page") int page,
			@RequestParam("rows") int rows,
			@RequestParam("sort") String sort
			) {
		checkApi(request);
//		List<String> permissions = new ArrayList<String>();
//		permissions.add("admin:gero:"+geroId+":staff:info:read");
//		checkPermissions(permissions);
		
		// 参数检查
		// TODO
		
		try {
			// 获取基础的 JSON返回
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
			
			User requestUser = new User();
			requestUser.setName(name);
			requestUser.setGeroId(geroId);
			requestUser.setElderId(elderId);
			
			Page<User> pages = new Page<User>(page, rows);
			pages = setOrderBy(pages, sort);
			requestUser.setPage(pages);
			
			List<User> users;
			users = relativeInfoService.getUsersOfRelatives(requestUser);
			
			basicReturnedJson.setTotal((int) requestUser.getPage().getCount());
			
			if (users != null) {
				for (User user : users) {
					Map<String, Object> resultMap = new HashMap<String, Object>(); 
					resultMap.put("relative_id", user.getUserId()); 
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
					
					RelativeEntity requestRelativeEntity = new RelativeEntity();
					requestRelativeEntity.setId(user.getUserId());
					RelativeEntity relativeEntity = relativeInfoService.getRelative(requestRelativeEntity);
					if (relativeEntity == null)
						throw new Exception("内部错误： user 找不到对应的 relative");
					
					resultMap.put("elder_id", relativeEntity.getElderId()); 
					resultMap.put("name", relativeEntity.getName()); 
					resultMap.put("relationship", relativeEntity.getRelationship()); 
					resultMap.put("urgent", relativeEntity.getUrgent()); 
					resultMap.put("cancel_date", relativeEntity.getCancelDate());
					
					basicReturnedJson.addEntity(resultMap);
				}
				
			}
			
			return basicReturnedJson.getMap();
			
		} catch(Exception e) {
			e.printStackTrace();
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.RELATIVE_INFO_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	}
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Object postRelative(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@RequestBody String inJson
			) {
//		checkApi(request);
//		List<String> permissions = new ArrayList<String>();
//		permissions.add("admin:gero:"+geroId+":elder:info:add");
//		checkPermissions(permissions);
		
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
				// for Relative
				|| requestParamMap.get("name") == null
				|| requestParamMap.get("elderId") == null
				)
				throw new Exception();
			
			// 参数详细验证
			
			// birthday
			// TODO
		} catch(Exception e) {
			String otherMessage = "[" + "必须参数: username, name, idneitity_no, birthday, phone_no, elder_id" + " | " + inJson + "]";
			String message = ErrorConstants.format(ErrorConstants.RELATIVE_INFO_POST_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			// insert into Relative
			RelativeEntity requestRelativeEntity = new RelativeEntity(); 
			BeanUtils.populate(requestRelativeEntity, requestParamMap);
			if (requestParamMap.get("elderIdentityNo") != null) {
				String elderIdentityNo = (String) requestParamMap.get("elderIdentityNo");
				ElderEntity elderEntity = elderInfoService.getElderEntityByIdentityNo(elderIdentityNo);
				requestRelativeEntity.setElderId(elderEntity.getId());
			}
			Integer relativeId = relativeInfoService.insertRelative(requestRelativeEntity);
			
			// insert into User
			requestParamMap.put("userType", CommonConstants.RELATIVE_TYPE);
			requestParamMap.put("userId", relativeId);
			
			User requestUser = new User(); 
			BeanUtils.populate(requestUser, requestParamMap);
			requestUser.setUsername(requestUser.getIdentityNo());
			Integer userId = systemService.insertUser(requestUser);
			String pinyinName = PinyinUtils.getPinyin(requestUser.getName() + userId);
			requestUser.setUsername(pinyinName);
			systemService.updateUser(requestUser);
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.RELATIVE_INFO_POST_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
	
	
	@RequestMapping(value="/{rid}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getRelative(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@PathVariable("rid") int relativeId
			) {
//		checkApi(request);
//		List<String> permissions = new ArrayList<String>();
//		permissions.add("admin:gero:"+geroId+":elder:info:read");
//		permissions.add("staff:"+getCurrentUser().getUserId()+":gero:"+geroId+":elder:read");
		
		
		try {
			// 获取基础的 JSON返回
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
			
			RelativeEntity requestRelativeEntity = new RelativeEntity();
			requestRelativeEntity.setId(relativeId);
			RelativeEntity relativeEntity = relativeInfoService.getRelative(requestRelativeEntity);
			
			if (relativeEntity == null)
				throw new Exception("找不到对应的 elder");
			
			User user = systemService.getUserByUserTypeAndUserId(CommonConstants.RELATIVE_TYPE, relativeId);
			if (user == null)
				throw new Exception("内部错误：找不到对应的 user");
			
//			permissions.add("user:"+user.getId()+":info:read");
//			checkPermissions(permissions);
			
			Map<String, Object> resultMap = new HashMap<String, Object>(); 
			resultMap.put("relative_id", user.getUserId()); 
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
			
			
			resultMap.put("elder_id", relativeEntity.getElderId()); 
			resultMap.put("name", relativeEntity.getName()); 
			resultMap.put("relationship", relativeEntity.getRelationship()); 
			resultMap.put("urgent", relativeEntity.getUrgent()); 
			resultMap.put("cancel_date", relativeEntity.getCancelDate());
			
			basicReturnedJson.addEntity(resultMap);
			
			return basicReturnedJson.getMap();
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.RELATIVE_INFO_SPECIFIC_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
		
	}

	@Transactional
	@RequestMapping(value="/{rid}", method = RequestMethod.PUT, produces = MediaTypes.JSON_UTF_8)
	public Object putRelative(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@PathVariable("rid") int relativeId,
			@RequestBody String inJson
			) {
//		checkApi(request);
//		List<String> permissions = new ArrayList<String>();
//		permissions.add("admin:gero:"+geroId+":elder:info:update");
		
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		tempRquestParamMap.put("geroId", geroId);
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		
		try {
			// 参数详细验证
//			if (requestParamMap.get("areaId") != null && StringUtils.isBlank((CharSequence) requestParamMap.get("areaId")))
//				throw new Exception();
			
			
		} catch(Exception e) {
			String otherMessage = "[" + inJson + "]";
			String message = ErrorConstants.format(ErrorConstants.RELATIVE_INFO_ELDER_PUT_PARAM_INVALID , otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			// update Relative
			RelativeEntity requestRelativeEntity = new RelativeEntity(); 
			BeanUtils.populate(requestRelativeEntity, requestParamMap);
			requestRelativeEntity.setId(relativeId);
			relativeInfoService.updateRelative(requestRelativeEntity);
			
			// update User
			requestParamMap.put("userType", CommonConstants.RELATIVE_TYPE);
			requestParamMap.put("userId", relativeId);
			User tempUser = systemService.getUserByUserTypeAndUserId(CommonConstants.RELATIVE_TYPE, relativeId);
			
//			permissions.add("user:"+tempUser.getId()+":info:read");
//			checkPermissions(permissions);
			
			User postUser = new User(); 
			BeanUtils.populate(postUser, requestParamMap);
			postUser.setId(tempUser.getId());
			systemService.updateUser(postUser);
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.RELATIVE_INFO_ELDER_PUT_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
	
	@Transactional
	@RequestMapping(value="/{rid}", method = RequestMethod.DELETE, produces = MediaTypes.JSON_UTF_8)
	public Object deleteRelative(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@PathVariable("rid") int relativeId
			) {
//		checkApi(request);
//		List<String> permissions = new ArrayList<String>();
//		permissions.add("admin:gero:"+geroId+":elder:info:add");
//		checkPermissions(permissions);
		
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> requestParamMap = new HashMap<String, Object>();
		requestParamMap.put("geroId", geroId);
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			Date now = new Date();
			
			String nowDateTime =  DateUtils.formatDateTime(now);
			
			// delete Relative
			RelativeEntity requestRelativeEntity = new RelativeEntity(); 
			BeanUtils.populate(requestRelativeEntity, requestParamMap);
			requestRelativeEntity.setId(relativeId);
			requestRelativeEntity.setCancelDate(nowDateTime);
			relativeInfoService.deleteRelative(requestRelativeEntity);
			
			// delete User
			requestParamMap.put("userType", CommonConstants.RELATIVE_TYPE);
			requestParamMap.put("userId", relativeId);
			User tempUser = systemService.getUserByUserTypeAndUserId(CommonConstants.RELATIVE_TYPE, relativeId);
			User postUser = new User(); 
			BeanUtils.populate(postUser, requestParamMap);
			postUser.setId(tempUser.getId());
			postUser.setCancelDate(nowDateTime);
			systemService.deleteUser(postUser);
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.RELATIVE_INFO_ELDER_DELETE_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
	
}
