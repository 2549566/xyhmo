package com.xyhmo.dao;


import com.xyhmo.commom.dao.MyBatisRepository;
import com.xyhmo.domain.WareInfo;

import java.util.List;

@MyBatisRepository
public interface WareInfoDao {


    /**
     * 获取商品列表
     *
     * */
    List<WareInfo> selectWareInfoList(String pin);
    /**
     * 获取所有的商品
     *
     * */
    List<WareInfo> selectAllWareInfoList();

    /**
     * 根据userType获取商品列表
     *
     * */
    List<WareInfo> selectWareInfoListByUserType(WareInfo wareInfo);
}
