package com.xyhmo.vo.order;

import com.xyhmo.commom.model.BaseModel;
import com.xyhmo.domain.OrderWare;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OrderVo extends BaseModel implements Serializable{

    private static final long serialVersionUID = -8897824706002754817L;

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
    private Double deliveryPrice=0.00;
    /**
     * 订单状态：1：业务员已报单，并且代理商进入配送或者业务员取货状态 2：代理商已经点击确认 3：业务员点击确认结单（未支付） 4：业务员点击确认，并支付
     *
     * */
    private Integer orderStatus;
    /**
     * 应付金额
     * */
    private Double payablePrice=0.00;
    /**
     * 实收金额
     *
     * */
    private Double realIncomePrice=0.00;
    /**
     * 节省金额
     *
     * */
    private Double saveMonyPrice=0.00;
    /**
     * 是否一起支付
     *
     * */
    private Integer isTotalPay;
    /**
     * 总支付金额
     *
     * */
    private Double totalPayPrice=0.00;
    /**
     * 一起支付订单ID
     *
     * */
    private String totalPayOrderId;
    /**
     * 是否已支付
     * 1：已支付
     * 0：未支付
     * */
    private Integer isPay=0;
    /**
     * 商品list
     *
     * */
    private List<OrderWare> orderWareList;
    /**
     * 业务员驳回原因
     *
     * */
    private String rejectCase;
    /**
     * 描述
     *
     * */
    private String context;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

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

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getPayablePrice() {
        return payablePrice;
    }

    public void setPayablePrice(Double payablePrice) {
        this.payablePrice = payablePrice;
    }

    public Double getRealIncomePrice() {
        return realIncomePrice;
    }

    public void setRealIncomePrice(Double realIncomePrice) {
        this.realIncomePrice = realIncomePrice;
    }

    public Double getSaveMonyPrice() {
        return saveMonyPrice;
    }

    public void setSaveMonyPrice(Double saveMonyPrice) {
        this.saveMonyPrice = saveMonyPrice;
    }

    public Integer getIsTotalPay() {
        return isTotalPay;
    }

    public void setIsTotalPay(Integer isTotalPay) {
        this.isTotalPay = isTotalPay;
    }

    public Double getTotalPayPrice() {
        return totalPayPrice;
    }

    public void setTotalPayPrice(Double totalPayPrice) {
        this.totalPayPrice = totalPayPrice;
    }

    public String getTotalPayOrderId() {
        return totalPayOrderId;
    }

    public void setTotalPayOrderId(String totalPayOrderId) {
        this.totalPayOrderId = totalPayOrderId;
    }

    public List<OrderWare> getOrderWareList() {
        return orderWareList;
    }

    public void setOrderWareList(List<OrderWare> orderWareList) {
        this.orderWareList = orderWareList;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public String getRejectCase() {
        return rejectCase;
    }

    public void setRejectCase(String rejectCase) {
        this.rejectCase = rejectCase;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
