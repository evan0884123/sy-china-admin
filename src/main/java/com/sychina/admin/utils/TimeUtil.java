package com.sychina.admin.utils;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static Long getFirstDayOfWeek(){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK,2);
        return calendar.getTimeInMillis();
    }

    public static Long getLastDayOfWeek(){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK,2);
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE) + 6);
        return calendar.getTimeInMillis();
    }

    public static Long getCurrentMonthFirstDay(){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MONTH,0);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        return calendar.getTimeInMillis();
    }

    public static Long getCurrentMonthLastDay(){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH,1);
        calendar.set(Calendar.DAY_OF_MONTH,0);
        return calendar.getTimeInMillis();
    }
}
