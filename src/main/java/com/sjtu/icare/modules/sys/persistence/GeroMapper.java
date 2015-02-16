package com.sjtu.icare.modules.sys.persistence;

import java.util.List;

import com.sjtu.icare.modules.sys.entity.Gero;
import com.sjtu.icare.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface GeroMapper {

  List<Gero> getGerosByName(String name);

  Gero getGero(int id);
  
  void insertGero(Gero gero);
  
//  void insertGeroStatus(Gero gero);

}