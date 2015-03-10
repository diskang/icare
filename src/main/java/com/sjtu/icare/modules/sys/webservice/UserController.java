package com.sjtu.icare.modules.sys.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.ws.spi.http.HttpHandler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.sjtu.icare.common.persistence.Page;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.modules.sys.entity.Privilege;
import com.sjtu.icare.modules.sys.entity.Role;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;
import com.sjtu.icare.modules.sys.utils.UserUtils;


/**
*
* UserController
* 主要处理 /resthouse/user 内的api
* 
* @author jty
*/
@RestController
@RequestMapping("/user")
public class UserController {
	private static Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private SystemService systemService;
	
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> getUserInfoList(
			@RequestParam int page,
			@RequestParam int limit
			){
		BasicReturnedJson result = new BasicReturnedJson();
		Page<User> userPage = new Page<User>(page, limit);
		userPage = systemService.findUser(userPage, new User());
		List<User> userList = userPage.getList();
		int total = (int) userPage.getCount();
		for (User user : userList){
			if (user != null){
				result.addEntity(getUserMapFromUser(user));
				result.setStatus(HttpStatus.OK.value());
				result.setError(HttpStatus.OK.name());
				result.setTotal(total);
			}else {
				result.setStatus(HttpStatus.NOT_FOUND.value());
				result.setError(HttpStatus.NOT_FOUND.name());
				break;
			}
		}
		return result.getMap();
	}
	
	@RequestMapping(value = "/{uid}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> getUserInfoFromUserId(@PathVariable("uid") int uid){
		BasicReturnedJson result = new BasicReturnedJson();
		User user = UserUtils.get(uid);
		if (user != null){			
			result.addEntity(getUserMapFromUser(user));
			result.setStatus(HttpStatus.OK.value());
			result.setError(HttpStatus.OK.name());
		}else {
			result.setStatus(HttpStatus.NOT_FOUND.value());
			result.setError(HttpStatus.NOT_FOUND.name());
		}
		return result.getMap();
	}
	
	@RequestMapping(value = "/{uid}", method = RequestMethod.PUT, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> updateUserInfoFromUserId(
			@PathVariable("uid") int uid,
			@RequestParam("name") String name,
			@RequestParam("photo_url") String photoUrl
			){
		User user = UserUtils.get(uid);
		BasicReturnedJson result = new BasicReturnedJson();
		if (user != null){	
			user.setName(name);
			user.setPhotoUrl(photoUrl);	
			systemService.updateUserInfo(user);
			result.addEntity(getUserMapFromUser(user));
			result.setStatus(HttpStatus.OK.value());
			result.setError(HttpStatus.OK.name());
		}else {
			result.setStatus(HttpStatus.NOT_FOUND.value());
			result.setError(HttpStatus.NOT_FOUND.name());
		}
		return result.getMap();
	}
	
	@RequestMapping(value = "/{uid}/password", method = RequestMethod.PUT, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> updateUserPasswordFromUserId(
			@PathVariable("uid") int uid,
			@RequestParam("password") String password
			){
		User user = UserUtils.get(uid);
		BasicReturnedJson result = new BasicReturnedJson();
		if (user != null){	
			systemService.updatePasswordById(uid, password);
			result.setStatus(HttpStatus.OK.value());
			result.setError(HttpStatus.OK.name());
		}else {
			result.setStatus(HttpStatus.NOT_FOUND.value());
			result.setError(HttpStatus.NOT_FOUND.name());
		}
		return result.getMap();
	}
	
	
	
	private Map<String, Object> getUserMapFromUser(User user){
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("id", user.getId());
		userMap.put("name", user.getName());
		userMap.put("username", user.getLoginName());
		userMap.put("user_type", user.getUserType());
		userMap.put("user_id", user.getUserId());
		ArrayList<Object> roleList = new ArrayList<Object>();
		for (Role role : user.getRoleList()){
			Map<String, Object> roleMap = new HashMap<String, Object>();
			roleMap.put("id", role.getId());
			roleMap.put("name", role.getName());
			roleList.add(roleMap);
		}
		userMap.put("role_list", roleList);
		ArrayList<Object> privilegeList = new ArrayList<Object>();
		for (Privilege privilege : UserUtils.getPrivilegeList(user)){
			privilegeList.add(getPrivilegeMapFromPrivilege(privilege));
		}
		userMap.put("privilege_list", privilegeList);
		userMap.put("register_date", user.getRegisterDate());
		userMap.put("cancel_date", user.getCancelDate());
		userMap.put("photo_url", user.getPhotoUrl());
		return userMap;
	}
	
	private Map<String, Object> getRoleMapFromRole(Role role) {
		Map<String, Object> roleMap = new HashMap<String, Object>();
		roleMap.put("id", role.getId());
		roleMap.put("name", role.getName());
		roleMap.put("note", role.getNotes());
		ArrayList<Object> privilegeList = new ArrayList<Object>();
		for (Privilege privilege : role.getPrivilegeList()){
			privilegeList.add(getPrivilegeMapFromPrivilege(privilege));
		}
		roleMap.put("privilege_list", privilegeList);
		return roleMap;
	}
	
	private Map<String, Object> getPrivilegeMapFromPrivilege(Privilege privilege){
		Map<String, Object> privilegeMap = new HashMap<String, Object>();
		privilegeMap.put("id", privilege.getId());
		privilegeMap.put("name", privilege.getName());
		privilegeMap.put("parent_id", privilege.getParentId());
		privilegeMap.put("parent_ids", privilege.getParentIds());
		privilegeMap.put("permission", privilege.getPermission());
		privilegeMap.put("href", privilege.getHref());
		privilegeMap.put("icon", privilege.getIcon());
		return privilegeMap;
	}
	
}
