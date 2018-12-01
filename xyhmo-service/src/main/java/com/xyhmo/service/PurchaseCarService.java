package com.xyhmo.service;


import com.xyhmo.vo.purchaseCar.PurchaseWareVo;

import java.util.List;

public interface PurchaseCarService {
    /**
     * 获取购物车总数量
     *
     * */
    Integer getTotalNum(String token);
    /**
     * 获取购物车选中的总数量
     *
     * */
    Integer getSelectNum(String token);
    /**
     * 获取购物车内所有的商品列表
     *
     * */
    List<PurchaseWareVo> getPurchaseWareList(String token);
    /**
     * 向购物车内添加商品(添加购物车的时候，前台要做出校验，只有当返回数据的时候，才能让购物车添加)
     *
     * */
    Boolean addWareToPurchaseCar(String token,Long skuId);
    /**
     * 修改购物车内某个商品的数量
     *
     * */
    Boolean updateWareNum(String token,Long skuId,Integer num);
    /**
     * 修改购物车内的某个商品的选中状态
     *
     * */
    Boolean updateWareSelectStatus(String token,Long skuId,Boolean selected);
    /**
     * 删除购物车内选中的商品列表
     *
     * */
    List<PurchaseWareVo> deletePurchaseCarWareList(String token,String skuIds);
}
