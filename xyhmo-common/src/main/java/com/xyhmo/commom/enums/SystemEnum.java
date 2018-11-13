package com.xyhmo.commom.enums;

import com.xyhmo.commom.service.Contants;

/**
 * 正则表达式枚举
 *
 * */
public enum SystemEnum {

    SYSTEM_ERROR(-1,"系统错误"),
    SYSTEM_GEN_CODE_ERROR(-2003,"生成验证码错误"),
    SYSTEM_IP_SAFE(-3003,"同一个手机每天最多只能获取"+ Contants.CHECK_IP_TIMES+"次验证码"),
    SYSTEM_MOBILE_SAFE(-1003,"同一个手机号每天最多只能获取"+Contants.CHECK_MOBILE_TIMES_EVERYDAY+"次验证码"),
    SYSTEM_GEN_TOKEN_ERROR(-4001,"生成token失败");
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
