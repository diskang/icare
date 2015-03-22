package com.sjtu.icare.modules.elder.persistence;

import java.util.List;
import java.util.Map;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.elder.entity.ElderEntity;
import com.sjtu.icare.modules.sys.entity.User;

/**
 * @Description 老人数据的 Mapper
 * @author WangQi
 * @date 2015-03-06
 */

@MyBatisDao
public interface ElderDAO {

	ElderEntity getElderEntity(Map<String, Object> param);

	/**
	 * @Title getAllElders
	 * @Description TODO
	 * @param @param paramMap
	 * @param @return
	 * @return List<User>
	 * @throws
	 */
	List<User> getAllElders(User user);

	/**
	 * @Title insertElder
	 * @Description TODO
	 * @param @param elderEntity
	 * @return void
	 * @throws
	 */
	void insertElder(ElderEntity elderEntity);

	/**
	 * @Title updateElder
	 * @Description TODO
	 * @param @param paramMap
	 * @return void
	 * @throws
	 */
	void updateElder(Map<String, Object> paramMap);

	/**
	 * @Title deleteElder
	 * @Description TODO
	 * @param @param paramMap
	 * @return void
	 * @throws
	 */
	void deleteElder(Map<String, Object> paramMap);
	
}