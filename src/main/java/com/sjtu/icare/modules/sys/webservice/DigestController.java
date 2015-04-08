package com.sjtu.icare.modules.sys.webservice;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.security.Digests;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.Encodes;
import com.sjtu.icare.common.utils.ParamUtils;
import com.sjtu.icare.common.web.rest.BasicController;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.common.web.rest.SysBaseController;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;

/**
 * PAD 登录
 * @author JiaoTianyi
 *
 */
@RestController
@RequestMapping({"/pad/login"})
public class DigestController extends SysBaseController{
	private static Logger logger = Logger.getLogger(DigestController.class);
	
	@Autowired
	private SystemService systemService;
	
	@SuppressWarnings("static-access")
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)

	public Map<String, Object> insertGeroRole(
			@RequestBody String inJson
			){
		BasicReturnedJson result = new BasicReturnedJson();
		Map<String, Object> digestMap = new HashMap<String, Object>();
		
		Map<String, Object> requestBodyParamMap = ParamUtils.getMapByJson(inJson, logger);
		String username;
		String password;
		
		// 输入参数检查
		try {
			username = (String) String.valueOf(requestBodyParamMap.get("username"));
			password = (String) String.valueOf(requestBodyParamMap.get("password"));
						
			if (username == null || username.equals(""))
				throw new Exception();
			
		} catch(Exception e) {
			String message = "GET_DIGEST_BAD_REQUEST";
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		User user = systemService.getUserByUsername(username);
		
		if (user == null) {
			String message = "GET_DIGEST_NOT_FOUND";
			logger.error(message);
			throw new RestException(HttpStatus.NOT_FOUND, message);
		}
		
		if (!systemService.validatePassword(password, user.getPassword())) {
			String message = "GET_DIGEST_UNAUTHORIZED";
			logger.error(message);
			throw new RestException(HttpStatus.UNAUTHORIZED, message);
		}else {
			String digest = Encodes.encodeHex(Digests.sha1(user.getPassword().getBytes()));
			logger.debug("user:"+username+"digest:"+digest);
			digestMap.put("digest", digest);
		}
		digestMap.putAll(getUserMapFromUser(user));
		result.addEntity(digestMap);
		
		return result.getMap();
	}
}
