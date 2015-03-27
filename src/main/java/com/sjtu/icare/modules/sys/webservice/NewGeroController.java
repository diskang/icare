package com.sjtu.icare.modules.sys.webservice;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.modules.sys.entity.Gero;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;

@RestController
@RequestMapping({"/newGero"})
public class NewGeroController {
	private static Logger logger = Logger.getLogger(NewGeroController.class);
	
	@Autowired
	private SystemService systemService;
	
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> newGero(){
			BasicReturnedJson result = new BasicReturnedJson();
			Gero gero = new Gero();
			
			User admin = new User();
			
			return result.getMap();
	}
}
