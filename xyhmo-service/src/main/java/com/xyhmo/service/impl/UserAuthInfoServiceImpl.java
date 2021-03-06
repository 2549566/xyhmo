package com.xyhmo.service.impl;

import com.xyhmo.dao.UserAuthInfoDao;
import com.xyhmo.domain.UserAuthInfo;
import com.xyhmo.service.UserAuthInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public UserAuthInfo getUserAuthInfoByPin(String pin) {
        if(StringUtils.isEmpty(pin)){
            logger.error("UserAuthInfoServiceImpl pin is null");
        }
        return userAuthInfoDao.selectUserAuthInfoByPin(pin);
    }

    @Override
    public List<UserAuthInfo> getUserAuthInfoByPinList(List<String> workerPinList) {
        if(CollectionUtils.isEmpty(workerPinList)){
            return new ArrayList<>();
        }
        return userAuthInfoDao.selectUserAuthInfoByPinList(workerPinList);
    }
}
