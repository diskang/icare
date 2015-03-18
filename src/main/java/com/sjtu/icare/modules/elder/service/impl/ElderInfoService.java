package com.sjtu.icare.modules.elder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sjtu.icare.common.persistence.Page;
import com.sjtu.icare.modules.elder.entity.ElderEntity;
import com.sjtu.icare.modules.elder.persistence.ElderDAO;
import com.sjtu.icare.modules.elder.service.IElderInfoService;

@Service
@Transactional(readOnly = true)
public class ElderInfoService implements IElderInfoService{

	@Autowired
	private ElderDAO elderDao;
	
	@Override
	public ElderEntity getElderEntity(ElderEntity elderEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ElderEntity> findElder(Page<ElderEntity> page, ElderEntity elder) {
		// TODO Auto-generated method stub
		return null;
	}

}
