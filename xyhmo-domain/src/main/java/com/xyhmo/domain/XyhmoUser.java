package com.xyhmo.domain;


import java.io.Serializable;

public class XyhmoUser implements Serializable{
    /**
     * id 主键
     *
     * */
    private Long id;
    /**
     *  牧场别名，关联牧场CODE
     *
     * */
    private String pin;
    /**
     * 牧场类型：1鸡舍 2：牛棚 3：羊圈 4：猪圈
     *
     * */
    private String mobile;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
