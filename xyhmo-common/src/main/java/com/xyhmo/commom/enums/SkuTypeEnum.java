package com.xyhmo.commom.enums;

/**
 * 商品类型枚举
 *
 * */
public enum SkuTypeEnum {

    SKU_JUANCAI(1,"卷材"),
    SKU_TULIAO(2,"涂料"),
    SKU_MEIQI(3,"煤气"),
    SKU_OTHER(10,"其他");
    private int code;
    private String desc;

    SkuTypeEnum(int code, String desc) {
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
        for(SkuTypeEnum node:values()){
            if(node.getCode()==code){
                return node.getDesc();
            }
        }
        return "";
    }
}
