package com.sjtu.icare.modules.elder.service;

import java.util.List;
import java.util.Map;

import com.sjtu.icare.common.persistence.Page;
import com.sjtu.icare.modules.elder.entity.ElderEntity;
import com.sjtu.icare.modules.elder.entity.ElderItemEntity;
import com.sjtu.icare.modules.sys.entity.User;

public interface IElderInfoService {

	ElderEntity getElderEntity(ElderEntity elderEntity);
	
	public Page<ElderEntity> findElder(Page<ElderEntity> page, ElderEntity elder);

	/**
	 * @Title getAllElders
	 * @Description TODO
	 * @param @param user
	 * @param @return
	 * @return List<User>
	 * @throws
	 */
	List<User> getAllElders(User user);

	/**
	 * @Title insertElder
	 * @Description TODO
	 * @param @param postElderEntity
	 * @param @return
	 * @return Integer
	 * @throws
	 */
	Integer insertElder(ElderEntity elderEntity);

	/**
	 * @Title updateElder
	 * @Description TODO
	 * @param @param elderEntity
	 * @return void
	 * @throws
	 */
	void updateElder(ElderEntity elderEntity);

	/**
	 * @Title deleteElder
	 * @Description TODO
	 * @param @param postElderEntity
	 * @return void
	 * @throws
	 */
	void deleteElder(ElderEntity elderEntity);

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
	 * @param @param elderItemEntity
	 * @return void
	 * @throws
	 */
	void insertElderItem(ElderItemEntity elderItemEntity);

	/**
	 * @Title getElderItem
	 * @Description TODO
	 * @param @param queryElderItemEntity
	 * @param @return
	 * @return ElderItemEntity
	 * @throws
	 */
	ElderItemEntity getElderItem(ElderItemEntity elderItemEntity);

	/**
	 * @Title updateElderItem
	 * @Description TODO
	 * @param @param elderItemEntity
	 * @return void
	 * @throws
	 */
	void updateElderItem(ElderItemEntity elderItemEntity);

	/**
	 * @Title deleteElderItem
	 * @Description TODO
	 * @param @param postElderItemEntity
	 * @return void
	 * @throws
	 */
	void deleteElderItem(ElderItemEntity elderItemEntity);
	
	
}
