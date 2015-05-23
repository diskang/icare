package com.sjtu.icare.modules.wechat.webservice;

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

import com.sjtu.icare.common.config.CommonConstants;
import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.DateUtils;
import com.sjtu.icare.common.web.rest.BasicController;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.elder.entity.ElderBloodPressureEntity;
import com.sjtu.icare.modules.elder.entity.ElderHeartRateEntity;
import com.sjtu.icare.modules.elder.entity.ElderTemperatureEntity;
import com.sjtu.icare.modules.elder.service.IElderHealthDataService;
import com.sjtu.icare.modules.elder.service.IElderInfoService;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.wechat.service.IElderRelativeRelationshipService;
import com.sjtu.icare.modules.wechat.service.impl.ElderRelativeRelationshipService;

/*
 * not used
 * use api in elder module
 * */
@RestController
@RequestMapping("${api.wechat}/elder")
public class WechatElderController extends BasicController{
	private static Logger logger = Logger.getLogger(WechatElderController.class);
	
	@Autowired
	private IElderInfoService elderInfoService;
	@Autowired 
	private IElderRelativeRelationshipService elderRelativeRelationshipService;
	@Autowired 
	private IElderHealthDataService elderHealthDataService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getElderListByNameAndGero(
			HttpServletRequest request,
			@RequestParam(value="gero_id", required=false) Integer geroId,
			@RequestParam(value="elder_name", required=false) String elderName
			) {
		if(geroId==null||elderName==null){
			throw new RestException(HttpStatus.BAD_REQUEST, "both geroId and elderName required"); 
		}
		try{
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
			User queryUser = new User();
			queryUser.setName(elderName);
			queryUser.setUserType(CommonConstants.ELDER_TYPE);
			queryUser.setGeroId(geroId);
			queryUser.setFuzzyMatch(false);//match fully 
			List<User> users;
			users = elderInfoService.getAllElders(queryUser);
			for (User user : users) {
				
				Map<String, Object> resultMap = new HashMap<String, Object>(); 
				resultMap.put("elder_id", user.getUserId()); 
				resultMap.put("id", user.getId()); 
				resultMap.put("name", user.getName()); 
				resultMap.put("photo_url", user.getPhotoUrl()); 
				basicReturnedJson.addEntity(resultMap);
			}
			return basicReturnedJson.getMap();
		}catch(Exception e){
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.ELDER_INFO_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	}
	
	@RequestMapping(value="/{eid}/heart_rate",method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getElderHeartRate(
			HttpServletRequest request,
			@PathVariable("eid") int elderId,
//			@RequestParam(value="start_date", required=false) String startDate,
//			@RequestParam(value="end_date", required=false) String endDate,
			@RequestParam(value="wechat_id", required=false)String wechatId
			) 
	{
		if (wechatId==null || wechatId.isEmpty()) {
			throw new RestException(HttpStatus.BAD_REQUEST, "wechat Id required"); 
		}
		
		//get elderInfos from openId
		ElderRelativeRelationshipService.ElderRelativeRelationshipReturn
		relativeElders = elderRelativeRelationshipService.getElderRelativeRelationshipsByWechatId(wechatId);
		if(relativeElders==null){//user not register, redirect to register page
			throw new RestException(HttpStatus.UNAUTHORIZED, "user not register");
		}
		String status = (String)relativeElders.getStatus();
		if(! CommonConstants.SUBSCRIBED_WITH_RELATIONSHIP_BINDING.equals(status)){
			throw new RestException(HttpStatus.UNAUTHORIZED, "no elders bind");
		}
		
		try{
			ElderHeartRateEntity queryHeartRateEntity = new ElderHeartRateEntity();
			queryHeartRateEntity.setElderId(elderId);
			String endDate = DateUtils.formatDate(DateUtils.addDays(new Date(), 1),"yyyy-MM-dd");
			String startDate = DateUtils.formatDate(DateUtils.addDays(new Date(), -30),"yyyy-MM-dd");
			List<ElderHeartRateEntity> elderHeartRateEntityList = 
					elderHealthDataService.getElderHeartRateEntity(
					queryHeartRateEntity, startDate, endDate);
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
			for(ElderHeartRateEntity entity:elderHeartRateEntityList){
				basicReturnedJson.addEntity(entity);
			}
			return basicReturnedJson.getMap();
		}catch(Exception e){
//			logger.error("");
			e.printStackTrace();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "cannot get heart rate"); 
		}
	}
	
	@RequestMapping(value="/{eid}/blood_pressure",method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getElderBloodPressure(
			HttpServletRequest request,
			@PathVariable("eid") int elderId,
//			@RequestParam(value="start_date", required=false) String startDate,
//			@RequestParam(value="end_date", required=false) String endDate,
			@RequestParam(value="wechat_id", required=false)String wechatId
			) {
		if (wechatId==null || wechatId.isEmpty()) {
			throw new RestException(HttpStatus.BAD_REQUEST, "wechat Id required"); 
		}
		ElderRelativeRelationshipService.ElderRelativeRelationshipReturn
		relativeElders = elderRelativeRelationshipService.getElderRelativeRelationshipsByWechatId(wechatId);
		if(relativeElders==null){//user not register, redirect to register page
			throw new RestException(HttpStatus.UNAUTHORIZED, "user not register");
		}
		String status = (String)relativeElders.getStatus();
		if(! CommonConstants.SUBSCRIBED_WITH_RELATIONSHIP_BINDING.equals(status)){
			throw new RestException(HttpStatus.UNAUTHORIZED, "no elders bind");
		}
		try {
			ElderBloodPressureEntity queryBloodPressureEntity = new ElderBloodPressureEntity();
			queryBloodPressureEntity.setElderId(elderId);
			
			String endDate = DateUtils.formatDate(DateUtils.addDays(new Date(), 1),"yyyy-MM-dd");
			//get data for last 30 days
			String startDate = DateUtils.formatDate(DateUtils.addDays(new Date(), -30),"yyyy-MM-dd");
			List<ElderBloodPressureEntity> elderBloodPressureEntityList = 
					elderHealthDataService.getElderBloodPressureEntities(
					queryBloodPressureEntity, startDate, endDate);
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
			for(ElderBloodPressureEntity entity: elderBloodPressureEntityList){
				basicReturnedJson.addEntity(entity);
			}
			return basicReturnedJson.getMap();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "cannot get blood pressure"); 
		}
		
	}
	
	@RequestMapping(value="/{eid}/temperature",method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getElderTemperature(
			HttpServletRequest request,
			@PathVariable("eid") int elderId,
//			@RequestParam(value="start_date", required=false) String startDate,
//			@RequestParam(value="end_date", required=false) String endDate,
			@RequestParam(value="wechat_id", required=false)String wechatId
			) {
		if (wechatId==null || wechatId.isEmpty()) {
			throw new RestException(HttpStatus.BAD_REQUEST, "wechat Id required"); 
		}
		ElderRelativeRelationshipService.ElderRelativeRelationshipReturn
		relativeElders = elderRelativeRelationshipService.getElderRelativeRelationshipsByWechatId(wechatId);
		if(relativeElders==null){//user not register, redirect to register page
			throw new RestException(HttpStatus.UNAUTHORIZED, "user not register");
		}
		String status = (String)relativeElders.getStatus();
		if(! CommonConstants.SUBSCRIBED_WITH_RELATIONSHIP_BINDING.equals(status)){
			throw new RestException(HttpStatus.UNAUTHORIZED, "no elders bind");
		}
		//TODO
		try {
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
			ElderTemperatureEntity queryTemperatureEntity = new ElderTemperatureEntity();
			queryTemperatureEntity.setElderId(elderId);
			String endDate = DateUtils.formatDate(DateUtils.addDays(new Date(), 1),"yyyy-MM-dd");
			//get data for last 30 days
			String startDate = DateUtils.formatDate(DateUtils.addDays(new Date(), -30),"yyyy-MM-dd");
			List<ElderTemperatureEntity> elderTemperatureEntityList = 
					elderHealthDataService.getElderTemperatureEntities(
					queryTemperatureEntity, startDate, endDate);
			for(ElderTemperatureEntity entity:elderTemperatureEntityList){
				basicReturnedJson.addEntity(entity);
			}
			return basicReturnedJson.getMap();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "cannot get temperature"); 
		}
		
	}
}
