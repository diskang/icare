/**
 * @Description TODO
 * @Author Wang Qi
 * @Date Mar 30, 2015
 */
package com.sjtu.icare.modules.elder.persistence;

import java.util.List;
import java.util.Map;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.elder.entity.ElderRelativeRelationshipEntity;
import com.sjtu.icare.modules.elder.entity.RelativeEntity;
import com.sjtu.icare.modules.sys.entity.User;

@MyBatisDao
public interface RelativeDAO {

	List<User> getUsersOfRelatives(User user);

	RelativeEntity getRelative(RelativeEntity relativeEntity);

	void insertRelative(RelativeEntity relativeEntity);

	void updateRelative(RelativeEntity relativeEntity);

	void deleteRelative(RelativeEntity relativeEntity);

	List<User> getUsersOfRelatives2(User user);

	void insertElderRelativeRelationship(ElderRelativeRelationshipEntity elderRelativeRelationshipEntity);

	void deleteElderRelativeRelationship(ElderRelativeRelationshipEntity elderRelativeRelationshipEntity);

	List<ElderRelativeRelationshipEntity> getElderRelativeRelationships(
			ElderRelativeRelationshipEntity elderRelativeRelationshipEntity);

}
