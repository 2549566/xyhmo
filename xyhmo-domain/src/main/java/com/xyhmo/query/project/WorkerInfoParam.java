package com.xyhmo.query.project;

import java.io.Serializable;

/**
 * 业务员确认工程干活工人列表入参
 *
 * */
public class WorkerInfoParam implements Serializable{

    private static final long serialVersionUID = 1963897425091943009L;

    /**
     * pin
     *
     * */
    private String pin;
    /**
     * 工人姓名
     *
     * */
    private String realName;

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
}
