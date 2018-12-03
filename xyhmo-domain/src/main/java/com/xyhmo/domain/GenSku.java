package com.xyhmo.domain;

import java.io.Serializable;

public class GenSku implements Serializable{

    private static final long serialVersionUID = -3835814076393043988L;

    /**
     * id
     *
     * */
    private Long id;
    /**
     * sku 名称
     *
     * */
    private String skuName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }
}
