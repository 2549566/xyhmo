package com.xyhmo.domain;

import com.xyhmo.commom.model.BaseModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 该类是发布工程人的订单的详细信息。用于工人申请加入该工程
 *
 * */
public class ProjectLeaderWith extends BaseModel implements Serializable{

    private static final long serialVersionUID = 8582955021850628995L;

    /**
     * id
     *
     * */
    private Long id;
    /**
     * 订单ID
     *
     * */
    private String orderId;
    /**
     * 发布订单的pin
     *
     * */
    private String pin;

    /**
     * 干活的工人pin
     *
     * */
    private String workerPin;
    /**
     * 每天工资
     *
     * */
    private BigDecimal everyDaySalary;
    /**
     * 干活的总天数
     *
     * */
    private Integer totalDay;
    /**
     * 总工资
     *
     * */
    private BigDecimal totaSalary;
    /**
     * 干活工人的电话号码
     *
     * */
    private String workerMoblie;
    /**
     * 发布订单的人给干活工人的评分
     *
     * */
    private Double score;
    /**
     * 1:期待下次合作 0：不期待
     *
     * */
    private Integer nextCooperation;
    /**
     * 工人名称
     *
     * */
    private String workerName;

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

    public String getWorkerPin() {
        return workerPin;
    }

    public void setWorkerPin(String workerPin) {
        this.workerPin = workerPin;
    }

    public Integer getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(Integer totalDay) {
        this.totalDay = totalDay;
    }

    public String getWorkerMoblie() {
        return workerMoblie;
    }

    public void setWorkerMoblie(String workerMoblie) {
        this.workerMoblie = workerMoblie;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getNextCooperation() {
        return nextCooperation;
    }

    public void setNextCooperation(Integer nextCooperation) {
        this.nextCooperation = nextCooperation;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public BigDecimal getEveryDaySalary() {
        return everyDaySalary;
    }

    public void setEveryDaySalary(BigDecimal everyDaySalary) {
        this.everyDaySalary = everyDaySalary;
    }

    public BigDecimal getTotaSalary() {
        return totaSalary;
    }

    public void setTotaSalary(BigDecimal totaSalary) {
        this.totaSalary = totaSalary;
    }
}
