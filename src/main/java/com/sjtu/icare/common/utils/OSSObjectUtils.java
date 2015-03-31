package com.sjtu.icare.common.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.common.utils.DateUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectResult;
import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.sys.web.UploadController;

/**
 * 上传图片工具类
 * @author garfieldjty
 *
 */
public class OSSObjectUtils {  
	
	private static Logger logger = Logger.getLogger(UploadUtils.class);
	
	private static String key = "iwa1cq1kHCGjc0KW";
	private static String secret = "toL6dPkxWjazEkbZuqIHr3vKIcnshJ";
	private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
	private static String bucket = "garfieldjty-test";
	private static Long expireTime = (long) 3600000;
	
//	private static String basicPath = "userfiles";//上传根目录
	private static String postfix = ".jpg";//文件后缀名
	private String innerPath;//根目录内部地址
	private String filename;//文件名
    private String url;//访问URL
  
    /**
     * 
     * @param file
     * @param innerPath
     * @param filename
     * @param type
     * @return
     */
    public Map<String, String> uploadFile(MultipartFile file, String innerPath, String  filename, String type)  {  
		
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
		this.url = innerPath+filename+postfix;
		logger.debug(url);		
		
		OSSClient client = new OSSClient(endpoint, key, secret);
		
		URL genUrl = null;
		
		try {
			InputStream content = file.getInputStream();
			
			ObjectMetadata meta = new ObjectMetadata();
			
			meta.setContentType(file.getContentType());
			meta.addUserMetadata("date", DateUtils.getDate());
			
			PutObjectResult putResult = client.putObject(bucket, url, content, meta);
			
			Date expire = new Date();
			expire.setTime(expire.getTime()+expireTime);
			genUrl = client.generatePresignedUrl(bucket, url, expire);
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.PHOTO_UPLOAD_SERVICE_ERROR,
					"[name=" +filename + "]" );
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		Map<String, String> urls = new HashMap<String, String>();
		urls.put("url", url);
		urls.put("gen_url", genUrl.toString());
		return urls;
    }
    
    public Map<String, String> getPostParam(String fileUrl) {
		Map<String, String> postParams = new HashMap<String, String>();
		
		OSSClient client = new OSSClient(endpoint, key, secret);
    	Date expire = new Date();
		expire.setTime(expire.getTime()+expireTime);
		PolicyConditions policyConds = new PolicyConditions();
		policyConds.addConditionItem("bucket", bucket);
		policyConds.addConditionItem(MatchMode.Exact, PolicyConditions.COND_KEY, fileUrl);
		String postPolicy = client.generatePostPolicy(expire, policyConds);
		byte[] binaryData = null;
		try {
			binaryData = postPolicy.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			String message = ErrorConstants.format(ErrorConstants.PHOTO_UPLOAD_SERVICE_ERROR,
					"[name=" +filename + "]" );
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
		String encodedPolicy = BinaryUtil.toBase64String(binaryData);
		String postSignature = client.calculatePostSignature(postPolicy);
		
		postParams.put("OSSAccessKeyId", key);
		postParams.put("policy", encodedPolicy);
		postParams.put("signature", postSignature);
		postParams.put("key", fileUrl);
		
		return postParams;
	}
    
    
    
    public String getDownloadUrl(String url) {
    	
    	OSSClient client = new OSSClient(endpoint, key, secret);
    	Date expire = new Date();
		expire.setTime(expire.getTime()+expireTime);
    	URL genUrl = client.generatePresignedUrl(bucket, url, expire);
    	
		return genUrl.toString();
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
    
	public static String getPostfix() {
		return postfix;
	}

	public static void setPostfix(String postfix) {
		OSSObjectUtils.postfix = postfix;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
    
    
}  