package com.xyhmo.commom.enums;

/**
 * 正则表达式枚举
 *
 * */
public enum RegularEnum {

    REGULAR_POSITIVE_INTEGER("^[0-9]*[1-9][0-9]*$","正整数");
    private String code;
    private String desc;

    RegularEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



    public static String getSkuTypeDesc(String code){
        if(null == code){
            return "";
        }
        for(RegularEnum node:values()){
            if(node.getCode()==code){
                return node.getDesc();
            }
        }
        return "";
    }
}
