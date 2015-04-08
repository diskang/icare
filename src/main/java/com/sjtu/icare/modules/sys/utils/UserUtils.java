package com.sjtu.icare.modules.sys.utils;

import java.util.List;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.google.common.collect.Lists;
import com.sjtu.icare.common.utils.SpringContextHolder;
import com.sjtu.icare.modules.sys.entity.Privilege;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.persistence.PrivilegeMapper;
import com.sjtu.icare.modules.sys.persistence.RoleMapper;
import com.sjtu.icare.modules.sys.persistence.UserMapper;
import com.sjtu.icare.common.utils.CacheUtils;
import com.sjtu.icare.modules.sys.entity.Role;
import com.sjtu.icare.modules.sys.utils.security.SystemAuthorizingRealm.UserPrincipal;
import com.sjtu.icare.modules.sys.utils.UserUtils;


/**
 * 用户工具类
 * @author jty
 * @version 2015-03-03
 */
public class UserUtils {
	private static final Logger logger = Logger.getLogger(UserUtils.class);
	private static UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
	private static RoleMapper roleMapper = SpringContextHolder.getBean(RoleMapper.class);
	private static PrivilegeMapper privilegeMapper = SpringContextHolder.getBean(PrivilegeMapper.class);
	
	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_ID_ = "id_";
	public static final String USER_CACHE_LOGIN_NAME_ = "ln";

	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_PRIVILEGE_LIST = "privilegeList";
	
	/**
	 * 根据ID获取用户
	 * @param id
	 * @return 取不到返回null
	 */
	public static User get(int id){
		User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
		if (user ==  null){
			user = userMapper.get(id);
			if (user == null){
				return null;
			}
			user.setRoleList(roleMapper.findList(new Role(user)));
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}
		return user;
	}
	
	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static User getByLoginName(String loginName){
		User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
		if (user == null){
			logger.debug("to get user");
			try {
				user = userMapper.getByUsername(new User(-1, loginName));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.debug("getted user");
			if (user == null){
				return null;
			}
			List<Role> roleList = roleMapper.findList(new Role(user));
			user.setRoleList(roleList);		
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}
		return user;
	}
	
	/**
	 * 根据user_type和user_id获取用户
	 * @param userType, userId
	 * @return 取不到返回null
	 */
	public static User getByUserId(int userType, int userId){
		logger.debug("to get user");
		User user = userMapper.getByUserId(new User(-1, userType, userId));
		logger.debug("getted user");
		if (user == null){
			return null;
		}
		List<Role> roleList = roleMapper.findList(new Role(user));
		user.setRoleList(roleList);		

		return user;
	}
	
	/**
	 * 清除当前用户缓存
	 */
	public static void clearCache(){
		removeCache(CACHE_ROLE_LIST);
		removeCache(CACHE_PRIVILEGE_LIST);
		UserUtils.clearCache(getUser());
	}
	
	/**
	 * 清除指定用户缓存
	 * @param user
	 */
	public static void clearCache(User user){
		CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName());
	}
	
	/**
	 * 获取当前用户
	 * @return 取不到返回 new User()
	 */
	public static User getUser(){
		UserPrincipal principal = getPrincipal();
		if (principal!=null){
			User user = get(principal.getId());
			if (user != null){
				return user;
			}
			return new User();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new User();
	}
	
	/**
	 * 获取当前用户角色列表
	 * @return
	 */
	public static List<Role> getRoleList(){
		@SuppressWarnings("unchecked")
		List<Role> roleList = (List<Role>)getCache(CACHE_ROLE_LIST);
		if (roleList == null){
			User user = getUser();
			if (user.isAdmin()){
				roleList = roleMapper.findAllList(new Role());
			}else{
				Role role = new Role();
//				role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user.getCurrentUser(), "o", "u"));
				roleList = roleMapper.findList(role);
			}
			putCache(CACHE_ROLE_LIST, roleList);
		}
		return roleList;
	}
	
	/**
	 * 获取当前用户授权菜单
	 * @return
	 */
	public static List<Privilege> getPrivilegeList(){
		@SuppressWarnings("unchecked")
		List<Privilege> privilegelList = (List<Privilege>)getCache(CACHE_PRIVILEGE_LIST);
		if (privilegelList == null){
			User user = getUser();
			if (user.isAdmin()){
				privilegelList = privilegeMapper.findAllList(new Privilege());
			}else{
				Privilege p = new Privilege();
				p.setUserId(user.getId());
				privilegelList = privilegeMapper.findByUserId(p);
			}
			putCache(CACHE_PRIVILEGE_LIST, privilegelList);
		}
		return privilegelList;
	}
	
	/**
	 * 获取用户授权菜单(Stateless)
	 * @return
	 */
	public static List<Privilege> getPrivilegeList(User u){
		List<Privilege> privilegelList = Lists.newArrayList();
		if (u.isAdmin()){
			privilegelList = privilegeMapper.findAllList(new Privilege());
		}else{
			Privilege p = new Privilege();
			p.setUserId(u.getId());
			privilegelList = privilegeMapper.findByUserId(p);
		}
		return privilegelList;
	}
	
	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	
	/**
	 * 获取当前登录者对象
	 */
	public static UserPrincipal getPrincipal(){
		try{
			Subject subject = SecurityUtils.getSubject();
			UserPrincipal principal = (UserPrincipal)subject.getPrincipal();
			if (principal != null){
				return principal;
			}
//			subject.logout();
		}catch (UnavailableSecurityManagerException e) {
			
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	public static Session getSession(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null){
				session = subject.getSession();
			}
			if (session != null){
				return session;
			}
//			subject.logout();
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	// ============== User Cache ==============
	
		public static Object getCache(String key) {
			return getCache(key, null);
		}
		
		public static Object getCache(String key, Object defaultValue) {
//			Object obj = getCacheMap().get(key);
			Object obj = getSession().getAttribute(key);
			return obj==null?defaultValue:obj;
		}

		public static void putCache(String key, Object value) {
//			getCacheMap().put(key, value);
			getSession().setAttribute(key, value);
		}

		public static void removeCache(String key) {
//			getCacheMap().remove(key);
			getSession().removeAttribute(key);
		}
}
