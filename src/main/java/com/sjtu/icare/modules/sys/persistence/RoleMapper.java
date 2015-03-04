package com.sjtu.icare.modules.sys.persistence;


import org.apache.ibatis.annotations.Param;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.sys.entity.Role;
import com.sjtu.icare.common.persistence.CrudMapper;

/**
 * 角色DAO接口
 * @author jty
 * @version 2015-03-03
 */
@MyBatisDao
public interface RoleMapper extends CrudMapper<Role> {

	public Role getByName(Role role);
	
	public Role getByEnname(Role role);

	/**
	 * 维护角色与菜单权限关系
	 * @param role
	 * @return
	 */
	public int deleteRoleMenu(Role role);

	public int insertRoleMenu(Role role);

}