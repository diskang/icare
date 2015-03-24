/**
 * @Package com.sjtu.icare.modules.gero.persistence
 * @Description TODO
 * @date Mar 24, 2015 5:27:04 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.gero.persistence;

import java.util.List;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.gero.entity.GeroEntity;

@MyBatisDao
public interface GeroDAO {

	/**
	 * @Title getGeros
	 * @Description TODO
	 * @param @param geroEntity
	 * @param @return
	 * @return List<GeroEntity>
	 * @throws
	 */
	List<GeroEntity> getGeros(GeroEntity geroEntity);

}
