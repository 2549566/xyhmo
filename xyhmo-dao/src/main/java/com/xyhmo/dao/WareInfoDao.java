package com.xyhmo.dao;


import com.xyhmo.commom.dao.MyBatisRepository;
import com.xyhmo.domain.WareInfo;

import java.util.List;
import java.util.Set;

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

    /**
     * 根据SKUID获取商品信息
     *
     * */
    WareInfo selectWareInfoBySkuId(Long skuId);

    /**
     * 根据skuIds获取商品列表
     *
     * */
    List<WareInfo> selectWareListBySkuIds(List<Long> skuIds);
}
