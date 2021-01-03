package com.example.anotherweatherapp.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "WeatherForecast")
public class WeatherForecast {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "lat")
    @SerializedName("lat")
    @Expose
    private Double lat;
    @ColumnInfo(name = "lon")
    @SerializedName("lon")
    @Expose
    private Double lon;
    @ColumnInfo(name = "timezone")
    @SerializedName("timezone")
    @Expose
    private String timezone;

    @ColumnInfo(name = "timezone_offset")
    @SerializedName("timezone_offset")
    @Expose
    private Long timezoneOffset;

    @Embedded
    @SerializedName("current")
    @Expose
    private Current current;


    @SerializedName("minutely")
    @Expose
    @Ignore
    private List<Minutely> minutely = null;

    @SerializedName("hourly")
    @Expose
    @Ignore
    private List<Hourly> hourly = null;

    @SerializedName("daily")
    @Expose
    @Ignore
    private List<Daily> daily = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Long getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(Long timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public List<Minutely> getMinutely() {
        return minutely;
    }

    public void setMinutely(List<Minutely> minutely) {
        this.minutely = minutely;
    }

    public List<Hourly> getHourly() {
        return hourly;
    }

    public void setHourly(List<Hourly> hourly) {
        this.hourly = hourly;
    }

    public List<Daily> getDaily() {
        return daily;
    }

    public void setDaily(List<Daily> daily) {
        this.daily = daily;
    }
}
