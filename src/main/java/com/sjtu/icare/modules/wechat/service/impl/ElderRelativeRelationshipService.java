/**
 * @Description TODO
 * @Author Wang Qi
 * @Date May 16, 2015
 */
package com.sjtu.icare.modules.wechat.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjtu.icare.common.config.CommonConstants;
import com.sjtu.icare.modules.elder.entity.ElderRelativeRelationshipEntity;
import com.sjtu.icare.modules.elder.persistence.RelativeDAO;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;
import com.sjtu.icare.modules.wechat.service.IElderRelativeRelationshipService;
import com.sjtu.icare.modules.wechat.service.impl.ElderRelativeRelationshipService.ElderRelativeRelationshipReturn.Elder;
import com.sjtu.icare.modules.wechat.service.impl.ElderRelativeRelationshipService.ElderRelativeRelationshipReturn.Relative;

@Service
public class ElderRelativeRelationshipService implements
		IElderRelativeRelationshipService {
	@Autowired
	private SystemService systemService;
	@Autowired
	private RelativeDAO relativeDAO;
	
	
	public static class ElderRelativeRelationshipReturn {
		private String status = null;
		
		public static class Elder {
			Integer elderUserId;
			Integer elderId;
			String elderName;
			String photoUrl;
			public Integer getElderUserId() {
				return elderUserId;
			}
			public void setElderUserId(Integer elderUserId) {
				this.elderUserId = elderUserId;
			}
			public Integer getElderId() {
				return elderId;
			}
			public void setElderId(Integer elderId) {
				this.elderId = elderId;
			}
			public String getElderName() {
				return elderName;
			}
			public void setElderName(String elderName) {
				this.elderName = elderName;
			}
			public String getPhotoUrl() {
				return photoUrl;
			}
			public void setPhotoUrl(String photoUrl) {
				this.photoUrl = photoUrl;
			}
		}
		List<Elder> elders = new ArrayList<ElderRelativeRelationshipService.ElderRelativeRelationshipReturn.Elder>();
		
		public static class Relative {
			Integer relativeUserId;

			public Integer getRelativeUserId() {
				return relativeUserId;
			}

			public void setRelativeUserId(Integer relativeUserId) {
				this.relativeUserId = relativeUserId;
			}
			
		}
		Relative relative = new Relative();
		
		
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public List<Elder> getElders() {
			return elders;
		}
		public void setElders(List<Elder> elders) {
			this.elders = elders;
		}
		public void addElder(Elder elder) {
			this.elders.add(elder);
		}
		public Relative getRelative() {
			return relative;
		}
		public void setRelative(Relative relative) {
			this.relative = relative;
		}
		
		
		
	}
	
	@Override
	public ElderRelativeRelationshipReturn getElderRelativeRelationshipsByRelativeId(
			String openId) {
		String status = null;
		User user = systemService.getUserByWechatId(openId);
		if (user == null) {
			return null;
		} else {
			ElderRelativeRelationshipReturn result = new ElderRelativeRelationshipReturn();
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
			
			if (elderRelativeRelationshipEntities != null) {
				for (ElderRelativeRelationshipEntity elderRelativeRelationshipEntity : elderRelativeRelationshipEntities) {
					user = systemService.getUser(elderRelativeRelationshipEntity.getElderUserId());
					Elder elder = new Elder();
					elder.setElderUserId(elderRelativeRelationshipEntity.getElderUserId());
					elder.setElderId(user.getUserId());
					elder.setElderName(user.getName());
					elder.setPhotoUrl(user.getPhotoUrl());
					result.addElder(elder);
				}
			}
			
			Relative relative = new Relative();
			relative.setRelativeUserId(relativeUserId);
			result.setRelative(relative);
			result.setStatus(status);
			return result;
		}
	}
	
}
