package com.xyhmo.commom.enums;

/**
 * 用户类型枚举
 *
 * */
public enum UserTypeEnum {

    WORKER(3,"业务员"),
    MANAGER(0,"系统管理员"),
    PROXY(2,"代理商"),
    PROXY_STAFF(21,"代理商维护人员"),
    VENDER(1,"厂商"),
    VENDER_STAFF(11,"厂商维护人员");
    private int code;
    private String desc;

    UserTypeEnum(int code, String desc) {
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



    public static String getUserTypeDesc(Integer code){
        if(null == code){
            return "";
        }
        for(UserTypeEnum node:values()){
            if(node.getCode()==code){
                return node.getDesc();
            }
        }
        return "";
    }
}
