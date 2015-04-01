package com.sjtu.icare.modules.sys.persistence;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sjtu.icare.common.persistence.CrudMapper;
import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.sys.entity.User;

/**
 * 用户DAO接口
 * 
 * @author jty
 * @version 2015-03-03
 */
@MyBatisDao
public interface UserMapper extends CrudMapper<User> {

	/**
	 * 根据登录名称查询用户
	 * 
	 * @param username
	 * @return
	 */
	public User getByUsername(User user);

	/**
	 * 根据用户类别和用户ID查询用户
	 * 
	 * @param userType
	 *            , userId
	 * @return
	 */
	public User getByUserId(User user);

	// /**
	// * 查询全部用户数目
	// * @return
	// */
	// public long findAllCount(User user);

	/**
	 * 更新用户密码
	 * 
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);

	/**
	 * 删除用户角色关联数据
	 * 
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);

	/**
	 * 插入用户角色关联数据
	 * 
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);

	/**
	 * 逻辑删除用户
	 * 
	 * @param id
	 * @param cancelDate
	 */
	void delete(@Param("id") int id, @Param("cancelDate") String cancelDate);// id,
																				// cancelDate

	/**
	 * @Title insertUser
	 * @Description TODO
	 * @param @param user
	 * @param @return
	 * @return Integer
	 * @throws
	 */
	public Integer insertUser(User user);

	/**
	 * @Title updateUser
	 * @Description TODO
	 * @param @param paramMap
	 * @return void
	 * @throws
	 */
	public void updateUser(Map<String, Object> paramMap);

}