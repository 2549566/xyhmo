package com.xyhmo.vo.project;

import java.io.Serializable;
import java.util.Date;

public class ProjectWorkerTimeVo implements Serializable{

    private static final long serialVersionUID = 7735601344884753812L;

    /**
     * 干活工人pin
     *
     *
     * */
    private String pin;
    /**
     * 开始工程时间
     *
     * */
    private Date startDate;
    /**
     * 结束工程时间
     *
     * */
    private Date endDate;

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
