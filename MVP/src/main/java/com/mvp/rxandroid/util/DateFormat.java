package com.mvp.rxandroid.util;

import java.util.Calendar;

/**
 * Created by elang on 16/5/23.
 * 处理各种时间格式
 */
public class DateFormat {

    /**
     * 通过时间戳得到 年 月 日
     *
     * @return 格式 nn年mm月dd日
     */
    public static String getDate(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        String string = null;
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        string = year + "年" + month + "月" + day + "日 ";
        return string;
    }


    /**
     * 倒计时
     *
     * @param second 秒
     * @return 12时34分56秒
     */
    public static String getSurplusTime(String second) {
        if (second.equals("")) {
            return "";
        }
        String string = null;
        int ss = Integer.parseInt(second);
        if (ss >= 3600) {
            string = ss / 3600 + "时" + (ss % 3600) / 60 + "分" + ss % 60 + "秒";
        } else if (ss >= 60 && ss < 3600) {
            string = (ss % 3600) / 60 + "分" + ss % 60 + "秒";
        } else {
            string = ss % 60 + "秒";
        }
        return string;
    }
}
