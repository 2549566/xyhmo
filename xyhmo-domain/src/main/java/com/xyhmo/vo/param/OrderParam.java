package com.xyhmo.vo.param;

import java.io.Serializable;
import java.util.Map;

public class OrderParam implements Serializable{

    private static final long serialVersionUID = -4585576865287831933L;

    /**
     * 用户登录token
     *
     * */
    private String token;
    /**
     * 是否配送 1:配送 2：不配送
     *
     * */
    private Integer isDelivery;
    /**
     * 配送地址
     *
     * */
    private String address;
    /**
     * 购买的商品
     *
     * */
    private Map<Long,Integer> skuMap;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(Integer isDelivery) {
        this.isDelivery = isDelivery;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<Long, Integer> getSkuMap() {
        return skuMap;
    }

    public void setSkuMap(Map<Long, Integer> skuMap) {
        this.skuMap = skuMap;
    }
}
