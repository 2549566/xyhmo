package com.xyhmo.domain;


import com.xyhmo.commom.model.BaseModel;

import java.io.Serializable;

public class UserInfo extends BaseModel implements Serializable{

    private static final long serialVersionUID = 143681554461930233L;
    /**
     * id 主键
     *
     * */
    private Long id;
    /**
     * 用户唯一pin，注册时生成
     *
     * */
    private String pin;
    /**
     * 用户登录后token
     *
     * */
    private String token;
    /**
     * 用户手机号
     *
     * */
    private String mobileNumber;
    /**
     * 用户名
     *
     * */
    private String userName;
    /**
     * 用户密码
     *
     * */
    private String password;
    /**
     * 用户类型
     *
     * */
    private Integer userType;
    /**
     * 绑定的商家PIN
     *
     */
    private String bindVender;
    /**
     * 绑定的代理商pin
     *
     * */
    private String bindVenderProxy;
    /**
     * 是否认证
     *
     * */
    private Integer isAuth;
    /**
     * 头像路径
     *
     * */
    private String imageHearder;
    /**
     * 评分
     *
     * */
    private Integer score;
    /**
     * 是否接受订单
     *
     * */
    private Integer isAcceptOrder;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getBindVender() {
        return bindVender;
    }

    public void setBindVender(String bindVender) {
        this.bindVender = bindVender;
    }

    public String getBindVenderProxy() {
        return bindVenderProxy;
    }

    public void setBindVenderProxy(String bindVenderProxy) {
        this.bindVenderProxy = bindVenderProxy;
    }

    public Integer getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Integer isAuth) {
        this.isAuth = isAuth;
    }

    public String getImageHearder() {
        return imageHearder;
    }

    public void setImageHearder(String imageHearder) {
        this.imageHearder = imageHearder;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getIsAcceptOrder() {
        return isAcceptOrder;
    }

    public void setIsAcceptOrder(Integer isAcceptOrder) {
        this.isAcceptOrder = isAcceptOrder;
    }
}
