package com.xyhmo.domain;


import java.io.Serializable;
import java.util.Date;

public class UserEvaluate implements Serializable{

    private static final long serialVersionUID = -4805796134937438169L;

    /**
     * id
     *
     */
    private Long id;
    /**
     * pin 用户唯一pin
     *
     */
    private String pin;
    /**
     * 订单ID
     *
     */
    private String orderId;
    /**
     * 客户评分
     *
     *
     */
    private Integer score;
    /**
     * 客户唯一pin
     *
     */
    private String customerPin;
    /**
     *  创建时间
     *
     * */
    private Date created;
    /**
     * 修改时间
     *
     * */
    private Date modified;
    /**
     * 数据状态 1：正常 -1：删除
     *
     * */
    private Integer status;
    /**
     * 评价内容
     *
     *
     */
    private String evaluateContent;

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCustomerPin() {
        return customerPin;
    }

    public void setCustomerPin(String customerPin) {
        this.customerPin = customerPin;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEvaluateContent() {
        return evaluateContent;
    }

    public void setEvaluateContent(String evaluateContent) {
        this.evaluateContent = evaluateContent;
    }
}
