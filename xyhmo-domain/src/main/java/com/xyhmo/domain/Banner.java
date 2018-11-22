package com.xyhmo.domain;

import com.xyhmo.commom.model.BaseModel;

import java.io.Serializable;

/**
 * 轮播图对象
 *
 * */
public class Banner extends BaseModel implements Serializable{

    private static final long serialVersionUID = -6081198718686354335L;
    /**
     * id
     *
     * */
    private Long id;
    /**
     * id
     *
     * */
    private String pin;
    /**
     * 轮播图排序
     *
     * */
    private Integer sort;
    /**
     * 图片地址
     *
     * */
    private String imgPath;
    /**
     * 详情路径
     *
     * */
    private String detailPath;
    /**
     * 角色类型
     *
     * */
    private Integer roleType;
    /**
     * 轮播图名称
     *
     * */
    private Integer name;
    /**
     *  轮播图描述
     *
     * */
    private String desc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getDetailPath() {
        return detailPath;
    }

    public void setDetailPath(String detailPath) {
        this.detailPath = detailPath;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
