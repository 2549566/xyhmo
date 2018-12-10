package com.xyhmo.dao;

import com.xyhmo.commom.dao.MyBatisRepository;
import com.xyhmo.domain.Order;

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
    List<Order> selectOrderListByOrderStatus(Order order);
}
