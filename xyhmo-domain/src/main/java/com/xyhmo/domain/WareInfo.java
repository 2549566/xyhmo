package com.xyhmo.domain;

import com.xyhmo.commom.model.BaseModel;

import java.io.Serializable;
import java.math.BigDecimal;

public class WareInfo extends BaseModel implements Serializable{

    private static final long serialVersionUID = -5689281391281157590L;

    /**
     *  id
     *
     */
    private Long id;
    /**
     * pin
     *
     * */
    private String pin;
    /**
     * 商品ID
     *
     * */
    private Long skuId;
    /**
     * 商品名称
     *
     * */
    private String skuName;
    /**
     * 商品图片路径
     *
     * */
    private String imgPath;
    /**
     * 商品介绍
     *
     * */
    private String skuDesc;
    /**
     * 商品当前价格
     *
     * */
    private BigDecimal skuPrice;
    /**
     * 商品之前价格
     *
     * */
    private BigDecimal skuBeforePrice;
    /**
     * 商品上下架状态
     *
     * */
    private Integer skuStatus;
    /**
     * 所属厂商
     *
     * */
    private String venderPin;
    /**
     * 商品类型：1：卷材 2：煤气 3：涂料 10：其他
     *
     * */
    private Integer skuType;
    /**
     * 冗余字段：商品所在的地点，即代理商所在的地点
     *
     * */
    private String reduCoordinate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getSkuDesc() {
        return skuDesc;
    }

    public void setSkuDesc(String skuDesc) {
        this.skuDesc = skuDesc;
    }

    public BigDecimal getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(BigDecimal skuPrice) {
        this.skuPrice = skuPrice;
    }

    public BigDecimal getSkuBeforePrice() {
        return skuBeforePrice;
    }

    public void setSkuBeforePrice(BigDecimal skuBeforePrice) {
        this.skuBeforePrice = skuBeforePrice;
    }

    public Integer getSkuStatus() {
        return skuStatus;
    }

    public void setSkuStatus(Integer skuStatus) {
        this.skuStatus = skuStatus;
    }

    public String getVenderPin() {
        return venderPin;
    }

    public void setVenderPin(String venderPin) {
        this.venderPin = venderPin;
    }

    public Integer getSkuType() {
        return skuType;
    }

    public void setSkuType(Integer skuType) {
        this.skuType = skuType;
    }

    public String getReduCoordinate() {
        return reduCoordinate;
    }

    public void setReduCoordinate(String reduCoordinate) {
        this.reduCoordinate = reduCoordinate;
    }
}
