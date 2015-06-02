/**
 * @Package com.sjtu.icare.modules.gero.webservice
 * @Description TODO
 * @date Mar 30, 2015 4:12:24 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.wechat.webservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
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
import com.sjtu.icare.common.utils.PinyinUtils;
import com.sjtu.icare.common.web.rest.BasicController;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.elder.entity.RelativeEntity;
import com.sjtu.icare.modules.elder.service.IRelativeInfoService;
import com.sjtu.icare.modules.elder.service.impl.ElderInfoService;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;

@RestController
@RequestMapping("${api.wechat}/relative")
public class WechatRelativeController  extends BasicController {
	private static Logger logger = Logger.getLogger(WechatRelativeController.class);
	
	@Autowired
	private IRelativeInfoService relativeInfoService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private ElderInfoService elderInfoService;
	/*
	 * get relatives
	 * */
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getRelativesForWechat(
			HttpServletRequest request,
			@RequestParam(value="elder_id", required=false) Integer elderId,
			@RequestParam(value="name", required=false) String name,
			@RequestParam("page") int page,
			@RequestParam("rows") int rows,
			@RequestParam("sort") String sort
			) {
//		checkApi(request);
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
			requestUser.setElderId(elderId);
			
			Page<User> pages = new Page<User>(page, rows);
			pages = setOrderBy(pages, sort);
			requestUser.setPage(pages);
			
			List<User> users;
			users = relativeInfoService.getUsersOfRelatives2(requestUser);
			
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
					resultMap.put("political_statu s", user.getPoliticalStatus()); 
					resultMap.put("register_date", user.getRegisterDate()); 
					resultMap.put("residence_address", user.getResidenceAddress()); 
					resultMap.put("username", user.getUsername()); 
					resultMap.put("user_type", user.getUserType()); 
					resultMap.put("wechat_id", user.getWechatId()); 
					resultMap.put("zip_code", user.getZipCode()); 
					
					resultMap.put("photo_src", user.getPhotoSrc()); 
					
					RelativeEntity requestRelativeEntity = new RelativeEntity();
					requestRelativeEntity.setId(user.getUserId());
					RelativeEntity relativeEntity = relativeInfoService.getRelative(requestRelativeEntity);
					if (relativeEntity == null)
						throw new Exception("内部错误： user 找不到对应的 relative");
					
//					resultMap.put("elder_id", relativeEntity.getElderId()); 
//					ElderEntity requestElderEntity = new ElderEntity();
//					requestElderEntity.setId(relativeEntity.getElderId());
//					ElderEntity elderEntity = elderInfoService.getElderEntity(requestElderEntity);
//					resultMap.put("elder_name", elderEntity.getName());
					
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
	
	/*
	 * post a relative
	 * */
	@Transactional
	@RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Object postRelativeForWechat(
			HttpServletRequest request,
			@RequestParam(value="wechat_id",required=false) String wechatId,
			@RequestBody String inJson
			) {
		if (wechatId==null || wechatId.isEmpty()) {
			throw new RestException(HttpStatus.BAD_REQUEST, "wechat Id required"); 
		}
		try{
			User user = systemService.getUserByWechatId(wechatId);
			if(user!=null)throw new Exception();
		}catch(Exception e){
			throw new RestException(HttpStatus.FORBIDDEN, "cannot create new user due to previous reasons");
		}
		
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		// TODO geroId
		requestParamMap.put("geroId", null);
		requestParamMap.put("registerDate", DateUtils.getDateTime());
		requestParamMap.put("birthday", DateUtils.getDate());
		requestParamMap.put("identityNo","0");//TODO null
		
		// TODO passworkd register date self gen
		try {
			
			if (requestParamMap.get("name") == null
				|| requestParamMap.get("phoneNo") == null
//				|| requestParamMap.get("wechatId") == null
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
		requestParamMap.put("password", SystemService.entryptPassword((String) requestParamMap.get("phoneNo")));
		// 插入数据
		try {
			// insert into Relative
			RelativeEntity requestRelativeEntity = new RelativeEntity(); 
			BeanUtils.populate(requestRelativeEntity, requestParamMap);

			Integer relativeId = relativeInfoService.insertRelative(requestRelativeEntity);
			
			// insert into User
			requestParamMap.put("userType", CommonConstants.RELATIVE_TYPE);
			requestParamMap.put("userId", relativeId);
			
			User requestUser = new User(); 
			BeanUtils.populate(requestUser, requestParamMap);
			requestUser.setUsername(requestUser.getIdentityNo());
			requestUser.setGeroId(null);
			requestUser.setWechatId(wechatId);
			requestUser.setSubscribe("1");
			//requestUser.setSubscribeTime();
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
	
	/*
	 * update a relative's information
	 * TODO unsecured method, vulnerable 
	 * */
	@Transactional
	@RequestMapping(method = RequestMethod.PUT, produces = MediaTypes.JSON_UTF_8)
	public Object modifyRelativeForWechat(
			HttpServletRequest request,
			@RequestParam(value="wechat_id",required=false) String wechatId,
			@RequestBody String inJson
			) {
		if (wechatId==null || wechatId.isEmpty()) {
			throw new RestException(HttpStatus.BAD_REQUEST, "wechat Id required"); 
		}
		User requestUser = null; 
		try{
			User user = systemService.getUserByWechatId(wechatId);
			if(user==null){
				throw new Exception();
			}else{
				requestUser = user;
			}
		}catch(Exception e){
			throw new RestException(HttpStatus.NOT_FOUND, "no user found by wechat id");
		}
		
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		try {
			String phoneNo = (String) requestParamMap.get("phoneNo");
			String householdAddress = (String) requestParamMap.get("householdAddress");
			if(phoneNo==null&& householdAddress==null){
				throw new Exception();
			}else{
				requestUser.setPhoneNo(phoneNo);
				requestUser.setHouseholdAddress(householdAddress);
				
			}
		} catch(Exception e) {
			throw new RestException(HttpStatus.BAD_REQUEST, "phone and address must be given");
		}
		// 更新数据
		try {
			// TABLE relative no change
			systemService.updateUser(requestUser);
		} catch(Exception e) {
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "update relative failed");
		}

		return basicReturnedJson.getMap();
		
	}
	
}
