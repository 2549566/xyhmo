package com.xyhmo.commom.utils;

import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.exception.ParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.*;

public class ParamCheckUtil {

	public static boolean checkMobileNumber(String mobile){
		if(StringUtils.isEmpty(mobile) ||  !mobile.matches( "[0-9]{1,}" )){
			throw new ParamException(ParamEnum.PARAM_MOBILE_RULE.getCode(),ParamEnum.PARAM_MOBILE_RULE.getDesc());
		}
		return true;
	}

	public static boolean checkCode(String code){
		if(StringUtils.isEmpty(code) ||  !code.matches( "[0-9]{1,}" )){
			throw new ParamException(ParamEnum.PARAM_CODE_RULE.getCode(),ParamEnum.PARAM_CODE_RULE.getDesc());
		}
		return true;
	}
}