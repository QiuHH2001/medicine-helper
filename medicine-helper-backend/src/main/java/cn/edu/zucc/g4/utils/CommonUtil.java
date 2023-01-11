package cn.edu.zucc.g4.utils;

import java.util.Date;

public class CommonUtil {
    public static Boolean isToday(Date someday) {
        Date today = new Date();
        return (today.getYear() == someday.getYear()) && (today.getMonth() == someday.getMonth()) && (today.getDay() == someday.getDay());
    }
}
