package com.sjtu.icare.modules.sys.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.aspectj.apache.bcel.generic.InstructionConstants.Clinit;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.DateUtil;
import com.aliyun.oss.model.OSSObject;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.OSSObjectUtils;
import com.sjtu.icare.common.web.rest.MediaTypes;

/**
 * 获取用户照片（For Stateless ）
 * @author garfieldjty
 *
 */
@RestController
@RequestMapping("/downloadObject")
public class DownloadObjectController {
	private static Logger logger = Logger.getLogger(DownloadObjectController.class);
	
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> downloadUserPhoto(
			@RequestParam("file_url") String filename,
			HttpServletRequest request,
			HttpServletResponse response) throws ClientException, ParseException{
		
		BasicReturnedJson result = new BasicReturnedJson();
		OSSObjectUtils oss = new OSSObjectUtils();
		result.addEntity(oss.getDownloadUrl(filename));
		return result.getMap();
	}
}
