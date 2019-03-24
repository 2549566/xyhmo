package com.xyhmo.commom.enums;

/**
 * 商品类型枚举
 *
 * */
public enum DateEnum {

    DATE_PROJECTORDER_INITDAY(10,"招工订单缓存中的初始化天数"),
    DATE_PROJECTORDER_ADDDAY(15,"招工订单缓存中增加的天数");
    private int code;
    private String desc;

    DateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



    public static String getSkuTypeDesc(Integer code){
        if(null == code){
            return "";
        }
        for(DateEnum node:values()){
            if(node.getCode()==code){
                return node.getDesc();
            }
        }
        return "";
    }
}
