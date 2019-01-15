package com.xyhmo.domain;

import com.xyhmo.commom.model.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * 该类是干活工人的订单类，用于干活工人的订单展示
 *
 * */
public class ProjectWorker extends BaseModel implements Serializable{
    private static final long serialVersionUID = -3402774045392131101L;
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
     * 干活工人的pin
     *
     * */
    private String pin;
    /**
     * 发布该订单的人
     *
     * */
    private String leadePin;
    /**
     * 发布该订单人的电话号码
     *
     * */
    private String leaderMobile;
    /**
     * 发布订单的人，对该工人的评分
     *
     * */
    private Double score;
    /**
     * 1:期待下次合作 0：不期待
     *
     * */
    private Integer nextCooperation;
    /**
     * 工程坐标
     *
     * */
    private String coordinate;
    /**
     * 工程地址
     *
     * */
    private String completeAddress;
    /**
     * 工程标题
     *
     * */
    private String projectTitle;
    /**
     * 工程需要人数
     *
     * */
    private Integer projectNeedWorker;
    /**
     * 工程天数
     *
     * */
    private Integer projectNeedDay;
    /**
     * 每天工人工资
     *
     * */
    private Double everyDaySalary;
    /**
     * 工程开始日期
     *
     * */
    private Date projectStartTime;
    /**
     * 工程结束日期
     *
     * */
    private Date projectEndTime;
    /**
     * 0:未开始 1：正在进行中 2：工程暂停 5：工程不正常结束 10：工程结束
     *
     * */
    private Integer projectStatus;
    /**
     * 工程总付款金额
     *
     * */
    private Double projectTotalPay;
    /**
     * 发布人的名称
     *
     * */
    private String leaderName;
    /**
     * 工程描述
     *
     * */
    private String description;

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

    public String getLeadePin() {
        return leadePin;
    }

    public void setLeadePin(String leadePin) {
        this.leadePin = leadePin;
    }

    public String getLeaderMobile() {
        return leaderMobile;
    }

    public void setLeaderMobile(String leaderMobile) {
        this.leaderMobile = leaderMobile;
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

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public Integer getProjectNeedWorker() {
        return projectNeedWorker;
    }

    public void setProjectNeedWorker(Integer projectNeedWorker) {
        this.projectNeedWorker = projectNeedWorker;
    }

    public Date getProjectStartTime() {
        return projectStartTime;
    }

    public void setProjectStartTime(Date projectStartTime) {
        this.projectStartTime = projectStartTime;
    }

    public Integer getProjectNeedDay() {
        return projectNeedDay;
    }

    public void setProjectNeedDay(Integer projectNeedDay) {
        this.projectNeedDay = projectNeedDay;
    }

    public Double getEveryDaySalary() {
        return everyDaySalary;
    }

    public void setEveryDaySalary(Double everyDaySalary) {
        this.everyDaySalary = everyDaySalary;
    }

    public Date getProjectEndTime() {
        return projectEndTime;
    }

    public void setProjectEndTime(Date projectEndTime) {
        this.projectEndTime = projectEndTime;
    }

    public Integer getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Double getProjectTotalPay() {
        return projectTotalPay;
    }

    public void setProjectTotalPay(Double projectTotalPay) {
        this.projectTotalPay = projectTotalPay;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompleteAddress() {
        return completeAddress;
    }

    public void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }
}
