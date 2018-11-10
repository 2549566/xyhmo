package com.xyhmo.service.impl;

import com.xyhmo.dao.UserInfoDao;
import com.xyhmo.domain.UserInfo;
import com.xyhmo.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public Long save(UserInfo userInfo) {
        if(null==userInfo){
            logger.error("userinfo is null");
            return null;
        }
        return userInfoDao.insert(userInfo);
    }

    @Override
    public UserInfo autuwaredUserInfo(String mobile) {
        return null;
    }
}
