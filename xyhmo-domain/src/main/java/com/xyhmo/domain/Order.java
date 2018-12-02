package com.xyhmo.domain;

import com.xyhmo.commom.model.BaseModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单对象
 *
 *
 * */
public class Order extends BaseModel implements Serializable {

    private static final long serialVersionUID = -661568280765397370L;

    private String tableName;

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
     * 代理商pin
     *
     * */
    private String proxyPin;
    /**
     * 送货地址坐标
     *
     * */
    private String coordinate;
    /**
     * 地址描述
     *
     * */
    private String address;
    /**
     * 是否需要配送
     * 1:需要配送
     * 0：不需要配送
     * */
    private Integer isDelivery;
    /**
     * 配送费用
     *
     * */
    private BigDecimal deliveryPrice;
    /**
     * 订单状态：1：业务员已报单，并且代理商进入配送或者业务员取货状态 2：代理商已经点击确认 3：业务员点击确认结单（未支付） 4：业务员点击确认，并支付
     *
     * */
    private Integer orderType;
    /**
     * 应付金额
     * */
    private BigDecimal payablePrice;
    /**
     * 实收金额
     *
     * */
    private BigDecimal realIncomePrice;
    /**
     * 节省金额
     *
     * */
    private BigDecimal saveMonyPrice;

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

    public String getProxyPin() {
        return proxyPin;
    }

    public void setProxyPin(String proxyPin) {
        this.proxyPin = proxyPin;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(Integer isDelivery) {
        this.isDelivery = isDelivery;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public BigDecimal getPayablePrice() {
        return payablePrice;
    }

    public void setPayablePrice(BigDecimal payablePrice) {
        this.payablePrice = payablePrice;
    }

    public BigDecimal getRealIncomePrice() {
        return realIncomePrice;
    }

    public void setRealIncomePrice(BigDecimal realIncomePrice) {
        this.realIncomePrice = realIncomePrice;
    }

    public BigDecimal getSaveMonyPrice() {
        return saveMonyPrice;
    }

    public void setSaveMonyPrice(BigDecimal saveMonyPrice) {
        this.saveMonyPrice = saveMonyPrice;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
