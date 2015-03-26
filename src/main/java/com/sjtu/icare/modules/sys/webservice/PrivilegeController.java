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
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.ParamUtils;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.common.web.rest.SysBaseController;
import com.sjtu.icare.modules.sys.entity.Privilege;
import com.sjtu.icare.modules.sys.service.SystemService;

/**
 * 权限管理（超管）
 * @author garfieldjty
 *
 */
@RestController
@RequestMapping({"${api.web}/privilege","${api.service}/privilege"})
public class PrivilegeController extends SysBaseController{
	
	private static Logger logger = Logger.getLogger(PrivilegeController.class);
	
	@Autowired
	private SystemService systemService;
	
	/**
	 * 获取全部权限列表
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> getPrivilegeListController(
			HttpServletRequest request){
		checkApi(request);
		
		BasicReturnedJson result = new BasicReturnedJson();
		
		result = getPrivilegeList(result);
		
		return result.getMap();
	}
	
	/**
	 * 添加权限
	 * @param inJson
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> insertPrivilege(
			HttpServletRequest request,
			@RequestBody String inJson
			){
		checkApi(request);
		
		BasicReturnedJson result = new BasicReturnedJson();
		
		Map<String, Object> requestBodyParamMap = ParamUtils.getMapByJson(inJson, logger);
		String name;
		int parentId;
		String permission;
		String href;
		String icon;
		String notes;
		String api;
		
		// 输入参数检查
		try {
			name = (String) requestBodyParamMap.get("name");
			parentId = (Integer) requestBodyParamMap.get("parent_id");
			permission = (String) requestBodyParamMap.get("permission");
			href = (String) requestBodyParamMap.get("href");
			icon = (String) requestBodyParamMap.get("icon");
			notes = (String) requestBodyParamMap.get("notes");
			api = (String) requestBodyParamMap.get("api");
			if (name == null || name.length()<1 || parentId<0 ) {
				throw new Exception();
			}
		} catch(Exception e) {
			String message = ErrorConstants.format(ErrorConstants.INSERT_PRIVILEGE_PARAM_INVALID,
					"[name=" + requestBodyParamMap.get("name") + "]" +
					"[parent_id=" + requestBodyParamMap.get("parent_id") + "]" +
					"[permission=" + requestBodyParamMap.get("permission") + "]" +
					"[href=" + requestBodyParamMap.get("href") + "]" +
					"[icon=" + requestBodyParamMap.get("icon") + "]" +
					"[notes=" + requestBodyParamMap.get("notes") + "]"
					);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		// 检查父节点
		Privilege parent;
		try {
			parent = systemService.getPrivilegeById(parentId);
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.GET_PARENT_PRIVILEGE_ERROR,
					"[parent_id=" + requestBodyParamMap.get("parent_id") + "]" 
					);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
		if (parent == null) {
			String message = ErrorConstants.format(ErrorConstants.GET_PARENT_PRIVILEGE_NOT_FOUND,
					"[parent_id=" + requestBodyParamMap.get("parent_id") + "]" 
					);
			logger.error(message);
			throw new RestException(HttpStatus.NOT_FOUND, message);
		}
		
		Privilege newPrivilege = new Privilege();
		newPrivilege.setName(name);
		newPrivilege.setParent(parent);
		newPrivilege.setParentIds(parent.getParentIds()+parentId+",");
		newPrivilege.setPermission(permission);
		newPrivilege.setHref(href);
		newPrivilege.setIcon(icon);
		newPrivilege.setNotes(notes);
		newPrivilege.setApi(api);
		
		// 插入新节点
		try {
			systemService.insertPrivilege(newPrivilege);
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.INSERT_PRIVILEGE_SERVICE_ERROR,
					"[parent_id=" + requestBodyParamMap.get("parent_id") + "]" 
					);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
		result = getPrivilegeList(result);
		
		return result.getMap();
	}
	
	/**
	 * 修改权限信息
	 * @param pid
	 * @param inJson
	 * @return
	 */
	@RequestMapping(value = "{pid}", method = RequestMethod.PUT, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> updatePrivilege(
			HttpServletRequest request,
			@PathVariable("pid") int pid,
			@RequestBody String inJson
			){
		checkApi(request);

		BasicReturnedJson result = new BasicReturnedJson();
		
		Map<String, Object> requestBodyParamMap = ParamUtils.getMapByJson(inJson, logger);
		String name;
		String permission;
		String href;
		String icon;
		String notes;
		String api;
		
		// 输入参数检查
		try {
			name = (String) requestBodyParamMap.get("name");
			permission = (String) requestBodyParamMap.get("permission");
			href = (String) requestBodyParamMap.get("href");
			icon = (String) requestBodyParamMap.get("icon");
			notes = (String) requestBodyParamMap.get("notes");
			api = (String) requestBodyParamMap.get("api");
			if (name == null || name.length()<1 ) {
				throw new Exception();
			}
		} catch(Exception e) {
			String message = ErrorConstants.format(ErrorConstants.INSERT_PRIVILEGE_PARAM_INVALID,
					"[name=" + requestBodyParamMap.get("name") + "]" +
					"[permission=" + requestBodyParamMap.get("permission") + "]" +
					"[href=" + requestBodyParamMap.get("href") + "]" +
					"[icon=" + requestBodyParamMap.get("icon") + "]" +
					"[notes=" + requestBodyParamMap.get("notes") + "]"
					);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		Privilege privilege = getPrivilege(pid);		
		privilege.setName(name);
		privilege.setPermission(permission);
		privilege.setHref(href);
		privilege.setIcon(icon);
		privilege.setNotes(notes);
		privilege.setApi(api);
		
		// 修改信息
		try {
			systemService.updatePrivilege(privilege);
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.UPDATE_PRIVILEGE_SERVICE_ERROR,
					"[pid=" + pid + "]" +
					"[name=" + requestBodyParamMap.get("name") + "]" +
					"[permission=" + requestBodyParamMap.get("permission") + "]" +
					"[href=" + requestBodyParamMap.get("href") + "]" +
					"[icon=" + requestBodyParamMap.get("icon") + "]" +
					"[notes=" + requestBodyParamMap.get("notes") + "]"
			);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		result = getPrivilegeList(result);
		
		return result.getMap();
	}
	
	@RequestMapping(value = "{pid}", method = RequestMethod.DELETE, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> deletePrivilege(
			HttpServletRequest request,
			@PathVariable("pid") int pid
			){
		checkApi(request);
		
		BasicReturnedJson result = new BasicReturnedJson();
		
		Privilege deletePrivilege = getPrivilege(pid);
		
		try {
			systemService.deletePrivilege(deletePrivilege);
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.DELETE_PRIVILEGE_SERVICE_ERROR,
					"[pid=" + pid + "]"
			);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		result = getPrivilegeList(result);
		
		return result.getMap();
	}
	
	/**
	 * 获取ID权限
	 * @param id
	 * @return
	 */
	private Privilege getPrivilege (int id){
		Privilege privilege;
		try {
			privilege = systemService.getPrivilegeById(id);
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.GET_PRIVILEGE_SERVICE_ERROR,
					"[pid=" + id+ "]"
					);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
		if (privilege == null) {
			String message = ErrorConstants.format(ErrorConstants.GET_PRIVILEGE_NOT_FOUND,
					"[pid=" + id+ "]"
					);
			logger.error(message);
			throw new RestException(HttpStatus.NOT_FOUND, message);
		}
		return privilege;
	}
	
	/**
	 * 获取权限列表
	 * @return
	 */
	private List<Privilege> getPrivilegeList() {
		List<Privilege> privileges = new ArrayList<Privilege>();
		try {
			privileges = systemService.getPrivilegeList();
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.GET_PRIVILEGE_LIST_SERVICE_ERROR,"");
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
		if (privileges.size()<=0) {
			String message = ErrorConstants.format(ErrorConstants.GET_PRIVILEGE_LIST_EMPTY_ERROR,"");
			logger.error(message);
			throw new RestException(HttpStatus.NOT_FOUND, message);
		}
		return privileges;
	}
	
	/**
	 * 获取权限列表并输出
	 * @param result
	 * @return
	 */
	private BasicReturnedJson getPrivilegeList(BasicReturnedJson result) {
		List<Privilege> privileges = getPrivilegeList();		
		for (Privilege privilege : privileges){
			result.addEntity(getPrivilegeMapFromPrivilege(privilege));
		}
		return result;
	}
	
}
