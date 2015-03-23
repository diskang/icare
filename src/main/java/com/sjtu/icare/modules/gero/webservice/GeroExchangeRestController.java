/**
 * @Package com.sjtu.icare.modules.gero.webservice
 * @Description TODO
 * @date Mar 22, 2015 5:33:04 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.gero.webservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.sjtu.icare.common.utils.MapListUtils;
import com.sjtu.icare.common.utils.ParamUtils;
import com.sjtu.icare.common.utils.ParamValidator;
import com.sjtu.icare.common.web.rest.BasicController;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.gero.entity.GeroElderExchangeEntity;
import com.sjtu.icare.modules.gero.service.IGeroElderExchangeService;


@RestController
@RequestMapping({"${api.web}/gero/{gid}/exchange", "${api.service}/gero/{gid}/exchange"})
public class GeroExchangeRestController extends BasicController {
	private static Logger logger = Logger.getLogger(GeroExchangeRestController.class);

	@Autowired
	private IGeroElderExchangeService geroElderExchangeService;
	
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getGeroElderExchanges(
			@PathVariable("gid") int geroId,
			@RequestParam(value="participants", required=false) String participants,
			@RequestParam(value="mediators", required=false) String mediators,
			@RequestParam(value="result", required=false) String result,
			@RequestParam(value="recorder", required=false) Integer recorder,
			@RequestParam(value="time", required=false) String time,
			@RequestParam("page") int page,
			@RequestParam("limit") int limit,
			@RequestParam("order_by") String orderByTag
			) {
		
		// 获取基础的 JSON返回
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
	
		// 参数检查
		// TODO
		
		// 参数预处理
		
		try {
			
			GeroElderExchangeEntity queryElderExchangeEntity = new GeroElderExchangeEntity();
			queryElderExchangeEntity.setGeroId(geroId);
			queryElderExchangeEntity.setParticipants(participants);
			queryElderExchangeEntity.setMediators(mediators);
			queryElderExchangeEntity.setResult(result);
			queryElderExchangeEntity.setRecorder(recorder);
			queryElderExchangeEntity.setTime(time);
			
			Page<GeroElderExchangeEntity> pages = new Page<GeroElderExchangeEntity>(page, limit);
			pages = setOrderBy(pages, orderByTag);
			queryElderExchangeEntity.setPage(pages);
			
			List<GeroElderExchangeEntity> geroElderExchangeEntities = geroElderExchangeService.getGeroElderExchangeEntities(queryElderExchangeEntity);
			
			if (geroElderExchangeEntities != null) {
				// 构造返回的 JSON
				for (GeroElderExchangeEntity geroElderExchangeEntity : geroElderExchangeEntities) {
					Map<String, Object> resultMap = new HashMap<String, Object>(); 
					resultMap.put("id", geroElderExchangeEntity.getId()); 
					resultMap.put("participants", geroElderExchangeEntity.getParticipants()); 
					resultMap.put("mediators", geroElderExchangeEntity.getMediators());
					resultMap.put("description", geroElderExchangeEntity.getDescription());
					resultMap.put("result", geroElderExchangeEntity.getResult());
					resultMap.put("recorder", geroElderExchangeEntity.getRecorder());
					resultMap.put("time", geroElderExchangeEntity.getTime());
					
					basicReturnedJson.addEntity(resultMap);
				}
				
			}

			return basicReturnedJson.getMap();
			
		} catch (Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_ELDER_EXCHANGE_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	
	}

	@RequestMapping(value="/{eid}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getGeroElderExchange(
			@PathVariable("gid") int geroId,
			@PathVariable("eid") int geroElderExchangeId
			) {
		
		// 获取基础的 JSON返回
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
	
		// 参数检查
		// TODO
		
		// 参数预处理
		
		try {
			
			GeroElderExchangeEntity queryElderExchangeEntity = new GeroElderExchangeEntity();
			queryElderExchangeEntity.setGeroId(geroId);
			queryElderExchangeEntity.setId(geroElderExchangeId);
			
			GeroElderExchangeEntity geroElderExchangeEntity = geroElderExchangeService.getGeroElderExchangeEntity(queryElderExchangeEntity);
			
			if (geroElderExchangeEntity != null) {
				// 构造返回的 JSON
				Map<String, Object> resultMap = new HashMap<String, Object>(); 
				resultMap.put("id", geroElderExchangeEntity.getId()); 
				resultMap.put("participants", geroElderExchangeEntity.getParticipants()); 
				resultMap.put("mediators", geroElderExchangeEntity.getMediators());
				resultMap.put("description", geroElderExchangeEntity.getDescription());
				resultMap.put("result", geroElderExchangeEntity.getResult());
				resultMap.put("recorder", geroElderExchangeEntity.getRecorder());
				resultMap.put("time", geroElderExchangeEntity.getTime());
				
				basicReturnedJson.addEntity(resultMap);
			
			}

			return basicReturnedJson.getMap();
			
		} catch (Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_ELDER_EXCHANGE_SPECIFIC_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	
	}
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Object postGeroArea(
			@PathVariable("gid") int geroId,
			@RequestBody String inJson
			) {	
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		tempRquestParamMap.put("geroId", geroId);
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		
		String participants;
		String mediators;
		String result;
		Integer recorder;
		String time;
		
		try {
			participants = (String) requestParamMap.get("participants");
			mediators = (String) requestParamMap.get("mediators");
			result = (String) requestParamMap.get("result");
			recorder = (Integer) requestParamMap.get("recorder");
			time = (String) requestParamMap.get("time");
			
			if (participants == null || mediators == null || result == null || recorder == null)
				throw new Exception();
			
			// 参数详细验证
			if (time != null && !ParamValidator.isDate(time))
				throw new Exception();
			
		} catch(Exception e) {
			String otherMessage = "[" + inJson + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_ELDER_EXCHANGE_POST_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			GeroElderExchangeEntity postGeroElderExchangeEntity = new GeroElderExchangeEntity();
			BeanUtils.populate(postGeroElderExchangeEntity, requestParamMap);
			geroElderExchangeService.insertGeroElderExchange(postGeroElderExchangeEntity);
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_ELDER_EXCHANGE_POST_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
		
	@Transactional
	@RequestMapping(value="/{eid}", method = RequestMethod.PUT, produces = MediaTypes.JSON_UTF_8)
	public Object postGeroArea(
			@PathVariable("gid") int geroId,
			@PathVariable("eid") int geroElderExchangeId,
			@RequestBody String inJson
			) {	
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		tempRquestParamMap.put("geroId", geroId);
		tempRquestParamMap.put("id", geroElderExchangeId);
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		
		Integer recorder;
		String time;
		
		try {
			recorder = (Integer) requestParamMap.get("recorder");
			time = (String) requestParamMap.get("time");
			
			// 参数详细验证
			if (time != null && !ParamValidator.isDate(time))
				throw new Exception();
			
		} catch(Exception e) {
			String otherMessage = "[" + inJson + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_ELDER_EXCHANGE_PUT_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			GeroElderExchangeEntity putGeroElderExchangeEntity = new GeroElderExchangeEntity();
			BeanUtils.populate(putGeroElderExchangeEntity, requestParamMap);
			geroElderExchangeService.updateGeroElderExchange(putGeroElderExchangeEntity);
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_ELDER_EXCHANGE_PUT_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}

	@RequestMapping(value="/{eid}", method = RequestMethod.DELETE, produces = MediaTypes.JSON_UTF_8)
	public Object deleteGeroElderExchange(
			@PathVariable("gid") int geroId,
			@PathVariable("eid") int geroElderExchangeId
			) {
		
		// 获取基础的 JSON返回
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
	
		// 参数检查
		// TODO
		
		// 参数预处理
		
		try {
			
			GeroElderExchangeEntity queryGeroElderExchangeEntity = new GeroElderExchangeEntity();
			queryGeroElderExchangeEntity.setGeroId(geroId);
			queryGeroElderExchangeEntity.setId(geroElderExchangeId);
			
			geroElderExchangeService.deleteGeroElderExchangeEntity(queryGeroElderExchangeEntity);
			
			return basicReturnedJson.getMap();
			
		} catch (Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_ELDER_EXCHANGE_SPECIFIC_DELETE_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	
	}

}
