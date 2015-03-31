package com.sjtu.icare.modules.sys.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.aspectj.apache.bcel.generic.InstructionConstants.Clinit;
import org.springframework.http.HttpStatus;
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
import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.OSSObjectUtils;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;

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
	public void downloadUserPhoto(
			@RequestParam("file_url") String filename,
			HttpServletRequest request,
			HttpServletResponse response) throws ClientException, ParseException{
		
		BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;
        response.setContentType("image/jpeg");
        OSSObjectUtils ossUtils = new OSSObjectUtils();
        try {
			bis = new BufferedInputStream(ossUtils.getFileInputStream(filename));  
			bos = new BufferedOutputStream(response.getOutputStream());
	        byte[] buff = new byte[2048];  
	        int bytesRead;  
	        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
	            bos.write(buff, 0, bytesRead);  
	        }  
	        bis.close();  
	        bos.close(); 
		} catch (Exception e) {
			String message = ErrorConstants.format("download error ",
					"[name=" +filename + "]" );
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}  
	}
}
