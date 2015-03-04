package com.sjtu.icare.modules.sys.persistence;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.common.persistence.CrudMapper;

/**
 * 用户DAO接口
 * @author jty
 * @version 2015-03-03
 */
@MyBatisDao
public interface UserMapper extends CrudMapper<User>{

    User findByUsername(String username);

    //User findByUserId(int userId);//应该不会用id来查。

    void updatePasswordById(@Param("id")int id,@Param("password")String password);//id,password
    
    void save(User user);

    void delete(@Param("id")int id,@Param("cancelDate")String cancelDate);//id, cancelDate
    
    /**
	 * 根据登录名称查询用户
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(User user);
	
//	/**
//	 * 查询全部用户数目
//	 * @return
//	 */
//	public long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);
	
//	/**
//	 * 更新登录信息，如：登录IP、登录时间
//	 * @param user
//	 * @return
//	 */
//	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);
	
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);

}