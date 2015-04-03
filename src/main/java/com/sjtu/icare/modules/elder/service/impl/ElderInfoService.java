package com.sjtu.icare.modules.elder.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sjtu.icare.common.config.CommonConstants;
import com.sjtu.icare.common.persistence.Page;
import com.sjtu.icare.common.utils.MapListUtils;
import com.sjtu.icare.modules.elder.entity.ElderEntity;
import com.sjtu.icare.modules.elder.entity.ElderItemEntity;
import com.sjtu.icare.modules.elder.persistence.ElderDAO;
import com.sjtu.icare.modules.elder.persistence.ElderItemDAO;
import com.sjtu.icare.modules.elder.service.IElderInfoService;
import com.sjtu.icare.modules.sys.entity.User;

@Service
@Transactional(readOnly = true)
public class ElderInfoService implements IElderInfoService{

	@Autowired
	private ElderDAO elderDao;
	@Autowired
	private ElderItemDAO elderItemDao;
	
	@Override
	public ElderEntity getElderEntity(ElderEntity elderEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(elderEntity);
		return elderDao.getElderEntity(paramMap);
	}

	@Override
	public Page<ElderEntity> findElder(Page<ElderEntity> page, ElderEntity elder) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.elder.service.IElderInfoService#getAllElders(java.util.Map)
	 */
	@Override
	public List<User> getAllElders(User user) {
		user.setUserType(CommonConstants.ELDER_TYPE);
		return user.getPage().setList(elderDao.getAllElders(user)).getList();
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.elder.service.IElderInfoService#insertElder(com.sjtu.icare.modules.elder.entity.ElderEntity)
	 */
	@Override
	public Integer insertElder(ElderEntity elderEntity) {
		// Map<String, Object> paramMap = MapListUtils.beanToMap(staffEntity);
		elderDao.insertElder(elderEntity);
		return (Integer) elderEntity.getId();
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.elder.service.IElderInfoService#updateElder(com.sjtu.icare.modules.elder.entity.ElderEntity)
	 */
	@Override
	public void updateElder(ElderEntity elderEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(elderEntity);
		elderDao.updateElder(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.elder.service.IElderInfoService#deleteElder(com.sjtu.icare.modules.elder.entity.ElderEntity)
	 */
	@Override
	public void deleteElder(ElderEntity elderEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(elderEntity);
		elderDao.deleteElder(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.elder.service.IElderInfoService#getElderItems(com.sjtu.icare.modules.elder.entity.ElderItemEntity)
	 */
	@Override
	public List<ElderItemEntity> getElderItems(
			ElderItemEntity elderItemEntity) {
		return elderItemEntity.getPage().setList(elderItemDao.getElderItems(elderItemEntity)).getList();
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.elder.service.IElderInfoService#insertElderItem(com.sjtu.icare.modules.elder.entity.ElderItemEntity)
	 */
	@Override
	public void insertElderItem(ElderItemEntity elderItemEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(elderItemEntity);
		elderItemDao.insertElderItem(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.elder.service.IElderInfoService#getElderItem(com.sjtu.icare.modules.elder.entity.ElderItemEntity)
	 */
	@Override
	public ElderItemEntity getElderItem(ElderItemEntity elderItemEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(elderItemEntity);
		return elderItemDao.getElderItem(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.elder.service.IElderInfoService#updateElderItem(com.sjtu.icare.modules.elder.entity.ElderItemEntity)
	 */
	@Override
	public void updateElderItem(ElderItemEntity elderItemEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(elderItemEntity);
		elderItemDao.updateElderItem(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.elder.service.IElderInfoService#deleteElderItem(com.sjtu.icare.modules.elder.entity.ElderItemEntity)
	 */
	@Override
	public void deleteElderItem(ElderItemEntity elderItemEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(elderItemEntity);
		elderItemDao.deleteElderItem(paramMap);
	}

	@Override
	public ElderEntity getElderEntityByIdentityNo(String elderIdentityNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("identityNo", elderIdentityNo);
		return elderDao.getElderEntity(paramMap);
	}

}
