/**
 * @Description TODO
 * @Author Wang Qi
 * @Date Aug 21, 2015
 */
package com.sjtu.icare.modules.admin.webservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.icare.common.config.VersionUrl;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.StringUtils;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;

@RestController
@RequestMapping("/versioncode_url")
public class VersioncodeController {

	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getUrl(
			@RequestParam(value="versioncode", required=true) String versioncode) {
		try {
			versioncode = versioncode.trim();
			BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
			
    		String url = VersionUrl.getUrl(versioncode);
    		if (!StringUtils.isBlank(url)) {
    			
    			Map<String, Object> resultMap = new HashMap<String, Object>();
    			
    			resultMap.put("url", url); 
    			resultMap.put("versioncode", versioncode); 
    			
    			basicReturnedJson.addEntity(resultMap);
    		}

            return basicReturnedJson.getMap();    
		} catch(Exception e) {
			e.printStackTrace();
			String otherMessage = "[" + e.getMessage() + "]";
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "");
		}
				
	}
	
}

