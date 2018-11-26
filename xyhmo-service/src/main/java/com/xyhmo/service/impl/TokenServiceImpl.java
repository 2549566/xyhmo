package com.xyhmo.service.impl;

import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.commom.exception.SystemException;
import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.commom.utils.UniqueUtil;
import com.xyhmo.service.TokenService;
import com.xyhmo.vo.UserVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class TokenServiceImpl implements TokenService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisService redisService;

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

    @Override
    public UserVo checkTokenExist(String token) {
        if(StringUtils.isEmpty(token)){
            throw new ParamException(ParamEnum.PARAM_TOKEN_IS_EMPTY.getCode(),ParamEnum.PARAM_TOKEN_IS_EMPTY.getDesc());
        }
        if(null==redisService.get(Contants.REDIS_TOKEKN_BEFORE+token)){
            throw new ParamException(ParamEnum.PARAM_TOKEN_NOT_EXIST.getCode(),ParamEnum.PARAM_TOKEN_NOT_EXIST.getDesc());
        }
        return redisService.get(Contants.REDIS_TOKEKN_BEFORE+token);
    }
}
