package com.xyhmo.query;

import java.io.Serializable;

public class WareInfoQuery implements Serializable{

    private static final long serialVersionUID = 7494339701807199471L;

    /**
     * 用户唯一pin
     *
     * */
    private String pin;
    /**
     *
     * 商品类型：1：卷材 2：煤气 3：涂料 10：其他
     * */
    private Integer skuType;
    /**
     * 商品上下架状态
     *
     * */
    private Integer skuStatus;
    /**
     * 数据状态
     *
     * */
    private Integer status;
    /**
     * 商品名称
     *
     * */
    private String skuName;

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Integer getSkuType() {
        return skuType;
    }

    public void setSkuType(Integer skuType) {
        this.skuType = skuType;
    }

    public Integer getSkuStatus() {
        return skuStatus;
    }

    public void setSkuStatus(Integer skuStatus) {
        this.skuStatus = skuStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }
}
