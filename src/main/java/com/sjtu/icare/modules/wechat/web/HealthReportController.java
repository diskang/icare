package com.sjtu.icare.modules.wechat.web;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sjtu.icare.common.config.CommonConstants;
import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.mapper.JsonMapper;
import com.sjtu.icare.common.utils.DateUtils;
import com.sjtu.icare.common.web.BaseController;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.modules.elder.entity.ElderBloodPressureEntity;
import com.sjtu.icare.modules.elder.entity.ElderHeartRateEntity;
import com.sjtu.icare.modules.elder.entity.ElderTemperatureEntity;
import com.sjtu.icare.modules.elder.service.IElderHealthDataService;
import com.sjtu.icare.modules.wechat.service.IElderRelativeRelationshipService;
import com.sjtu.icare.modules.wechat.service.impl.ElderRelativeRelationshipService;
import com.sjtu.icare.modules.wechat.service.impl.ElderRelativeRelationshipService.ElderRelativeRelationshipReturn.Elder;

@Controller
@RequestMapping({"/wechat/healthreport"})
public class HealthReportController extends BaseController{

	@Autowired WxMpInMemoryConfigStorage wxMpConfigStorage;
	@Autowired WxMpService wxMpService;
	@Autowired private IElderRelativeRelationshipService elderRelativeRelationshipService;
	@Autowired private IElderHealthDataService elderHealthDataService;
	private JsonMapper jsonMapper = new JsonMapper();
	private static Logger logger = Logger.getLogger(HealthReportController.class);
			
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public String getMenu(@RequestParam(value="code",required=false) String code,
			@RequestParam(value="state",required=false) String state,
			@RequestParam(value="wechat_id",required=false) String wechatId, 
			HttpServletRequest request, HttpServletResponse response, Model model){
		
		String openId ="";
		if(code==null||code.isEmpty()){
			if(wechatId==null||wechatId.isEmpty()){
				logger.error("no user information given");
				return "error/403";//TODO add a 400 page
			}else{
				openId = wechatId;
			}
		}else{
			//get openId by code
			try {
				WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
				openId = wxMpOAuth2AccessToken.getOpenId();
			} catch (WxErrorException e) {
				return "error/404";
			}
			if(openId==null || openId.isEmpty()){
				logger.error("get AccessToken failed or code invalid, no openId found");
				return "error/403";
			}
		}
		try{
			//get elderInfos from openId
			ElderRelativeRelationshipService.ElderRelativeRelationshipReturn
			relativeElders = elderRelativeRelationshipService.getElderRelativeRelationshipsByWechatId(openId);
			if(relativeElders==null){//user not register, redirect to register page
				model.addAttribute("wechatId",openId);
				return "module/wechat/register";
			}
			String status = (String)relativeElders.getStatus();
			
			List<Elder>elders = relativeElders.getElders();
			if(CommonConstants.SUBSCRIBED_WITHOUT_RELATIONSHIP_BINDING.equals(status)){
				//redirect to binding
				return "redirect:/wechat/bind?wechat_id="+openId;
			}else if(CommonConstants.SUBSCRIBED_WITH_RELATIONSHIP_BINDING.equals(status)){
				//get elder health data by elder id in the last week
				ElderBloodPressureEntity queryBloodPressureEntity = new ElderBloodPressureEntity();
				ElderHeartRateEntity queryHeartRateEntity = new ElderHeartRateEntity();
				ElderTemperatureEntity queryTemperatureEntity = new ElderTemperatureEntity();
				List<Object> elderHealthReportList= new ArrayList<Object>();
				for (Elder elder: elders) {
					Integer elderId = elder.getElderId();
					queryBloodPressureEntity.setElderId(elderId);
					queryHeartRateEntity.setElderId(elderId);
					queryTemperatureEntity.setElderId(elderId);
					//从明天开始前推才能把今天的数据取出来- -,so +1
					String endDate = DateUtils.formatDate(DateUtils.addDays(new Date(), 1),"yyyy-MM-dd");
					//get data for last 7 days
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
					elderHealthMap.put("elderName", elder.getElderName());
					if(elderHeartRateEntityList!=null&&elderHeartRateEntityList.size()>0){
						ElderHeartRateEntity heartRate= elderHeartRateEntityList.get(elderHeartRateEntityList.size()-1);
						elderHealthMap.put("hr", heartRate.getRate());
					}else{
						elderHealthMap.put("hr", null);
					}
					if(elderTemperatureEntityList!=null&&elderTemperatureEntityList.size()>0){
						ElderTemperatureEntity temperature= elderTemperatureEntityList.get(elderTemperatureEntityList.size()-1);
						elderHealthMap.put("t", temperature.getTemperature() );
					}else{
						elderHealthMap.put("t", null);
					}
					if(elderBloodPressureEntityList!=null&&elderBloodPressureEntityList.size()>0){
						ElderBloodPressureEntity bloodPressure= elderBloodPressureEntityList.get(elderBloodPressureEntityList.size()-1);
						String bpString = bloodPressure.getSystolicPressure()+"/"+bloodPressure.getDiastolicPressure();
						elderHealthMap.put("bp", bpString );
					}else{
						elderHealthMap.put("bp", null);
					}
					elderHealthReportList.add(elderHealthMap);
				}
				model.addAttribute("healthReport",jsonMapper.toJson(elderHealthReportList));
				return "module/wechat/healthReport";
			}else{//UNSubscribe cannot visit
				logger.error("relative's relationship status:"+status);
				return "error/403";
			}
		}catch(Exception e){
			String otherMessage = "获取老人血压,心率，体温数据失败：\n" +	 "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.ELDER_BLOOD_PRESSURE_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			return "error/500";
		}

	}
	
}
