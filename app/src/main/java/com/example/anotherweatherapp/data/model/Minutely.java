package com.example.anotherweatherapp.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(foreignKeys = @ForeignKey(
        entity = Example.class,
        parentColumns = "id",
        childColumns = "example_id"
))
public class Minutely {



    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "example_id")
    private int exampleId;

    @SerializedName("dt")
    @Expose
    private Long dt;
    @SerializedName("precipitation")
    @Expose
    private Long precipitation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExampleId() {
        return exampleId;
    }

    public void setExampleId(int exampleId) {
        this.exampleId = exampleId;
    }
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
