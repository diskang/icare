/**
 * @Package com.sjtu.icare.modules.elder.webservice
 * @Description TODO
 * @date Mar 20, 2015 3:29:14 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.elder.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
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
import com.sjtu.icare.common.persistence.Page;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.MapListUtils;
import com.sjtu.icare.common.utils.ParamUtils;
import com.sjtu.icare.common.web.rest.BasicController;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.elder.entity.ElderItemEntity;
import com.sjtu.icare.modules.elder.service.IElderInfoService;
import com.sjtu.icare.modules.staff.entity.StaffEntity;
import com.sjtu.icare.modules.sys.entity.User;

@RestController
@RequestMapping({"${api.web}/gero/{gid}/elder/{eid}/care_item", "${api.service}/gero/{gid}/elder/{eid}/care_item"})
public class ElderCareItemRestController extends BasicController {
	private static Logger logger = Logger.getLogger(ElderCareItemRestController.class);
	
	@Autowired
	private IElderInfoService elderInfoService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getElderItems(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@PathVariable("eid") int elderId,
			@RequestParam("page") int page,
			@RequestParam("rows") int limit,
			@RequestParam("sort") String orderByTag
			) {
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":elder:care_item:read");
		permissions.add("elder:"+elderId+":care_item:read");
		permissions.add("carer:"+getCurrentUser().getUserId()+":gero:"+geroId+":elder:care_item:read");
		checkPermissions(permissions);
		
		Page<ElderItemEntity> elderItemEntityPage = new Page<ElderItemEntity>(page, limit);
		elderItemEntityPage = setOrderBy(elderItemEntityPage, orderByTag);
		
		// 参数检查
		// TODO
		
		try {
			// 获取基础的 JSON返回
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
			
			ElderItemEntity queyrElderItemEntity = new ElderItemEntity();
			
			queyrElderItemEntity.setElderId(elderId);
			queyrElderItemEntity.setPage(elderItemEntityPage);
			List<ElderItemEntity> elderItemEntities;
			elderItemEntities = elderInfoService.getElderItems(queyrElderItemEntity);
			basicReturnedJson.setTotal((int) queyrElderItemEntity.getPage().getCount());
			
			if (elderItemEntities != null) {
				for (ElderItemEntity elderItemEntity : elderItemEntities) {
					
					Map<String, Object> resultMap = new HashMap<String, Object>(); 
					resultMap.put("id", elderItemEntity.getId()); 
					resultMap.put("care_item_id", elderItemEntity.getCareItemId()); 
					resultMap.put("care_item_name", elderItemEntity.getCareItemName()); 
					resultMap.put("icon", elderItemEntity.getIcon()); 
					resultMap.put("level", elderItemEntity.getLevel()); 
					resultMap.put("period", elderItemEntity.getPeriod()); 
					resultMap.put("start_time", elderItemEntity.getStartTime()); 
					resultMap.put("end_time", elderItemEntity.getEndTime()); 
					
					basicReturnedJson.addEntity(resultMap);
				}
			}
			
			
			return basicReturnedJson.getMap();
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.ELDER_ITEM_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	}
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Object postElderItem(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@PathVariable("eid") int elderId,
			@RequestBody String inJson
			) {
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":elder:care_item:add");
		checkPermissions(permissions);
		
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		tempRquestParamMap.put("elderId", elderId);
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		
		try {
			
			if (requestParamMap.get("careItemId") == null
				|| requestParamMap.get("level") == null
				)
				throw new Exception();
			// 参数详细验证
			// TODO
		} catch(Exception e) {
			String otherMessage = "[" + inJson + "]";
			String message = ErrorConstants.format(ErrorConstants.ELDER_ITEM_POST_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			// 参数与处理
			if (requestParamMap.get("period") == null)
				requestParamMap.put("period", 1);
			// insert into ELDER_ITEM
			ElderItemEntity postElderItemEntity = new ElderItemEntity(); 
			BeanUtils.populate(postElderItemEntity, requestParamMap);
			elderInfoService.insertElderItem(postElderItemEntity);
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.ELDER_ITEM_POST_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
	
	@RequestMapping(value="/{itemid}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getElderItem(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@PathVariable("eid") int elderId,
			@PathVariable("itemid") int itemId
			) {
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":elder:care_item:read");
		permissions.add("carer:"+getCurrentUser().getUserId()+":gero:"+geroId+":elder:care_item:read");
		checkPermissions(permissions);
		
		try {
			// 获取基础的 JSON返回
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
			
			ElderItemEntity queryElderItemEntity = new ElderItemEntity();
			queryElderItemEntity.setId(itemId);
			queryElderItemEntity.setElderId(elderId);
			
			ElderItemEntity elderItemEntity = elderInfoService.getElderItem(queryElderItemEntity);
			
			if (elderItemEntity != null) {
				
				Map<String, Object> resultMap = new HashMap<String, Object>(); 
				resultMap.put("id", elderItemEntity.getId()); 
				resultMap.put("care_item_id", elderItemEntity.getCareItemId()); 
				resultMap.put("care_item_name", elderItemEntity.getCareItemName()); 
				resultMap.put("icon", elderItemEntity.getIcon()); 
				resultMap.put("level", elderItemEntity.getLevel()); 
				resultMap.put("period", elderItemEntity.getPeriod()); 
				resultMap.put("start_time", elderItemEntity.getStartTime()); 
				resultMap.put("end_time", elderItemEntity.getEndTime()); 
				
				basicReturnedJson.addEntity(resultMap);
				
			}
			
			return basicReturnedJson.getMap();
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.ELDER_ITEM_SPECIFIC_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
		
	}
	
	@Transactional
	@RequestMapping(value="/{itemid}", method = RequestMethod.PUT, produces = MediaTypes.JSON_UTF_8)
	public Object putElderItem(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@PathVariable("eid") int elderId,
			@PathVariable("itemid") int itemId,
			@RequestBody String inJson
			) {
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":elder:care_item:update");
		checkPermissions(permissions);
		
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		tempRquestParamMap.put("elderId", elderId);
		tempRquestParamMap.put("id", itemId);
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		
		try {
			if (requestParamMap.get("careItemId") == null
				|| requestParamMap.get("level") == null
				)
				throw new Exception();
				// 参数详细验证
				// TODO
			
		} catch(Exception e) {
			String otherMessage = "[" + inJson + "]";
			String message = ErrorConstants.format(ErrorConstants.ELDER_ITEM_SPECIFIC_PUT_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			// update into ELDER_ITEM
			ElderItemEntity postElderItemEntity = new ElderItemEntity(); 
			BeanUtils.populate(postElderItemEntity, requestParamMap);
			elderInfoService.updateElderItem(postElderItemEntity);
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.ELDER_ITEM_SPECIFIC_PUT_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
	
	@Transactional
	@RequestMapping(value="/{itemid}", method = RequestMethod.DELETE, produces = MediaTypes.JSON_UTF_8)
	public Object deleteElderItem(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@PathVariable("eid") int elderId,
			@PathVariable("itemid") int itemId
			) {
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":elder:care_item:delete");
		checkPermissions(permissions);
		
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = new HashMap<String, Object>();
		tempRquestParamMap.put("elderId", elderId);
		tempRquestParamMap.put("id", itemId);
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			// update into ELDER_ITEM
			ElderItemEntity postElderItemEntity = new ElderItemEntity(); 
			BeanUtils.populate(postElderItemEntity, requestParamMap);
			elderInfoService.deleteElderItem(postElderItemEntity);
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.ELDER_ITEM_SPECIFIC_DELETE_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
}
