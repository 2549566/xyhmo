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

	public static boolean checkSkuIds(String skuIds){
		if(StringUtils.isEmpty(skuIds) || !skuIds.matches("(\\d+\\,?)+")){
			throw new ParamException(ParamEnum.PARAM_SKUIDS_ERROR.getCode(),ParamEnum.PARAM_SKUIDS_ERROR.getDesc());
		}
		return true;
	}

	public static boolean checkPageNum(Integer pageNum){
		if(null==pageNum ||pageNum<=0){
			throw new ParamException(ParamEnum.PARAM_PAGE_ERROR.getCode(),ParamEnum.PARAM_PAGE_ERROR.getDesc());
		}
		return true;
	}

	public static boolean checkIp(String ip){
		String regex= "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
				+"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		// 判断ip地址是否与正则表达式匹配
		if(StringUtils.isEmpty(ip) || !ip.matches(regex)){
			throw new ParamException(ParamEnum.PARAM_IP_RULE.getCode(),ParamEnum.PARAM_IP_RULE.getDesc());
		}
		return true;
	}
}