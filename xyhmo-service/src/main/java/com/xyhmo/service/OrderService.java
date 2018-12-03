package com.xyhmo.service;

import java.util.Map;

/**
 * 订单业务层
 *
 * */
public interface OrderService {
    /**
     * 插入一条订单
     *
     *
     * */
    Long saveOrder(String token,Map<Long,Integer> orderMap) throws Exception;
}
