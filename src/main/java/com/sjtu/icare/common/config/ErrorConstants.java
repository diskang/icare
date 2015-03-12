/**
 * @Package com.sjtu.icare.common.config
 * @Description TODO
 * @date Mar 7, 2015 5:11:17 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.common.config;


public class ErrorConstants {
	public static final String LOG_MARKER_START = "~~*$$";
	public static final String LOG_MARKER_END = "$$*~~";
	public static final String ERRCONST_MARKER = "#";
	
	
	public static final String COMMON_ERROR_INVALID_JSON = "200001 json格式错误";

	// elder service constants
	public static final String ELDER_TEMPERATURE_GET_PARAM_INVALID = "100001 GET 老人温度：参数错误";
	public static final String ELDER_TEMPERATURE_GET_SERVICE_FAILED = "100002 GET 老人温度：后台获取数据失败";
	public static final String ELDER_TEMPERATURE_POST_PARAM_INVALID = "100003 POST 老人温度：请求参数错误";
	public static final String ELDER_TEMPERATURE_POST_SERVICE_FAILED = "100004 POST 老人温度：后台插入数据失败";
	public static final String ELDER_BLOOD_PRESSURE_GET_PARAM_INVALID = "100005 GET 老人血压：参数错误";
	public static final String ELDER_BLOOD_PRESSURE_GET_SERVICE_FAILED = "100006 GET 老人血压：后台获取数据失败";
	public static final String ELDER_BLOOD_PRESSURE_POST_PARAM_INVALID = "100007 POST 老人血压：请求参数错误";
	public static final String ELDER_BLOOD_PRESSURE_POST_SERVICE_FAILED = "100008 POST 老人血压：后台插入数据失败";
	public static final String ELDER_HEART_RATE_GET_PARAM_INVALID = "100009 GET 老人心率：参数错误";
	public static final String ELDER_HEART_RATE_GET_SERVICE_FAILED = "100010 GET 老人心率：后台获取数据失败";
	public static final String ELDER_HEART_RATE_POST_PARAM_INVALID = "100011 POST 老人心率：请求参数错误";
	public static final String ELDER_HEART_RATE_POST_SERVICE_FAILED = "100012 POST 老人心率：后台插入数据失败";

	// gero service constants
	public static final String GERO_AREA_GET_SERVICE_FAILED = "200001 GET 养老院区域：后台获取数据失败";
	public static final String GERO_AREA_POST_PARAM_INVALID = "200002 POST 养老院区域：请求参数错误";
	public static final String GERO_AREA_POST_SERVICE_FAILED = "200003 POST 养老院区域：后台插入数据失败";
	public static final String GERO_AREA_GET_SUBAREA_PARAM_INVALID = "200004 GET 养老院子区域：请求参数错误";
	public static final String GERO_AREA_GET_SUBAREA_SERVICE_FAILED = "200006 GET 养老院子区域：后台获取数据失败";

	public static String format(String errorConstant, String otherMessage) {
		if (errorConstant == null)
			return null;
		if (otherMessage == null)
			otherMessage = "";
		
		return 
				LOG_MARKER_START +
				ERRCONST_MARKER + errorConstant + ERRCONST_MARKER +
				otherMessage +
				LOG_MARKER_END + '\n';
	}
	
	public static String getNumber(String errorConstant) {
		if (errorConstant == null)
			return null;
		String[] strings = errorConstant.split("[ ]+");
		if (strings.length > 0)
			return strings[0];
		else 
			return null;
	}

	public static String getMessage(String errorConstant) {
		if (errorConstant == null)
			return null;
		String[] strings = errorConstant.split("[ ]+");
		StringBuilder sb = new StringBuilder();
		if (strings.length > 1) {
			for (int i=1; i < strings.length; ++i){
				sb.append(strings[i]);
				sb.append(" ");
			}
			return sb.toString().trim();
		} else 
			return null;
	}

	public static void main(String[] args) {
		System.out.println(getNumber(ELDER_HEART_RATE_GET_PARAM_INVALID));
		System.out.println(getMessage(ELDER_HEART_RATE_GET_PARAM_INVALID));
		System.out.println(format(ELDER_HEART_RATE_GET_PARAM_INVALID, null));
	}
	
}
