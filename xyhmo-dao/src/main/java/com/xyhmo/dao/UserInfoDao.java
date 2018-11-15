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
}
