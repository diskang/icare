package com.sjtu.icare.modules.sys.webservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.persistence.Page;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.MapValueComparator;
import com.sjtu.icare.common.utils.ParamUtils;
import com.sjtu.icare.common.web.rest.GeroBaseController;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.sys.entity.Gero;
import com.sjtu.icare.modules.sys.entity.Privilege;
import com.sjtu.icare.modules.sys.entity.Role;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;
import com.sjtu.icare.modules.sys.utils.UserUtils;
import com.sjtu.icare.modules.sys.utils.security.SystemAuthorizingRealm.UserPrincipal;
import com.sun.tools.javac.resources.javac;

/**
*
* GeroRoleController
* 养老院的角色管理（管理员权限）
* 
* @author jty
*/
@RestController
@RequestMapping("/admin/gero")
public class GeroRoleController extends GeroBaseController {
	private static Logger logger = Logger.getLogger(GeroRoleController.class);
	
	@Autowired
	private SystemService systemService;

	@RequestMapping(value = "/{gid}/role", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> getGeroRoleList(
			@PathVariable("gid") int gid,
			@RequestParam("page") int page,
			@RequestParam("limit") int limit,
			@RequestParam("order_by") String orderByTag){
		
//		checkGero(gid);
		
		BasicReturnedJson result = new BasicReturnedJson();
		
		Gero gero = getGeroFromId(gid);
		
		Page<Role> rolePage = new Page<Role>(page,limit);

		rolePage = setOrderBy(rolePage, orderByTag);
		
		// get page from service
		List<Role> roleList;
		int total;
		try {
			rolePage = systemService.getRolePageFromGeroId(rolePage, gero);
			roleList = rolePage.getList();
			total = (int) rolePage.getCount();
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.GERO_ROLE_GET_PAGE_SERVICE_ERROR,"");
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
		for (Role role : roleList){
			if (role != null){
				result.addEntity(getRoleInfoMapFromRole(role));
			}else {
				String message = ErrorConstants.format(ErrorConstants.ROLE_FOR_ID_NOT_FOUND,"");
				logger.error(message);
				throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
			}
		}
		result.setTotal(total);
		return result.getMap();
	}
	
	@RequestMapping(value = "/{gid}/role", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> insertGeroRole(
			@PathVariable("gid") int gid,
			@RequestBody String inJson
			){
		
//		checkGero(gid);
		
		BasicReturnedJson result = new BasicReturnedJson();
		
		Map<String, Object> requestBodyParamMap = ParamUtils.getMapByJson(inJson, logger);
		String name;
		String notes;
		
		// 输入参数检查
		try {
			name = (String) String.valueOf(requestBodyParamMap.get("name"));
			notes = (String) String.valueOf(requestBodyParamMap.get("notes"));
						
			if (name == null || name.equals("null"))
				throw new Exception();
			
		} catch(Exception e) {
			String message = ErrorConstants.format(ErrorConstants.GERO_ROLE_INSERT_PARAM_INVALID,
					"[name=" + requestBodyParamMap.get("name") + "]" +
					"[notes=" + requestBodyParamMap.get("notes") + "]");
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		Role role = new Role();
		role.setName(name);
		role.setNotes(notes);
		role.setGeroId(gid);
		
		try {
			if (systemService.getRoleByNameAndGero(role) != null)
				throw new Exception();
		} catch (Exception e1) {
			String message = ErrorConstants.format(ErrorConstants.GERO_ROLE_INSERT_CONFLICT_ERROR,
					"[name=" + requestBodyParamMap.get("name") + "]" +
					"[notes=" + requestBodyParamMap.get("notes") + "]");
			logger.error(message);
			throw new RestException(HttpStatus.CONFLICT, message);
		}
		
		try {
			systemService.insertGeroRole(role);
			role = systemService.getRoleByNameAndGero(role);
			result.addEntity(getRoleInfoMapFromRole(role));
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.GERO_ROLE_INSERT_SERVICE_ERROR,
					"[name=" + requestBodyParamMap.get("name") + "]" +
					"[notes=" + requestBodyParamMap.get("notes") + "]");
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
		return result.getMap();
	}
	
	@RequestMapping(value = "/{gid}/role/{rid}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> getGeroRole(
			@PathVariable("rid") int rid,
			@PathVariable("gid") int gid
			){
		
//		checkGero(gid);
		
		BasicReturnedJson result = new BasicReturnedJson();
		
		Role role = getRoleFromId(rid);
		
		checkRoleInGero(role, gid);
		
		try {
			role = systemService.getPrivilegeListByRole(role);
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.GERO_ROLE_GET_PRIVILEGE_SERVICE_ERROR,
					"[rid=" + rid + "]");
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
		result.addEntity(getRoleMapFromRole(role));
		return result.getMap();
	}
	
	@RequestMapping(value = "/{gid}/role/{rid}", method = RequestMethod.PUT, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> updateGeroRoleInfo(
			@PathVariable("rid") int rid,
			@PathVariable("gid") int gid,
			@RequestBody String inJson
			){
		
//		checkGero(gid);
		
		BasicReturnedJson result = new BasicReturnedJson();
		
		Role role = getRoleFromId(rid);
		
		checkRoleInGero(role, gid);
		
		Map<String, Object> requestBodyParamMap = ParamUtils.getMapByJson(inJson, logger);
		String name;
		String notes;
		
		// 输入参数检查
		try {
			name = (String) String.valueOf(requestBodyParamMap.get("name"));
			notes = (String) String.valueOf(requestBodyParamMap.get("notes"));
						
			if (name == null || name.equals("null"))
				throw new Exception();
			
		} catch(Exception e) {
			String message = ErrorConstants.format(ErrorConstants.GERO_ROLE_INSERT_PARAM_INVALID,
					"[name=" + requestBodyParamMap.get("name") + "]" +
					"[notes=" + requestBodyParamMap.get("notes") + "]");
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		role.setName(name);
		role.setNotes(notes);
		
		try {
			if (systemService.getRoleByNameAndGero(role) != null)
				throw new Exception();
		} catch (Exception e1) {
			String message = ErrorConstants.format(ErrorConstants.GERO_ROLE_UPDATE_CONFLICT_ERROR,
					"[name=" + requestBodyParamMap.get("name") + "]" +
					"[notes=" + requestBodyParamMap.get("notes") + "]");
			logger.error(message);
			throw new RestException(HttpStatus.CONFLICT, message);
		}
		
		try {
			systemService.updateGeroRole(role);
			role = systemService.getRoleByNameAndGero(role);
			result.addEntity(getRoleInfoMapFromRole(role));
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.GERO_ROLE_UPDATE_SERVICE_ERROR,
					"[name=" + requestBodyParamMap.get("name") + "]" +
					"[notes=" + requestBodyParamMap.get("notes") + "]");
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
		return result.getMap();
	}
	
	@RequestMapping(value = "/{gid}/role/{rid}", method = RequestMethod.DELETE, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> updateGeroRoleInfo(
			@PathVariable("rid") int rid,
			@PathVariable("gid") int gid
			){
		
//		checkGero(gid);
		
		BasicReturnedJson result = new BasicReturnedJson();
		
		Role role = getRoleFromId(rid);
		
		checkRoleInGero(role, gid);
		
		try {
			systemService.deleteGeroRole(role);
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.GERO_ROLE_DELETE_SERVICE_ERROR,
					"[rid=" + rid + "]");
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
		return result.getMap();
	}
	
	@RequestMapping(value = "/{gid}/role/{rid}/privilege", method = RequestMethod.PUT, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> insertGeroRolePrivilege(
			@PathVariable("rid") int rid,
			@PathVariable("gid") int gid,
			@RequestBody String inJson
			){

//		checkGero(gid);
		
		BasicReturnedJson result = new BasicReturnedJson();
		
		Role role = getRoleFromId(rid);
		
		checkRoleInGero(role, gid);
		
		Map<String, Object> requestBodyParamMap = ParamUtils.getMapByJson(inJson, logger);
		List<Integer> privilegeIdList = new ArrayList<Integer>();
		
		// 输入参数检查
		try {
			privilegeIdList = (List<Integer>) requestBodyParamMap.get("insert_privilege_ids");			
		} catch(Exception e) {
			String message = ErrorConstants.format(ErrorConstants.GERO_ROLE_PRIVILEGE_INSERT_PARAM_INVALID,
					"[insert_privilege_ids=" + requestBodyParamMap.get("insert_privilege_ids") + "]" );
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		//获取当前用户名
		Subject subject = SecurityUtils.getSubject();
		String username;
		if (subject.getPrincipal().equals(String.class)) {
			username = (String) subject.getPrincipal();
		}else {
			username = ((UserPrincipal) subject.getPrincipal()).getUsername();
		}
		User user = UserUtils.getByLoginName(username);
		
		//获取当前用户权限
		List<Privilege> privilegeList = UserUtils.getPrivilegeList(user);
		
		//取新增权限列表并排序
		List<Privilege> inputPrivilegeList = new ArrayList<Privilege>();
		Map<String, String> inputPrivilegeMap = new HashMap<String, String>();
		
		for (int id : privilegeIdList){
			 for (Privilege privilege : privilegeList){
				 if (privilege.getId() == id) {
					inputPrivilegeList.add(privilege);
					inputPrivilegeMap.put(id+"", privilege.getParentIds());
				}
			 }
		}
		inputPrivilegeMap = sortMapByValue(inputPrivilegeMap);
		
		List<Privilege> rolePrivileges = systemService.getPrivilegeListByRole(role).getPrivilegeList();
		
		
		for (Map.Entry<String, String> entry : inputPrivilegeMap.entrySet()){
			int id = Integer.parseInt(entry.getKey());
			logger.debug(entry.getKey() + " " + entry.getValue());
			Privilege inputPrivilege = new Privilege();
			for (Privilege privilege : inputPrivilegeList){
				if (privilege.getId() == id)
					inputPrivilege = privilege;
			}
			//insert privilege
			//判断权限是否存在，父节点是否存在
			Boolean parentExist = false;
			for (Privilege privilege : rolePrivileges){
				if (privilege.getId() == id) {
					throw new RestException();
				}
				if (inputPrivilege.getParentId() == privilege.getId()) {
					parentExist = true;
				}
			}
			if (parentExist==false)
				throw new RestException();
			
			rolePrivileges.add(inputPrivilege);
		}
		
		role.setPrivilegeList(rolePrivileges);
		systemService.insertRolePrivilege(role);
		
		return result.getMap();
	}
	
	
	private void checkRoleInGero(Role role, int gid){
		try {
			if (role.getGeroId() != gid && role.getGeroId() != 1)
				throw new Exception();
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.GERO_ROLE_GET_ROLE_NOT_IN_GERO,
					"[gid=" + gid + "]"+
					"[rid=" + role.getId() + "]");
			logger.error(message);
			throw new RestException(HttpStatus.UNAUTHORIZED, message);
		}
	}
	
	/** 
     * 使用 Map按value进行排序 
     * @param map 
     * @return 
     */  
    public static Map<String, String> sortMapByValue(Map<String, String> map) {  
        if (map == null || map.isEmpty()) {  
            return null;  
        }  
        Map<String, String> sortedMap = new LinkedHashMap<String, String>();  
        List<Map.Entry<String, String>> entryList = new ArrayList<Map.Entry<String, String>>(map.entrySet());  
        Collections.sort(entryList, new MapValueComparator());  
        Iterator<Map.Entry<String, String>> iter = entryList.iterator();  
        Map.Entry<String, String> tmpEntry = null;  
        while (iter.hasNext()) {  
            tmpEntry = iter.next();  
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());  
        }  
        return sortedMap;  
    }   
}

