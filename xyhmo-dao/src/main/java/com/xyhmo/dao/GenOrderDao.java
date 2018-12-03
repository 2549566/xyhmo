package com.xyhmo.dao;

import com.xyhmo.commom.dao.MyBatisRepository;
import com.xyhmo.domain.GenOrder;

@MyBatisRepository
public interface GenOrderDao {
    /**
     * 插入一条数据
     *
     * */
    Long insert(GenOrder genOrder);
}
