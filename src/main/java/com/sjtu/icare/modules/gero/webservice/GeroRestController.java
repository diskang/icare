/**
 * @Package com.sjtu.icare.modules.gero.webservice
 * @Description TODO
 * @date Mar 24, 2015 5:11:14 PM
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
import com.sjtu.icare.common.persistence.Page;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.DateUtils;
import com.sjtu.icare.common.utils.MapListUtils;
import com.sjtu.icare.common.utils.ParamUtils;
import com.sjtu.icare.common.utils.StringUtils;
import com.sjtu.icare.common.web.rest.BasicController;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.gero.entity.GeroEntity;
import com.sjtu.icare.modules.gero.service.IGeroService;

@RestController
@RequestMapping({"${api.web}/gero", "${api.service}/gero"})
public class GeroRestController extends BasicController {
	private static Logger logger = Logger.getLogger(GeroRestController.class);

	@Autowired
	private IGeroService geroService;
	
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getGeros(
			HttpServletRequest request,
			@RequestParam("page") int page,
			@RequestParam("rows") int rows,
			@RequestParam("sort") String sort
			) {
		checkApi(request);
  		
		try {
			// 获取基础的 JSON返回
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();

			GeroEntity requestGeroEntity = new GeroEntity();
			
			Page<GeroEntity> pages = new Page<GeroEntity>(page, rows);
			pages = setOrderBy(pages, sort);
			requestGeroEntity.setPage(pages);
			
			List<GeroEntity> geroEntities = geroService.getGeros(requestGeroEntity);
			
			if (geroEntities != null) {
				
				for (GeroEntity geroEntity : geroEntities) {
					
					Map<String, Object> resultMap = new HashMap<String, Object>(); 
					resultMap.put("id", geroEntity.getId()); 
					resultMap.put("name", geroEntity.getName()); 
					resultMap.put("city", geroEntity.getCity()); 
					resultMap.put("district", geroEntity.getDistrict()); 
					resultMap.put("level", geroEntity.getCareLevel()); 
					resultMap.put("address", geroEntity.getAddress()); 
					resultMap.put("contact", geroEntity.getContact()); 
					resultMap.put("contact_id", geroEntity.getContactId()); 
					resultMap.put("license", geroEntity.getLicence()); 
					resultMap.put("scale", geroEntity.getScale()); 
					
					basicReturnedJson.addEntity(resultMap);
				}
			}
			basicReturnedJson.setTotal((int) requestGeroEntity.getPage().getCount());
			
			return basicReturnedJson.getMap();
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	}
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Object postGero(
			HttpServletRequest request,
			@RequestBody String inJson
			) {
		checkApi(request);
		
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		requestParamMap.put("registerDate", DateUtils.getDateTime());
		
		try {
			if (requestParamMap.get("name") == null
				|| requestParamMap.get("city") == null
				|| requestParamMap.get("district") == null
				)
				throw new Exception();
			
			if (requestParamMap.get("contactId") != null && StringUtils.isBlank((CharSequence) requestParamMap.get("contactId")))
				throw new Exception();			
			// 参数详细验证
			// TODO
		} catch(Exception e) {
			String otherMessage = "[" + inJson + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_POST_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			
			GeroEntity requestGeroEntity = new GeroEntity();
			BeanUtils.populate(requestGeroEntity, requestParamMap);
			geroService.insertGero(requestGeroEntity);
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_POST_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}

	@RequestMapping(value="/{gid}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getGero(
			HttpServletRequest request,
			@PathVariable("gid") int geroId
			) {
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+"info:read");
		checkPermissions(permissions);
  		
		try {
			// 获取基础的 JSON返回
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();

			GeroEntity requestGeroEntity = new GeroEntity();
			requestGeroEntity.setId(geroId);
			GeroEntity geroEntity = geroService.getGero(requestGeroEntity);
			
			if (geroEntity != null) {
				Map<String, Object> resultMap = new HashMap<String, Object>(); 
				resultMap.put("id", geroEntity.getId()); 
				resultMap.put("name", geroEntity.getName()); 
				resultMap.put("city", geroEntity.getCity()); 
				resultMap.put("district", geroEntity.getDistrict()); 
				resultMap.put("level", geroEntity.getCareLevel()); 
				resultMap.put("address", geroEntity.getAddress()); 
				resultMap.put("contact", geroEntity.getContact()); 
				resultMap.put("contact_id", geroEntity.getContactId()); 
				resultMap.put("license", geroEntity.getLicence()); 
				resultMap.put("scale", geroEntity.getScale()); 
				
				basicReturnedJson.addEntity(resultMap);
			}
			
			return basicReturnedJson.getMap();
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_GET_SPECIFIC_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	}

	@Transactional
	@RequestMapping(value="/{gid}", method = RequestMethod.PUT, produces = MediaTypes.JSON_UTF_8)
	public Object putGero(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@RequestBody String inJson
			) {
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":info:update");
		checkPermissions(permissions);
		
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		requestParamMap.put("id", geroId);
		
		
		try {
			if (requestParamMap.get("contactId") != null && StringUtils.isBlank((CharSequence) requestParamMap.get("contactId")))
				throw new Exception();			
			// 参数详细验证
			// TODO
		} catch(Exception e) {
			String otherMessage = "[" + inJson + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_PUT_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			
			GeroEntity requestGeroEntity = new GeroEntity();
			BeanUtils.populate(requestGeroEntity, requestParamMap);
			geroService.updateGero(requestGeroEntity);
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_PUT_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
	
	@RequestMapping(value="/{gid}", method = RequestMethod.DELETE, produces = MediaTypes.JSON_UTF_8)
	public Object deleteGero(
			HttpServletRequest request,
			@PathVariable("gid") int geroId
			) {
		checkApi(request);
		
		try {
			// 获取基础的 JSON返回
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();

			GeroEntity requestGeroEntity = new GeroEntity();
			requestGeroEntity.setId(geroId);
			requestGeroEntity.setCancelDate(DateUtils.getDateTime());
			geroService.deleteGero(requestGeroEntity);
			
			return basicReturnedJson.getMap();
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_DELETE_SPECIFIC_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	}

	
}
