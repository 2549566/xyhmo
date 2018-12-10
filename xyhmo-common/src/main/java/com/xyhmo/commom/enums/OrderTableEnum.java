package com.xyhmo.commom.enums;

/**
 * 商品类型枚举
 *
 * */
public enum OrderTableEnum {

    ORDER_TABLE_COUNT(4,"订单表数量"),
    ORDER_WARE_TABLE_COUNT(4,"订单关联表，订单中的商品表的数量");
    private int code;
    private String desc;

    OrderTableEnum(int code, String desc) {
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



    public static String OrderTableDesc(Integer code){
        if(null == code){
            return "";
        }
        for(OrderTableEnum node:values()){
            if(node.getCode()==code){
                return node.getDesc();
            }
        }
        return "";
    }
}
