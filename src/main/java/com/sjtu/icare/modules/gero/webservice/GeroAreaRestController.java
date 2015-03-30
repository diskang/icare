/**
 * @Package com.sjtu.icare.modules.gero.webservice
 * @Description gero area controller
 * @date Mar 9, 2015 4:12:24 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.gero.webservice;

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

import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.MapListUtils;
import com.sjtu.icare.common.utils.ParamUtils;
import com.sjtu.icare.common.web.rest.BasicController;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.gero.entity.GeroAreaEntity;
import com.sjtu.icare.modules.gero.service.IGeroAreaService;

@RestController
@RequestMapping({"${api.web}/gero/{gid}/area", "${api.service}/gero/{gid}/area"})
public class GeroAreaRestController extends BasicController {
	private static Logger logger = Logger.getLogger(GeroAreaRestController.class);

	@Autowired
	private IGeroAreaService geroAreaService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getGeroAreas(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@RequestParam(value="parent_id", required=false) Integer parentId,
			@RequestParam(value="parent_ids", required=false) String parentIds,
			@RequestParam(value="type", required=false) Integer type,
			@RequestParam(value="level", required=false) Integer level,
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="full_name", required=false) String fullName
			) {
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":area:read");
		permissions.add("staff:"+getCurrentUser().getUserId()+":gero:"+geroId+":area:read");
		checkPermissions(permissions);
		
		// 获取基础的 JSON返回
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
	
		// 参数检查
		// 参数预处理
		
		try {
			GeroAreaEntity queryGeroAreaEntity = new GeroAreaEntity();
			queryGeroAreaEntity.setGeroId(geroId);
			queryGeroAreaEntity.setParentId(parentId);
			queryGeroAreaEntity.setParentIds(parentIds);
			queryGeroAreaEntity.setType(type);
			queryGeroAreaEntity.setLevel(level);
			queryGeroAreaEntity.setName(name);
			queryGeroAreaEntity.setFullName(fullName);
			
			
			List<GeroAreaEntity> geroAreaEntities = geroAreaService.getGeroAreas(queryGeroAreaEntity);
			
			if (geroAreaEntities != null) {
				// 构造返回的 JSON
				for (GeroAreaEntity geroAreaEntity : geroAreaEntities) {
					Map<String, Object> resultMap = new HashMap<String, Object>(); 
					resultMap.put("id", geroAreaEntity.getId()); 
					resultMap.put("parent_id", geroAreaEntity.getParentId()); 
					resultMap.put("parent_ids", geroAreaEntity.getParentIds()); 
					resultMap.put("type", geroAreaEntity.getType()); 
					resultMap.put("level", geroAreaEntity.getLevel()); 
					resultMap.put("name", geroAreaEntity.getName()); 
					resultMap.put("full_name", geroAreaEntity.getFullName()); 
					
					basicReturnedJson.addEntity(resultMap);
				}
				
			}

			return basicReturnedJson.getMap();
			
		} catch (Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_AREA_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	
	}

	@Transactional
	@RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Object postGeroArea(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@RequestBody String inJson
			) {	
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":area:add");
		checkPermissions(permissions);
		
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		tempRquestParamMap.put("geroId", geroId);
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		
		Integer parentId = null;
		Integer type = null;
		Integer level = null;
		String name = null;
		String parentIds = null;
		String parentFullName = "";
		
		try {
			parentId = (Integer) requestParamMap.get("parentId");
			type = (Integer) requestParamMap.get("type");
			name = (String) requestParamMap.get("name");
			
			if (parentId == null || type == null || name == null)
				throw new Exception();
			
			// 根节点 parentId 为 0， level 从1开始
			// 参数预处理
			if (parentId == 0) {
				parentIds = "0,";
				level = 1;
				
			} else {
				GeroAreaEntity queryParentGeroAreaEntity = new GeroAreaEntity();
				queryParentGeroAreaEntity.setId(parentId);
				GeroAreaEntity parentGeroAreaEntity = geroAreaService.getGeroArea(queryParentGeroAreaEntity);
				
				parentFullName = parentGeroAreaEntity.getFullName();
				parentIds = parentGeroAreaEntity.getParentIds() + parentId + ",";
				level = parentGeroAreaEntity.getLevel() + 1;
			}
				

		} catch(Exception e) {
			String otherMessage = "[parent_id=" + requestParamMap.get("parentId") + "]" +
					"[type=" + requestParamMap.get("type") + "]" +
					"[level=" + requestParamMap.get("level") + "]" +
					"[name=" + requestParamMap.get("name") + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_AREA_POST_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
	
			GeroAreaEntity postEntity = new GeroAreaEntity();
			BeanUtils.populate(postEntity, requestParamMap);
			postEntity.setFullName(parentFullName + name + ",");
			postEntity.setParentIds(parentIds);
			postEntity.setLevel(level);
			geroAreaService.insertGeroAreaRecord(postEntity);
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_AREA_POST_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
	
	@RequestMapping(value = "/{aid}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getGeroAreaAndSubareas(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@PathVariable("aid") int areaId,
			@RequestParam(value="sub_level", required=false) Integer subLevel
			) {
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":area:read");
		permissions.add("staff:"+getCurrentUser().getUserId()+":gero:"+geroId+":area:read");
		checkPermissions(permissions);
		
		// 获取基础的 JSON返回
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
	
		// 参数检查
		if (subLevel != null && subLevel < 0) {
			String otherMessage = "sub_level 参数错误:" +
					"[sub_level=" + subLevel + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_AREA_GET_SUBAREA_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		// 参数预处理
		if (subLevel == null)
			subLevel = 1;
		
		try {
			GeroAreaEntity queryGeroAreaEntity = new GeroAreaEntity();
			queryGeroAreaEntity.setGeroId(geroId);
			queryGeroAreaEntity.setId(areaId);
			GeroAreaEntity ancestorGeroAreaEntity = geroAreaService.getGeroArea(queryGeroAreaEntity);
			
			if (ancestorGeroAreaEntity != null) {
				List<GeroAreaEntity> descendantGeroAreaEntities = geroAreaService.getGeroSubareas(ancestorGeroAreaEntity, subLevel);
				
				// 构造返回的 JSON
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("id", ancestorGeroAreaEntity.getId()); 
				resultMap.put("parent_id", ancestorGeroAreaEntity.getParentId()); 
				resultMap.put("parent_ids", ancestorGeroAreaEntity.getParentIds()); 
				resultMap.put("type", ancestorGeroAreaEntity.getType()); 
				resultMap.put("level", ancestorGeroAreaEntity.getLevel()); 
				resultMap.put("name", ancestorGeroAreaEntity.getName()); 
				resultMap.put("full_name", ancestorGeroAreaEntity.getFullName()); 
				
				ArrayList<Object> tempList = new ArrayList<Object>();
				
				if (descendantGeroAreaEntities != null) {
					for (GeroAreaEntity geroAreaEntity : descendantGeroAreaEntities) {
						Map<String, Object> tempMap = new HashMap<String, Object>();
						tempMap.put("id", ancestorGeroAreaEntity.getId()); 
						tempMap.put("parent_id", geroAreaEntity.getParentId()); 
						tempMap.put("parent_ids", geroAreaEntity.getParentIds()); 
						tempMap.put("type", geroAreaEntity.getType()); 
						tempMap.put("level", geroAreaEntity.getLevel()); 
						tempMap.put("name", geroAreaEntity.getName()); 
						tempList.add(tempMap);
					}
				}
				
				resultMap.put("area_list", tempList); 
				basicReturnedJson.addEntity(resultMap);
			}
			
			return basicReturnedJson.getMap();
			
		} catch (Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_AREA_GET_SUBAREA_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
	}
	
	@Transactional
	@RequestMapping(value = "/{aid}", method = RequestMethod.PUT, produces = MediaTypes.JSON_UTF_8)
	public Object putGeroArea(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@PathVariable("aid") int areaId,
			@RequestBody String inJson
			) {	
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":area:update");
		checkPermissions(permissions);
		
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		tempRquestParamMap.put("geroId", geroId);
		tempRquestParamMap.put("id", areaId);
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 修改数据
		try {
			GeroAreaEntity postEntity = new GeroAreaEntity(); 
			BeanUtils.populate(postEntity, requestParamMap);
			geroAreaService.updateGeroAreaRecord(postEntity);
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_AREA_PUT_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}

	@Transactional
	@RequestMapping(value = "/{aid}", method = RequestMethod.DELETE, produces = MediaTypes.JSON_UTF_8)
	public Object deleteGeroArea(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@PathVariable("aid") int areaId
			) {	
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":area:delete");
		checkPermissions(permissions);
		
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = new HashMap<String, Object>();
		tempRquestParamMap.put("geroId", geroId);
		tempRquestParamMap.put("id", areaId);
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 删除数据
		try {
			GeroAreaEntity inputEntity = new GeroAreaEntity(); 
			BeanUtils.populate(inputEntity, requestParamMap);
			geroAreaService.deleteGeroAreaRecord(inputEntity);
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_AREA_DELETE_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
	
}