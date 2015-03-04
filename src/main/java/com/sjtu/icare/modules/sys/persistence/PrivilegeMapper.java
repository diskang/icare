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

	public List<Privilege> findByParentIdsLike(Privilege privilege);

	public List<Privilege> findByUserId(Privilege privilege);
	
	public int updateParentIds(Privilege privilege);
	
	public int updateSort(Privilege privilege);
	
}