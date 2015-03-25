/**
 * @Package com.sjtu.icare.modules.op.service.impl
 * @Description TODO
 * @date Mar 17, 2015 3:54:37 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.op.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjtu.icare.common.utils.MapListUtils;
import com.sjtu.icare.modules.op.entity.AreaItemEntity;
import com.sjtu.icare.modules.op.entity.CareItemEntity;
import com.sjtu.icare.modules.op.persistence.AreaItemDAO;
import com.sjtu.icare.modules.op.persistence.CareItemDAO;
import com.sjtu.icare.modules.op.service.IItemService;

@Service
public class ItemService implements IItemService {

	@Autowired
	private CareItemDAO careItemDao;
	@Autowired
	private AreaItemDAO areaItemDao;
	
	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.op.service.IItemService#getCareItems(com.sjtu.icare.modules.op.entity.CareItemEntity)
	 */
	@Override
	public List<CareItemEntity> getCareItems(CareItemEntity careItemEntity) {
		return careItemEntity.getPage().setList(careItemDao.getCareItemEntitiesByGeroId(careItemEntity)).getList();
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.op.service.IItemService#getCareItem(com.sjtu.icare.modules.op.entity.CareItemEntity)
	 */
	@Override
	public CareItemEntity getCareItem(CareItemEntity careItemEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(careItemEntity);
		return careItemDao.getCareItemEntityById(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.op.service.IItemService#deleteCareItem(com.sjtu.icare.modules.op.entity.CareItemEntity)
	 */
	@Override
	public void deleteCareItem(CareItemEntity careItemEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(careItemEntity);
		careItemDao.deleteCareItem(paramMap);	
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.op.service.IItemService#insertCareItem(com.sjtu.icare.modules.op.entity.CareItemEntity)
	 */
	@Override
	public void insertCareItem(CareItemEntity careItemEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(careItemEntity);
		careItemDao.insertCareItem(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.op.service.IItemService#updateCareItem(com.sjtu.icare.modules.op.entity.CareItemEntity)
	 */
	@Override
	public void updateCareItem(CareItemEntity careItemEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(careItemEntity);
		careItemDao.updateCareItem(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.op.service.IItemService#getAreaItems(com.sjtu.icare.modules.op.entity.AreaItemEntity)
	 */
	@Override
	public List<AreaItemEntity> getAreaItems(AreaItemEntity areaItemEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(areaItemEntity);
		return areaItemDao.getAreaItems(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.op.service.IItemService#insertAreaItem(com.sjtu.icare.modules.op.entity.AreaItemEntity)
	 */
	@Override
	public void insertAreaItem(AreaItemEntity areaItemEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(areaItemEntity);
		areaItemDao.insertAreaItem(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.op.service.IItemService#getAreaItem(com.sjtu.icare.modules.op.entity.AreaItemEntity)
	 */
	@Override
	public AreaItemEntity getAreaItem(AreaItemEntity areaItemEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(areaItemEntity);
		return areaItemDao.getAreaItem(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.op.service.IItemService#updateAreaItem(com.sjtu.icare.modules.op.entity.AreaItemEntity)
	 */
	@Override
	public void updateAreaItem(AreaItemEntity areaItemEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(areaItemEntity);
		areaItemDao.updateAreaItem(paramMap);
		
	}

	/* (non-Javadoc)
	 * @see com.sjtu.icare.modules.op.service.IItemService#deleteAreaItem(com.sjtu.icare.modules.op.entity.AreaItemEntity)
	 */
	@Override
	public void deleteAreaItem(AreaItemEntity areaItemEntity) {
		Map<String, Object> paramMap = MapListUtils.beanToMap(areaItemEntity);
		areaItemDao.deleteAreaItem(paramMap);
	}

}
