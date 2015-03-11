/**
 * @Package com.sjtu.icare.modules.gero.service
 * @Description TODO
 * @date Mar 9, 2015 5:16:11 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.gero.service;

import java.util.List;
import java.util.Map;

import com.sjtu.icare.modules.gero.entity.GeroAreaEntity;

public interface IGeroAreaService {

	/**
	 * @Title getGeroAreas
	 * @Description TODO
	 * @param @param paramsMap
	 * @param @return
	 * @return Object
	 * @throws
	 */
	List<GeroAreaEntity> getGeroAreas(int geroId);

	/**
	 * @Title insertGeroAreaRecord
	 * @Description TODO
	 * @param @param parentId
	 * @param @param type
	 * @param @param level
	 * @param @param name
	 * @return void
	 * @throws
	 */
	void insertGeroAreaRecord(Integer parentId, Integer type, Integer level,
			String name);
	
}