package com.sjtu.icare.modules.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sjtu.icare.common.security.Digests;
import com.sjtu.icare.common.service.BaseService;
import com.sjtu.icare.common.utils.DateUtils;
import com.sjtu.icare.common.utils.Encodes;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.persistence.UserMapper;
import com.sjtu.icare.modules.sys.utils.security.SystemAuthorizingRealm;

@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService  {
	
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private SystemAuthorizingRealm systemRealm;
	

	//-- User Service --//
	
	public User getUserByUsername(String username) {
		return userMapper.findByUsername(username);
	}
	



	@Transactional(readOnly = false)
	public void saveUser(User user) {
		//need to support different types of user
		//add other info except account info
		
		userMapper.save(user);
		systemRealm.clearAllCachedAuthorizationInfo();
		
	}

	@Transactional(readOnly = false)
	public void deleteUser(int id) {

		userMapper.delete(id, DateUtils.getDate());
		
	}
	
	@Transactional(readOnly = false)
	public void updatePasswordById(int id, String username, String newPassword) {
		
		userMapper.updatePasswordById(id, newPassword);
		systemRealm.clearCachedAuthorizationInfo(username);
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