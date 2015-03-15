package com.sjtu.icare.modules.op.persisitence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.op.entity.ElderAudioRecordEntity;

/**
 * @Description 老人状况录音记录的 Mapper
 * @author lzl
 * @date 2015-03-13
 */

@MyBatisDao
public interface ElderAudioRecordDAO {

	ElderAudioRecordEntity getElderAudioRecordEntityById(int id);
	
	List<ElderAudioRecordEntity> getElderAudioRecordEntitiesByRecorderidentityRecorderid(@Param("recorderIdentity") int recorderIdentity, @Param("recorderId") int recorderId);
	
	List<ElderAudioRecordEntity> getElderAudioRecordEntitiesByElderid(int elderId);
}