package com.xyhmo.commom.enums;

/**
 * 业务异常枚举
 */
public enum BusiessExceptionEnum {

    PROJECT_ORDER_WORKER_APPLYERROR(-100001,"该工程人数已招满"),
    PROJECT_ORDER_REDIS_GETBYPROJECTORDERID(-100002,"该工程人数已招满，请看看其他招工信息"),
    PROJECT_ORDER_IS_APPLY(-100003,"该招工信息您已申报"),
    PROJECT_ORDER_SUREWORKERLIST_ERROR(-100004,"确认干活工人失败"),
    PROJECT_ORDER_WORKER_IS_NOT_FULL(-100005,"报工人数还未报满");
    private int code;
    private String desc;

    BusiessExceptionEnum(int code, String desc) {
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
        for(BusiessExceptionEnum node:values()){
            if(node.getCode()==code){
                return node.getDesc();
            }
        }
        return "";
    }
}
