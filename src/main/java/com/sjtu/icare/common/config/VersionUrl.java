/**
 * @Description TODO
 * @Author Wang Qi
 * @Date Aug 21, 2015
 */
package com.sjtu.icare.common.config;

import java.util.Map;

import com.google.common.collect.Maps;
import com.sjtu.icare.common.utils.PropertiesLoader;

/**
 * @author sean_7
 *
 */
public class VersionUrl {

	private static Map<String, String> map = Maps.newHashMap();
	
	private static PropertiesLoader propertiesLoader = new PropertiesLoader("versioncode-url.properties");
	
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = propertiesLoader.getProperty(key);
			map.put(key, value);
		}
		return value;
	}

	public static String getUrl(String versioncode) {
		return getConfig(versioncode);
	}
	
}
