/**
 * Copyright &copy; 2012-2013  All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sjtu.icare.modules.sys.utils.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.modules.sys.webservice.UserController;


/**
 * 表单验证（包含验证码）过滤类
 * @author ThinkGem
 * @version 2013-5-19
 */
@Service
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

	private static Logger logger = Logger.getLogger(FormAuthenticationFilter.class);
	
	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;

	public String getCaptchaParam() {
		return captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		if (password==null){
			password = "";
		}
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		String captcha = getCaptcha(request);
		return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha);
	}
	
	protected boolean executeLogin(ServletRequest request,  
            ServletResponse response) throws Exception {
		UsernamePasswordToken token = (UsernamePasswordToken) createToken(request, response);
        try {  
            /*图形验证码验证*/    
            Subject subject = getSubject(request, response);  
            subject.login(token);//正常验证  
            logger.info(token.getUsername()+"登录成功");  
            return onLoginSuccess(token, subject, request, response);  
        }catch (AuthenticationException e) {  
        	logger.info(token.getUsername()+"登录失败--"+e);  
            return onLoginFailure(token, e, request, response);
        }  
	}
	
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {  
		
		UsernamePasswordToken token = (UsernamePasswordToken) createToken(request, response); 
	    logger.debug("before login");
	     
    	if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (logger.isTraceEnabled()) {
                	logger.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (logger.isTraceEnabled()) {
                	logger.trace("Login page view.");
                }
                //allow them to see the login page ;)
                return true;
            }
        }else {
        	try { 
			Subject subject = getSubject(request, response);
			logger.debug("subject getted");
			subject.login(token);
        	} catch ( UnknownAccountException uae ) { 
    	    	onLoginFail(response,"No Account");
    	    	return false;
    	    } catch ( IncorrectCredentialsException ice ) { 
    	    	onLoginFail(response,"Key incorrect");
    	    	return false;
    	    } catch (LockedAccountException lae ) { 
    	    	onLoginFail(response,"Account Locked");
    	    	return false;
    	    } catch (ExcessiveAttemptsException eae ) { 
    	    	onLoginFail(response,"Excessive Attempts");
    	    	return false;
    	    }catch (AuthenticationException e) {
    	    	onLoginFail(response,"Authentication Error");
    	    	return false;
    		}catch (UnauthorizedException e) {
    	    	onLoginFail(response,"Unauthorized Error");
    	    	return false;
    		}catch (Exception e) {  
    	    	e.printStackTrace();  
    			onLoginFail(response,"Login Error"); //6、登录失败  
    			return false;  
    	    }  
		}
	    
	    return true;  
	}
	//登录失败时默认返回401状态码  
	private void onLoginFail(ServletResponse response,String errorString) throws IOException {  
	    HttpServletResponse httpResponse = (HttpServletResponse) response;  
	    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  
//	    JSONObject errorJsonObject = new JSONObject();
//	    errorJsonObject.put("errno", HttpServletResponse.SC_UNAUTHORIZED+"");
//	    errorJsonObject.put("error", errorString);
	    BasicReturnedJson result = new BasicReturnedJson();
	    result.setError(errorString);
	    result.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    httpResponse.getWriter().write(result.toString());
	}
}