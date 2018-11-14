package com.xyhmo.service.impl;

import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.utils.ParamCheckUtil;
import com.xyhmo.commom.utils.UniqueUtil;
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
    public Long save(String mobile) {
        logger.info("UserInfoServiceImpl save:mobile="+mobile);
        ParamCheckUtil.checkMobileNumber(mobile);
        UserInfo userInfo = new UserInfo();
        userInfo.setMobileNumber(mobile);
        userInfo.setPin(UniqueUtil.genPin(mobile));
        userInfo.setStatus(ParamEnum.PARAM_DATA_USED.getCode());
        return userInfoDao.insert(userInfo);
    }

    @Override
    public UserInfo autuwaredUserInfo(String mobile) {
        return null;
    }

    @Override
    public UserInfo getUserInfoByMobile(String mobile) {
        return userInfoDao.selectUserInfoByMobile(mobile);
    }
}
