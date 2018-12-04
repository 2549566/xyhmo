package com.xyhmo.service;

import com.xyhmo.vo.param.OrderParam;
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
    Long saveOrder(OrderParam orderParam) throws Exception;
}
