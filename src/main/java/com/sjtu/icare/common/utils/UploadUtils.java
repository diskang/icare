package com.sjtu.icare.common.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.sys.web.UploadController;

/**
 * 上传图片工具类
 * @author garfieldjty
 *
 */
public class UploadUtils {  
	
	private static Logger logger = Logger.getLogger(UploadUtils.class);
	
	private static String basicPath = "/userfiles";//上传根目录
	private static String postfix = ".jpg";//文件后缀名
	private String innerPath;//根目录内部地址
	private String filename;//文件名
	private String fullPath;//绝对地址
    private String url;//访问URL
  
    /**
     * 上传照片方法
     * @param file
     * @param request
     * @param innerPath
     * @param filename
     * @param type 目前支持head和icon的大小控制
     * @return
     */
    public Boolean uploadFile(MultipartFile file, HttpServletRequest request, String innerPath, String  filename, String type)  {  
		
    	//判断是否是jpg图片
    	if (!file.getContentType().equals("image/jpeg")) {
			String message = ErrorConstants.format(ErrorConstants.PHOTO_UPLOAD_TYPE_ERROR,
					"[type=" +file.getContentType() + "]" );
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
    	
    	//检查照片大小
		checkPictureValidate(file,type);
		
		//初始化
		this.innerPath = innerPath;
		this.filename = filename;
		this.url = basicPath+innerPath+filename+postfix;
		this.fullPath = request.getSession().getServletContext().getRealPath(basicPath+innerPath);
		logger.debug(fullPath);
		
		//若没有文件夹则建立相关文件夹
		File fullPathFile = new File(fullPath);
		if (!fullPathFile.exists()) {
			fullPathFile.mkdirs();
		}
		File source = new File(fullPath,filename+postfix);
		
		//写入文件
		try {
			file.transferTo(source);
			return true;
		}  catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.PHOTO_UPLOAD_SERVICE_ERROR,
					"[name=" +filename + "]" );
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
    }
    
    /**
     * 检查照片大小
     * @param file
     * @param type
     */
    private void checkPictureValidate(MultipartFile file, String type) {
    	long max_size = 0;//文件最大大小
    	int max_width = 0;//照片最大宽度
    	int max_height = 0;//照片最大高度
    	int max_rate = 0;//照片最大高宽比
    	
    	if (type.equals("head")) {
    		max_size = 1048576;
    		max_width = 500;
    		max_height = 500;
    		max_rate = 1;
		}else if (type.equals("icon")) {
			max_size = 204800;
    		max_width = 200;
    		max_height = 200;
		}
    	
    	BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(file.getInputStream());
		} catch (IOException e) {
			String message = ErrorConstants.format(ErrorConstants.PHOTO_UPLOAD_SERVICE_ERROR,
					"[name=" +filename + "]" );
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		
		if (file.getSize() > max_size && max_size != 0) {
			String message = ErrorConstants.format(ErrorConstants.PHOTO_UPLOAD_SIZE_ERROR,
					"[type=" + type + "]" + 
					"[size=" + file.getSize() + "]" );
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		float rate = (float)height/width;
		if ((width > max_width && max_width != 0) ||
				(height > max_height && max_height !=0) ||
				(rate < max_rate && max_rate != 0) ) {
			String message = ErrorConstants.format(ErrorConstants.PHOTO_UPLOAD_RESOLUTION_ERROR,
					"[type=" + type + "]" + 
					"["+"w:"+bufferedImage.getWidth()+"h:"+bufferedImage.getHeight()+ "]" );
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
	}
    
    // getter & setter
    
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