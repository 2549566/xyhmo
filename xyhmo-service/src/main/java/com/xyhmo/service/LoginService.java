package com.xyhmo.service;

import com.xyhmo.vo.UserVo;

public interface LoginService {
    /**
     * 注册用户
     *
     * */
    Boolean register(String mobile);

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

}
