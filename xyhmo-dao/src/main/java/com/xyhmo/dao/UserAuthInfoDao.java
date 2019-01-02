package com.xyhmo.dao;


import com.xyhmo.commom.dao.MyBatisRepository;
import com.xyhmo.domain.UserAuthInfo;

import java.util.List;

@MyBatisRepository
public interface UserAuthInfoDao {

    /**
     * 插入一条数据
     *
     * */
    Long  insert(UserAuthInfo userAuthInfo);

    /**
     * 根据pin获取用户认证信息
     *
     * */
    UserAuthInfo selectUserAuthInfoByPin(String pin);
    /**
     * 根据pinlist获取用户信息
     *
     * */
    List<UserAuthInfo> selectUserAuthInfoByPinList(List<String> workerPinList);
}
