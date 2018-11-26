package com.xyhmo.service;


import com.xyhmo.domain.UserAuthInfo;
import com.xyhmo.domain.UserInfo;
import com.xyhmo.vo.UserVo;

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

    /**
     * 修改用户信息
     * */
    void EditUserInfo(UserVo vo);
    /**
     * 组装用户信息
     *
     * */
    UserVo autowaredToVo(UserInfo userInfo, UserAuthInfo userAuthInfo);

    /**
     * 根据用户pin获取用户信息
     *
     * */
    UserInfo getUserInfoByPin(String pin);
    /**
     * 根据用户pin获取userVo
     *
     * */
    UserVo getUserVoByToken(String token);
}
