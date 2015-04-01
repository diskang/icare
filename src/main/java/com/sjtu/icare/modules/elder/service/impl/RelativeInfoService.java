/**
 * @Description TODO
 * @Author Wang Qi
 * @Date Mar 30, 2015
 */
package com.sjtu.icare.modules.elder.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjtu.icare.common.config.CommonConstants;
import com.sjtu.icare.modules.elder.entity.RelativeEntity;
import com.sjtu.icare.modules.elder.persistence.RelativeDAO;
import com.sjtu.icare.modules.elder.service.IRelativeInfoService;
import com.sjtu.icare.modules.sys.entity.User;

@Service
public class RelativeInfoService implements IRelativeInfoService {
	@Autowired
	private RelativeDAO relativeDAO;
	
	
	@Override
	public RelativeEntity getRelative(RelativeEntity relativeEntity) {
		return relativeDAO.getRelative(relativeEntity);
	}

	@Override
	public List<User> getUsersOfRelatives(User user) {
		user.setUserType(CommonConstants.RELATIVE_TYPE);
		return user.getPage().setList(relativeDAO.getUsersOfRelatives(user)).getList();
	}

	@Override
	public Integer insertRelative(RelativeEntity relativeEntity) {
		relativeDAO.insertRelative(relativeEntity);
		return (Integer) relativeEntity.getId();
	}

	@Override
	public void updateRelative(RelativeEntity relativeEntity) {
		relativeDAO.updateRelative(relativeEntity);
	}

	@Override
	public void deleteRelative(RelativeEntity relativeEntity) {
		relativeDAO.deleteRelative(relativeEntity);
		
	}




}
