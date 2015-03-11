/**
 * @Package com.sjtu.icare.common.utils
 * @Description TODO
 * @date Mar 9, 2015 8:58:31 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class CommonUtils {
	public static final char UNDERLINE='_';
   
	public static String camelToUnderline(String param){
	    if (param==null || "".equals(param.trim())) {
    		return null;
	    }
	    
	    int len = param.length();
	    StringBuilder sb=new StringBuilder(len);
	    for (int i = 0; i < len; i++) {
	        char c = param.charAt(i);
	        if (Character.isUpperCase(c)){
	            sb.append(UNDERLINE);
	            sb.append(Character.toLowerCase(c));
	        } else {
	            sb.append(c);
	        }
	    }
	    return sb.toString();
	}
	
	public static String underlineToCamel(String param){
	    if (param == null || "".equals(param.trim())) {
    		return null;
	    }
	    
	    int len = param.length();
	    StringBuilder sb = new StringBuilder(len);
	    for (int i = 0; i < len; i++) {
	        char c=param.charAt(i);
	        if (c==UNDERLINE) {
	           if (++i < len) {
	               sb.append(Character.toUpperCase(param.charAt(i)));
	           }
	        } else {
	            sb.append(c);
	        }
	    }
	    return sb.toString();
	}
	
	/**
	 * 
	 * @Title convertMapWithCamelStyle
	 * @Description 将 key 是下划线风格的 Map 转化成 key 是驼峰风格的 Map。
	 * @param @param map
	 * @param @return
	 * @return Map<String,Object>
	 * @throws
	 */
	public static Map<String, Object> convertMapToCamelStyle(Map<String, Object> map) {
		if (map == null) 
			return null;
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		for (String key : map.keySet()) {
			String newKey = underlineToCamel(key);
			resultMap.put(newKey, null);
			Object value = map.get(key);
			if (value instanceof Map)
				resultMap.put(newKey, convertMapToCamelStyle((Map<String, Object>) value));
			else if (value instanceof List)
				resultMap.put(newKey, convertListToCamelStyle((List<Object>) value));
			else 
				resultMap.put(newKey, value);
		}
		
		return resultMap;
	}
	
	public static List<Object> convertListToCamelStyle(List<Object> list) {
		if (list == null) 
			return null;
		
		ArrayList<Object> resultList = new ArrayList<Object>();
		
		for (Object element : list) {
			if (element instanceof Map)
				resultList.add(convertMapToCamelStyle((Map<String, Object>) element));
			else if (element instanceof List)
				resultList.add(convertListToCamelStyle((List<Object>) element));
			else 
				resultList.add(element);
	
		}
		
		return resultList;
	}

//	public static void main(String[] args) {
//		
//		String jsonString = "{\"parent_id\" : 1, \"my_code\" : \"3344\", \"list\" : [{\"dell_computer\": \"dell\"}, {\"mac_book\" : \"apple\"}, 1, \"3\"]}";
//		Map<String, Object> map = (Map<String, Object>) JSON.parse(jsonString);
//		Map<String, Object> resultMap = convertMapToCamelStyle(map);
//		System.out.println(resultMap);
//	}
}
