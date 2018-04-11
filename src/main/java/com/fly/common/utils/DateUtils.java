package com.fly.common.utils;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理
 * <p>
 * @author liufei
 */
public class DateUtils {
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        }
        return null;
    }

    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String formatTime(Date date) {
        if (date != null) {
            return format(date, DATE_TIME_PATTERN);
        }
        return null;
    }

    /**
     * 对分钟进行加减
     * @param date
     * @param minutes
     * @return
     */
    public static Date addDateMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }
}
