package com.sjtu.icare.modules.sys.utils.security;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;

public class MultiSessionStorageEvaluator implements SessionStorageEvaluator{
	private static final Logger logger = Logger.getLogger(MultiSessionStorageEvaluator.class);
	public boolean isSessionStorageEnabled(Subject subject) {

		  boolean enabled = false;

		  if(WebUtils.isWeb(subject)) {

		    HttpServletRequest request = WebUtils.getHttpRequest(subject);
		    if (request.getHeader("accept").indexOf("application/json")<0) {
		    	enabled = true;
			}
		    else {
		    	enabled = false;
			}
		    //set 'enabled' based on the current request.

		  } else {

		    //not a web request - maybe a RMI or daemon invocation?

		    //set 'enabled' another way …
			
			  return enabled;
		  }
		  return enabled;
	}
}
