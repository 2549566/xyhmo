package com.xyhmo.domain;

import com.xyhmo.commom.model.BaseModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 该类是招工订单的全部信息，放入缓存中
 *
 * */
public class ProjectOrderVo extends BaseModel implements Serializable{

    private static final long serialVersionUID = 8017565364418500607L;

    private String tableName;

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
    private BigDecimal everyDaySalary;
    /**
     * 工程开始日期
     *
     * */
    private String projectStartTime;
    /**
     * 工程结束日期
     *
     * */
    private String projectEndTime;
    /**
     * 0:未开始 1：正在进行中 2：工程暂停 5：工程不正常结束 10：工程结束
     *
     * */
    private Integer projectStatus;
    /**
     * 工程总付款金额
     *
     * */
    private BigDecimal projectTotalPay;
    /**
     * 手机号
     */
    private String mobileNumber;
    /**
     * 工程描述
     *
     * */
    private String description;
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
    private String cityName;
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
    /**
     * 完整地址
     *
     * */
    private String completeAddress;
    /**
     * 当前工程招工人数
     *
     * */
    private Integer currentWorkerNumber;
    /**
     * 工人报单列表
     *
     * */
    private List<ProjectLeaderWith> projectLeaderWithList;

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

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
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

    public Integer getProjectNeedWorker() {
        return projectNeedWorker;
    }

    public void setProjectNeedWorker(Integer projectNeedWorker) {
        this.projectNeedWorker = projectNeedWorker;
    }

    public Integer getProjectNeedDay() {
        return projectNeedDay;
    }

    public void setProjectNeedDay(Integer projectNeedDay) {
        this.projectNeedDay = projectNeedDay;
    }

    public String getProjectStartTime() {
        return projectStartTime;
    }

    public void setProjectStartTime(String projectStartTime) {
        this.projectStartTime = projectStartTime;
    }

    public String getProjectEndTime() {
        return projectEndTime;
    }

    public void setProjectEndTime(String projectEndTime) {
        this.projectEndTime = projectEndTime;
    }

    public Integer getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

    public String getCompleteAddress() {
        return completeAddress;
    }

    public void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }

    public List<ProjectLeaderWith> getProjectLeaderWithList() {
        return projectLeaderWithList;
    }

    public void setProjectLeaderWithList(List<ProjectLeaderWith> projectLeaderWithList) {
        this.projectLeaderWithList = projectLeaderWithList;
    }

    public BigDecimal getEveryDaySalary() {
        return everyDaySalary;
    }

    public void setEveryDaySalary(BigDecimal everyDaySalary) {
        this.everyDaySalary = everyDaySalary;
    }

    public BigDecimal getProjectTotalPay() {
        return projectTotalPay;
    }

    public void setProjectTotalPay(BigDecimal projectTotalPay) {
        this.projectTotalPay = projectTotalPay;
    }

    public Integer getCurrentWorkerNumber() {
        return currentWorkerNumber;
    }

    public void setCurrentWorkerNumber(Integer currentWorkerNumber) {
        this.currentWorkerNumber = currentWorkerNumber;
    }
}
