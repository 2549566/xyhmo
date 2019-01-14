package com.xyhmo.util;
/**
 * 生成ID工具类
 *
 *
 * */
public interface GenIdService {

    /**
     * 生成skuId
     *
     * */
    Long genSkuId(Integer skuType);
    /**
     * 根据城市生成订单ID
     *
     * */
    String genOrderId(String city);
    /**
     * 生成工程订单ID
     *
     * */
    String genProjectOrderId(String city);
}
