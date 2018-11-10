package com.xyhmo.commom.enums;

/**
 * 正则表达式枚举
 *
 * */
public enum SystemEnum {

    PARAM_ERROR(-1,"系统错误"),
    PARAM_CODE_OVER(-2003,"生成验证码错误");
    private int code;
    private String desc;

    SystemEnum(int code, String desc) {
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
        for(SystemEnum node:values()){
            if(node.getCode()==code){
                return node.getDesc();
            }
        }
        return "";
    }
}
