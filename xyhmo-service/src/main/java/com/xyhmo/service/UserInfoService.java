package com.xyhmo.service;


import com.xyhmo.domain.UserInfo;

public interface UserInfoService {
    /**
     * 插入一条数据
     *
     * */
    Long save(String mobile);
    /**
     * 组装userInfo
     *
     * */
    UserInfo autuwaredUserInfo(String mobile);

    /**
     * 根据mobile获取用户信息
     *
     * */
    UserInfo getUserInfoByMobile(String mobile);
}
