package com.xyhmo.commom.enums;

/**
 * 订单状态枚举
 *订单状态：1：业务员已报单，并且代理商进入配送或者业务员取货状态 2：代理商已经点击确认 3：业务员点击确认结单（未支付） 4：业务员点击确认，并支付
 * */
public enum OrderTypeEnum {

    ORDER_YWY_SUBMIT(1,"业务员已报单"),
    ORDER_DLS_SURE(2,"代理商已经点击确认(材料已到业务员手中)"),
    ORDER_YWY_SURE_NOTPAY(3,"业务员点击确认结单（未支付）"),
    ORDER_YWY_SURE_PAY(4,"业务员点击确认，并支付");
    private int code;
    private String desc;

    OrderTypeEnum(int code, String desc) {
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



    public static String getOrderTypeEnum(Integer code){
        if(null == code){
            return "";
        }
        for(OrderTypeEnum node:values()){
            if(node.getCode()==code){
                return node.getDesc();
            }
        }
        return "";
    }
}
