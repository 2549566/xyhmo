package com.xyhmo.service.impl;

import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.SystemException;
import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.commom.utils.UniqueUtil;
import com.xyhmo.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class TokenServiceImpl implements TokenService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String genToken(String mobile){
        logger.info("TokenServiceImpl genToken:mobile ="+mobile);
        String token = "";
        try {
            token = UniqueUtil.genToken();
        } catch (NoSuchAlgorithmException e) {
            throw new SystemException(SystemEnum.SYSTEM_GEN_TOKEN_ERROR.getCode(),SystemEnum.SYSTEM_GEN_TOKEN_ERROR.getDesc());
        }
        return token;
    }
}
