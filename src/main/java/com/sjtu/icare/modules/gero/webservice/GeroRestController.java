/**
 * @Package com.sjtu.icare.modules.gero.webservice
 * @Description TODO
 * @date Mar 24, 2015 5:11:14 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.gero.webservice;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"${api.web}/gero", "${api.service}/gero"})
public class GeroRestController {
	private static Logger logger = Logger.getLogger(GeroRestController.class);

	
	
	
}
