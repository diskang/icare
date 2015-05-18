package com.sjtu.icare.modules.wechat.webservice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.web.rest.BasicController;
import com.sjtu.icare.common.web.rest.MediaTypes;

/*
 * not used
 * use api in elder module
 * */
@RestController
@RequestMapping("${api.wechat}/elder")
public class WechatElderController extends BasicController{
//	private static Logger logger = Logger.getLogger(WechatElderController.class);
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getElderListByNameAndGero(
			HttpServletRequest request,
			@RequestParam(value="elder_id", required=false) Integer elderId,
			@RequestParam(value="name", required=false) String name,
			@RequestParam("page") int page,
			@RequestParam("rows") int rows,
			@RequestParam("sort") String sort
			) {
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		return basicReturnedJson.getMap();
	}
}
