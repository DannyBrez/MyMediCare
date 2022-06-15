package com.example.myapplication;

public class Readings
{
    private String date, temperature, bloodPressure, heartrate;

    public Readings(String date, String temperature, String bloodPressure, String heartrate)
    {
        this.date = date;
        this.temperature = temperature;
        this.bloodPressure = bloodPressure;
        this.heartrate = heartrate;
    }

    public String getDate()
    {
        return date;
    }

    public String getTemperature()
    {
        return temperature;
    }

    public String getBloodPressure()
    {
        return bloodPressure;
    }

    public String getHeartrate()
    {
        return heartrate;
    }
}

