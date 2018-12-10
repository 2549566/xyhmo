package com.xyhmo.dao;

import com.xyhmo.commom.dao.MyBatisRepository;
import com.xyhmo.domain.OrderWare;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface OrderWareDao {

    /**
     * 插入一条订单商品
     *
     * */
    Long insert(OrderWare orderWare);

    /**
     * 根据id列表获取订单商品列表
     *
     * */
    List<OrderWare> selectOrderWareListByOrderIdList(@Param("tableName")String orderWareTableName, @Param("orderIdList")List<String> orderIdList);
}
