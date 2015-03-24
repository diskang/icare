/**
 * @Package com.sjtu.icare.modules.gero.webservice
 * @Description TODO
 * @date Mar 24, 2015 5:11:14 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.gero.webservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.persistence.Page;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.web.rest.BasicController;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.elder.entity.ElderEntity;
import com.sjtu.icare.modules.gero.entity.GeroEntity;
import com.sjtu.icare.modules.gero.service.IGeroService;
import com.sjtu.icare.modules.sys.entity.User;

@RestController
@RequestMapping({"${api.web}/gero", "${api.service}/gero"})
public class GeroRestController extends BasicController {
	private static Logger logger = Logger.getLogger(GeroRestController.class);

	@Autowired
	private IGeroService geroService;
	
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getGeros(
			@RequestParam("page") int page,
			@RequestParam("rows") int rows,
			@RequestParam("sort") String sort
			) {
		
  		
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
	
	
}
