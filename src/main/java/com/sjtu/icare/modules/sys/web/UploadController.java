package com.sjtu.icare.modules.sys.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.DateUtils;
import com.sjtu.icare.common.utils.UploadUtils;
import com.sjtu.icare.common.web.rest.MediaTypes;

@RestController
@RequestMapping("/upload")
public class UploadController {
	private static Logger logger = Logger.getLogger(UploadController.class);
	
	@RequestMapping(value = "/user/{uid}", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> getGeroRoleList(
			@PathVariable("uid") int uid,
			@RequestParam("user_photo") MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response){
	        
		BasicReturnedJson result = new BasicReturnedJson();
		
		String innerPath = "/user/";
		String filename = "user_"+uid+"_"+DateUtils.getDate();
		
		UploadUtils uploadUtils = new UploadUtils();
		if( uploadUtils.uploadFile(file,request,innerPath,filename)){
			result.addEntity(uploadUtils.getUrl());
		}
		return result.getMap();
	}
}
