package com.xyhmo.dao;

import com.xyhmo.commom.dao.MyBatisRepository;
import com.xyhmo.domain.GenOrder;
import com.xyhmo.domain.GenSku;

@MyBatisRepository
public interface GenSkuDao {
    /**
     * 插入一条数据
     *
     * */
    Long insert(GenSku genSku);
}
