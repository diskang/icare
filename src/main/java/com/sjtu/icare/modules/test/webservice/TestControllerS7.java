/**
 * @Package com.sjtu.icare.modules.test.webservice
 * @Description TODO
 * @date Mar 10, 2015 4:32:28 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.test.webservice;

import java.util.List;

import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sjtu.icare.common.beanvalidator.BeanValidators;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.modules.elder.webservice.ElderTemperatureRestController;
import com.sjtu.icare.modules.gero.entity.GeroAreaEntity;
import com.sjtu.icare.modules.gero.service.impl.GeroAreaService;
import com.sjtu.icare.modules.test.entity.TestEntity;

@RestController
@RequestMapping("/test/wangqi")
public class TestControllerS7 {
	
	@Autowired
	private Validator validator;
	@Autowired
	private GeroAreaService geroAreaService;
	
	private static Logger logger = Logger.getLogger(ElderTemperatureRestController.class);

	@RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Object testMethod(@RequestBody String json) {
		GeroAreaEntity queryGeroAreaEntity = new GeroAreaEntity();
		queryGeroAreaEntity.setGeroId(1);
		queryGeroAreaEntity.setId(1);
		List<GeroAreaEntity> result = geroAreaService.getGeroAreas(queryGeroAreaEntity);
		GeroAreaEntity result2 = geroAreaService.getGeroArea(queryGeroAreaEntity);
		
		return "test";
	}

}
