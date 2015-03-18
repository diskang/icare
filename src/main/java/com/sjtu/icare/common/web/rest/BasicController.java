package com.sjtu.icare.common.web.rest;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;

import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.config.Global;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.utils.UserUtils;
import com.sjtu.icare.modules.sys.utils.security.SystemAuthorizingRealm.UserPrincipal;

public class BasicController {
	
	private static Logger logger = Logger.getLogger(BasicController.class);
	
	protected void checkPermission(HttpServletRequest request){
		User user = getCurrentUser();
		String requestUrl = request.getRequestURI();
		String requestMethod = request.getMethod();
		String webAPIPath = Global.getWebAPIPath();
		String serviceAPIPath = Global.getServiceAPIPath();
		if (requestUrl.contains(serviceAPIPath))
			requestUrl = requestUrl.substring((requestUrl.indexOf(serviceAPIPath)+serviceAPIPath.length()));
		else
			requestUrl = requestUrl.substring((requestUrl.indexOf(webAPIPath)+webAPIPath.length()));
		logger.debug(requestUrl);
		logger.debug(requestMethod);
		if (user.getUserType()==0)
			return;
		String[] urlList = requestUrl.split("/");
		for (int i=0 ; i<urlList.length ; i++){
			if (StringUtils.isNumeric(urlList[i])) {
				urlList[i] = "{id}";
			}
			else if (urlList[i].contains("-")) {
				urlList[i] = "{date}";
			}
		}
		String requestPermission = StringUtils.join(urlList,"/") + "#" + requestMethod;
		logger.debug(requestPermission);
		SecurityUtils.getSubject().checkPermission(requestPermission);
	}
	
	/**
	 *  获取当前用户
	 * @return
	 */
	protected User getCurrentUser(){
		User user;
		try {
			if (!SecurityUtils.getSubject().isAuthenticated()) {
				throw new Exception();
			}
			Subject subject = SecurityUtils.getSubject();
			String username;
			if (subject.getPrincipal().equals(String.class)) {
				username = (String) subject.getPrincipal();
			}else {
				username = ((UserPrincipal) subject.getPrincipal()).getUsername();
			}
			user = UserUtils.getByLoginName(username);
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.GET_USER_PRINCIPAL_FAILED,
					"[" + "]" );
			logger.error(message);
			throw new RestException(HttpStatus.UNAUTHORIZED, message);
		}
		return user;
	}
}
