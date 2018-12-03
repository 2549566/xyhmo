package com.xyhmo.domain;

import java.io.Serializable;

public class GenOrder implements Serializable{

    private static final long serialVersionUID = -3835814076393043988L;

    /**
     * id
     *
     * */
    private Long id;
    /**
     * order 名称
     *
     * */
    private String orderName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
}
