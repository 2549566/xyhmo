package com.xyhmo.service;


import com.xyhmo.domain.UserAuthInfo;
import com.xyhmo.domain.UserInfo;
import com.xyhmo.vo.UserVo;

import java.util.List;

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

    /**
     *  获取代理商所属的业务员列表
     *
     * */
    List<UserInfo> getUserInfoByProxyPin(String pin);
    /**
     * 组装用户信息
     * 用户入参为列表
     * 用户基本信息+用户认证信息
     *
     * */
    List<UserVo> autowaredToVoList(List<UserInfo> userInfoList, List<UserAuthInfo> userAuthInfoList);
}
