package com.xyhmo.commom.utils;

import org.apache.commons.lang.StringUtils;

public class TableNameUtil {

    public static String genOrderTabeleName(String pin){
        if(StringUtils.isBlank(pin)){
            return "";
        }
        return "order_bj_"+ HashCodeUtil.toHash(pin)%4;
    }

    public static String genOrderWareTabeleName(String pin){
        if(StringUtils.isBlank(pin)){
            return "";
        }
        return "order_ware_bj_"+ HashCodeUtil.toHash(pin)%4;
    }

    public static String genProjectLeaderTableName(String pin) {
        if(StringUtils.isBlank(pin)){
            return "";
        }
        return "project_leader_"+ HashCodeUtil.toHash(pin)%4;
    }
    /**
     * 干活工人申报
     * 用招工发布人的pin来分表
     *
     * */
    public static String genProjectLeaderWithTableName(String pin) {
        if(StringUtils.isBlank(pin)){
            return "";
        }
        return "project_leader_with_"+ HashCodeUtil.toHash(pin)%4;
    }
    /**
     * 工人查看自己的订单信息
     * 用工人自己的pin去分表
     *
     * */
    public static String genProjectWorkerTableName(String pin) {
        if(StringUtils.isBlank(pin)){
            return "";
        }
        return "project_worker_"+ HashCodeUtil.toHash(pin)%4;
    }
}
