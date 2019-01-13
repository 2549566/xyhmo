package com.xyhmo.query.project;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建工程订单入参
 *
 * */
public class ProjectCreateReq implements Serializable{
    private static final long serialVersionUID = -4688737788221343286L;
    /**
     * token
     * 用户登录校验
     * */
    private String token;
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
     * 工程描述
     *
     * */
    private String describe;
    /**
     * 工程坐标
     *
     * */
    private String coordinate;
    /**
     * 省份ID
     *
     * */
    private Integer provinceId;
    /**
     * 市区ID
     *
     * */
    private Integer cityId;
    /**
     * 县城ID
     *
     * */
    private Integer countyId;
    /**
     * 省份名称
     *
     * */
    private String provinceName;
    /**
     * 市区名称
     *
     * */
    private String citName;
    /**
     * 县城名称
     *
     * */
    private String countyName;
    /**
     * 详细地址
     *
     * */
    private String addressDetail;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCitName() {
        return citName;
    }

    public void setCitName(String citName) {
        this.citName = citName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }
}
