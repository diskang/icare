package com.sjtu.icare.modules.sys.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
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

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.DateUtil;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.DateUtils;
import com.sjtu.icare.common.utils.OSSObjectUtils;
import com.sjtu.icare.common.utils.UploadUtils;
import com.sjtu.icare.common.web.rest.MediaTypes;

/**
 * 上传文件控制器
 * @author garfieldjty
 *
 */
@RestController
@RequestMapping("/uploadObject")
public class UploadObjectController {
	private static Logger logger = Logger.getLogger(UploadObjectController.class);
	
	/**
	 * 上传用户照片
	 * @param uid
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws ClientException 
	 */
	@RequestMapping(value = "/user/{uid}", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> uploadUserPhoto(
			@PathVariable("uid") int uid,
			@RequestParam("user_photo") MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response){
	        
		BasicReturnedJson result = new BasicReturnedJson();
		
		String innerPath = "user/";
		String filename = "user_"+uid+"_"+DateUtils.getDate();
		
		OSSObjectUtils uploadUtils = new OSSObjectUtils();
		
		Map<String, String> urls = uploadUtils.uploadFile(file, innerPath, filename, "head");
		
		Map<String, String> postParam = uploadUtils.getPostParam(innerPath + filename + ".jpg");
		
		result.addEntity(urls);
		result.addEntity(postParam);
		
		return result.getMap();
	}
	

	/**
	 * 上传养老院图标
	 * @param gid
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/gero/{gid}/CMS/icon", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> uploadGeroIcon(
			@PathVariable("gid") int gid,
			@RequestParam("gero_icon") MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response){
	        
		BasicReturnedJson result = new BasicReturnedJson();
		
		String innerPath = "gero/"+gid+"/CMS/icon/";
		String filename = "gero_"+gid+"_icon_"+DateUtils.getDate();
		
		OSSObjectUtils uploadUtils = new OSSObjectUtils();
		
		Map<String, String> urls = uploadUtils.uploadFile(file, innerPath, filename, "icon");
		
		result.addEntity(urls);
		
		return result.getMap();
	}
	
	/**
	 * 上传养老院照片
	 * @param gid
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/gero/{gid}/CMS/picture", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> uploadGeroPicture(
			@PathVariable("gid") int gid,
			@RequestParam("gero_picture") MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response){
	        
		BasicReturnedJson result = new BasicReturnedJson();
		
		String innerPath = "gero/"+gid+"/CMS/picture/";
		String filename = "gero_"+gid+"_picture_"+DateUtils.getDate();
		
		OSSObjectUtils uploadUtils = new OSSObjectUtils();
		
		Map<String, String> urls = uploadUtils.uploadFile(file, innerPath, filename, "");
		
		result.addEntity(urls);
		
		return result.getMap();
	}
	
	/**
	 * 上传职工基本表
	 * @param gid
	 * @param sid
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/gero/{gid}/document/staff/{sid}/basic", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> uploadStaffBasicForm(
			@PathVariable("gid") int gid,
			@PathVariable("sid") int sid,
			@RequestParam("basic_form") MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response){
	        
		BasicReturnedJson result = new BasicReturnedJson();
		
		String innerPath = "gero/document/"+gid+"/staff/basic/";
		String filename = "staff_"+sid+"_basic_"+DateUtils.getDate();
		
		OSSObjectUtils uploadUtils = new OSSObjectUtils();
		
		Map<String, String> urls = uploadUtils.uploadFile(file, innerPath, filename, "");
		
		result.addEntity(urls);
		
		return result.getMap();
	}
	
	/**
	 * 上传老人申请表
	 * @param gid
	 * @param eid
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/gero/{gid}/document/elder/{eid}/apply", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> uploadElderApplyForm(
			@PathVariable("gid") int gid,
			@PathVariable("eid") int eid,
			@RequestParam("apply_form") MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response){
	        
		BasicReturnedJson result = new BasicReturnedJson();
		
		String innerPath = "gero/document/"+gid+"/elder/apply/";
		String filename = "elder_"+eid+"_apply_"+DateUtils.getDate();
		
		OSSObjectUtils uploadUtils = new OSSObjectUtils();
		
		Map<String, String> urls = uploadUtils.uploadFile(file, innerPath, filename, "");
		
		result.addEntity(urls);
		
		return result.getMap();
	}
	
	/**
	 * 上传老人调查表
	 * @param gid
	 * @param eid
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/gero/{gid}/document/elder/{eid}/survey", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> uploadElderSurveyForm(
			@PathVariable("gid") int gid,
			@PathVariable("eid") int eid,
			@RequestParam("survey_form") MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response){
	        
		BasicReturnedJson result = new BasicReturnedJson();
		
		String innerPath = "gero/document/"+gid+"/elder/survey/";
		String filename = "elder_"+eid+"_survey_"+DateUtils.getDate();
		
		OSSObjectUtils uploadUtils = new OSSObjectUtils();
		
		Map<String, String> urls = uploadUtils.uploadFile(file, innerPath, filename, "");
		
		result.addEntity(urls);
		
		return result.getMap();
	}
	
	/**
	 * 上传老人审批表
	 * @param gid
	 * @param eid
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/gero/{gid}/document/elder/{eid}/assess", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> uploadElderAssessForm(
			@PathVariable("gid") int gid,
			@PathVariable("eid") int eid,
			@RequestParam("assess_form") MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response){
	        
		BasicReturnedJson result = new BasicReturnedJson();
		
		String innerPath = "gero/document/"+gid+"/elder/assess/";
		String filename = "elder_"+eid+"_assess_"+DateUtils.getDate();
		
		OSSObjectUtils uploadUtils = new OSSObjectUtils();
		
		Map<String, String> urls = uploadUtils.uploadFile(file, innerPath, filename, "");
		
		result.addEntity(urls);
		
		return result.getMap();
	}
	
	/**
	 * 上传老人跟踪表
	 * @param gid
	 * @param eid
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/gero/{gid}/document/elder/{eid}/track", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> uploadElderTrackForm(
			@PathVariable("gid") int gid,
			@PathVariable("eid") int eid,
			@RequestParam("track_form") MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response){
	        
		BasicReturnedJson result = new BasicReturnedJson();
		
		String innerPath = "gero/document/"+gid+"/elder/track/";
		String filename = "elder_"+eid+"_track_"+DateUtils.getDate();
		
		OSSObjectUtils uploadUtils = new OSSObjectUtils();
		
		Map<String, String> urls = uploadUtils.uploadFile(file, innerPath, filename, "");
		
		result.addEntity(urls);
		
		return result.getMap();
	}
}
