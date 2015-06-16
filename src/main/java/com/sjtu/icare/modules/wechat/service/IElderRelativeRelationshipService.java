/**
 * @Description TODO
 * @Author Wang Qi
 * @Date May 16, 2015
 */
package com.sjtu.icare.modules.wechat.service;

import com.sjtu.icare.modules.wechat.service.impl.ElderRelativeRelationshipService.ElderRelativeRelationshipReturn;

public interface IElderRelativeRelationshipService {

	ElderRelativeRelationshipReturn getElderRelativeRelationshipsByWechatId(String openId);

}
