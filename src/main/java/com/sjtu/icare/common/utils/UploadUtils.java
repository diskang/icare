package com.sjtu.icare.common.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.sjtu.icare.modules.sys.web.UploadController;

public class UploadUtils {  
	
	private static Logger logger = Logger.getLogger(UploadUtils.class);
	
	private static String basicPath = "/uploadfiles";
	private static String postfix = ".jpg";
	private String innerPath;
	private String filename;
	private String fullPath;
    private String url;
  
    //文件上传  
    public Boolean uploadFile(MultipartFile file, HttpServletRequest request, String innerPath, String  filename)  {  
    	try {
    		if (!file.getContentType().equals("image/jpeg")) {
				throw new Exception();
			}
    		BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
    		logger.debug("w:"+bufferedImage.getWidth()+"h:"+bufferedImage.getHeight());
    		this.innerPath = innerPath;
    		this.filename = filename;
    		this.url = basicPath+innerPath+filename+postfix;
    		this.fullPath = request.getSession().getServletContext().getRealPath(basicPath+innerPath);
			logger.debug(fullPath);
			File fullPathFile = new File(fullPath);
			if (!fullPathFile.exists()) {
				fullPathFile.mkdirs();
			}
			File source = new File(fullPath,filename);   
			file.transferTo(source);
			return true;
		}  catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	return false;
    }

	public static String getBasicPath() {
		return basicPath;
	}

	public static void setBasicPath(String basicPath) {
		UploadUtils.basicPath = basicPath;
	}

	public static String getPostfix() {
		return postfix;
	}

	public static void setPostfix(String postfix) {
		UploadUtils.postfix = postfix;
	}

	public String getInnerPath() {
		return innerPath;
	}

	public void setInnerPath(String innerPath) {
		this.innerPath = innerPath;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
    
    
}  