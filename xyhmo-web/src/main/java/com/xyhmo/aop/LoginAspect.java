package com.xyhmo.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class LoginAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Pointcut("execution(* com.xyhmo.controller.banner.BannerController..add())")
//    public void myMethod(){};
//    @Before("myMethod()")
    @Before(value ="execution(* com.xyhmo.controller.banner.BannerController..add())" )
    public void before() {
        logger.info("method staetssssssssssssssssssssssssssssssssssssssssssssss");
        System.out.println("method staetssssssssssssssssssssssssssssssssssssssssssssss");
    }
//    @After("myMethod()")
    public void after() {
        logger.info("444444444444444444444444444444444444444444444444444444");
        System.out.println("11111111111111111111111111111111111111111111111111111");
    }
}
