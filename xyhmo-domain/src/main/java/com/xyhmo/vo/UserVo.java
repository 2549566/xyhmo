package com.xyhmo.vo;


import com.xyhmo.commom.model.BaseModel;

import java.io.Serializable;
import java.util.Date;

public class UserVo extends BaseModel implements Serializable{

    private static final long serialVersionUID = -7142167768212454696L;
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
     * 真实姓名
     *
     * */
    private String realName;
    /**
     * 身份证正面图片路径
     *
     * */
    private String imageCardFace;
    /**
     * 身份证背面照片路径
     *
     * */
    private String imageCardBack;
    /**
     * 企业资质认证路径
     *
     * */
    private String imageCompanyQualiy;
    /**
     * 公司名称
     *
     * */
    private String companyName;
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
    /**
     * 用户认证创建时间
     *
     * */
    private Date authCreated;
    /**
     * 用户认证修改时间
     *
     * */
    private Date authModified;

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getImageCardFace() {
        return imageCardFace;
    }

    public void setImageCardFace(String imageCardFace) {
        this.imageCardFace = imageCardFace;
    }

    public String getImageCardBack() {
        return imageCardBack;
    }

    public void setImageCardBack(String imageCardBack) {
        this.imageCardBack = imageCardBack;
    }

    public String getImageCompanyQualiy() {
        return imageCompanyQualiy;
    }

    public void setImageCompanyQualiy(String imageCompanyQualiy) {
        this.imageCompanyQualiy = imageCompanyQualiy;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public Date getAuthCreated() {
        return authCreated;
    }

    public void setAuthCreated(Date authCreated) {
        this.authCreated = authCreated;
    }

    public Date getAuthModified() {
        return authModified;
    }

    public void setAuthModified(Date authModified) {
        this.authModified = authModified;
    }
}
