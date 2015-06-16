package com.sjtu.icare.modules.wechat.webservice;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.icare.common.config.CommonConstants;
import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.web.rest.BasicController;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.elder.entity.ElderRelativeRelationshipEntity;
import com.sjtu.icare.modules.elder.persistence.RelativeDAO;
import com.sjtu.icare.modules.elder.service.IRelativeInfoService;
import com.sjtu.icare.modules.elder.service.impl.ElderInfoService;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;
import com.sjtu.icare.modules.wechat.service.IElderRelativeRelationshipService;
import com.sjtu.icare.modules.wechat.service.impl.ElderRelativeRelationshipService.ElderRelativeRelationshipReturn;


@RestController
@RequestMapping("${api.wechat}")
public class WechatRelativeElderRelationshipController extends BasicController{
	private static Logger logger = Logger.getLogger(WechatRelativeElderRelationshipController.class);
	@Autowired
	private SystemService systemService;
	@Autowired
	private RelativeDAO relativeDAO;
	@Autowired
	private ElderInfoService elderInfoService;
	@Autowired
	private IRelativeInfoService relativeInfoService;
	@Autowired
	private IElderRelativeRelationshipService elderRelativeRelationshipService;

	@Transactional
	@RequestMapping(value="/relative/{rid}/elder/{eid}",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Object postElderRelativeRelationship(
			HttpServletRequest request,
			@PathVariable("rid") int relativeId,
			@PathVariable("eid") int elderId,
			@RequestParam(value="wechat_id", required=false)String wechatId,
			@RequestBody String inJson
			) {
//		checkApi(request);
//		List<String> permissions = new ArrayList<String>();
//		permissions.add("admin:gero:"+geroId+":elder:info:add");
//		checkPermissions(permissions);
		
		// 将参数转化成驼峰格式的 Map
//		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
//		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		// TODO
//		Integer elderUserId = null;
//		Integer relativeUserId = null;
//		try {
//			
//			if (requestParamMap.get("relative_user_id") == null || requestParamMap.get("elder_user_id") == null)
//				throw new Exception();
//			
//			elderUserId = (Integer) requestParamMap.get("elder_user_id");
//			relativeUserId = (Integer) requestParamMap.get("relative_user_id");
//			
//			
//		} catch(Exception e) {
//			String otherMessage = "[" + "必须参数: relative_user_id, elder_user_id" + " | " + inJson + "]";
//			String message = ErrorConstants.format(ErrorConstants.RELATIVE_INFO_POST_PARAM_INVALID, otherMessage);
//			logger.error(message);
//			throw new RestException(HttpStatus.BAD_REQUEST, message);
//		}
		if(wechatId==null||wechatId.isEmpty()){
			throw new RestException(HttpStatus.BAD_REQUEST, "wechatId required");
		}
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		User user = systemService.getUserByUserTypeAndUserId(CommonConstants.RELATIVE_TYPE, relativeId);
		if(user==null){
			throw new RestException(HttpStatus.NOT_FOUND, "no user found by relative Id");
		}else{
			if(!wechatId.equals(user.getWechatId())){
				logger.error(wechatId+"||"+user.getWechatId());
				throw new RestException(HttpStatus.UNAUTHORIZED, "wechatId not match with user's id");
			}
			//add role
//			List<Role> roleList = new ArrayList<Role>();
//			Integer geroId = user.getGeroId();
//			Role tmpRole = new Role();
//			tmpRole.setGeroId(geroId);
//			tmpRole.setName("家属");
//			tmpRole = systemService.getRoleByNameAndGero(tmpRole);
//			roleList.add(tmpRole);
//			user.setRoleList(roleList);
//			if(!systemService.updateUserRoles(user)){
//				logger.error("failed to add role to relative");
//			}
		}
		ElderRelativeRelationshipReturn tempresultMap = elderRelativeRelationshipService.getElderRelativeRelationshipsByWechatId(wechatId);
		if(tempresultMap==null){
			throw new RestException(HttpStatus.NOT_FOUND, "no user found by wechat Id");
		}
		Integer relativeUserId = user.getId();
		user = systemService.getUserByUserTypeAndUserId(CommonConstants.ELDER_TYPE, elderId);
		if(user==null){
			throw new RestException(HttpStatus.NOT_FOUND, "no elder found by elder Id");
		}
		Integer elderUserId = user.getId();
		//entity to query
		ElderRelativeRelationshipEntity requestElderRelativeRelationshipEntity = new ElderRelativeRelationshipEntity();
		List<ElderRelativeRelationshipEntity> elderRelativeRelationshipEntities =  null;//query result
		try {//get existing relationships
			requestElderRelativeRelationshipEntity.setRelativeUserId(relativeUserId);
			elderRelativeRelationshipEntities = relativeDAO.getElderRelativeRelationships(requestElderRelativeRelationshipEntity);
		}catch(Exception e){
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "cannot get relationship for relative"); 
		} 
		
		if (elderRelativeRelationshipEntities!=null){
			int maxElderNum = 2;
			int bindedElderNum = elderRelativeRelationshipEntities.size();
			if( bindedElderNum <maxElderNum){
				for(ElderRelativeRelationshipEntity e:elderRelativeRelationshipEntities){
					if(e.getElderUserId()==elderUserId){
						throw new RestException(HttpStatus.BAD_REQUEST, "elder already binded"); 
					}
				}
			}
			//check how many elders the relative already bind, support number <=2
			else if (bindedElderNum >= maxElderNum){
				throw new RestException(HttpStatus.FORBIDDEN, "exceed maximum binded number, can only add two relatives"); 
			}
		}
		try{//no problems found, add new elder 's user id 
			requestElderRelativeRelationshipEntity.setElderUserId(elderUserId);
			// insert into Relative table
			relativeInfoService.insertElderRelativeRelationship(requestElderRelativeRelationshipEntity);
		}catch(Exception e) {
			String message = ErrorConstants.format(ErrorConstants.RELATIVE_INFO_POST_SERVICE_FAILED, e.getMessage());
			logger.error(message);
			e.printStackTrace();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
		return basicReturnedJson.getMap();
		
	}
	
	/*get user's elder by his wechat_id
	 * NOTE: path variable not used in this controller
	 * NOTE2: this api not used
	 * */
	@RequestMapping(value="/relative/{relativeId}/elder", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getElderRelativeRelationship(
			HttpServletRequest request,
			@PathVariable("relativeId") String relativeId,
			@RequestParam(value="wechat_id",required=false)String wechatId
			) {
//		checkApi(request);
//		List<String> permissions = new ArrayList<String>();
//		permissions.add("admin:gero:"+geroId+":elder:info:read");
//		permissions.add("staff:"+getCurrentUser().getUserId()+":gero:"+geroId+":elder:read");
		if(wechatId==null||wechatId.isEmpty()){
			throw new RestException(HttpStatus.BAD_REQUEST, "wechatId required");
		}
		
		try {
			// 获取基础的 JSON返回
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
			
			ElderRelativeRelationshipReturn tempresultMap = elderRelativeRelationshipService.getElderRelativeRelationshipsByWechatId(wechatId);
			if(tempresultMap==null){
				throw new RestException(HttpStatus.NOT_FOUND, "no user found by wechat Id");
			}
			basicReturnedJson.addEntity(tempresultMap);
			
			return basicReturnedJson.getMap();
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.RELATIVE_INFO_SPECIFIC_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
		
	}

	/*unbind a relative and and elder*/
	@Transactional
	@RequestMapping(value="relative/{rid}/elder/{eid}", method = RequestMethod.DELETE, produces = MediaTypes.JSON_UTF_8)
	public Object deleteElderRelativeRelationship(
			HttpServletRequest request,
			@PathVariable("rid") Integer relativeId,
			@PathVariable("eid") Integer elderId,
			@RequestParam(value="wechat_id",required=false)String wechatId
			) {
//		checkApi(request);
//		List<String> permissions = new ArrayList<String>();
//		permissions.add("admin:gero:"+geroId+":elder:info:add");
//		checkPermissions(permissions);
		if(wechatId==null||wechatId.isEmpty()){
			throw new RestException(HttpStatus.BAD_REQUEST, "wechatId required");
		}
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			User user = systemService.getUserByUserTypeAndUserId(CommonConstants.RELATIVE_TYPE, relativeId);
			if(user==null){
				throw new RestException(HttpStatus.NOT_FOUND, "no user found by relative Id");
			}else{
				if(user.getWechatId()!=wechatId){
					throw new RestException(HttpStatus.UNAUTHORIZED, "wechatId not match with user's id");
				}
			}
			Integer relativeUserId = user.getId();
			user = systemService.getUserByUserTypeAndUserId(CommonConstants.ELDER_TYPE, elderId);
			Integer elderUserId = user.getId();
			
			ElderRelativeRelationshipEntity requestElderRelativeRelationshipEntity = new ElderRelativeRelationshipEntity();
			requestElderRelativeRelationshipEntity.setElderUserId(elderUserId);
			requestElderRelativeRelationshipEntity.setRelativeUserId(relativeUserId);
			relativeInfoService.deleteElderRelativeRelationship(requestElderRelativeRelationshipEntity);
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.RELATIVE_INFO_ELDER_DELETE_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
	
}
