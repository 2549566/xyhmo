package com.xyhmo.domain;

import com.xyhmo.commom.model.BaseModel;

import java.io.Serializable;

/**
 * 快报对象
 *
 * */
public class Bulletin extends BaseModel implements Serializable{

    private static final long serialVersionUID = 7594372697443064846L;

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
     * 快报排序
     *
     * */
    private Integer sort;
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
     * 快报名称
     *
     * */
    private Integer name;
    /**
     *  快报描述
     *
     * */
    private String desc;
    /**
     *  快报内容
     *
     * */
    private String content;

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
