/**
 * @Package com.sjtu.icare.common.utils
 * @Description 共有的 JSON 返回部分
 * @date Mar 7, 2015 1:53:08 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSON;


public class BasicReturnedJson {
	private HashMap<String, Object> map = new HashMap<String, Object>();
	
	public BasicReturnedJson() {
		map.put("status", HttpStatus.OK.value());
		map.put("entities", new ArrayList<Object>());
	}
	
	public int getStatus() {
		return (int) map.get("status");
	}
	
	public void setStatus(int status) {
		map.put("status", status);
	}
	
	public String getError() {
		return (String) map.get("error");
	}
	
	public void setError(String error) {
		map.put("error", error);
	}

	public int getTotal() {
		return (int) map.get("total");
	}
	
	public void setTotal(int total) {
		map.put("total", total);
	}
	
	public String getNextToken() {
		return (String) map.get("next_token");
	}
	
	public void setNextToken(String nextToken) {
		map.put("next_token", nextToken);
	}
	
	public List<Object> getEntities() {
		return (List<Object>) map.get("entities");
	}
	
	public void setEntities(List<Object> entities) {
		map.put("entities", entities);
	}
	
	public void addEntity(Object entity) {
		((List<Object>) map.get("entities")).add(entity);
	}
	
	public Map<String, Object> getMap() {
		return map;
	}
	
	public String toString() {
		return JSON.toJSONString(map);
	}
	
}


