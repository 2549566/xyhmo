package com.xyhmo.service.impl;

import com.xyhmo.dao.UserAuthInfoDao;
import com.xyhmo.domain.UserAuthInfo;
import com.xyhmo.service.UserAuthInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthInfoServiceImpl implements UserAuthInfoService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserAuthInfoDao userAuthInfoDao;

    @Override
    public Long save(UserAuthInfo userAuthInfo) {
        if(null==userAuthInfo){
            logger.error("userAuthInfo is null");
            return null;
        }
        return userAuthInfoDao.insert(userAuthInfo);
    }
}
