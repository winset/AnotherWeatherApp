package com.example.anotherweatherapp.data.model;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Temp {
    @ColumnInfo(name = "temp_day")
    @SerializedName("day")
    @Expose
    private Double day;
    @ColumnInfo(name = "temp_min")
    @SerializedName("min")
    @Expose
    private Double min;
    @ColumnInfo(name = "temp_max")
    @SerializedName("max")
    @Expose
    private Double max;
    @ColumnInfo(name = "temp_night")
    @SerializedName("night")
    @Expose
    private Double night;
    @ColumnInfo(name = "temp_eve")
    @SerializedName("eve")
    @Expose
    private Double eve;
    @ColumnInfo(name = "temp_morn")
    @SerializedName("morn")
    @Expose
    private Double morn;

    public Double getDay() {
        return day;
    }

    public void setDay(Double day) {
        this.day = day;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getNight() {
        return night;
    }

    public void setNight(Double night) {
        this.night = night;
    }

    public Double getEve() {
        return eve;
    }

    public void setEve(Double eve) {
        this.eve = eve;
    }

    public Double getMorn() {
        return morn;
    }

    public void setMorn(Double morn) {
        this.morn = morn;
    }

}
