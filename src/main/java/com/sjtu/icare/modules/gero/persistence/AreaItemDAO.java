/**
 * @Package com.sjtu.icare.modules.gero.persistence
 * @Description TODO
 * @date Mar 9, 2015 5:18:57 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.gero.persistence;

import java.util.List;
import java.util.Map;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.gero.entity.CareItemEntity;

@MyBatisDao
public interface AreaItemDAO {

	CareItemEntity getCareItemEntityById(Map<String, Object> careItemEntity);
	
	List<CareItemEntity> getCareItemEntitiesByGeroid(Map<String, Object> careItemEntity);
	
	List<CareItemEntity> getCareItemEntitiesByLevel(Map<String, Object> careItemEntity);
}
