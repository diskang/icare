/**
 * @Package com.sjtu.icare.modules.staff.webservice
 * @Description TODO
 * @date Mar 23, 2015 7:27:10 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.staff.webservice;

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
import com.sjtu.icare.common.utils.ParamValidator;
import com.sjtu.icare.common.utils.StringUtils;
import com.sjtu.icare.common.web.rest.BasicController;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.op.entity.CareworkEntity;
import com.sjtu.icare.modules.op.service.IWorkService;

@RestController
@RequestMapping({"${api.web}/gero/{gid}/carework", "${api.service}/gero/{gid}/carework"})
public class CareworkRestController extends BasicController {
	private static Logger logger = Logger.getLogger(CareworkRestController.class);
	
	
	@Autowired
	private IWorkService workService; 
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getCareworks(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@RequestParam(value="start_date", required=false) String startDate,
			@RequestParam(value="elder_id", required=false) Integer elderId,
			@RequestParam(value="staff_id", required=false) Integer staffId,
			@RequestParam("page") int page,
			@RequestParam("rows") int limit,
			@RequestParam("sort") String orderByTag
			) {
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":carework:read");
		checkPermissions(permissions);
		
		// 参数检查
		if (startDate != null && !ParamValidator.isDate(startDate)) {
			String otherMessage = "start_date 不符合日期格式:" +
					"[start_date=" + startDate + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_CAREWORK_GET_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		try {
			// 参数预处理
			String elderIds = null;
			if (elderId != null)
				elderIds = "" + elderId;
			if (startDate == null)
				startDate = DateUtils.getDate();
			
			// 获取基础的 JSON返回
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
			
			CareworkEntity requestCareworkEntity = new CareworkEntity();
			requestCareworkEntity.setGeroId(geroId);
			requestCareworkEntity.setElderIds(elderIds);
			requestCareworkEntity.setCarerId(staffId);
			
			Page<CareworkEntity> pages = new Page<CareworkEntity>(page, limit);
			pages = setOrderBy(pages, orderByTag);
			requestCareworkEntity.setPage(pages);
			
			requestCareworkEntity.setReqStartDate(startDate);
			 
			List<CareworkEntity> careworkEntities = workService.getCareworkEntities(requestCareworkEntity);
			basicReturnedJson.setTotal((int) requestCareworkEntity.getPage().getCount());
			
			
			for (CareworkEntity careworkEntity : careworkEntities) {
				Map<String, Object> resultMap = new HashMap<String, Object>(); 
				resultMap.put("id", careworkEntity.getId()); 
				resultMap.put("staff_id", careworkEntity.getCarerId()); 
				resultMap.put("end_date", careworkEntity.getEndDate()); 
				resultMap.put("status", careworkEntity.getStatus());
				
				List<Integer> elderIdsList = new ArrayList<Integer>();
				String[] tempStrings = careworkEntity.getElderIds().split(",");
				for (String string : tempStrings)
					if (StringUtils.isNotBlank(string)) {
						elderIdsList.add(Integer.parseInt(string.trim()));
					}
				
				resultMap.put("elder_ids", elderIdsList); 
				
				basicReturnedJson.addEntity(resultMap);
			}
			
			
			
			return basicReturnedJson.getMap();
			
		} catch(Exception e) {
			e.printStackTrace();
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_CAREWORK_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	}
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Object postCarework(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@RequestBody String inJson
			) {
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":carework:add");
		checkPermissions(permissions);
		
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		requestParamMap.put("geroId", geroId);
		
		Integer staffId = null;
		List<Integer> elderIdsList = null;
		String endDate = null;
		Integer status = 0;
		
		try {
			staffId = (Integer) requestParamMap.get("staffId");
			elderIdsList = (List<Integer>) requestParamMap.get("elderIds");
			endDate = (String) requestParamMap.get("endDate");
			
			if (requestParamMap.get("staffId") == null
				|| requestParamMap.get("elderIds") == null
				)
				throw new Exception();
			
			// 参数详细验证
			if (endDate != null && !ParamValidator.isDate(endDate))
				throw new Exception();
		} catch(Exception e) {
			String otherMessage = "[" + inJson + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_CAREWOKR_POST_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			
			String elderIds = ",";
			for (Integer integer : elderIdsList) {
				elderIds += integer.toString() + ",";
			}
			
			CareworkEntity requestCareworkEntity = new CareworkEntity();
			BeanUtils.populate(requestCareworkEntity, requestParamMap);
			requestCareworkEntity.setElderIds(elderIds);
			requestCareworkEntity.setCarerId(staffId);
			requestCareworkEntity.setStatus(status);
			workService.insertCarework(requestCareworkEntity);
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_CAREWOKR_POST_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
	
	@Transactional
	@RequestMapping(value="{carework_id}", method = RequestMethod.PUT, produces = MediaTypes.JSON_UTF_8)
	public Object putCarework(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@PathVariable("carework_id") int careworkId,
			@RequestBody String inJson
			) {
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":carework:update");
		checkPermissions(permissions);
		
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		requestParamMap.put("geroId", geroId);
		requestParamMap.put("id", careworkId);
		
		Integer staffId = null;
		List<Integer> elderIdsList = null;
		String endDate = null;
		
		try {
			staffId = (Integer) requestParamMap.get("staffId");
			elderIdsList = (List<Integer>) requestParamMap.get("elderIds");
			endDate = (String) requestParamMap.get("endDate");
			
			// 参数详细验证
//			if (endDate != null && !ParamValidator.isDate(endDate))
//				throw new Exception();
		} catch(Exception e) {
			String otherMessage = "[" + inJson + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_CAREWOKR_PUT_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			
			String elderIds = null;
			if (elderIdsList != null) {
				elderIds = ",";
				for (Integer integer : elderIdsList) {
					elderIds += integer.toString() + ",";
				}
			}
			
			CareworkEntity requestCareworkEntity = new CareworkEntity();
			BeanUtils.populate(requestCareworkEntity, requestParamMap);
			requestCareworkEntity.setElderIds(elderIds);
			requestCareworkEntity.setCarerId(staffId);
			workService.updateCarework(requestCareworkEntity);
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_CAREWOKR_PUT_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
	
	@Transactional
	@RequestMapping(value="{carework_id}", method = RequestMethod.DELETE, produces = MediaTypes.JSON_UTF_8)
	public Object deleteCarework(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@PathVariable("carework_id") int careworkId
			) {
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":carework:delete");
		checkPermissions(permissions);
		
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = new HashMap<String, Object>();
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		requestParamMap.put("geroId", geroId);
		requestParamMap.put("id", careworkId);
		
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 删除数据
		try {
			
		
			CareworkEntity requestCareworkEntity = new CareworkEntity();
			BeanUtils.populate(requestCareworkEntity, requestParamMap);
			workService.deleteCarework(requestCareworkEntity);
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_CAREWOKR_DELETE_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
	
}
