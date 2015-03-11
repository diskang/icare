/**
 * @Package com.sjtu.icare.common.utils
 * @Description TODO
 * @date Mar 9, 2015 7:38:33 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSON;
import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.web.rest.RestException;

public class ParamUtils {
	public static Date convertStringToDate(String param) {
		Date date = null;
		
		SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = pattern.parse(param);
		} catch (Exception e){
			// pass
		}
		pattern = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = pattern.parse(param);
		} catch (Exception e){
			// pass
		}
		
		return date;
	}
	/**
	 * 
	 * @Title getMapByJson
	 * @Description 如果JSON格式错误会直接返回 RestException，并 log。
	 * @param @param jsonString
	 * @param @param logger
	 * @param @return
	 * @param @throws RestException
	 * @return Map<String,Object>
	 * @throws
	 */
	public static Map<String, Object> getMapByJson(String jsonString, Logger logger) throws RestException {
		Map<String, Object> requestBodyParamMap;
		
		try {
			requestBodyParamMap = (Map<String, Object>) JSON.parse(jsonString);
			return requestBodyParamMap;
			
		} catch(Exception e) {
			String message = "#" + ErrorConstants.COMMON_ERROR_INVALID_JSON + "#\n" + 
					"非法JSON:\n" +
					jsonString +
					"\n";
			if (logger != null) 
				logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
	}
	
}