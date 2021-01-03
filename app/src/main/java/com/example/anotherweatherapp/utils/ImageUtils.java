package com.example.anotherweatherapp.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.anotherweatherapp.R;


public class ImageUtils {

    public static Drawable getImageFromId(Context context, int id){
        if(id>=200 && id<=201 || id>=230 && id<=231){
            return context.getResources().getDrawable(R.mipmap.storm5,context.getTheme());
        }else if(id==202 || id==232){
            return  context.getResources().getDrawable(R.mipmap.storm4,context.getTheme());
        }else if(id>=210 && id<=221){
            return context.getResources().getDrawable(R.mipmap.lightning,context.getTheme());
        }else if(id>=300 && id<=301){
            return context.getResources().getDrawable(R.mipmap.storm1,context.getTheme());
        }else if(id>=302 && id<=321){
            return context.getResources().getDrawable(R.mipmap.storm,context.getTheme());
        }else if(id==500 || id==501 || id==520 || id==521){
            return context.getResources().getDrawable(R.mipmap.rain1,context.getTheme());
        }else if(id>=502 && id<=504 || id==522 || id==531){
            return context.getResources().getDrawable(R.mipmap.rain,context.getTheme());
        }else if(id==511){
            return context.getResources().getDrawable(R.mipmap.rain3,context.getTheme());
        }else if(id==600 || id==601){
            return context.getResources().getDrawable(R.mipmap.snow,context.getTheme());
        }else if(id==602|| id>=620 && id<=622){
            return context.getResources().getDrawable(R.mipmap.snowy2,context.getTheme());
        }else if(id>=611 && id<=616){
            return context.getResources().getDrawable(R.mipmap.snowy1,context.getTheme());
        }else if(id==701 || id==721){
            return context.getResources().getDrawable(R.mipmap.foggy1,context.getTheme());
        }else if(id==711){
            return context.getResources().getDrawable(R.mipmap.foggy,context.getTheme());
        }else if(id>=721 && id<=762){
            return context.getResources().getDrawable(R.mipmap.fog,context.getTheme());
        }else if(id==771){
            return context.getResources().getDrawable(R.mipmap.windy,context.getTheme());
        }else if(id==781){
            return context.getResources().getDrawable(R.mipmap.hurricane,context.getTheme());
        }else if(id==800 || id==801){
            return context.getResources().getDrawable(R.mipmap.sun1,context.getTheme());
        }else if(id==802){
            return context.getResources().getDrawable(R.mipmap.cloudy,context.getTheme());
        }else if(id==803){
            return context.getResources().getDrawable(R.mipmap.cloud,context.getTheme());
        }else if(id==804){
            return context.getResources().getDrawable(R.mipmap.cloudy2,context.getTheme());
        }else {
            return context.getResources().getDrawable(R.mipmap.thermometer,context.getTheme());
        }
    }

}
