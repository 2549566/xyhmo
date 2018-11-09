package com.xyhmo.dao;


import com.xyhmo.commom.dao.MyBatisRepository;
import com.xyhmo.domain.XyhmoUser;

@MyBatisRepository
public interface XyhmoUserDao {

    /**
     * 插入一条数据
     *
     * */
    XyhmoUser selectUserByPin(String pin);

}
