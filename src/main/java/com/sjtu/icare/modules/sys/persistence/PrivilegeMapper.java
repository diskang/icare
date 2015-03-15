package com.sjtu.icare.modules.sys.persistence;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.common.persistence.CrudMapper;
import com.sjtu.icare.modules.sys.entity.Privilege;

/**
 * 菜单DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface PrivilegeMapper extends CrudMapper<Privilege> {
	
	/**
	 *  查找子列表
	 * @param privilege
	 * @return
	 */
	public List<Privilege> findByParentIdsLike(Privilege privilege);
	
	/**
	 *  查用户权限
	 * @param privilege
	 * @return
	 */
	public List<Privilege> findByUserId(Privilege privilege);
	
	/**
	 *  查角色权限
	 * @param privilege
	 * @return
	 */
	public List<Privilege> findByRoleId(Privilege privilege);
	
	/**
	 *  更新parent_ids
	 * @param privilege
	 * @return
	 */
	public int updateParentIds(Privilege privilege);
	
}