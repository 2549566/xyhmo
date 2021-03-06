package com.xyhmo.service.orderWare;

import com.xyhmo.domain.Order;
import com.xyhmo.domain.OrderWare;
import com.xyhmo.vo.order.OrderVo;
import com.xyhmo.vo.param.OrderParam;

import java.util.List;

/**
 * 订单业务层
 *
 * */
public interface OrderWorkerService {
    /**
     * 插入一条订单
     *
     *
     * */
    Long saveOrder(OrderParam orderParam) throws Exception;

    /**
     * 根据订单状态获取业务员订单列表
     *
     * */
    List<OrderVo> getWorkerOrderList(String token, Integer orderStatus);
    /**
     * 将订单表和订单商品表组装成订单VO
     *
     * */
    void translationToOrderVos(List<OrderVo> orderVos, List<Order> orderList, List<OrderWare> orderWareList);

    /**
     * 业务员确认代理商填的订单信息
     *
     * */
    void sureOrderJiedan(String token, String orderId);

    /**
     * 业务员驳回代理商填写的订单信息
     *
     * */
    void rejectOrder(String token, String orderId, String rejectCase);
    /**
     * 根据workerPin获取业务员订单列表（无Redis）
     *
     * */
    List<OrderVo> getWorkerOrderListByWorkerPin(String workerPin);

    /**
     * 注意，订单状态的修改，要同时更新 工人列表缓存和代理商列表缓存
     *
     * */
}
