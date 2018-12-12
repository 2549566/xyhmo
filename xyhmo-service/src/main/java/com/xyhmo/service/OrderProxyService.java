package com.xyhmo.service;

import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.vo.order.OrderVo;

import java.util.List;

public interface OrderProxyService {

    /**
     * 获取代理商订单表
     *
     * */
    List<OrderVo> getOrderProxyList(String token,Integer orderStatus) throws ParamException,Exception;
    /**
     * 注意，订单状态的修改，要同时更新 工人列表缓存和代理商列表缓存
     *
     * */
}
