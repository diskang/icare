package com.sjtu.icare.modules.elder.service;

import com.sjtu.icare.common.persistence.Page;
import com.sjtu.icare.modules.elder.entity.ElderEntity;

public interface IElderInfoService {

	ElderEntity getElderEntity(ElderEntity elderEntity);
	
	public Page<ElderEntity> findElder(Page<ElderEntity> page, ElderEntity elder);
	
	
}
