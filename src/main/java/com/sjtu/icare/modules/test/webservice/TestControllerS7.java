/**
 * @Package com.sjtu.icare.modules.test.webservice
 * @Description TODO
 * @date Mar 10, 2015 4:32:28 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.test.webservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.elder.webservice.ElderTemperatureRestController;
import com.sjtu.icare.modules.gero.service.impl.GeroAreaService;
import com.sjtu.icare.modules.op.entity.CareworkRecordEntity;
import com.sjtu.icare.modules.op.persistence.CareworkDAO;
import com.sjtu.icare.modules.op.service.IItemRecordService;
import com.sjtu.icare.modules.op.service.IItemService;
import com.sjtu.icare.modules.staff.entity.StaffEntity;
import com.sjtu.icare.modules.staff.service.impl.DutyCarerService;

@RestController
@RequestMapping("/test/wangqi")
public class TestControllerS7 {
	
	@Autowired
	private Validator validator;
	@Autowired
	private GeroAreaService geroAreaService;
	@Autowired
	private DutyCarerService dutyCarerService;
	@Autowired
	private CareworkDAO careworkDAO;
	@Autowired
	private IItemRecordService itemRecordService;
	
	private static Logger logger = Logger.getLogger(ElderTemperatureRestController.class);

	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object testMethod(
			@RequestParam(value="staff_id", required=false) Integer staffId,
			@RequestParam(value="elder_id", required=false) Integer elderId) {
	
		try {
			
			CareworkRecordEntity careworkRecordEntity = new CareworkRecordEntity();
			careworkRecordEntity.setCarerId(staffId);
			careworkRecordEntity.setElderId(elderId);
			List<CareworkRecordEntity> careworkRecordEntityList = itemRecordService.getLatestCareworkRecords(
					careworkRecordEntity);
			return careworkRecordEntityList;
			
		} catch(Exception e) {
			e.printStackTrace();
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.DUTY_CARER_ELDER_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
//		ElderEntity queryElderEntity = new ElderEntity();
//		queryElderEntity.setId(1);
//		List<StaffEntity> dutyCarers = dutyCarerService.getDutyCarerByElderIdAndDate(queryElderEntity, "2015-03-13");
		
	}

}
