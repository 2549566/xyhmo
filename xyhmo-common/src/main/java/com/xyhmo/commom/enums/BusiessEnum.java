package com.xyhmo.commom.enums;

/**
 * 业务枚举
 */
public enum BusiessEnum {

    WARE_TYPE_JUANCAI(1,"卷材"),
    WARE_TYPE_MEIQI(2,"煤气"),
    WARE_TYPE_TULIAO(3,"涂料"),
    WARE_TYPE_OTHER(10,"其他");
    private int code;
    private String desc;

    BusiessEnum(int code, String desc) {
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



    public static String getCodeDesc(Integer code){
        if(null == code){
            return "";
        }
        for(BusiessEnum node:values()){
            if(node.getCode()==code){
                return node.getDesc();
            }
        }
        return "";
    }
}
