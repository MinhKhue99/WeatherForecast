package com.minhkhue.weatherforecast;

public class Weather {
    public String day;
    public String status;
    public String icon;
    public String minTemp;
    public String maxTemp;

    public Weather(String day, String status, String icon, String minTemp, String maxTemp) {
        this.day = day;
        this.status = status;
        this.icon = icon;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }
}
