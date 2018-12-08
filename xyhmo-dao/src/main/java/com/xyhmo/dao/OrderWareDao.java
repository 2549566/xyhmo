package com.xyhmo.dao;

import com.xyhmo.commom.dao.MyBatisRepository;
import com.xyhmo.domain.OrderWare;

@MyBatisRepository
public interface OrderWareDao {

    /**
     * 插入一条订单商品
     *
     * */
    Long insert(OrderWare orderWare);
}
