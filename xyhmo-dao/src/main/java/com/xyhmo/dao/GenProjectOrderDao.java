package com.xyhmo.dao;

import com.xyhmo.commom.dao.MyBatisRepository;
import com.xyhmo.domain.GenOrder;
import com.xyhmo.domain.GenProjectOrder;

@MyBatisRepository
public interface GenProjectOrderDao {
    /**
     * 插入一条数据
     *
     * */
    Long insert(GenProjectOrder genProjectOrder);
}
