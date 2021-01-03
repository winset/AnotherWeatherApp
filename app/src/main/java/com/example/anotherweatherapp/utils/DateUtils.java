package com.example.anotherweatherapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String format(long time){
        Date date = new Date(time*1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
         return dateFormat.format(date);
    }

    public static String getDayOfWeekFromDate(long date){
        Date date1 =new Date(date*1000);
        SimpleDateFormat dateFormat =new SimpleDateFormat("EEE, dd MMM",Locale.getDefault());
        return dateFormat.format(date1);
    }
}
