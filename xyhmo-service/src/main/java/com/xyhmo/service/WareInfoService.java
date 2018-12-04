package com.xyhmo.service;

import com.github.pagehelper.PageInfo;
import com.xyhmo.domain.WareInfo;
import org.apache.activemq.util.WrappedException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface WareInfoService {
    /**
     * 获取商品列表
     *
     * */
    Map<Integer,List<WareInfo>> getWareInfoList(String token);
    /**
     * 获取所有商品列表
     *
     * */
    Map<Integer,List<WareInfo>> getAllWareInfoList();
    /**
     * 获取某个代理商商品列表
     *
     * */
    Map<Integer,List<WareInfo>> getWareInfoListByPin(String pin);

    /**
     * 分类，分页查询商品列表
     * TODO 缓存第一页的数据
     * */
    PageInfo getWareInfoListByUserType(String token, Integer skuType, Integer pageNum);
    /**
     * 根据商品ID，获取商品信息
     *
     *
     * */
    WareInfo getWareInfoBySkuId(Long skuId);
    /**
     * 根据skuIds批量获取商品
     *
     * */
    List<WareInfo> getWareListBySkuIds(Set<Long> skuIds);
}
