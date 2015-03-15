package com.sjtu.icare.common.web.rest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.modules.sys.service.SystemService;

@RestController
public class GeroBaseController extends SysBaseController{
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SystemService systemService;
	
	/**
	 *  检查用户是否有管理员权限
	 * @param gid
	 */
	public void checkGero(int gid) {
		try {
			SecurityUtils.getSubject().checkRole("gero:"+gid);
		} catch (AuthorizationException e) {
			String message = ErrorConstants.format(ErrorConstants.USER_NO_PRIVILEGE_FOR_THIS_GERO,"gero_id:"+gid);
			logger.error(message);
			throw new RestException(HttpStatus.UNAUTHORIZED, message);
		}
	}
}
