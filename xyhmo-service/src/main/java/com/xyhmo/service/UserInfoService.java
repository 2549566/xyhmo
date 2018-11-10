package com.xyhmo.service;


import com.xyhmo.domain.UserInfo;

public interface UserInfoService {
    /**
     * 插入一条数据
     *
     * */
    Long save(UserInfo userInfo);
    /**
     * 组装userInfo
     *
     * */
    UserInfo autuwaredUserInfo(String mobile);
}
