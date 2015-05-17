package com.sjtu.icare.modules.wechat.web;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.sjtu.icare.modules.wechat.service.IElderRelativeRelationshipService;

@RestController
@RequestMapping({"/wechat/healthreport"})
public class HealthReportController extends BasicController{

	@Autowired WxMpInMemoryConfigStorage wxMpConfigStorage;
	@Autowired WxMpService wxMpService;
	@Autowired private IElderRelativeRelationshipService elderRelativeRelationshipService;
	@Autowired private IElderHealthDataService elderHealthDataService;
	
	private static Logger logger = Logger.getLogger(HealthReportController.class);
			
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> getMenu(@RequestParam("code") String code,
			@RequestParam("state") String state,
			HttpServletRequest request){
		
		String openId ="";
		BasicReturnedJson result = new BasicReturnedJson();
		//get openId by code
		try {
			WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
			openId = wxMpOAuth2AccessToken.getOpenId();
	
		} catch (WxErrorException e) {
			throw new RestException(HttpStatus.NOT_FOUND, "cannot get openId");
		}
		try{
			//get elderIds from openId
			Map<String, Object> relativeElders = elderRelativeRelationshipService.getElderRelativeRelationshipsByRelativeId(openId);
			String status = (String)relativeElders.get("status");
			@SuppressWarnings("unchecked")
			List<Integer>elderIds = (List<Integer>)relativeElders.get("elderIds");
			if(CommonConstants.SUBSCRIBED_WITHOUT_RELATIONSHIP_BINDING.equals(status)){
				//redirect to binding
			}else if(CommonConstants.SUBSCRIBED_WITH_RELATIONSHIP_BINDING.equals(status)){
				//get elder health data by elder id in the last week
				ElderBloodPressureEntity queryBloodPressureEntity = new ElderBloodPressureEntity();
				ElderHeartRateEntity queryHeartRateEntity = new ElderHeartRateEntity();
				ElderTemperatureEntity queryTemperatureEntity = new ElderTemperatureEntity();
				for (int i = 0; i < elderIds.size(); i++) {
					int elderId = elderIds.get(i);
					queryBloodPressureEntity.setElderId(elderId);
					queryHeartRateEntity.setElderId(elderId);
					queryTemperatureEntity.setElderId(elderId);
					String endDate = DateUtils.getDate();
					String startDate = DateUtils.formatDate(DateUtils.addDays(new Date(), -7),"yyyy-MM-dd");
					List<ElderBloodPressureEntity> elderBloodPressureEntityList = 
							elderHealthDataService.getElderBloodPressureEntities(
							queryBloodPressureEntity, startDate, endDate);
					List<ElderHeartRateEntity> elderHeartRateEntityList = 
							elderHealthDataService.getElderHeartRateEntity(
							queryHeartRateEntity, startDate, endDate);
					List<ElderTemperatureEntity> elderTemperatureEntityList = 
							elderHealthDataService.getElderTemperatureEntities(
							queryTemperatureEntity, startDate, endDate);
					// 构造返回的 JSON
					Map<String, Object> elderHealthMap = new HashMap<String, Object>(); 
					elderHealthMap.put("elderId", elderId);
					//TODO need name
					elderHealthMap.put("bp", elderBloodPressureEntityList);
					elderHealthMap.put("hr", elderHeartRateEntityList);
					elderHealthMap.put("t", elderTemperatureEntityList);
					result.addEntity(elderHealthMap);
				}
				
			}else{//
				
			}
			return result.getMap();
		}catch(Exception e){
			String otherMessage = "获取老人血压,心率，体温数据失败：\n" +	 "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.ELDER_BLOOD_PRESSURE_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
		
	}
	
}
