package com.xyhmo.vo.project;

import com.xyhmo.commom.model.BaseModel;
import com.xyhmo.domain.ProjectLeaderWith;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 该类用于展示我发布的工程列表
 *
 * */
public class ProjectLeaderVo extends BaseModel implements Serializable {
    private static final long serialVersionUID = 4064864271218038978L;

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
     * 手机号
     */
    private String mobileNumber;
    /**
     * 工程描述
     *
     * */
    private String describe;
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
     * 发布工程的工人列表
     *
     * */
    private List<ProjectLeaderWith> projectLeaderWithList;

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

    public Double getEveryDaySalary() {
        return everyDaySalary;
    }

    public void setEveryDaySalary(Double everyDaySalary) {
        this.everyDaySalary = everyDaySalary;
    }

    public Date getProjectStartTime() {
        return projectStartTime;
    }

    public void setProjectStartTime(Date projectStartTime) {
        this.projectStartTime = projectStartTime;
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

    public List<ProjectLeaderWith> getProjectLeaderWithList() {
        return projectLeaderWithList;
    }

    public void setProjectLeaderWithList(List<ProjectLeaderWith> projectLeaderWithList) {
        this.projectLeaderWithList = projectLeaderWithList;
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
}
