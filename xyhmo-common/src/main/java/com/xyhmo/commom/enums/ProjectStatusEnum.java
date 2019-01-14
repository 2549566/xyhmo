package com.xyhmo.commom.enums;

/**
 * 工程状态
 * 0:未开始 1：正在进行中 2：工程暂停 5：工程不正常结束 10：工程结束
 *
 * */
public enum ProjectStatusEnum {

    PROJECT_NOT_START(0,"工程未开始"),
    PROJECT_HAVING(1,"工程进行中"),
    PROJECT_STOP(2,"工程暂停"),
    PROJECT_ERROR_END(5,"工程不正常结束"),
    PROJECT_END(10,"工程正常结束");
    private int code;
    private String desc;

    ProjectStatusEnum(int code, String desc) {
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



    public static String getProjectStatusEnum(Integer code){
        if(null == code){
            return "";
        }
        for(ProjectStatusEnum node:values()){
            if(node.getCode()==code){
                return node.getDesc();
            }
        }
        return "";
    }
}
