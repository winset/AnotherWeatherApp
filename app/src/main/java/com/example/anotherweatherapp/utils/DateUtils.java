package com.example.anotherweatherapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String format(long time){
        Date date = new Date(time*1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
         return dateFormat.format(date);
    }

}
