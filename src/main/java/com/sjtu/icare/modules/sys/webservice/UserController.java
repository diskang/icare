package com.sjtu.icare.modules.sys.webservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.sjtu.icare.common.config.OrderByConstant;
import com.sjtu.icare.common.persistence.Page;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.ParamUtils;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.common.web.rest.SysBaseController;
import com.sjtu.icare.modules.sys.entity.Role;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;


/**
*
* UserController
* 主要处理 /resthouse/user 内的api
* 
* @author jty
*/
@RestController
@RequestMapping({"${api.web}/user","${api.service}/user"})
public class UserController extends SysBaseController{
	
	private static Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private SystemService systemService;
	
	/**
	 * 获取用户列表（超管）（分页）
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> getUserInfoList(
			HttpServletRequest request,
			@RequestParam("page") int page,
			@RequestParam("rows") int limit,
			@RequestParam("order_by") String orderByTag
			){
		checkPermission(request);
		
		BasicReturnedJson result = new BasicReturnedJson();
		
		Page<User> userPage = new Page<User>(page, limit);
		
		userPage = setOrderBy(userPage, orderByTag);
		
		// get page from service
		List<User> userList;
		int total;
		try {
			userPage = systemService.findUser(userPage, new User());
			userList = userPage.getList();
			total = (int) userPage.getCount();
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.USER_INFO_GET_PAGE_SERVICE_ERROR,"");
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
		
		for (User user : userList){
			if (user != null){
				result.addEntity(getUserMapFromUser(user));
				result.setStatus(HttpStatus.OK.value());
				result.setError(HttpStatus.OK.name());
				result.setTotal(total);
			}else {
				String message = ErrorConstants.format(ErrorConstants.USER_FOR_ID_NOT_FOUND,"");
				logger.error(message);
				throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
			}
		}
		return result.getMap();
	}
	
	/**
	 * 获取uid用户详细信息
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/{uid}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> getUserInfoFromUserId(
			HttpServletRequest request,
			@PathVariable("uid") int uid){
		checkPermission(request);
		
		BasicReturnedJson result = new BasicReturnedJson();
		
		User user = getUserFromId(uid);
		
		result.addEntity(getUserMapFromUser(user));
		result.setStatus(HttpStatus.OK.value());
		result.setError(HttpStatus.OK.name());
		
		return result.getMap();
	}
	
	/**
	 * 更新用户详细信息
	 * @param uid
	 * @param inJson
	 * @return
	 */
	@RequestMapping(value = "/{uid}", method = RequestMethod.PUT, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> updateUserInfoFromUserId(
			@PathVariable("uid") int uid,
			@RequestBody String inJson
			){
		Map<String, Object> requestBodyParamMap = ParamUtils.getMapByJson(inJson, logger);
		String name;
		String photoUrl;
		
		// 输入参数检查
		try {
			name = (String) String.valueOf(requestBodyParamMap.get("name"));
			photoUrl = (String) String.valueOf(requestBodyParamMap.get("photo_url"));
						
			if (name == null || photoUrl == null || name.equals("null") || photoUrl.equals("null"))
				throw new Exception();
			
			// 此处可添加url的验证方法
			
		} catch(Exception e) {
			String message = ErrorConstants.format(ErrorConstants.USER_UPDATE_INFO_PARAM_INVALID,
					"[name=" + requestBodyParamMap.get("name") + "]" +
					"[photoUrl=" + requestBodyParamMap.get("photoUrl") + "]");
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		User user = getUserFromId(uid);
		
		BasicReturnedJson result = new BasicReturnedJson();
		
		user.setName(name);
		user.setPhotoUrl(photoUrl);	
		
		try {
			systemService.updateUserInfo(user);
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.USER_UPDATE_INFO_SERVICE_ERROR,
					"[name=" + requestBodyParamMap.get("name") + "]" +
					"[photoUrl=" + requestBodyParamMap.get("photoUrl") + "]");
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
		result.addEntity(getUserMapFromUser(user));
		result.setStatus(HttpStatus.OK.value());
		result.setError(HttpStatus.OK.name());
		
		return result.getMap();
	}
	
	/**
	 * 更新用户密码
	 * @param uid
	 * @param inJson
	 * @return
	 */
	@RequestMapping(value = "/{uid}/password", method = RequestMethod.PUT, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> updateUserPasswordFromUserId(
			@PathVariable("uid") int uid,
			@RequestBody String inJson
			){
		Map<String, Object> requestBodyParamMap = ParamUtils.getMapByJson(inJson, logger);
		String password;
		
		// 输入参数检查
		try {
			password = (String) String.valueOf(requestBodyParamMap.get("password"));
						
			if (password == null || password.equals("null"))
				throw new Exception();
			
			// 此处可添加密码长度的验证方法
			
		} catch(Exception e) {
			String message = ErrorConstants.format(ErrorConstants.USER_UPDATE_PASSWORD_PARAM_INVALID,
					"[password=" + requestBodyParamMap.get("password") + "]");
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		User user = getUserFromId(uid);
		
		BasicReturnedJson result = new BasicReturnedJson();
			
		try {
			systemService.updatePasswordById(user, password);
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.USER_UPDATE_PASSWORD_SERVICE_ERROR,
					"[password=" + requestBodyParamMap.get("password") + "]");
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
		result.setStatus(HttpStatus.OK.value());
		result.setError(HttpStatus.OK.name());
		
		return result.getMap();
	}
	
	/**
	 * 更新用户角色
	 * @param uid
	 * @param requestString
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/{uid}/role", method = RequestMethod.PUT, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> updateUserRoleFromUserId(
			@PathVariable("uid") int uid,
			@RequestBody String inJson
			){
		Map<String, Object> requestBodyParamMap = ParamUtils.getMapByJson(inJson, logger);
		List<Integer> roleIds;

		// 输入参数检查
		try {
			roleIds = (List<Integer>) requestBodyParamMap.get("ids");
			
		} catch(Exception e) {
			String message = ErrorConstants.format(ErrorConstants.USER_UPDATE_ROLE_PARAM_INVALID,
					"[roleIds=" + requestBodyParamMap.get("ids") + "]");
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
				
		User user = getUserFromId(uid);
		
		BasicReturnedJson result = new BasicReturnedJson();
		
		List<Role> roleList = new ArrayList<Role>();
		for (int id : roleIds){
			if (id>0){
				roleList.add(new Role(id));
			}
		}
		
		user.setRoleList(roleList);
		
		try {
			if (!systemService.updateUserRoles(user)) {
				throw new Exception();
			}
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.USER_UPDATE_ROLE_SERVICE_ERROR,
					"[roleIds=" + requestBodyParamMap.get("ids") + "]");
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
				
		user = getUserFromId(uid);
		result.addEntity(getUserMapFromUser(user));
		result.setStatus(HttpStatus.OK.value());
		result.setError(HttpStatus.OK.name());
		
		return result.getMap();
	}
	
	/**
	 * 删除用户（逻辑）
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/{uid}", method = RequestMethod.DELETE, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> deleteUserFromUserId(@PathVariable("uid") int uid){
		User user = getUserFromId(uid);
		BasicReturnedJson result = new BasicReturnedJson();

		try {
			systemService.deleteUser(user);
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.USER_UPDATE_ROLE_SERVICE_ERROR,
					"[uid=" + uid + "]");
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
		result.setStatus(HttpStatus.OK.value());
		result.setError(HttpStatus.OK.name());
		
		return result.getMap();
	}	

}
