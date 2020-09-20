package com.example.anotherweatherapp.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/*@Entity(foreignKeys = @ForeignKey(
        entity = HourlyForecastsInfo.class,
        parentColumns = "id",
        childColumns = "info_id"
))*/
public class HourlyForecastsTemperature {


 /*   @PrimaryKey
    private int id;
*/
   /* @ColumnInfo(name = "Value")*/
    @SerializedName("Value")
    @Expose
    private Double value;
    @SerializedName("Unit")
    @Expose
    private String unit;
    @SerializedName("UnitType")
    @Expose
    private Integer unitType;

 /*   public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getInfoId() {
        return infoId;
    }

    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }
*/
   /* @ColumnInfo(name = "info_id")
    private int infoId;*/

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getUnitType() {
        return unitType;
    }

    public void setUnitType(Integer unitType) {
        this.unitType = unitType;
    }

}
