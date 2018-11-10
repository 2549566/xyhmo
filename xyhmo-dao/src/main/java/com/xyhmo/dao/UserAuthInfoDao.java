package com.xyhmo.dao;


import com.xyhmo.commom.dao.MyBatisRepository;
import com.xyhmo.domain.UserAuthInfo;

@MyBatisRepository
public interface UserAuthInfoDao {

    /**
     * 插入一条数据
     *
     * */
    Long  insert(UserAuthInfo userAuthInfo);

}
