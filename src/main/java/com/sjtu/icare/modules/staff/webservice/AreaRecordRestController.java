/**
 * @Package com.sjtu.icare.modules.elder.webservice
 * @Description TODO
 * @date Mar 18, 2015 3:28:27 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.staff.webservice;

import java.util.ArrayList;
import java.util.Date;
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
import com.sjtu.icare.common.persistence.Page;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.DateUtils;
import com.sjtu.icare.common.utils.MapListUtils;
import com.sjtu.icare.common.utils.ParamUtils;
import com.sjtu.icare.common.utils.ParamValidator;
import com.sjtu.icare.common.web.rest.BasicController;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.op.entity.AreaworkRecordEntity;
import com.sjtu.icare.modules.op.service.IItemRecordService;

@RestController
@RequestMapping({"${api.web}/gero/{gid}/areawork_record", "${api.service}/gero/{gid}/areawork_record"})
public class AreaRecordRestController extends BasicController {
	private static Logger logger = Logger.getLogger(AreaRecordRestController.class);
	
	@Autowired
	private IItemRecordService itemRecordService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getAreaworkRecords(
			@PathVariable("gid") int geroId,
			@RequestParam(value="staff_id", required=false) Integer carerId,
			@RequestParam(value="area_id", required=false) Integer areaId,
			@RequestParam(value="start_date", required=false) String startDate,
			@RequestParam(value="end_date", required=false) String endDate,
			@RequestParam("page") int page,
			@RequestParam("rows") int rows,
			@RequestParam("sort") String sort
			) {
		
		// 参数检查
		if ((startDate != null && !ParamValidator.isDate(startDate)) || (endDate != null && !ParamValidator.isDate(endDate))) {
			String otherMessage = "start_date 或 end_date 不符合日期格式:" +
					"[start_date=" + startDate + "]" +
					"[end_date=" + endDate + "]";
			String message = ErrorConstants.format(ErrorConstants.AREAWORK_ITEMS_GET_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		try {
			// 参数预处理
			Map<String, String> tempMap = ParamUtils.getDateTimeOfStartDateAndEndDate(startDate, endDate);
			startDate = tempMap.get("startDate");
			endDate = tempMap.get("endDate");
			
			// 获取基础的 JSON返回
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
			
			AreaworkRecordEntity queryAreaworkRecordEntity = new AreaworkRecordEntity();
			queryAreaworkRecordEntity.setCarerId(carerId);
			queryAreaworkRecordEntity.setAreaId(areaId);
			
			Page<AreaworkRecordEntity> pages = new Page<AreaworkRecordEntity>(page, rows);
			pages = setOrderBy(pages, sort);
			queryAreaworkRecordEntity.setPage(pages);
			
			List<AreaworkRecordEntity> areaworkRecordEntities = itemRecordService.getAreaworkRecords(queryAreaworkRecordEntity, startDate, endDate);
			
			basicReturnedJson.setTotal((int) queryAreaworkRecordEntity.getPage().getCount());

			// 构造返回的 JSON
			for (AreaworkRecordEntity areaworkRecordEntity : areaworkRecordEntities) {
				
				Map<String, Object> resultMap = new HashMap<String, Object>(); 
				resultMap.put("id", areaworkRecordEntity.getId()); 
				resultMap.put("staff_id", areaworkRecordEntity.getCarerId()); 
				resultMap.put("staff_name", areaworkRecordEntity.getCarerName()); 
				resultMap.put("area_id", areaworkRecordEntity.getAreaId()); 
				resultMap.put("area_full_name", areaworkRecordEntity.getAreaFullName()); 
				resultMap.put("area_item_id", areaworkRecordEntity.getAreaItemId()); 
				resultMap.put("area_name", areaworkRecordEntity.getItemName()); 
				resultMap.put("finish_time", areaworkRecordEntity.getFinishTime()); 
				
				basicReturnedJson.addEntity(resultMap);
			}
			
			return basicReturnedJson.getMap();
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.AREAWORK_ITEMS_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	
	}
	

	@RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Object postAreaworkRecords(
			@RequestBody String inJson
			) {	
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		Map<String, Object> requestParamMap = MapListUtils.convertMapToCamelStyle(tempRquestParamMap);
		
		List<Map<String, Object>> areaItem;
		String finishTime;
		Integer carerId;
		Integer areaId;
		
		try {
			areaItem = (List<Map<String, Object>>) requestParamMap.get("areaItem");
			finishTime = (String) requestParamMap.get("finishTime");
			carerId = (Integer) requestParamMap.get("staffId");
			areaId = (Integer) requestParamMap.get("areaId");
			
			if (carerId == null || areaId == null)
				throw new Exception();
			
			// 参数详细验证
			if (areaItem == null)
				throw new Exception();
			
			if (finishTime != null && !ParamValidator.isDate(finishTime))
				throw new Exception();
		} catch(Exception e) {
			String otherMessage = "[" + inJson + "]";
			String message = ErrorConstants.format(ErrorConstants.AREAWORK_ITEMS_POST_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// 插入数据
		try {
			// 参数预处理
			if (finishTime == null) {
				Date today = new Date();
				finishTime = DateUtils.formatDateTime(today);
			}
			
			List<AreaworkRecordEntity> postEntities = new ArrayList<AreaworkRecordEntity>();
			for (Map<String, Object> entity : areaItem) {
				if (entity.get("id") == null || entity.get("name") == null)
					throw new Exception("请求参数 area_item 错误");
				entity.put("areaItemId", entity.get("id"));
				entity.remove("id");
				entity.put("itemName", entity.get("name"));
				entity.remove("name");
				
				AreaworkRecordEntity tempAreaworkRecordEntity = new AreaworkRecordEntity();
				BeanUtils.populate(tempAreaworkRecordEntity, entity);
				tempAreaworkRecordEntity.setFinishTime(finishTime);
				tempAreaworkRecordEntity.setCarerId(carerId);
				tempAreaworkRecordEntity.setAreaId(areaId);
				
				postEntities.add(tempAreaworkRecordEntity);
				
			}
			
			itemRecordService.insertAreaworkRecords(postEntities);
			
			
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.AREAWORK_ITEMS_POST_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
	
	
}
