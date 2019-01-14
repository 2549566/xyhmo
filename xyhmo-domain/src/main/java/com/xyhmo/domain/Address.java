package com.xyhmo.domain;


import java.io.Serializable;

/**
 * 地址
 *
 * */
public class Address implements Serializable{

    private static final long serialVersionUID = -3601015773257244340L;
    /**
     * id
     *
     * */
    private Long id;
    /**
     * 父ID
     *
     * */
    private String pid;
    /**
     * 城市名
     *
     * */
    private String cityName;
    /**
     * 地址类型
     *
     * */
    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
