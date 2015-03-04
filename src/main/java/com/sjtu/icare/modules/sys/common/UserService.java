//package com.sjtu.icare.modules.sys.common;
//
//import java.util.Map;
//
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.UnavailableSecurityManagerException;
//import org.apache.shiro.session.InvalidSessionException;
//import org.apache.shiro.subject.Subject;
//
//import com.google.common.collect.Maps;
//import com.sjtu.icare.common.service.BaseService;
//import com.sjtu.icare.common.utils.SpringContextHolder;
//import com.sjtu.icare.modules.sys.entity.User;
//import com.sjtu.icare.modules.sys.persistence.UserMapper;
//import com.sjtu.icare.modules.sys.utils.security.SystemAuthorizingRealm.UserPrincipal;
//
//
//
///**
// * 用户工具类
// * 一般网页访问不直接暴露SystemService给controller
// * 而是先访问缓存里的身份信息
// * 参考shiro相关
// * @author KangShiyong
// * @version 2015-2-15
// */
//public class UserService extends BaseService {
//
//	private static UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
//	
//
//	public static final String CACHE_USER = "user";
//	
//	
//	public static User getUser(){
//		User user = (User)getCache(CACHE_USER);
//		if (user == null){
//			try{
//				Subject subject = SecurityUtils.getSubject();
//				UserPrincipal principal = (UserPrincipal)subject.getPrincipal();
//				if (principal!=null){
//					user = userMapper.findByUsername(principal.getUsername());
//					putCache(CACHE_USER, user);
//				}
//			}catch (UnavailableSecurityManagerException e) {
//				
//			}catch (InvalidSessionException e){
//				
//			}
//		}
//		if (user == null){
//			user = new User();
//			try{
//				SecurityUtils.getSubject().logout();
//			}catch (UnavailableSecurityManagerException e) {
//				
//			}catch (InvalidSessionException e){
//				
//			}
//		}
//		return user;
//	}
//	
//	public static User getUser(boolean isRefresh){
//		if (isRefresh){
//			removeCache(CACHE_USER);
//		}
//		return getUser();
//	}
//
//	
//	
//	// ============== User Cache ==============
//	
//	public static Object getCache(String key) {
//		return getCache(key, null);
//	}
//	
//	public static Object getCache(String key, Object defaultValue) {
//		Object obj = getCacheMap().get(key);
//		return obj==null?defaultValue:obj;
//	}
//
//	public static void putCache(String key, Object value) {
//		getCacheMap().put(key, value);
//	}
//
//	public static void removeCache(String key) {
//		getCacheMap().remove(key);
//	}
//	
//	public static Map<String, Object> getCacheMap(){
//		Map<String, Object> map = Maps.newHashMap();
//		try{
//			Subject subject = SecurityUtils.getSubject();
//			UserPrincipal principal = (UserPrincipal)subject.getPrincipal();
//			return principal!=null?principal.getCacheMap():map;
//		}catch (UnavailableSecurityManagerException e) {
//			
//		}catch (InvalidSessionException e){
//			
//		}
//		return map;
//	}
//	
//}
