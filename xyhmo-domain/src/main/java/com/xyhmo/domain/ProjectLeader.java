package com.xyhmo.domain;

import com.xyhmo.commom.model.BaseModel;
import java.io.Serializable;
import java.util.Date;

/**
 * 该类是发布工程人的订单信息
 *
 * */
public class ProjectLeader extends BaseModel implements Serializable{

    private static final long serialVersionUID = -3207388033871460439L;

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
     * 工程坐标
     *
     * */
    private String coordinate;
    /**
     * 工程地址
     *
     * */
    private String address;
    /**
     * 用户姓名
     *
     * */
    private String userName;
    /**
     * 工程标题
     *
     * */
    private String projectTitle;
    /**
     * 工程需要人数
     *
     * */
    private Integer projecNeedWorker;
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
    private Date projecStartTime;
    /**
     * 工程结束日期
     *
     * */
    private Date projectEnTime;
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
     * 手机号
     */
    private String mobileNumber;
    /**
     * 工程描述
     *
     * */
    private String describe;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public Integer getProjecNeedWorker() {
        return projecNeedWorker;
    }

    public void setProjecNeedWorker(Integer projecNeedWorker) {
        this.projecNeedWorker = projecNeedWorker;
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

    public Date getProjecStartTime() {
        return projecStartTime;
    }

    public void setProjecStartTime(Date projecStartTime) {
        this.projecStartTime = projecStartTime;
    }

    public Date getProjectEnTime() {
        return projectEnTime;
    }

    public void setProjectEnTime(Date projectEnTime) {
        this.projectEnTime = projectEnTime;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
