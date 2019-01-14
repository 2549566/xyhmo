package com.xyhmo.commom.enums;

/**
 * 商品类型枚举
 *
 * */
public enum CityEnum {

    SKU_JUANCAI("BJ","北京"),
    PROJECT_CITY("PROJECTBJ","北京");
    private String code;
    private String desc;

    CityEnum(String code, String desc) {
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



    public static String getCityDesc(String code){
        if(null == code){
            return "";
        }
        for(CityEnum node:values()){
            if(node.getCode()==code){
                return node.getDesc();
            }
        }
        return "";
    }
}
