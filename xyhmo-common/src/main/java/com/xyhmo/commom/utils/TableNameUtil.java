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

    public static String genProjectOrderTableName(String pin) {
        if(StringUtils.isBlank(pin)){
            return "";
        }
        return "project_leader_"+ HashCodeUtil.toHash(pin)%4;
    }
}
