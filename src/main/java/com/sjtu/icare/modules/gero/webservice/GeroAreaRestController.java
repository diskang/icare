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

import org.apache.commons.beanutils.BeanUtils;
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
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.CommonUtils;
import com.sjtu.icare.common.utils.ParamUtils;
import com.sjtu.icare.common.utils.StringUtils;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.gero.entity.GeroAreaEntity;
import com.sjtu.icare.modules.gero.service.IGeroAreaService;

@RestController
@RequestMapping("/gero/{gid}/area")
public class GeroAreaRestController {
	private static Logger logger = Logger.getLogger(GeroAreaRestController.class);

	@Autowired
	private IGeroAreaService geroAreaService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getGeroAreas(
			@PathVariable("gid") int geroId
			) {
		
		// 获取基础的 JSON返回
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
	
		// 参数检查
		// 参数预处理
		
		try {
			GeroAreaEntity queryGeroAreaEntity = new GeroAreaEntity();
			queryGeroAreaEntity.setGeroId(geroId);
			List<GeroAreaEntity> geroAreaEntities = geroAreaService.getGeroAreas(queryGeroAreaEntity);
			
			// 构造返回的 JSON
			for (GeroAreaEntity geroAreaEntity : geroAreaEntities) {
				Map<String, Object> resultMap = new HashMap<String, Object>(); 
				resultMap.put("id", geroId); 
				resultMap.put("parent_id", geroAreaEntity.getParentId()); 
				resultMap.put("parent_ids", geroAreaEntity.getParentIds()); 
				resultMap.put("type", geroAreaEntity.getType()); 
				resultMap.put("level", geroAreaEntity.getLevel()); 
				resultMap.put("name", geroAreaEntity.getName()); 

				basicReturnedJson.addEntity(resultMap);
			}

			return basicReturnedJson.getMap();
			
		} catch (Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_AREA_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Object postGeroArea(
			@PathVariable("gid") int geroId,
			@RequestBody String inJson
			) {	
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		tempRquestParamMap.put("geroId", geroId);
		Map<String, Object> requestParamMap = CommonUtils.convertMapToCamelStyle(tempRquestParamMap);
		
		Integer parentId;
		String parentIds;
		Integer type;
		Integer level;
		String name;
		
		try {
			parentId = (Integer) requestParamMap.get("parentId");
			parentIds = (String) requestParamMap.get("parentIds");
			type = (Integer) requestParamMap.get("type");
			level = (Integer) requestParamMap.get("level");
			name = (String) requestParamMap.get("name");
			
			if (parentId == null || parentIds == null || type == null || level == null || name == null)
				throw new Exception();
			
			// 参数详细验证
			GeroAreaEntity queryParentGeroAreaEntity = new GeroAreaEntity();
			queryParentGeroAreaEntity.setId(parentId);
			GeroAreaEntity parentGeroAreaEntity = geroAreaService.getGeroArea(queryParentGeroAreaEntity);
			// 根节点 parentId 为 0， level 从1开始
			if (parentId == 0 && (level != 1 || !StringUtils.isBlank(parentIds)))
				throw new Exception();
			
			if (parentId != 0 && (parentGeroAreaEntity.getLevel() + 1 != level || !parentIds.equals(parentGeroAreaEntity.getParentIds() + parentId + ",")))
				throw new Exception();
			
		} catch(Exception e) {
			String otherMessage = "[parent_id=" + requestParamMap.get("parentId") + "]" +
					"[parent_ids=" + requestParamMap.get("parentIds") + "]" +
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
			@PathVariable("gid") int geroId,
			@PathVariable("aid") int areaId,
			@RequestParam(value="sub_level", required=false) Integer subLevel
			) {
		
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
			List<GeroAreaEntity> descendantGeroAreaEntities = geroAreaService.getGeroSubareas(ancestorGeroAreaEntity, subLevel);
			
			// 构造返回的 JSON
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("id", geroId); 
			resultMap.put("parent_id", ancestorGeroAreaEntity.getParentId()); 
			resultMap.put("parent_ids", ancestorGeroAreaEntity.getParentIds()); 
			resultMap.put("type", ancestorGeroAreaEntity.getType()); 
			resultMap.put("level", ancestorGeroAreaEntity.getLevel()); 
			resultMap.put("name", ancestorGeroAreaEntity.getName()); 
			
			ArrayList<Object> tempList = new ArrayList<Object>();
			for (GeroAreaEntity geroAreaEntity : descendantGeroAreaEntities) {
				Map<String, Object> tempMap = new HashMap<String, Object>();
				tempMap.put("id", geroId); 
				tempMap.put("parent_id", geroAreaEntity.getParentId()); 
				tempMap.put("parent_ids", geroAreaEntity.getParentIds()); 
				tempMap.put("type", geroAreaEntity.getType()); 
				tempMap.put("level", geroAreaEntity.getLevel()); 
				tempMap.put("name", geroAreaEntity.getName()); 
				tempList.add(tempMap);
			}
			resultMap.put("area_list", tempList); 
			basicReturnedJson.addEntity(resultMap);
			return basicReturnedJson.getMap();
			
		} catch (Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_AREA_GET_SUBAREA_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
	}
	
	@RequestMapping(value = "/{aid}", method = RequestMethod.PUT, produces = MediaTypes.JSON_UTF_8)
	public Object putGeroArea(
			@PathVariable("gid") int geroId,
			@PathVariable("aid") int areaId,
			@RequestBody String inJson
			) {	
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		tempRquestParamMap.put("geroId", geroId);
		tempRquestParamMap.put("id", areaId);
		Map<String, Object> requestParamMap = CommonUtils.convertMapToCamelStyle(tempRquestParamMap);
		
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

	@RequestMapping(value = "/{aid}", method = RequestMethod.DELETE, produces = MediaTypes.JSON_UTF_8)
	public Object deleteGeroArea(
			@PathVariable("gid") int geroId,
			@PathVariable("aid") int areaId
			) {	
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = new HashMap<String, Object>();
		tempRquestParamMap.put("geroId", geroId);
		tempRquestParamMap.put("id", areaId);
		Map<String, Object> requestParamMap = CommonUtils.convertMapToCamelStyle(tempRquestParamMap);
		
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