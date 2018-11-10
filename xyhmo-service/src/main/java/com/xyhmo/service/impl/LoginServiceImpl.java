package com.xyhmo.service.impl;

import com.xyhmo.domain.UserInfo;
import com.xyhmo.service.LoginService;
import com.xyhmo.service.UserAuthInfoService;
import com.xyhmo.service.UserInfoService;
import com.xyhmo.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserAuthInfoService userAuthInfoService;

    @Override
    public Boolean register(UserInfo userInfo) {
        if(null==userInfo){
            logger.error("register userInfo is null");
            return false;
        }
        return userInfoService.save(userInfo)>0;
    }

    @Override
    public UserVo login(String token) {
        return null;
    }
}
