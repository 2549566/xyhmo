package com.xyhmo.commom.enums;

/**
 * 商品类型枚举
 *
 * */
public enum DeliveryEnum {

    IS_DELIVERY(1,"配送"),
    IS_NOT_DELIVERY(0,"不配送");
    private Integer code;
    private String desc;

    DeliveryEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



    public static String getDeliveryDesc(Integer code){
        if(null == code){
            return "";
        }
        for(DeliveryEnum node:values()){
            if(node.getCode()==code){
                return node.getDesc();
            }
        }
        return "";
    }
}
