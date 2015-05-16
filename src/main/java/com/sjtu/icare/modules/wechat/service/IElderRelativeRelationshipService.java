/**
 * @Description TODO
 * @Author Wang Qi
 * @Date May 16, 2015
 */
package com.sjtu.icare.modules.wechat.service;

import java.util.Map;

public interface IElderRelativeRelationshipService {

	Map<String, Object> getElderRelativeRelationshipsByRelativeId(String openId);

}
