package com.xyhmo.commom.utils;

import com.xyhmo.commom.enums.OrderStatusEnum;
import com.xyhmo.commom.service.Contants;

public class RedisUtil {

    public static String getBeforeWorkerRedisKey(Integer orderStatus){
        if(OrderStatusEnum.ORDER_YWY_SUBMIT.getCode()==orderStatus || OrderStatusEnum.ORDER_DLS_SURE.getCode()==orderStatus){
            return Contants.REDIS_ORDERWORKER_WEIJIEDAN_PIN;
        }else if(OrderStatusEnum.ORDER_YWY_SURE_NOTPAY.getCode()==orderStatus){
            return Contants.REDIS_ORDERWORKER_WEIZHIFU_PIN;
        }else if(OrderStatusEnum.ORDER_YWY_SURE_PAY.getCode()==orderStatus){
            return Contants.REDIS_ORDERWORKER_YIZHIFU_PIN;
        }
        return "";
    }

    public static String getBeforeProxyRedisKey(Integer orderStatus){
        if(OrderStatusEnum.ORDER_YWY_SUBMIT.getCode()==orderStatus || OrderStatusEnum.ORDER_DLS_SURE.getCode()==orderStatus){
            return Contants.REDIS_ORDERPROXY_WEIJIEDAN_PIN;
        }else if(OrderStatusEnum.ORDER_YWY_SURE_NOTPAY.getCode()==orderStatus){
            return Contants.REDIS_ORDERPROXY_WEIZHIFU_PIN;
        }else if(OrderStatusEnum.ORDER_YWY_SURE_PAY.getCode()==orderStatus){
            return Contants.REDIS_ORDERPROXY_YIZHIFU_PIN;
        }
        return "";
    }
}
