package com.xyhmo.dao;

import com.xyhmo.commom.dao.MyBatisRepository;
import com.xyhmo.domain.Order;
import com.xyhmo.vo.order.OrderVo;

import java.util.List;

@MyBatisRepository
public interface OrderDao {

    /**
     * 插入一条订单
     *
     * */
    Long insert(Order order);

    /**
     * （代理商）根据订单状态获取订单列表
     *
     * */
    List<Order> selectOrderProxyListByOrderStatus(Order order);

    /**
     * (工人)根据订单状态获取订单列表
     *
     * */
    List<Order> selectOrderWorkerListByOrderStatus(Order order);

    /**
     * 修改订单状态
     *
     * */
    void updateOrderStatus(Order order);
    /**
     * 根据业务员pin获取订单列表
     *
     * */
    List<Order> selectOrderWorkerListByPin(Order order);
}
