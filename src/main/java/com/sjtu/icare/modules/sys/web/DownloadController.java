package com.sjtu.icare.modules.sys.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sjtu.icare.common.web.rest.MediaTypes;

/**
 * 获取用户照片（For Stateless ）
 * @author garfieldjty
 *
 */
@RestController
@RequestMapping("/download")
public class DownloadController {
	private static Logger logger = Logger.getLogger(DownloadController.class);
	
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public void downloadUserPhoto(
			@RequestParam("file_url") String filename,
			HttpServletRequest request,
			HttpServletResponse response){
		BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;
        String userPhotoPath = request.getSession().getServletContext().getRealPath("/")+filename;
        long fileLength = new File(userPhotoPath).length();
        response.setContentType("image/jpeg");
        response.setHeader("Content-Length", String.valueOf(fileLength));
        try {
			bis = new BufferedInputStream(new FileInputStream(userPhotoPath));  
			bos = new BufferedOutputStream(response.getOutputStream());
	        byte[] buff = new byte[2048];  
	        int bytesRead;  
	        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
	            bos.write(buff, 0, bytesRead);  
	        }  
	        bis.close();  
	        bos.close(); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
