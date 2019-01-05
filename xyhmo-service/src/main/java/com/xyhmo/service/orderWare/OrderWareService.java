package com.xyhmo.service.orderWare;

import com.xyhmo.vo.order.OrderVo;

public interface OrderWareService {
    /**
     * 根据orderId获取订单详情
     *
     * */
    OrderVo getOrderWareDetail(String workerPin,String orderId);
}
