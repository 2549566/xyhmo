package com.xyhmo.service;

import com.xyhmo.domain.UserAuthInfo;
import com.xyhmo.domain.UserInfo;
import com.xyhmo.vo.UserVo;

public interface LoginService {
    /**
     * 注册用户
     *
     * */
    Boolean register(String mobile,String token);

    /**
     * 用户登录
     *
     * */
    UserVo login(String token);

    /**
     * 生成手机验证码
     *
     * */
    String genCheckCode(String mobile);

    /**
     * 校验IP
     *
     * */
    boolean checkIp(String ip);
    /**
     * 获取用户信息
     *
     * */
    UserVo getUserInfo(String mobile);

    /**
     * 将用户信息存到Redis中
     *
     * */
    String saveUserVoToRedis(UserVo vo);
}
