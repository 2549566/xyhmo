package com.xyhmo.commom.enums;

/**
 * 正则表达式枚举
 *
 * */
public enum ParamEnum {

    PARAM_ERROR(-1,"参数异常"),
    PARAM_MOBILE_RULE(-1001,"手机号格式错误"),
    PARAM_CODE_RULE(-2001,"验证码格式错误"),
    PARAM_CODE_OVER(-2002,"验证码错误或已过期，请重新获取验证码"),
    PARAM_GEN_CODE(-2003,"生成验证码错误"),
    PARAM_IP_RULE(-3001,"ip格式错误"),
    PARAM_TOKEN_NOT_EXIST(-4002,"token不存在"),
    PARAM_TOKEN_IS_EMPTY(-4003,"token入参为空"),
    PARAM_PAGE_ERROR(-5001,"页码不能为空，并且不能小于1"),
    PARAM_DEFAULT_PAGESIZE(20,"默认pageSize"),
    PARAM_DATA_USED(1,"数据有效");
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
