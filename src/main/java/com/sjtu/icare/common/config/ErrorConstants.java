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
	public static final String GERO_ROLE_GET_ROLE_NOT_IN_GERO = "1000019 GET 查询养老院角色：该角色不属于此养老院";
	public static final String GERO_ROLE_UPDATE_CONFLICT_ERROR = "1000020 PUT 修改养老院角色列表：该养老院已存在该角色";
	public static final String GERO_ROLE_UPDATE_SERVICE_ERROR = "1000021 PUT 修改养老院角色列表：后台获取失败";
	public static final String GERO_ROLE_DELETE_SERVICE_ERROR = "1000022 DELETE 删除养老院角色：后台删除数据失败";
	public static final String GERO_ROLE_PRIVILEGE_INSERT_PARAM_INVALID = "1000023 POST 插入养老院角色权限：请求参数错误";
	public static final String GET_USER_PRINCIPAL_FAILED = "1000024 GET 获取当前用户令牌失败";
	public static final String GET_USER_PRIVILEGE_FAILED = "1000025 GET 获取当前用户权限失败";
	public static final String SORT_PRIVILEGE_FAILED = "1000026 GET 插入养老院角色权限：权限排序失败";
	public static final String GERO_ROLE_INSERT_PRIVILEGE_NO_PARENT = "1000027 GET 插入养老院角色权限：该角色没有此权限的父权限";
	public static final String GERO_ROLE_PRIVILEGE_DELETE_PARAM_INVALID = "1000028 DELETE 删除养老院角色权限：请求参数错误";
	public static final String GERO_ROLE_PRIVILEGE_DELETE_PRIVILEGE_NOT_FOUND = "1000029 DELETE 删除养老院角色权限：删除权限不存在";
	public static final String GERO_ROLE_PRIVILEGE_DELETE_SERVICE_ERROR = "1000030 DELETE 删除养老院角色权限：后台删除数据失败";
	public static final String GET_PRIVILEGE_LIST_SERVICE_ERROR = "1000031 GET 查询权限列表：后台获取失败";
	public static final String GET_PRIVILEGE_LIST_EMPTY_ERROR = "1000032 GET 查询权限列表：未取到数据";
	public static final String INSERT_PRIVILEGE_PARAM_INVALID = "1000033 POST 插入权限：请求参数错误";
	public static final String GET_PARENT_PRIVILEGE_ERROR = "1000033 POST 插入权限：系统取父权限失败";
	public static final String GET_PARENT_PRIVILEGE_NOT_FOUND = "1000033 POST 插入权限：父权限不存在";
	public static final String INSERT_PRIVILEGE_SERVICE_ERROR = "1000034 POST 插入权限：后台插入失败";
	public static final String GET_PRIVILEGE_NOT_FOUND = "1000035 PUT 修改权限：无此权限";
	public static final String GET_PRIVILEGE_SERVICE_ERROR = "1000036 PUT 修改权限：查询权限后台失败";
	public static final String UPDATE_PRIVILEGE_SERVICE_ERROR = "1000037 PUT 修改权限：修改权限后台失败";
	public static final String DELETE_PRIVILEGE_SERVICE_ERROR = "1000038 DELETE 删除权限：删除权限后台失败";
	public static final String GERO_ROLE_USER_INSERT_PARAM_INVALID = "1000039 INSERT 添加角色用户：请求参数错误";
	public static final String GERO_ROLE_USER_INSERT_NOT_FOUND = "1000040 INSERT 添加角色用户：养老院不存在该用户";
	public static final String GERO_ROLE_USER_SERVICE_ERROR = "1000040 INSERT 添加角色用户：后台修改失败";
	
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
	
	// upload service
	public static final String PHOTO_UPLOAD_TYPE_ERROR = "2000001 INSERT 上传图片：上传图片格式不正确";
	public static final String PHOTO_UPLOAD_SIZE_ERROR = "2000002 INSERT 上传图片：上传文件过大，不得大于1M";
	public static final String PHOTO_UPLOAD_RESOLUTION_ERROR = "2000003 INSERT 上传图片：上传分辨率不正确";
	public static final String PHOTO_UPLOAD_SERVICE_ERROR = "2000004 INSERT 上传图片：后台操作错误";
	
	// duty carer constants
	public static final String DUTY_CARER_ELDER_GET_PARAM_INVALID = "380001 GET 负责老人护工信息：请求参数错误";
	public static final String DUTY_CARER_ELDER_GET_SERVICE_FAILED = "380002 GET 负责老人护工信息：后台获取失败";
	public static final String DUTY_CARER_AREA_GET_PARAM_INVALID = "380003 GET 负责区域护工信息：请求参数错误";
	public static final String DUTY_CARER_AREA_GET_SERVICE_FAILED = "380004 GET 负责区域护工信息：后台获取失败";
	
	// gero care item constants
	public static final String GERO_CARE_ITEMS_GET_SERVICE_FAILED = "390001 GET 养老院护理项目：后台获取失败";
	public static final String GERO_CARE_ITEM_GET_SERVICE_FAILED = "390002 GET 养老院护理项目：后台获取失败";
	public static final String GERO_CARE_ITEM_DELETE_SERVICE_FAILED = "390003 DELETE 养老院护理项目：后台删除失败";
	public static final String GERO_CARE_ITEM_POST_PARAM_INVALID = "390004 POST 养老院护理项目：请求参数错误";
	public static final String GERO_CARE_ITEM_POST_SERVICE_FAILED = "390005 POST 养老院护理项目：后台插入失败";
	public static final String GERO_CARE_ITEM_PUT_PARAM_INVALID = "390006 PUT 养老院护理项目：请求参数错误";
	public static final String GERO_CARE_ITEM_PUT_SERVICE_FAILED = "390007 PUT 养老院护理项目：后台更新失败";
	
	// gero area item constants
	public static final String GERO_AREA_ITEMS_GET_SERVICE_FAILED = "400001 GET 养老院区域项目：后台获取失败";
	public static final String GERO_AREA_ITEM_POST_PARAM_INVALID = "400002 POST 养老院区域项目：请求参数错误";
	public static final String GERO_AREA_ITEM_POST_SERVICE_FAILED = "400003 POST 养老院区域项目：后台插入失败";
	public static final String GERO_AREA_ITEM_GET_SERVICE_FAILED = "400004 GET 养老院区域项目：后台获取失败";
	public static final String GERO_AREA_ITEM_PUT_PARAM_INVALID = "400005 PUT 养老院区域项目：请求参数错误";
	public static final String GERO_AREA_ITEM_PUT_SERVICE_FAILED = "400006 PUT 养老院区域项目：后台更新失败";
	public static final String GERO_AREA_ITEM_DELETE_SERVICE_FAILED = "400007 DELETE 养老院护理项目：后台删除失败";
	
	// carework records constants
	public static final String CAREWORK_ITEMS_GET_PARAM_INVALID = "410001 GET 老人护工项目记录：请求参数错误";
	public static final String CAREWORK_ITEMS_GET_SERVICE_FAILED = "410002 GET 老人护工项目记录：后台获取失败";
	public static final String CAREWORK_ITEMS_POST_PARAM_INVALID = "410003 POST 老人护工项目记录：请求参数错误";
	public static final String CAREWORK_ITEMS_POST_SERVICE_FAILED = "410004 POST 老人护工项目记录：后台服务失败";
	
	// areawork records constants
	public static final String AREAWORK_ITEMS_GET_PARAM_INVALID = "420001 GET 区域护工项目记录：请求参数错误";
	public static final String AREAWORK_ITEMS_GET_SERVICE_FAILED = "420002 GET 区域护工项目记录：后台获取失败";
	public static final String AREAWORK_ITEMS_POST_PARAM_INVALID = "420003 POST 区域护工项目记录：请求参数错误";
	public static final String AREAWORK_ITEMS_POST_SERVICE_FAILED = "420004 POST 区域护工项目记录：后台服务失败";
	
	// staff data constants
	public static final String STAFF_DATA_GET_PARAM_INVALID = "430001 GET 员工信息：请求参数错误";
	public static final String STAFF_DATA_GET_SERVICE_FAILED = "430002 GET 员工信息：后台服务失败";
	public static final String STAFF_DATA_POST_PARAM_INVALID = "430003 POST 员工信息：请求参数错误";
	public static final String STAFF_DATA_POST_SERVICE_FAILED = "430004 POST 员工信息：后台服务失败";
	public static final String STAFF_DATA_STAFF_GET_SERVICE_FAILED = "430005 GET 指定员工信息：后台服务失败";
	public static final String STAFF_DATA_STAFF_PUT_PARAM_INVALID = "430006 PUT 指定员工信息：请求参数错误";
	public static final String STAFF_DATA_STAFF_PUT_SERVICE_FAILED = "430007 PUT 指定员工信息：后台服务失败";
	public static final String STAFF_DATA_STAFF_DELETE_SERVICE_FAILED = "430008 DELETE 指定员工信息：后台服务失败";
	
	// elder info constants
	public static final String ELDER_INFO_GET_PARAM_INVALID = "440001 GET 老人信息：请求参数错误";
	public static final String ELDER_INFO_GET_SERVICE_FAILED = "440002 GET 老人信息：后台服务失败";
	public static final String ELDER_INFO_POST_PARAM_INVALID = "440003 POST 老人信息：请求参数错误";
	public static final String ELDER_INFO_POST_SERVICE_FAILED = "440004 POST 老人信息：后台服务失败";
	public static final String ELDER_INFO_ELDER_GET_SERVICE_FAILED = "440005 GET 指定老人信息：后台服务失败";
	public static final String ELDER_INFO_ELDER_PUT_PARAM_INVALID = "440006 PUT 老人信息：请求参数错误";
	public static final String ELDER_INFO_ELDER_PUT_SERVICE_FAILED = "440007 PUT 老人信息：后台服务失败";
	public static final String ELDER_INFO_ELDER_DELETE_SERVICE_FAILED = "440008 DELETE 老人信息：后台服务失败";
	
	// elder care item constants
	public static final String ELDER_ITEM_GET_SERVICE_FAILED = "450001 GET 老人项目：后台服务失败";
	public static final String ELDER_ITEM_POST_PARAM_INVALID = "450002 POST 老人项目：请求参数错误";
	public static final String ELDER_ITEM_POST_SERVICE_FAILED = "450003 POST 老人项目：后台服务失败";
	public static final String ELDER_ITEM_SPECIFIC_GET_SERVICE_FAILED = "450004 GET 指定老人项目：后台服务失败";
	public static final String ELDER_ITEM_SPECIFIC_PUT_PARAM_INVALID = "450005 PUT 指定老人项目：请求参数错误";
	public static final String ELDER_ITEM_SPECIFIC_PUT_SERVICE_FAILED = "450005 PUT 指定老人项目：后台服务失败";
	public static final String ELDER_ITEM_SPECIFIC_DELETE_SERVICE_FAILED = "450006 DELETE 指定老人项目：后台服务失败";
	
	
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
