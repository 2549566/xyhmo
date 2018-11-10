package com.xyhmo.dao;


import com.xyhmo.commom.dao.MyBatisRepository;
import com.xyhmo.domain.UserInfo;

@MyBatisRepository
public interface UserInfoDao {

    /**
     * 插入一条数据
     *
     * */
    Long  insert(UserInfo userInfo);

}
