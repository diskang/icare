package com.sjtu.icare.modules.sys.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
import com.sjtu.icare.common.config.OrderByConstant;
import com.sjtu.icare.common.persistence.Page;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.ParamUtils;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.sys.entity.Gero;
import com.sjtu.icare.modules.sys.entity.Privilege;
import com.sjtu.icare.modules.sys.entity.Role;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;

/**
*
* GeroRoleController
* 养老院的角色管理（管理员权限）
* 
* @author jty
*/
@RestController
@RequestMapping("/gero")
public class GeroRoleController {
	private static Logger logger = Logger.getLogger(GeroRoleController.class);
	
	@Autowired
	private SystemService systemService;
	
	@RequestMapping(value = "/{gid}/role", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	
	public Map<String, Object> getGeroRoleList(
			@PathVariable("gid") int gid,
			@RequestParam("page") int page,
			@RequestParam("limit") int limit,
			@RequestParam("order_by") String orderByTag){
		
		BasicReturnedJson result = new BasicReturnedJson();
		
		Gero gero = getGeroFromId(gid);
		
		Page<Role> rolePage = new Page<Role>(page,limit);
		
		SecurityUtils.getSubject().checkRole("");

		String orderBy = "id";
		try {
			orderBy = OrderByConstant.valueOf(orderByTag).getTag();
		} catch (Exception e1) {
			String message = ErrorConstants.format(ErrorConstants.ORDER_BY_PARAM_INVALID,"");
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		rolePage.setOrderBy(orderBy);
		
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
				result.addEntity(getRoleMapFromRole(role));
			}else {
				String message = ErrorConstants.format(ErrorConstants.ROLE_FOR_ID_NOT_FOUND,"");
				logger.error(message);
				throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
			}
		}
		result.setStatus(HttpStatus.OK.value());
		result.setError(HttpStatus.OK.name());
		result.setTotal(total);
		return result.getMap();
	}
	
	@RequestMapping(value = "/{gid}/role", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> insertGeroRole(
			@PathVariable("gid") int gid,
			@RequestBody String inJson
			){
		
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
			
			// 此处可添加url的验证方法
			
		} catch(Exception e) {
			String message = ErrorConstants.format(ErrorConstants.GERO_ROLE_INSERT_PARAM_INVALID,
					"[name=" + requestBodyParamMap.get("name") + "]" +
					"[notes=" + requestBodyParamMap.get("notes") + "]");
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		Gero gero = getGeroFromId(gid);
		
		Role role = new Role();
		role.setName(name);
		role.setNotes(notes);
		role.setGeroId(gid);
		
		try {
			if (systemService.getRole(role) != null)
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
			role = systemService.getRole(role);
			result.addEntity(getRoleMapFromRole(role));
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
	public Map<String, Object> getGeroRole(){
		
		BasicReturnedJson result = new BasicReturnedJson();
		Subject userSubject = SecurityUtils.getSubject();
		
		return result.getMap();
	}
	
	/**
	 * Role返回格式
	 * @param role
	 * @return
	 */
	private Map<String, Object> getRoleMapFromRole(Role role) {
		Map<String, Object> roleMap = new HashMap<String, Object>();
		roleMap.put("id", role.getId());
		roleMap.put("name", role.getName());
		roleMap.put("note", role.getNotes());
		return roleMap;
	}
	
	private Gero getGeroFromId(int gid){
		
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
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		return gero;
	}
}