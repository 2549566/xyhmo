package com.xyhmo.dao;


import com.xyhmo.commom.dao.MyBatisRepository;
import com.xyhmo.domain.UserInfo;

import java.util.List;

@MyBatisRepository
public interface UserInfoDao {

    /**
     * 插入一条数据
     *
     * */
    Long  insert(UserInfo userInfo);

    /**
     * 根据用户手机号获取用户信息
     *
     * */
    UserInfo selectUserInfoByMobile(String mobileNumber);
    /**
     * 根据用户pin获取用户信息
     *
     * */
    UserInfo selectUserInfoByPin(String pin);

    /**
     * 修改用户信息
     *
     * */
    void updateUserInfo(UserInfo userInfo);
    /**
     * 获取代理商的业务员信息
     *
     * */
    List<UserInfo> selectUserInfoByProxyPin(String pin);
    /**
     * 获取厂商的代理商信息
     * 代理商的状态有 userType =2 和 userType=21
     * 当userType=21的时候，是代理商的工作人员，所以不查询此类型的代理商
     * */
    List<UserInfo> selectProxyListByVenderPin(String pin);
}
