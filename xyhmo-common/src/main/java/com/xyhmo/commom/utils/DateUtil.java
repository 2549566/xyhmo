package com.xyhmo.commom.utils;

import java.util.Date;

public class DateUtil {

    public static Long getDatePoor(Date endDate, Date nowDate) {

        long nd = 1000 * 24 * 60 * 60;//每天毫秒数
        long diff = endDate.getTime() - nowDate.getTime(); // 获得两个时间的毫秒时间差异

        long day = diff / nd;   // 计算差多少天

        return day;

    }
}
