package com.sjtu.icare.modules.sys.persistence;


import org.apache.ibatis.annotations.Param;

import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface UserMapper {

    User findByUsername(String username);

    //User findByUserId(int userId);//应该不会用id来查。

    void updatePasswordById(@Param("id")int id,@Param("password")String password);//id,password
    
    void save(User user);

    void delete(@Param("id")int id,@Param("cancelDate")String cancelDate);//id, cancelDate

}