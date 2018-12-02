package com.xyhmo.dao;

import com.xyhmo.commom.dao.MyBatisRepository;
import com.xyhmo.domain.Order;

@MyBatisRepository
public interface OrderDao {

    /**
     * 插入一条订单
     *
     * */
    Long insert(Order order);
}
