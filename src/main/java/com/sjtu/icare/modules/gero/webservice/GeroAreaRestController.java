///**
// * @Package com.sjtu.icare.modules.gero.webservice
// * @Description gero area controller
// * @date Mar 9, 2015 4:12:24 PM
// * @author Wang Qi
// * @version TODO
// */
//package com.sjtu.icare.modules.gero.webservice;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.sjtu.icare.common.config.ErrorConstants;
//import com.sjtu.icare.common.utils.BasicReturnedJson;
//import com.sjtu.icare.common.utils.ParamUtils;
//import com.sjtu.icare.common.web.rest.MediaTypes;
//import com.sjtu.icare.common.web.rest.RestException;
//import com.sjtu.icare.modules.elder.webservice.ElderTemperatureRestController;
//import com.sjtu.icare.modules.gero.entity.GeroAreaEntity;
//import com.sjtu.icare.modules.gero.service.IGeroAreaService;
//
//@RestController
//@RequestMapping("/gero/{gid}/area")
//public class GeroAreaRestController {
//	private static Logger logger = Logger.getLogger(ElderTemperatureRestController.class);
//
//	@Autowired
//	private IGeroAreaService geroAreaService;
//	
//	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
//	public Object getGeroAreas(
//			@PathVariable("gid") int geroId
//			) {
//		
//		// 获取基础的 JSON返回
//		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
//	
//		// 参数检查
//		// 参数预处理
//		
//		try {
//			
//			List<GeroAreaEntity> geroAreaEntities = geroAreaService.getGeroAreas(geroId);
//			
//			// 构造返回的 JSON
//			for (GeroAreaEntity geroAreaEntity : geroAreaEntities) {
//				Map<String, Object> resultMap = new HashMap<String, Object>(); 
//				resultMap.put("id", geroId); 
//				resultMap.put("parent_id", geroAreaEntity.getParentId()); 
//				resultMap.put("parent_ids", geroAreaEntity.getParentIds()); 
//				resultMap.put("type", geroAreaEntity.getType()); 
//				resultMap.put("level", geroAreaEntity.getLevel()); 
//				resultMap.put("name", geroAreaEntity.getName()); 
//
//				basicReturnedJson.addEntity(resultMap);
//			}
//
//			return basicReturnedJson.getMap();
//			
//		} catch (Exception e) {
//			String message = "#" + ErrorConstants.GERO_AREA_GET_SERVICE_FAILED + "#\n" + 
//					"获取养老院数据失败：\n" +
//					"[" + e.getMessage() + "]" +
//					"\n";
//			logger.error(message);
//			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
//		}
//	
//	}
//
//	@RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
//	public Object postGeroArea(
//			@PathVariable("gid") int geroId,
//			@RequestBody String inJson
//			) {	
//		// TODO 把下划线格式改成驼峰
//		Map<String, Object> requestBodyParamMap = ParamUtils.getMapByJson(inJson, logger);
//
//		Integer parentId;
//		Integer type;
//		Integer level;
//		String name;
//		
//		try {
//			parentId = (Integer) requestBodyParamMap.get("parent_id");
//			type = (Integer) requestBodyParamMap.get("type");
//			level = (Integer) requestBodyParamMap.get("level");
//			name = (String) requestBodyParamMap.get("name");
//			
//			if (parentId == null || type == null || level == null || name == null)
//				throw new Exception();
//			
//			// 参数详细验证
//			// TODO
//			
//		} catch(Exception e) {
//			String message = "#" + ErrorConstants.GERO_AREA_INSERT_PARAM_INVALID + "#\n" + 
//					"非法参数:\n" +
//					"[doctor_id=" + requestBodyParamMap.get("doctor_id") + "]" +
//					"[temperature=" + requestBodyParamMap.get("temperature") + "]" +
//					"[time=" + requestBodyParamMap.get("time") + "]" +
//					"\n";
//			logger.error(message);
//			throw new RestException(HttpStatus.BAD_REQUEST, message);
//		}
//		
//		// 获取基础的 JSON
//		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
//		
//		// 插入数据
//		try {
//			geroAreaService.insertGeroAreaRecord(parentId, type, level, name);
//		} catch(Exception e) {
//			String message = "#" + ErrorConstants.GERO_AREA_INSERT_SERVICE_FAILED + "#\n" + 
//					"非法参数:\n" +
//					"[" + e.getMessage() + "]" +
//					"\n";
//			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
//		}
//
//		return basicReturnedJson.getMap();
//		
//	}
//	
//	// TODO
//	@RequestMapping(value = "/{aid}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
//	public Object getGeroAreaAndSubareas(
//			@PathVariable("gid") int geroId,
//			@PathVariable("aid") int areaId,
//			@RequestParam(value="sub_level", required=false) String subLevel
//			) {
//					
//		return null;
//		
//	}
//}
