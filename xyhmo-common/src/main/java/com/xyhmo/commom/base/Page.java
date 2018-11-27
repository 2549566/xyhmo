package com.xyhmo.commom.base;

import java.io.Serializable;

public class Page implements Serializable{

    private static final long serialVersionUID = 7254797666692204214L;

    /**
     * 页码
     *
     * */
    private Integer pageNum;

    /**
     * 页大小
     *
     * */
    private Integer pageSize;

    public void setPageNum(Integer pageNum) {
        this.pageNum = (this.pageNum != null ? this.pageNum : pageNum);
        if (this.pageNum < 1) {
            this.pageNum = 1;
        }
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = (this.pageSize != null ? this.pageSize : pageSize);
        if (this.pageSize > 100) {
            this.pageSize = 100;
        }
    }

    /**
     * 限制外部传入页码不可以小于1
     * @param pageNum
     * @return
     */
    public Integer getPageNum(int pageNum) {
        this.pageNum = (this.pageNum != null ? this.pageNum : pageNum);
        if (this.pageNum < 1) {
            this.pageNum = 1;
        }
        return this.pageNum;
    }
    /**
     * 限制外部传入页大小不可以大于100，防止对后端服务造成影响
     * @param pageSize
     * @return
     */
    public Integer getPageSize(int pageSize) {
        this.pageSize = (this.pageSize != null ? this.pageSize : pageSize);
        if (this.pageSize > 100) {
            this.pageSize = 100;
        }
        return this.pageSize;
    }
}
