/**
 * @Description TODO
 * @Author Wang Qi
 * @Date Mar 30, 2015
 */
package com.sjtu.icare.modules.elder.service;

import java.util.List;

import com.sjtu.icare.modules.elder.entity.ElderRelativeRelationshipEntity;
import com.sjtu.icare.modules.elder.entity.RelativeEntity;
import com.sjtu.icare.modules.sys.entity.User;

public interface IRelativeInfoService {

	RelativeEntity getRelative(RelativeEntity relativeEntity);

	List<User> getUsersOfRelatives(User user);

	Integer insertRelative(RelativeEntity relativeEntity);

	void updateRelative(RelativeEntity relativeEntity);

	void deleteRelative(RelativeEntity relativeEntity);

	List<User> getUsersOfRelatives2(User user);

	void insertElderRelativeRelationship(ElderRelativeRelationshipEntity elderRelativeRelationshipEntity);

	void deleteElderRelativeRelationship(ElderRelativeRelationshipEntity elderRelativeRelationshipEntity);

	List<ElderRelativeRelationshipEntity> getElderRelativeRelationships(ElderRelativeRelationshipEntity elderRelativeRelationshipEntity);


}
