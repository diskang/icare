/**
 * @Description TODO
 * @Author Wang Qi
 * @Date May 16, 2015
 */
package com.sjtu.icare.modules.wechat.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjtu.icare.common.config.CommonConstants;
import com.sjtu.icare.modules.elder.entity.ElderRelativeRelationshipEntity;
import com.sjtu.icare.modules.elder.persistence.RelativeDAO;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;
import com.sjtu.icare.modules.wechat.service.IElderRelativeRelationshipService;

@Service
public class ElderRelativeRelationship implements
		IElderRelativeRelationshipService {
	@Autowired
	private SystemService systemService;
	@Autowired
	private RelativeDAO relativeDAO;
	
	@Override
	public Map<String, Object> getElderRelativeRelationshipsByRelativeId(
			String openId) {
		Map<String, Object> result = new HashMap<String, Object>();
		String status = null;
		User user = systemService.getUserByWechatId(openId);
		Integer relativeUserId = user.getId();
		ElderRelativeRelationshipEntity requestElderRelativeRelationshipEntity = new ElderRelativeRelationshipEntity();
		requestElderRelativeRelationshipEntity.setRelativeUserId(relativeUserId);
		List<ElderRelativeRelationshipEntity> elderRelativeRelationshipEntities = relativeDAO.getElderRelativeRelationships(requestElderRelativeRelationshipEntity);
		if ("1".equals(user.getSubscribe())) {
			if (elderRelativeRelationshipEntities == null || elderRelativeRelationshipEntities.size() == 0) {
				status = CommonConstants.SUBSCRIBED_WITHOUT_RELATIONSHIP_BINDING;
			} else {
				status = CommonConstants.SUBSCRIBED_WITH_RELATIONSHIP_BINDING;
			}
		} else {
			status = CommonConstants.UNSUBSCRIBED;
		}
			
		List<Integer> elderIds = null;
		if (elderRelativeRelationshipEntities != null) {
			elderIds = new ArrayList<Integer>();
			for (ElderRelativeRelationshipEntity elderRelativeRelationshipEntity : elderRelativeRelationshipEntities) {
				user = systemService.getUser(elderRelativeRelationshipEntity.getElderUserId());
				elderIds.add(user.getUserId());
			}
		}
		result.put("status", status);
		result.put("relationship", elderRelativeRelationshipEntities);
		result.put("elderIds", elderIds);
		return result;
	}
	
}
