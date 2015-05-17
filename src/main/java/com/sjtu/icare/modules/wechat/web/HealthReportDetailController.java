package com.sjtu.icare.modules.wechat.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import me.chanjar.weixin.mp.api.WxMpServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.modules.elder.service.IElderHealthDataService;
import com.sjtu.icare.modules.wechat.service.IElderRelativeRelationshipService;

@RestController
@RequestMapping({"/wechat/healthreport/detail"})
public class HealthReportDetailController {
	@Autowired WxMpServiceImpl wxMpService;
	@Autowired
	private IElderHealthDataService elderHealthDataService;
	@Autowired
	private IElderRelativeRelationshipService elderRelativeRelationshipService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> getMenu(@RequestParam("open_id") String openId,HttpServletRequest request){
//		checkApi(request);
		
		BasicReturnedJson result = new BasicReturnedJson();
		
		
			
		result.addEntity(openId);
			

		return result.getMap();
	}
	
}
