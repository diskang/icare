package com.sjtu.icare.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjtu.icare.modules.sys.entity.Gero;
import com.sjtu.icare.modules.sys.persistence.GeroMapper;

/**
 *  测试类
 * @author garfieldjty
 *
 */
@Service
public class GeroService{
	@Autowired
	private GeroMapper geroMapper;
	
	public Gero getGero(int id){
		return geroMapper.getGero(id);
	}
	
	public List<Gero> getGerosByName(String name){
		return geroMapper.getGerosByName(name);
	}
	
	public void insertGero(Gero gero){
		geroMapper.insertGero(gero);
	}
}