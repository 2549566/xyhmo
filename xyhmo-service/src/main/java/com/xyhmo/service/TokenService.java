package com.xyhmo.service;


public interface TokenService {
    /**
     * 生成token，并存入Redis 中
     *
     * */
    String genToken(String mobile);
}
