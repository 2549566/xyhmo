package com.xyhmo.commom.enums;

/**
 * 正则表达式枚举
 *
 * */
public enum ReturnEnum {

    RETURN_SUCCESS(1,"成功"),
    RETURN_MOBILE_CHECK_CODE(2001,"获取验证码成功");
    private int code;
    private String desc;

    ReturnEnum(int code, String desc) {
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



    public static String getNodeDesc(Integer code){
        if(null == code){
            return "";
        }
        for(ReturnEnum node:values()){
            if(node.getCode()==code){
                return node.getDesc();
            }
        }
        return "";
    }
}
