package com.sjtu.icare.modules.sys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sjtu.icare.common.persistence.Page;
import com.sjtu.icare.common.security.Digests;
import com.sjtu.icare.common.service.BaseService;
import com.sjtu.icare.common.service.ServiceException;
import com.sjtu.icare.common.utils.DateUtils;
import com.sjtu.icare.common.utils.Encodes;
import com.sjtu.icare.common.utils.MapListUtils;
import com.sjtu.icare.modules.sys.entity.Gero;
import com.sjtu.icare.modules.sys.entity.Privilege;
import com.sjtu.icare.modules.sys.entity.Role;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.persistence.GeroMapper;
import com.sjtu.icare.modules.sys.persistence.PrivilegeMapper;
import com.sjtu.icare.modules.sys.persistence.RoleMapper;
import com.sjtu.icare.modules.sys.persistence.UserMapper;
import com.sjtu.icare.modules.sys.utils.UserUtils;
import com.sjtu.icare.modules.sys.utils.security.SystemAuthorizingRealm;

/**
 * 系统服务（包含用户、角色、权限管理）
 * 
 * @author garfieldjty
 *
 */
@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService {
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
	@Autowired
	private SystemAuthorizingRealm systemRealm;

	// -- User Service --//

	/**
	 * 获取用户
	 * 
	 * @param id
	 * @return
	 */
	public User getUser(int id) {
		return UserUtils.get(id);
	}

	/**
	 * 根据登录名获取用户
	 * 
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
	 * 
	 * @param user_type
	 *            , userId
	 * @return
	 */
	public User getUserByUserTypeAndUserId(int userType, int userId) {
		logger.debug(userType);
		logger.debug(userId);
		User u = UserUtils.getByUserId(userType, userId);
		return u;
	}

	/**
	 * 分页查询user列表
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public Page<User> findUser(Page<User> page, User user) {
		// 设置分页参数
		user.setPage(page);
		// 执行分页查询
		page.setList(userMapper.findList(user));
		return page;
	}

	/**
	 * 查询user列表不分页
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public List<User> findUserList(User user) {
		return userMapper.findList(user);
	}

	/**
	 * 无分页查询人员列表
	 * 
	 * @param user
	 * @return
	 */
	public List<User> findUser(User user) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		// user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(),
		// "o", "a"));
		List<User> list = userMapper.findList(user);
		return list;
	}

	/**
	 * 更新用户
	 * 
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void saveUser(User user) {
		if (user.getId() <= 0) {
			userMapper.insert(user);
		} else {
			// 清除原用户机构用户缓存
			User oldUser = userMapper.get(user.getId());
			// 更新用户数据
			userMapper.update(user);
		}
		if (user.getId() > 0) {
			// 更新用户与角色关联
			userMapper.deleteUserRole(user);
			if (user.getRoleList() != null && user.getRoleList().size() > 0) {
				userMapper.insertUserRole(user);
			} else {
				throw new ServiceException(user.getLoginName() + "没有设置角色！");
			}
			// 清除用户缓存
			UserUtils.clearCache(user);
			// 清除权限缓存
			systemRealm.clearAllCachedAuthorizationInfo();
		}
	}

	/**
	 * 更新用户角色
	 */
	@Transactional(readOnly = false)
	public boolean updateUserRoles(User user) {
		if (user.getId() > 0) {
			// 更新用户与角色关联
			userMapper.deleteUserRole(user);
			if (user.getRoleList() != null && user.getRoleList().size() > 0) {
				userMapper.insertUserRole(user);
			} 
			// 清除用户缓存
			UserUtils.clearCache(user);
			// 清除权限缓存
			systemRealm.clearAllCachedAuthorizationInfo();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 更新用户基本信息
	 * 
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void updateUserInfo(User user) {
		userMapper.updateUserInfo(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
	}

	/**
	 * 逻辑删除用户
	 * 
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void deleteUser(User user) {
		if (user != null) {
			user.setCancelDate(DateUtils.formatDate(new Date()));
			userMapper.delete(user);
			// 清除用户缓存
			UserUtils.clearCache(user);
			// 清除权限缓存
			systemRealm.clearAllCachedAuthorizationInfo();
		}
	}

	/**
	 * 更新密码
	 * 
	 * @param id
	 * @param loginName
	 * @param newPassword
	 */
	@Transactional(readOnly = false)
	public void updatePasswordById(int id, String loginName, String newPassword) {
		User user = new User(id);
		user.setPassword(entryptPassword(newPassword));
		userMapper.updatePasswordById(user);
		// 清除用户缓存
		user.setLoginName(loginName);
		UserUtils.clearCache(user);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
	}

	/**
	 * 更新密码
	 * 
	 * @param user
	 * @param newPassword
	 */
	@Transactional(readOnly = false)
	public void updatePasswordById(User user, String newPassword) {
		user.setPassword(entryptPassword(newPassword));
		userMapper.updatePasswordById(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
		// // 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
	}

	/**
	 * 取分页角色列表
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<Role> getRolePageFromUserId(Page<Role> page, User user) {
		Role role = new Role(user);
		role.setPage(page);
		page.setList(roleMapper.findList(role));
		return page;
	}

	/**
	 * 取养老院
	 * 
	 * @param gero
	 * @return
	 */
	@Transactional(readOnly = true)
	public Gero getGeroById(Gero gero) {
		return geroMapper.getGero(gero.getId());
	}

	/**
	 * 取养老院角色分页列表
	 * 
	 * @param page
	 * @param gero
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<Role> getRolePageFromGeroId(Page<Role> page, Gero gero) {
		Role role = new Role();
		role.setGeroId(gero.getId());
		role.setPage(page);
		page.setList(roleMapper.findAllList(role));
		return page;
	}

	/**
	 * 添加养老院角色
	 * 
	 * @param role
	 */
	@Transactional(readOnly = false)
	public void insertGeroRole(Role role) {
		roleMapper.insert(role);
	}

	/**
	 * 从角色名和养老院取角色
	 * 
	 * @param role
	 * @return
	 */
	@Transactional(readOnly = true)
	public Role getRoleByNameAndGero(Role role) {
		return roleMapper.getByNameAndGero(role);
	}

	/**
	 * ID取角色
	 * 
	 * @param role
	 * @return
	 */
	@Transactional(readOnly = true)
	public Role getRoleById(Role role) {
		role = roleMapper.get(role);
		return role;
	}

	/**
	 * 取角色权限
	 * 
	 * @param role
	 * @return
	 */
	@Transactional(readOnly = true)
	public Role getPrivilegeListByRole(Role role) {
		Privilege privilege = new Privilege();
		privilege.setRoleId(role.getId());
		role.setPrivilegeList(privilegeMapper.findByRoleId(privilege));
		return role;
	}

	/**
	 * 更新角色基本信息
	 * 
	 * @param role
	 */
	@Transactional(readOnly = false)
	public void updateGeroRole(Role role) {
		roleMapper.update(role);
	}

	/**
	 * 删除角色
	 * 
	 * @param role
	 */
	@Transactional(readOnly = false)
	public void deleteGeroRole(Role role) {
		roleMapper.delete(role);
	}

	/**
	 * 取权限
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public Privilege getPrivilegeById(int id) {
		return privilegeMapper.get(id);
	}

	/**
	 * 添加角色权限
	 * 
	 * @param role
	 */
	@Transactional(readOnly = false)
	public void insertRolePrivilege(Role role) {
		roleMapper.insertRolePrivilege(role);
	}

	/**
	 * 删除角色权限
	 * 
	 * @param role
	 */
	@Transactional(readOnly = false)
	public void deleteRolePrivilege(Role role, Privilege privilege) {
		List<Privilege> deletePrivileges = new ArrayList<Privilege>();
		deletePrivileges.add(privilege);
		privilege.setParentIds(privilege.getParentIds() + privilege.getId()
				+ ',');
		deletePrivileges.addAll(privilegeMapper.findByParentIdsLike(privilege));
		role.setPrivilegeList(deletePrivileges);
		roleMapper.deleteRolePrivilege(role);
	}

	/**
	 * 获取权限列表
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Privilege> getPrivilegeList() {
		return privilegeMapper.findAllList(new Privilege());
	}

	/**
	 * 添加权限
	 * 
	 * @param privilege
	 */
	@Transactional(readOnly = false)
	public void insertPrivilege(Privilege privilege) {
		privilegeMapper.insert(privilege);
	}

	/**
	 * 更新权限信息
	 * 
	 * @param privilege
	 */
	@Transactional(readOnly = false)
	public void updatePrivilege(Privilege privilege) {
		privilegeMapper.update(privilege);
	}

	/**
	 * 删除权限
	 * 
	 * @param privilege
	 */
	@Transactional(readOnly = false)
	public void deletePrivilege(Privilege privilege) {
		privilege.setParentIds(privilege.getParentIds() + privilege.getId()
				+ ',');
		privilegeMapper.deleteChildrens(privilege);
		privilegeMapper.delete(privilege);
	}

	@Transactional(readOnly = false)
	public void updateRoleUser(Role role) {
		roleMapper.deleteRoleUser(role);
		roleMapper.insertRoleUser(role);
	}

	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt,
				HASH_INTERATIONS);
		return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
	}

	/**
	 * 验证密码
	 * 
	 * @param plainPassword
	 *            明文密码
	 * @param password
	 *            密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		byte[] salt = Encodes.decodeHex(password.substring(0, 16));
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt,
				HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)
				+ Encodes.encodeHex(hashPassword));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sjtu.icare.modules.staff.service.IStaffDataService#insertUser(com
	 * .sjtu.icare.modules.sys.entity.User)
	 */
	public Integer insertUser(User user) {
		userMapper.insertUser(user);
		return user.getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sjtu.icare.modules.staff.service.IStaffDataService#updateUser(com
	 * .sjtu.icare.modules.sys.entity.User)
	 */
	public void updateUser(User user) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(user);
		userMapper.updateUser(paramMap);
	}

	public User getUserByWechatId(String openId) {
		User u = UserUtils.getUserByWechatId(openId);
		return u;
	}

}