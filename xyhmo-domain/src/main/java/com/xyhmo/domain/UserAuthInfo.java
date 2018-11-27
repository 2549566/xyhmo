package com.xyhmo.domain;

import com.xyhmo.commom.model.BaseModel;

import java.io.Serializable;

public class UserAuthInfo extends BaseModel implements Serializable{

    private static final long serialVersionUID = 1027381343484762446L;

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
     * 坐标
     *
     * */
    private String coordinate;
    /**
     * 地址
     *
     * */
    private String address;

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
}
