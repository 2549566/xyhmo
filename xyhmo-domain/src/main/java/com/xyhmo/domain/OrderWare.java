package com.xyhmo.domain;

import com.xyhmo.commom.model.BaseModel;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderWare extends BaseModel implements Serializable {

    private static final long serialVersionUID = 2072242427789248887L;

    private Long id;
    /**
     * 订单ID，北京地区以BJ开头
     *
     * */
    private String orderId;
    /**
     * 用户pin
     *
     * */
    private String pin;
    /**
     * 购买的该商品数量
     *
     * */
    private Integer wareNum;
    /**
     * 购买的该商品单价
     *
     * */
    private BigDecimal warePrice;
    /**
     * 商品ID
     *
     * */
    private Long skuId;
    /**
     * 冗余字段：商品名称
     *
     *
     * */
    private String reduSkuName;
    /**
     * 冗余字段：商品图标路径
     *
     *
     * */
    private String reduImgPath;
    /**
     * 商品之前价格
     *
     * */
    private BigDecimal reduSkuBeforePrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Integer getWareNum() {
        return wareNum;
    }

    public void setWareNum(Integer wareNum) {
        this.wareNum = wareNum;
    }

    public BigDecimal getWarePrice() {
        return warePrice;
    }

    public void setWarePrice(BigDecimal warePrice) {
        this.warePrice = warePrice;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getReduSkuName() {
        return reduSkuName;
    }

    public void setReduSkuName(String reduSkuName) {
        this.reduSkuName = reduSkuName;
    }

    public String getReduImgPath() {
        return reduImgPath;
    }

    public void setReduImgPath(String reduImgPath) {
        this.reduImgPath = reduImgPath;
    }

    public BigDecimal getReduSkuBeforePrice() {
        return reduSkuBeforePrice;
    }

    public void setReduSkuBeforePrice(BigDecimal reduSkuBeforePrice) {
        this.reduSkuBeforePrice = reduSkuBeforePrice;
    }
}
