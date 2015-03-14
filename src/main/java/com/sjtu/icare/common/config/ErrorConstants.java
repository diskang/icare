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

	public static final String GERO_AREA_GET_SERVICE_FAILED = "200001 GET 养老院区域：后台获取数据失败";
	public static final String GERO_AREA_POST_PARAM_INVALID = "200002 POST 养老院区域：请求参数错误";
	public static final String GERO_AREA_POST_SERVICE_FAILED = "200003 POST 养老院区域：后台插入数据失败";
	public static final String GERO_AREA_GET_SUBAREA_PARAM_INVALID = "200004 GET 养老院子区域：请求参数错误";
	public static final String GERO_AREA_GET_SUBAREA_SERVICE_FAILED = "200006 GET 养老院子区域：后台获取数据失败";
	public static final String GERO_AREA_PUT_SERVICE_FAILED = "200007 PUT 养老院区域：修改数据失败";
	public static final String GERO_AREA_DELETE_SERVICE_FAILED = "200008 DELETE 养老院区域：删除数据失败";

	// user service constants
	public static final String USER_UPDATE_INFO_PARAM_INVALID = "1000001 PUT 更新用户信息：请求参数错误";
	public static final String USER_FOR_ID_NOT_FOUND = "1000002 未取到该ID的用户";
	public static final String USER_UPDATE_INFO_SERVICE_ERROR = "1000003 PUT 更新用户信息：后台插入数据失败";
	public static final String USER_INFO_GET_PAGE_SERVICE_ERROR = "1000004 GET 获取用户列表：后台获取失败";
	public static final String USER_UPDATE_PASSWORD_PARAM_INVALID = "1000005 PUT 更新用户密码：请求参数错误";
	public static final String USER_UPDATE_PASSWORD_SERVICE_ERROR = "1000006 PUT 更新用户密码：后台插入数据失败";
	public static final String USER_UPDATE_ROLE_PARAM_INVALID = "1000007 PUT 更新用户角色：请求参数错误";
	public static final String USER_UPDATE_ROLE_SERVICE_ERROR = "1000008 PUT 更新用户角色：后台插入数据失败";
	public static final String USER_DELETE_SERVICE_ERROR = "1000009 DELETE 删除用户：后台插入数据失败";
	public static final String GERO_FOR_ID_NOT_FOUND = "1000010 未取到该ID的养老院";
	public static final String GERO_ROLE_GET_PAGE_SERVICE_ERROR = "1000011 GET 获取养老院角色列表：后台获取失败";
	public static final String ROLE_FOR_ID_NOT_FOUND = "1000012 未取到该ID的角色";
	public static final String ORDER_BY_PARAM_INVALID = "1000013 未取到该排序条件";
	public static final String GERO_ROLE_INSERT_PARAM_INVALID = "1000014 POST 插入养老院角色列表：请求参数错误";
	public static final String GERO_ROLE_INSERT_SERVICE_ERROR = "1000015 POST 插入养老院角色列表：后台获取失败";
	public static final String GERO_ROLE_INSERT_CONFLICT_ERROR = "1000016 POST 插入养老院角色列表：该养老院已存在该角色";
	public static final String USER_NO_PRIVILEGE_FOR_THIS_GERO = "1000017 用户没有操作此养老院的权限";
	public static final String GERO_ROLE_GET_PRIVILEGE_SERVICE_ERROR = "1000018 GET 查询养老院角色权限：后台获取失败";
	
	// staff schedule constants
	public static final String STAFF_SCHEDULE_PLAN_GET_PARAM_INVALID = "300001 GET 员工排信息：请求参数错误";
	public static final String STAFF_SCHEDULE_PLAN_GET_SERVICE_FAILED = "300002 GET 员工排班信息：后台获取失败";
	public static final String STAFF_SCHEDULE_PLAN_POST_PARAM_INVALID = "300003 POST 员工排班信息：请求参数错误";
	public static final String STAFF_SCHEDULE_PLAN_POST_SERVICE_FAILED = "300004 POST 员工排班信息：后台插入数据失败";
	public static final String STAFF_SCHEDULE_PLAN_SPECIFIC_DAY_GET_PARAM_INVALID = "300005 GET 员工某天排班信息：请求参数错误";
	public static final String STAFF_SCHEDULE_PLAN_SPECIFIC_DAY_GET_SERVICE_FAILED = "300006 GET 员工某天排班信息：后台获取失败";
	public static final String STAFF_SCHEDULE_PLAN_SPECIFIC_DAY_POST_PARAM_INVALID = "300007 POST 员工某天排班信息：请求参数错误";
	public static final String STAFF_SCHEDULE_PLAN_SPECIFIC_DAY_POST_SERVICE_FAILED = "300008 POST 员工某天排班信息：后台插入数据失败";
	public static final String STAFF_SCHEDULE_PLAN_SPECIFIC_DAY_DELETE_PARAM_INVALID = "300009 DELETE 员工某天排班信息：请求参数错误";
	public static final String STAFF_SCHEDULE_PLAN_SPECIFIC_DAY_DELETE_SERVICE_FAILED = "300011 DELETE 员工某天排班信息：后台删除信息失败";
	
	// gero staff schedule plan constants 
	public static final String GERO_STAFF_SCHEDULE_PLAN_GET_PARAM_INVALID = "370001 GET 养老院排班信息：请求参数错误";
	public static final String GERO_STAFF_SCHEDULE_PLAN_GET_SERVICE_FAILED = "370002 GET 养老院排班信息：后台获取失败";
	public static final String GERO_STAFF_SCHEDULE_PLAN_PUT_PARAM_INVALID = "370003 PUT 养老院排班信息：请求参数错误";
	public static final String GERO_STAFF_SCHEDULE_PLAN_PUT_SERVICE_FAILED = "370004 PUT 养老院排班信息：后台获取失败";
	
	
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
