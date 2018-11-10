package com.xyhmo.commom.model;

import java.io.Serializable;
import java.util.Date;

public class BaseModel implements Serializable{
    /**
     *  创建时间
     *
     * */
    private Date created;
    /**
     * 修改时间
     *
     * */
    private Date modified;
    /**
     * 创建人
     *
     * */
    private String creator;
    /**
     * 修改人
     *
     * */
    private String modifier;
    /**
     * 数据状态 1：正常 -1：删除
     *
     * */
    private Integer status;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
