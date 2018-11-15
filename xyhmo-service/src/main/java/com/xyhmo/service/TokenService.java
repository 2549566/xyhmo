package com.xyhmo.service;


import com.xyhmo.vo.UserVo;

public interface TokenService {
    /**
     * 生成token，并存入Redis 中
     *
     * */
    String genToken(String mobile);

    /**
     * 校验token是否存在,并且从token里获取用户数据
     *
     * */
    UserVo checkTokenExist(String token);
}
