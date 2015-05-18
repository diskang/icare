package com.sjtu.icare.modules.wechat.webservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.sjtu.icare.common.web.rest.BasicController;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.elder.service.IElderInfoService;
import com.sjtu.icare.modules.sys.entity.User;

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
}
