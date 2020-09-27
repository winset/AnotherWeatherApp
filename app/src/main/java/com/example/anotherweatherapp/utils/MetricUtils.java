package com.example.anotherweatherapp.utils;

public class MetricUtils {

    public static double kelvinToCelsius(double kelvin) {
        double result = Math.ceil((kelvin-273.15) * 10) / 10;
        return result;
    }

}
