package com.sjtu.icare.modules.wechat.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sjtu.icare.common.config.CommonConstants;
import com.sjtu.icare.common.mapper.JsonMapper;
import com.sjtu.icare.common.utils.DateUtils;
import com.sjtu.icare.common.web.BaseController;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.modules.op.entity.CareworkRecordEntity;
import com.sjtu.icare.modules.op.service.IItemRecordService;
import com.sjtu.icare.modules.wechat.service.IElderRelativeRelationshipService;
import com.sjtu.icare.modules.wechat.service.impl.ElderRelativeRelationshipService;
import com.sjtu.icare.modules.wechat.service.impl.ElderRelativeRelationshipService.ElderRelativeRelationshipReturn.Elder;

@Controller
@RequestMapping({"/wechat/activity"})
public class ElderActivityController extends BaseController{
	
	private static Logger logger = Logger.getLogger(ElderActivityController.class);
	@Autowired private IItemRecordService itemRecordService;
	@Autowired private IElderRelativeRelationshipService elderRelativeRelationshipService;
	@Autowired private WxMpService wxMpService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public String getMenu(@RequestParam(value="code",required=false) String code,
			@RequestParam(value="wechat_id",required=false) String wechatId, 
			HttpServletRequest request, HttpServletResponse response, Model model
			, @RequestHeader HttpHeaders headers){
		
		logger.info(headers);
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
		ElderRelativeRelationshipService.ElderRelativeRelationshipReturn relativeElders;
		try{
			//get elderInfos from openId
			relativeElders = elderRelativeRelationshipService
					.getElderRelativeRelationshipsByWechatId(openId);
		}catch(Exception e){
			logger.error("can not get relative's elders");
			return "error/500";
		}
		if(relativeElders==null){//user not register, redirect to register page
			model.addAttribute("wechatId",openId);
			return "module/wechat/register";
		}
		
		String status = (String)relativeElders.getStatus();	
		List<Elder>elders = relativeElders.getElders();
		
		if(CommonConstants.SUBSCRIBED_WITHOUT_RELATIONSHIP_BINDING.equals(status)){
			//redirect to binding
			return "redirect:/wechat/bind?wechat_id="+openId;
		}
		if(CommonConstants.SUBSCRIBED_WITH_RELATIONSHIP_BINDING.equals(status)){
			CareworkRecordEntity careworkRecordEntity = new CareworkRecordEntity();
			List<Object> elderCareRecordList= new ArrayList<Object>();
			String startDate = DateUtils.formatDate(new Date(),"yyyy-MM-dd");
			String endDate = DateUtils.formatDate(DateUtils.addDays(new Date(), 1),"yyyy-MM-dd");
			
			for (Elder elder: elders) {
				Map<String,Object> elderRecordObject = new HashMap<String, Object>();
				Integer elderId = elder.getElderId();
				careworkRecordEntity.setElderId(elderId);
				List<CareworkRecordEntity> careworkRecordEntityList = itemRecordService.getCareworkRecords(
						careworkRecordEntity, startDate, endDate);
				elderRecordObject.put("elderId", elderId);
				elderRecordObject.put("elderName", elder.getElderName());
				elderRecordObject.put("elderRecord", careworkRecordEntityList);
				elderCareRecordList.add(elderRecordObject);
			}
			JsonMapper jsonMapper = new JsonMapper();
			model.addAttribute("activityRecord", jsonMapper.toJson(elderCareRecordList));
			return "module/wechat/elderActivity";
			
		}
		//CommonConstants.UNSUBSCRIBED, cannot visit
		logger.error("relative's relationship status:"+status);
		return "error/403";
	}
}
