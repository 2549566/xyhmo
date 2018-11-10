package com.xyhmo.service.impl;

import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.SystemException;
import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.commom.utils.ParamCheckUtil;
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
    @Autowired
    private RedisService redisService;
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

    @Override
    public String genCheckCode(String mobile) {
        try{
            Integer code =(int)((Math.random()*9+1)*1000);
            redisService.set(Contants.MOBILE_CHECK_CODE+mobile,code.toString(),Contants.MOBILE_CHECK_CODE_OVER_TIME);
            return code.toString();
        }catch (SystemException s){
            logger.error("LoginServiceImpl:生成手机验证码失败");
            throw new SystemException(SystemEnum.PARAM_CODE_OVER.getCode(),SystemEnum.PARAM_CODE_OVER.getDesc());
        }

    }
}
