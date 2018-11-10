package com.xyhmo.service;


import com.xyhmo.domain.UserInfo;
import com.xyhmo.vo.UserVo;

public interface LoginService {
    /**
     * 注册用户
     *
     * */
    Boolean register(UserInfo userInfo);

    /**
     * 用户登录
     *
     * */
    UserVo login(String token);

}
