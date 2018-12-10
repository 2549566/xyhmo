package com.xyhmo.service;

import com.xyhmo.vo.order.OrderVo;

import java.util.List;

public interface OrderProxyService {

    /**
     * 获取代理商订单表
     *
     * */
    List<OrderVo> getOrderProxyList(String token,Integer orderStatus) throws Exception;
}
