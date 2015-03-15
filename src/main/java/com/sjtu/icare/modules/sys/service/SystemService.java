package com.sjtu.icare.modules.sys.service;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sjtu.icare.common.security.Digests;
import com.sjtu.icare.common.service.BaseService;
import com.sjtu.icare.common.utils.DateUtils;
import com.sjtu.icare.common.utils.Encodes;
import com.sjtu.icare.modules.sys.entity.Gero;
import com.sjtu.icare.modules.sys.entity.Privilege;
import com.sjtu.icare.modules.sys.entity.Role;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.persistence.GeroMapper;
import com.sjtu.icare.modules.sys.persistence.PrivilegeMapper;
import com.sjtu.icare.modules.sys.persistence.RoleMapper;
import com.sjtu.icare.modules.sys.persistence.UserMapper;
import com.sjtu.icare.modules.sys.utils.security.SystemAuthorizingRealm;
import com.sjtu.icare.modules.sys.utils.UserUtils;
import com.sjtu.icare.modules.sys.web.TestController;
import com.sjtu.icare.common.persistence.Page;
import com.sjtu.icare.common.utils.CacheUtils;
import com.sjtu.icare.common.service.ServiceException;
import com.sjtu.icare.common.utils.StringUtils;

@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService  {
	private static final Logger logger = Logger.getLogger(SystemService.class);
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private PrivilegeMapper privilegeMapper;
	@Autowired
	private GeroMapper geroMapper;
//	@Autowired
//	private Session sessionDao;
	@Autowired
	private SystemAuthorizingRealm systemRealm;
	
//	public SessionDAO getSessionDao() {
//		return sessionDao;
//	}
	
	
	
	//-- User Service --//
	
	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	public User getUser(int id) {
		return UserUtils.get(id);
	}
	
	/**
	 * 根据登录名获取用户
	 * @param username
	 * @return
	 */
	public User getUserByUsername(String username) {
		logger.debug(username);
		User u = UserUtils.getByLoginName(username);
		return u;
	}
	
	/**
	 * 根据user_type和user_id获取用户
	 * @param user_type, userId
	 * @return
	 */
	public User getUserByUserTypeAndUserId(int userType, int userId) {
		logger.debug(userType);
		logger.debug(userId);
		User u = UserUtils.getByUserId(userType,userId);
		return u;
	}
	
	public Page<User> findUser(Page<User> page, User user) {
//		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
//		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		// 设置分页参数
		user.setPage(page);
		// 执行分页查询
		page.setList(userMapper.findList(user));
		return page;
	}
	
	/**
	 * 无分页查询人员列表
	 * @param user
	 * @return
	 */
	public List<User> findUser(User user){
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
//		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		List<User> list = userMapper.findList(user);
		return list;
	}
	
//	/**
//	 * 通过部门ID获取用户列表，仅返回用户id和name（树查询用户时用）
//	 * @param user
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public List<User> findUserByOfficeId(String officeId) {
//		List<User> list = (List<User>)CacheUtils.get(UserUtils.USER_CACHE);
//		if (list == null){
//			User user = new User();
//			CacheUtils.put(UserUtils.USER_CACHE, list);
//		}
//		return list;
//	}
	
	@Transactional(readOnly = false)
	public void saveUser(User user) {
		if (user.getId()<=0){
//			user.preInsert();
			userMapper.insert(user);
		}else{
			// 清除原用户机构用户缓存
			User oldUser = userMapper.get(user.getId());
			// 更新用户数据
//			user.preUpdate();
			userMapper.update(user);
		}
		if (user.getId()>0){
			// 更新用户与角色关联
			userMapper.deleteUserRole(user);
			if (user.getRoleList() != null && user.getRoleList().size() > 0){
				userMapper.insertUserRole(user);
			}else{
				throw new ServiceException(user.getLoginName() + "没有设置角色！");
			}
			// 将当前用户同步到Activiti
//			saveActivitiUser(user);
			// 清除用户缓存
			UserUtils.clearCache(user);
//			// 清除权限缓存
//			systemRealm.clearAllCachedAuthorizationInfo();
		}
	}
	
	@Transactional(readOnly = false)
	public boolean updateUserRoles(User user){
		if (user.getId()>0){
			// 更新用户与角色关联
			userMapper.deleteUserRole(user);
			if (user.getRoleList() != null && user.getRoleList().size() > 0){
				userMapper.insertUserRole(user);
			}else{
				throw new ServiceException(user.getLoginName() + "No role setted!");
			}
			// 将当前用户同步到Activiti
//			saveActivitiUser(user);
			// 清除用户缓存
			UserUtils.clearCache(user);
//			// 清除权限缓存
			systemRealm.clearAllCachedAuthorizationInfo();
			return true;
		}
		else {
			return false;
		}
	}
	
	@Transactional(readOnly = false)
	public void updateUserInfo(User user) {
//		user.preUpdate();
		userMapper.updateUserInfo(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void deleteUser(User user) {
		if (user != null) {
			user.setCancelDate((new java.sql.Date ( new Date().getTime())));
			userMapper.delete(user);
			// 同步到Activiti
//			deleteActivitiUser(user);
			// 清除用户缓存
			UserUtils.clearCache(user);
			// 清除权限缓存
			systemRealm.clearAllCachedAuthorizationInfo();
		}		
	}
	
	@Transactional(readOnly = false)
	public void updatePasswordById(int id, String loginName, String newPassword) {
		User user = new User(id);
		user.setPassword(entryptPassword(newPassword));
		userMapper.updatePasswordById(user);
		// 清除用户缓存
		user.setLoginName(loginName);
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void updatePasswordById(User user, String newPassword) {
		user.setPassword(entryptPassword(newPassword));
		userMapper.updatePasswordById(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
//		// 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
//	/**
//	 * 获得活动会话
//	 * @return
//	 */
//	public Collection<Session> getActiveSessions(){
//		return sessionDao.getActiveSessions(false);
//	}
	
		
//	public User getUserByUsername(String username) {
//		return userMapper.findByUsername(username);
//	}
	

//	@Transactional(readOnly = false)
//	public void saveUser(User user) {
//		//need to support different types of user
//		//add other info except account info
//		
//		userMapper.save(user);
//		systemRealm.clearAllCachedAuthorizationInfo();
//		
//	}

//	@Transactional(readOnly = false)
//	public void deleteUser(int id) {
//		userMapper.delete(id, DateUtils.getDate());
//	}
	
	@Transactional(readOnly = true)
	public Page<Role> getRolePageFromUserId (Page<Role> page, User user) {
		Role role = new Role(user);
		role.setPage(page);
		page.setList(roleMapper.findList(role));
		return page;
	}
	
//	@Transactional(readOnly = false)
//	public void updatePasswordById(int id, String username, String newPassword) {
//		
//		userMapper.updatePasswordById(id, newPassword);
//		systemRealm.clearCachedAuthorizationInfo(username);
//	}
	
	@Transactional(readOnly = true)
	public Gero getGeroById (Gero gero){
		return geroMapper.getGero(gero.getId());
	}
	
	@Transactional(readOnly = true)
	public Page<Role> getRolePageFromGeroId (Page<Role> page, Gero gero) {
		Role role = new Role();
		role.setGeroId(gero.getId());
		role.setPage(page);
		page.setList(roleMapper.findAllList(role));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void insertGeroRole (Role role) {
		roleMapper.insert(role);
	}
	
	@Transactional(readOnly = true)
	public Role getRoleByNameAndGero (Role role) {
		return roleMapper.getByNameAndGero(role);
	}
	
	@Transactional(readOnly = true)
	public Role getRoleById (Role role) {
		role = roleMapper.get(role);
		return role;
	}
	
	@Transactional(readOnly = true)
	public Role getPrivilegeListByRole (Role role) {
		Privilege privilege = new Privilege();
		privilege.setRoleId(role.getId());
		role.setPrivilegeList(privilegeMapper.findByRoleId(privilege));
		return role;
	}
	
	@Transactional(readOnly = false)
	public void updateGeroRole (Role role) {
		roleMapper.update(role);
	}
	
	@Transactional(readOnly = false)
	public void deleteGeroRole (Role role) {
		roleMapper.delete(role);
	}
	
	@Transactional(readOnly = true)
	public Privilege getPrivilegeById (int id) {
		return privilegeMapper.get(id);
	}
	
	@Transactional(readOnly = false)
	public void insertRolePrivilege (Role role) {
		roleMapper.insertRolePrivilege(role);
	}
	
	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
	}
	
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
	}
	
}