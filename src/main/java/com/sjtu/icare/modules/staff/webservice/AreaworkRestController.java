/**
 * @Package com.sjtu.icare.modules.staff.webservice
 * @Description TODO
 * @date Mar 24, 2015 3:32:12 PM
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
import com.sjtu.icare.common.utils.MapListUtils;
import com.sjtu.icare.common.utils.ParamUtils;
import com.sjtu.icare.common.utils.ParamValidator;
import com.sjtu.icare.common.utils.StringUtils;
import com.sjtu.icare.common.web.rest.BasicController;
import com.sjtu.icare.common.web.rest.GeroBaseController;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.op.entity.AreaworkEntity;
import com.sjtu.icare.modules.op.service.IWorkService;

@RestController
@RequestMapping({"${api.web}/gero/{gid}/areawork", "${api.service}/gero/{gid}/areawork"})
public class AreaworkRestController extends GeroBaseController {
	private static Logger logger = Logger.getLogger(AreaworkRestController.class);
	
	
	@Autowired
	private IWorkService workService; 
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getAreaworks(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@RequestParam(value="start_date", required=false) String startDate,
			@RequestParam(value="end_date", required=false) String endDate,
			@RequestParam(value="aid", required=false) Integer areaId,
			@RequestParam(value="sid", required=false) Integer staffId,
			@RequestParam("page") int page,
			@RequestParam("limit") int limit,
			@RequestParam("order_by") String orderByTag
			) {
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":areawork:read");
		checkPermissions(permissions);
		
		// 参数检查
		if ((startDate != null && !ParamValidator.isDate(startDate)) || (endDate != null && !ParamValidator.isDate(endDate))) {
			String otherMessage = "start_date 或 end_date 不符合日期格式:" +
					"[start_date=" + startDate + "]" +
					"[end_date=" + endDate + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_AREAWORK_GET_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		try {
			// 参数预处理
			String areaIds = null;
			if (areaId != null)
				areaIds = "" + areaId;
			
			Map<String, String> tempMap = ParamUtils.getDateOfStartDateAndEndDate(startDate, endDate);
			startDate = tempMap.get("startDate");
			endDate = tempMap.get("endDate");
			
			// 获取基础的 JSON返回
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
			
			AreaworkEntity requestAreaworkEntity = new AreaworkEntity();
			requestAreaworkEntity.setGeroId(geroId);
			requestAreaworkEntity.setAreaIds(areaIds);
			requestAreaworkEntity.setCarerId(staffId);
			
			Page<AreaworkEntity> pages = new Page<AreaworkEntity>(page, limit);
			pages = setOrderBy(pages, orderByTag);
			requestAreaworkEntity.setPage(pages);
			
			requestAreaworkEntity.setReqStartDate(startDate);
			requestAreaworkEntity.setReqEndDate(endDate);
			
			List<AreaworkEntity> areaworkEntities = workService.getAreaworkEntities(requestAreaworkEntity);
			basicReturnedJson.setTotal((int) requestAreaworkEntity.getPage().getCount());
			
			for (AreaworkEntity areaworkEntity : areaworkEntities) {
				Map<String, Object> resultMap = new HashMap<String, Object>(); 
				resultMap.put("id", areaworkEntity.getId()); 
				resultMap.put("staff_id", areaworkEntity.getCarerId()); 
				resultMap.put("end_date", areaworkEntity.getEndDate()); 
				resultMap.put("status", areaworkEntity.getStatus());
				
				List<Integer> areaIdsList = new ArrayList<Integer>();
				String[] tempStrings = areaworkEntity.getAreaIds().split(",");
				for (String string : tempStrings)
					if (StringUtils.isNotBlank(string)) {
						areaIdsList.add(Integer.parseInt(string.trim()));
					}
				
				resultMap.put("area_ids", areaIdsList); 
				
				basicReturnedJson.addEntity(resultMap);
			}
			
			return basicReturnedJson.getMap();
			
		} catch(Exception e) {
			e.printStackTrace();
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_AREAWORK_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	}
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Object postAreawork(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@RequestBody String inJson
			) {
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":areawork:add");
		checkPermissions(permissions);
		
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		requestParamMap.put("geroId", geroId);
		
		Integer staffId = null;
		List<Integer> areaIdsList = null;
		String endDate = null;
		Integer status = 0;
		
		try {
			staffId = (Integer) requestParamMap.get("staffId");
			areaIdsList = (List<Integer>) requestParamMap.get("areaIds");
			endDate = (String) requestParamMap.get("endDate");
			
			if (requestParamMap.get("staffId") == null
				|| requestParamMap.get("areaIds") == null
				)
				throw new Exception();
			
			// 参数详细验证
			if (endDate != null && !ParamValidator.isDate(endDate))
				throw new Exception();
		} catch(Exception e) {
			String otherMessage = "[" + inJson + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_AREAWORK_POST_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			
			String areaIds = ",";
			for (Integer integer : areaIdsList) {
				areaIds += integer.toString() + ",";
			}
			
			AreaworkEntity requestAreaworkEntity = new AreaworkEntity();
			BeanUtils.populate(requestAreaworkEntity, requestParamMap);
			requestAreaworkEntity.setAreaIds(areaIds);
			requestAreaworkEntity.setCarerId(staffId);
			requestAreaworkEntity.setStatus(status);
			workService.insertAreawork(requestAreaworkEntity);
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_AREAWORK_POST_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
	
	@Transactional
	@RequestMapping(value="{areawork_id}", method = RequestMethod.PUT, produces = MediaTypes.JSON_UTF_8)
	public Object putAreawork(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@PathVariable("areawork_id") int areaworkId,
			@RequestBody String inJson
			) {
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":areawork:update");
		checkPermissions(permissions);
		
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		requestParamMap.put("geroId", geroId);
		requestParamMap.put("id", areaworkId);
		
		Integer staffId = null;
		List<Integer> areaIdsList = null;
		String endDate = null;
		
		try {
			staffId = (Integer) requestParamMap.get("staffId");
			areaIdsList = (List<Integer>) requestParamMap.get("areaIds");
			endDate = (String) requestParamMap.get("endDate");
			
			// 参数详细验证
			if (endDate != null && !ParamValidator.isDate(endDate))
				throw new Exception();
		} catch(Exception e) {
			String otherMessage = "[" + inJson + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_AREAWORK_PUT_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			
			String areaIds = null;
			if (areaIdsList != null) {
				areaIds = ",";
				for (Integer integer : areaIdsList) {
					areaIds += integer.toString() + ",";
				}
			}
			
			AreaworkEntity requestAreaworkEntity = new AreaworkEntity();
			BeanUtils.populate(requestAreaworkEntity, requestParamMap);
			requestAreaworkEntity.setAreaIds(areaIds);
			requestAreaworkEntity.setCarerId(staffId);
			workService.updateAreawork(requestAreaworkEntity);
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_AREAWORK_PUT_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
	
	@Transactional
	@RequestMapping(value="{areawork_id}", method = RequestMethod.DELETE, produces = MediaTypes.JSON_UTF_8)
	public Object deleteCarework(
			HttpServletRequest request,
			@PathVariable("gid") int geroId,
			@PathVariable("areawork_id") int areaworkId
			) {
		checkApi(request);
		List<String> permissions = new ArrayList<String>();
		permissions.add("admin:gero:"+geroId+":areawork:delete");
		checkPermissions(permissions);
		
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = new HashMap<String, Object>();
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		requestParamMap.put("geroId", geroId);
		requestParamMap.put("id", areaworkId);
		
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 删除数据
		try {
		
			AreaworkEntity requestAreaworkEntity = new AreaworkEntity();
			BeanUtils.populate(requestAreaworkEntity, requestParamMap);
			workService.deleteAreawork(requestAreaworkEntity);
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_AREAWORK_DELETE_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
}
