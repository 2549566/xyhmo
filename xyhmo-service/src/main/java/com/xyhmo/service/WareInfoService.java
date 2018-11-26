package com.xyhmo.service;

import com.xyhmo.domain.WareInfo;

import java.util.List;
import java.util.Map;

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
}
