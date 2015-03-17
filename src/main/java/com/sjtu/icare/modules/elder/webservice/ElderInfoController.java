package com.sjtu.icare.modules.elder.webservice;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.icare.common.persistence.Page;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.web.rest.GeroBaseController;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.modules.elder.entity.ElderEntity;
import com.sjtu.icare.modules.elder.service.IElderInfoService;
import com.sjtu.icare.modules.sys.entity.Gero;

@RestController
@RequestMapping("/gero/{gid}/elder")
public class ElderInfoController extends GeroBaseController{
	
	private static Logger logger = Logger.getLogger(ElderInfoController.class);
	
	@Autowired
	private IElderInfoService elderInfoService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getElderList(
			@PathVariable("eid") int eid,
			@PathVariable("gid") int gid,
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="gender", required=false) String gender,
			@RequestParam(value="age", required=false) String age,
			@RequestParam(value="care_level", required=false) String care_level,
			@RequestParam(value="area_id", required=false) String areaId,
			@RequestParam(value="page", required=false) int page,
			@RequestParam(value="limit", required=false) int limit,
			@RequestParam(value="order_by", required=false) String orderByTag
			){
		
//		 检查用户是否有访问此养老院权限
//		checkGero(gid);
		
		BasicReturnedJson result = new BasicReturnedJson();
		
		Gero gero = getGeroFromId(gid);
		
		Page<ElderEntity> elderPage = new Page<ElderEntity>(page,limit);
		elderPage = setOrderBy(elderPage,orderByTag);
		
		return "";
	}
	
	
	
}
