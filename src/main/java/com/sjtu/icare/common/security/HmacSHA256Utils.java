package com.sjtu.icare.common.security;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import com.sjtu.icare.common.utils.MapKeyComparator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HmacSHA256Utils {
	private static final Logger logger = Logger.getLogger(HmacSHA256Utils.class);
    public static String digest(String key, String content) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] secretByte = key.getBytes("utf-8");
            byte[] dataBytes = content.getBytes("utf-8");

            SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");
            mac.init(secret);

            byte[] doFinal = mac.doFinal(dataBytes);
            byte[] hexB = new Hex().encode(doFinal);
            return new String(hexB, "utf-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String digest(String key, Map<String, ?> map) {
        StringBuilder s = new StringBuilder();
        map = sortMapByKey(map);
        for(Object values : map.values()) {
            if(values instanceof String[]) {
                for(String value : (String[])values) {
                    s.append(value);
                }
            } else if(values instanceof List) {
                for(String value : (List<String>)values) {
                    s.append(value);
                }
            } else {
                s.append(values);
            }
        }
        logger.debug("stringBulider:"+s.toString());
        return digest(key, s.toString());
    }
    
    
    /** 
     * 使用 Map按key进行排序 
     * @param map 
     * @return 
     */  
    public static Map<String, ?> sortMapByKey(Map<String, ?> map) {  
        if (map == null || map.isEmpty()) {  
            return null;  
        }  
        Map<String, Object> sortMap = new TreeMap<String, Object>(new MapKeyComparator());  
        sortMap.putAll(map);  
        return sortMap;  
    }  
}