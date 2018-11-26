package com.xyhmo.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserTrade implements Serializable{

    private static final long serialVersionUID = -8814888018570273498L;

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
     * 所有交易总金额
     *
     * */
    private BigDecimal tradeTotalPrice;
    /**
     * 所有已支付金额
     *
     * */
    private BigDecimal tradePaidPrice;
    /**
     * 所有未支付金额
     *
     **/
    private BigDecimal tradeUnpaidPrice;
    /**
     * 今年交易总金额
     *
     * */
    private BigDecimal tradeYearTotalPrice;
    /**
     * 今年已支付金额
     *
     * */
    private BigDecimal tradeYearPaidPrice;
    /**
     * 今年未支付金额
     *
     * */
    private BigDecimal tradeYearUnpaidPrice;

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

    public BigDecimal getTradeTotalPrice() {
        return tradeTotalPrice;
    }

    public void setTradeTotalPrice(BigDecimal tradeTotalPrice) {
        this.tradeTotalPrice = tradeTotalPrice;
    }

    public BigDecimal getTradePaidPrice() {
        return tradePaidPrice;
    }

    public void setTradePaidPrice(BigDecimal tradePaidPrice) {
        this.tradePaidPrice = tradePaidPrice;
    }

    public BigDecimal getTradeUnpaidPrice() {
        return tradeUnpaidPrice;
    }

    public void setTradeUnpaidPrice(BigDecimal tradeUnpaidPrice) {
        this.tradeUnpaidPrice = tradeUnpaidPrice;
    }

    public BigDecimal getTradeYearTotalPrice() {
        return tradeYearTotalPrice;
    }

    public void setTradeYearTotalPrice(BigDecimal tradeYearTotalPrice) {
        this.tradeYearTotalPrice = tradeYearTotalPrice;
    }

    public BigDecimal getTradeYearPaidPrice() {
        return tradeYearPaidPrice;
    }

    public void setTradeYearPaidPrice(BigDecimal tradeYearPaidPrice) {
        this.tradeYearPaidPrice = tradeYearPaidPrice;
    }

    public BigDecimal getTradeYearUnpaidPrice() {
        return tradeYearUnpaidPrice;
    }

    public void setTradeYearUnpaidPrice(BigDecimal tradeYearUnpaidPrice) {
        this.tradeYearUnpaidPrice = tradeYearUnpaidPrice;
    }
}
