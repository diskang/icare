/**
 * @Package com.sjtu.icare.modules.test.webservice
 * @Description TODO
 * @date Mar 10, 2015 4:32:28 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.test.webservice;

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
import com.sjtu.icare.modules.test.entity.TestEntity;

@RestController
@RequestMapping("/test/wangqi")
public class TestControllerS7 {
	
	@Autowired
	private Validator validator;
	
	private static Logger logger = Logger.getLogger(ElderTemperatureRestController.class);

	@RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Object testMethod(@RequestBody String json) {
		
//		JSON.
		JSONObject jsonObject = JSON.parseObject(json);
//		System.out.println(jsonObject.toJSONString());
//		System.out.println("=======================");
		TestEntity testEntity = (TestEntity) JSONObject.toJavaObject(jsonObject, TestEntity.class);
//		BeanValidators.validateWithException(validator, testEntity);
//		System.out.println("" + testEntity);
		
		return testEntity;
	}

	
}
