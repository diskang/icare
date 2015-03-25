package com.sjtu.icare.modules.sys.utils.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import com.alibaba.fastjson.JSONObject;
import com.sjtu.icare.common.utils.BasicReturnedJson;

/**
 * 无状态验证过滤类
 * @author jty
 * @version 2015-03-06
 */
public class StatelessAuthcFilter extends AccessControlFilter {
	private static final Logger logger = Logger.getLogger(StatelessAuthcFilter.class);
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {  
		return false;  
	}
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {  
		//1、客户端生成的消息摘要  
		String clientDigest = request.getParameter("digest");  
		//2、客户端传入的用户身份  
		String username = request.getParameter("username");  
	    //3、客户端请求的参数列表  
	    Map<String, String[]> params = new HashMap<String, String[]>(request.getParameterMap());  
	    params.remove("digest");  
	    //4、生成无状态Token  
	    
	    StatelessToken token = new StatelessToken(username, params, clientDigest);  
	    logger.debug("before login");
	    try {  
			//5、委托给Realm进行登录 
			Subject subject = getSubject(request, response);
			logger.debug("subject getted");
			subject.login(token);
			// 鉴权转移到controller方法去做
//			String permission = getPathWithinApplication(request)+":"+GET_METHOD;
//			logger.debug(permission);
//			subject.checkPermission(permission);
			
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
	    return true;  
	}
	//登录失败时默认返回401状态码  
	private void onLoginFail(ServletResponse response,String errorString) throws IOException {  
	    HttpServletResponse httpResponse = (HttpServletResponse) response;  
	    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    httpResponse.setHeader("WWW-Authenticate", "Basic realm='fake'");
//	    JSONObject errorJsonObject = new JSONObject();
//	    errorJsonObject.put("errno", HttpServletResponse.SC_UNAUTHORIZED+"");
//	    errorJsonObject.put("error", errorString);
	    BasicReturnedJson result = new BasicReturnedJson();
	    result.setError(errorString);
	    result.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    httpResponse.getWriter().write(result.toString());  
	}
}
