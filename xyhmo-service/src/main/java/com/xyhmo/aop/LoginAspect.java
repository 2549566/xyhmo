package com.xyhmo.aop;

import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.vo.UserVo;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class LoginAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisService redisService;

    @Pointcut ("execution(* com.xyhmo.service.impl.BannerServiceImpl.getBannerList(String))&& args(token)")
    public void myMethod(String token){};

    @After("myMethod(token)")
    public void after(String token) {
        UserVo vo = redisService.get(Contants.REDIS_TOKEKN_BEFORE+token);
        if(null==vo){
            return ;
        }
        redisService.set(Contants.REDIS_TOKEKN_BEFORE+token,vo,Contants.TOKEN_OVER_TIME);
    }
}
