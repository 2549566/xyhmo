package com.xyhmo.service.impl;

import com.xyhmo.commom.utils.HashCodeUtil;
import com.xyhmo.dao.OrderDao;
import com.xyhmo.domain.Order;
import com.xyhmo.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;

@Service
public class OrderServiceImpl implements OrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderDao orderDao;

    @Override
    @Transient
    public Long saveOrder() {
        Order order =  new Order();
        String pin="'"+"ffffffffffffffffffffffffff"+"'";
        order.setPin(pin);
        String tableName = "order_bj_"+ HashCodeUtil.toHash(pin)%4;
        String orderId="'"+"BJ10000001"+"'";
        order.setTableName(tableName);
        order.setOrderId(orderId);
        order.setOrderType(1);
        Long id = orderDao.insert(order);

        System.out.println("id================="+id);
        return id;
    }
}
