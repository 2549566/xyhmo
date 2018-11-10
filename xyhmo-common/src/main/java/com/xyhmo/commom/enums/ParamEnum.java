package com.xyhmo.commom.enums;

/**
 * 正则表达式枚举
 *
 * */
public enum ParamEnum {

    PARAM_ERROR(-1,"参数异常"),
    PARAM_MOBILE_RULE(1001,"手机号格式错误"),
    PARAM_CODE_RULE(2001,"验证码格式错误"),
    PARAM_CODE_OVER(2002,"验证码过期");
    private int code;
    private String desc;

    ParamEnum(int code, String desc) {
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
        for(ParamEnum node:values()){
            if(node.getCode()==code){
                return node.getDesc();
            }
        }
        return "";
    }
}
