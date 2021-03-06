package com.xyhmo.service;


import com.xyhmo.domain.UserAuthInfo;

import java.util.List;

public interface UserAuthInfoService {
    /**
     * 插入一条数据
     *
     * */
    Long save(UserAuthInfo userAuthInfo);

    /**
     * 根据pin获取用户认证信息
     *
     * */
    UserAuthInfo getUserAuthInfoByPin(String pin);
    /**
     * 根据pin获取list
     *
     * */
    List<UserAuthInfo> getUserAuthInfoByPinList(List<String> workerPinList);
}
