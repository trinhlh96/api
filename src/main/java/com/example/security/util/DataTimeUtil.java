package com.example.security.util;

import java.util.Calendar;

public class DataTimeUtil {
    public static long getCurrentTimeInMLS(){
        return Calendar.getInstance().getTimeInMillis();
    }
    public static long getTimeAfterDay(int day){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        return calendar.getTimeInMillis();
    }
}
