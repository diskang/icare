/**
 * @Package com.sjtu.icare.common.utils
 * @Description TODO
 * @date Mar 9, 2015 8:58:31 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.common.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.sjtu.icare.common.exception.BeanToMapException;
import com.sjtu.icare.modules.test.entity.TestEntity;

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
	
	/**
	 * 
	 * @Title beanToMap
	 * @Description 只能浅转化，对于嵌套的类型无法转化成 Map
	 * @param @param bean
	 * @param @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" }) 
	public static Map<String, Object> beanToMap(Object bean) {
		try {
			Class type = bean.getClass(); 
			Map returnMap = new HashMap(); 
			BeanInfo beanInfo = Introspector.getBeanInfo(type); 
			
			PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors(); 
			for (int i = 0; i< propertyDescriptors.length; i++) { 
				PropertyDescriptor descriptor = propertyDescriptors[i]; 
				String propertyName = descriptor.getName(); 
				if (!propertyName.equals("class")) { 
					Method readMethod = descriptor.getReadMethod(); 
					Object result = readMethod.invoke(bean, new Object[0]); 
					if (result != null) { 
						returnMap.put(propertyName, result); 
					} else { 
						returnMap.put(propertyName, ""); 
					} 
				}
			} 
			return returnMap; 
			
		} catch(Exception e) {
			
			return null;
		}
	}

	/**
     * 将一个 Map 对象转化为一个 JavaBean
     * @param type 要转化的类型
     * @param map 包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InstantiationException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */ 
    @SuppressWarnings("rawtypes") 
    public static Object convertMap(Class type, Map map) 
            throws IntrospectionException, IllegalAccessException, 
            InstantiationException, InvocationTargetException { 
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性 
        Object obj = type.newInstance(); // 创建 JavaBean 对象 
 
        // 给 JavaBean 对象的属性赋值 
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors(); 
        for (int i = 0; i< propertyDescriptors.length; i++) { 
            PropertyDescriptor descriptor = propertyDescriptors[i]; 
            String propertyName = descriptor.getName(); 
 
            if (map.containsKey(propertyName)) { 
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。 
                Object value = map.get(propertyName); 
 
                Object[] args = new Object[1]; 
                args[0] = value; 
 
                descriptor.getWriteMethod().invoke(obj, args); 
            } 
        } 
        return obj; 
    } 
    

	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, IntrospectionException {
//		TestEntity testEntity = new TestEntity();
//		Map<String, Object> map = beanToMap(testEntity);
//		System.out.println(map);
		TestEntity testEntity = new TestEntity();
		Map<String, Object> map = beanToMap(testEntity);
		System.out.println(map);
	}
}
