package com.example.anotherweatherapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Minutely {

    @SerializedName("dt")
    @Expose
    private Long dt;
    @SerializedName("precipitation")
    @Expose
    private Long precipitation;

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public Long getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(Long precipitation) {
        this.precipitation = precipitation;
    }
}
