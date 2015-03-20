/**
 * @Package com.sjtu.icare.modules.elder.persistence
 * @Description TODO
 * @date Mar 20, 2015 3:55:10 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.elder.persistence;

import java.util.List;
import java.util.Map;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.elder.entity.ElderItemEntity;

@MyBatisDao
public interface ElderItemDAO {

	/**
	 * @Title getElderItems
	 * @Description TODO
	 * @param @param elderItemEntity
	 * @param @return
	 * @return List<ElderItemEntity>
	 * @throws
	 */
	List<ElderItemEntity> getElderItems(ElderItemEntity elderItemEntity);

	/**
	 * @Title insertElderItem
	 * @Description TODO
	 * @param @param paramMap
	 * @return void
	 * @throws
	 */
	void insertElderItem(Map<String, Object> paramMap);

	/**
	 * @return 
	 * @Title getElderItem
	 * @Description TODO
	 * @param @param paramMap
	 * @return void
	 * @throws
	 */
	ElderItemEntity getElderItem(Map<String, Object> paramMap);

	/**
	 * @Title updateElderItem
	 * @Description TODO
	 * @param @param paramMap
	 * @return void
	 * @throws
	 */
	void updateElderItem(Map<String, Object> paramMap);

	/**
	 * @Title deleteElderItem
	 * @Description TODO
	 * @param @param paramMap
	 * @return void
	 * @throws
	 */
	void deleteElderItem(Map<String, Object> paramMap);

}
