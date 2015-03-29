package com.sjtu.icare.common.web.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.config.OrderByConstant;
import com.sjtu.icare.common.persistence.Page;
import com.sjtu.icare.modules.sys.entity.Gero;
import com.sjtu.icare.modules.sys.entity.Privilege;
import com.sjtu.icare.modules.sys.entity.Role;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;
import com.sjtu.icare.modules.sys.utils.UserUtils;
import com.sjtu.icare.modules.sys.utils.security.SystemAuthorizingRealm.UserPrincipal;
import com.sjtu.icare.modules.sys.webservice.UserController;

@RestController
public class SysBaseController extends BasicController {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private SystemService systemService;
	
	/**
	 * 通过id获取user
	 * @param uid
	 * @return
	 */
	protected User getUserFromId (int uid){
		User user;
		try {
			user = UserUtils.get(uid);
			if (user == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.USER_FOR_ID_NOT_FOUND,
					"[uid=" + uid + "]");
			logger.error(message);
			throw new RestException(HttpStatus.NOT_FOUND, message);
		}
		return user;
	}
	
	/**
	 *  id获取养老院
	 * @param gid
	 * @return
	 */
	protected Gero getGeroFromId(int gid){
		Gero gero = new Gero();
		try {
			gero = systemService.getGeroById(new Gero(gid));
			if (gero == null){
				throw new Exception();
			}
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.GERO_FOR_ID_NOT_FOUND,
					"[gid=" + gid + "]");
			logger.error(message);
			throw new RestException(HttpStatus.NOT_FOUND, message);
		}
		return gero;
	}
	
	/**
	 *  id获取角色
	 * @param rid
	 * @return
	 */
	protected Role getRoleFromId(int rid){
		Role role = new Role();
		try {
			role = systemService.getRoleById(new Role(rid));
			if (role == null){
				throw new Exception();
			}
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.ROLE_FOR_ID_NOT_FOUND,
					"[rid=" + rid + "]");
			logger.error(message);
			throw new RestException(HttpStatus.NOT_FOUND, message);
		}
		return role;
	}
	
	protected List<Privilege> getRolePrivileges(Role role) {
		List<Privilege> rolePrivileges;
		try {
			rolePrivileges = systemService.getPrivilegeListByRole(role).getPrivilegeList();
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.GERO_ROLE_GET_PRIVILEGE_SERVICE_ERROR,
					"[rid:" + role.getId()  + "]" );
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		return rolePrivileges;
	}
	
	
	
	
	
	/**
	 * user返回格式
	 * @param user
	 * @return
	 */
	protected Map<String, Object> getUserMapFromUser(User user){
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("id", user.getId());
		userMap.put("name", user.getName());
		userMap.put("username", user.getLoginName());
		userMap.put("user_type", user.getUserType());
		userMap.put("user_id", user.getUserId());
		userMap.put("gero_id", user.getGeroId());
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
	
	/**
	 * Privilege返回格式
	 * @param privilege
	 * @return
	 */
	protected Map<String, Object> getPrivilegeMapFromPrivilege(Privilege privilege){
		Map<String, Object> privilegeMap = new HashMap<String, Object>();
		privilegeMap.put("id", privilege.getId());
		privilegeMap.put("name", privilege.getName());
		privilegeMap.put("parent_id", privilege.getParentId());
		privilegeMap.put("parent_ids", privilege.getParentIds());
		privilegeMap.put("permission", privilege.getPermission());
		privilegeMap.put("href", privilege.getHref());
		privilegeMap.put("icon", privilege.getIcon());
		privilegeMap.put("api", privilege.getApi());
		return privilegeMap;
	}
	
	/**
	 *  获取角色基本信息
	 * @param role
	 * @return
	 */
	protected Map<String, Object> getRoleInfoMapFromRole(Role role) {
		Map<String, Object> roleMap = new HashMap<String, Object>();
		roleMap.put("id", role.getId());
		roleMap.put("name", role.getName());
		roleMap.put("notes", role.getNotes());
		return roleMap;
	}
	
	/**
	 *  获取角色包括权限信息
	 * @param role
	 * @return
	 */
	protected Map<String, Object> getRoleMapFromRole(Role role) {
		Map<String, Object> roleMap = new HashMap<String, Object>();
		roleMap.put("id", role.getId());
		roleMap.put("name", role.getName());
		roleMap.put("notes", role.getNotes());
		ArrayList<Object> privilegeList = new ArrayList<Object>();
		for (Privilege privilege : role.getPrivilegeList()){
			privilegeList.add(getPrivilegeMapFromPrivilege(privilege));
		}
		roleMap.put("privilege_list", privilegeList);
		return roleMap;
	}
	
}
