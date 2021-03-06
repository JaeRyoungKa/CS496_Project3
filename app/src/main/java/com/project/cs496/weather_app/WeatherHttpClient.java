package com.project.cs496.weather_app;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherHttpClient {

    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?lat=";

    public String getWeatherDataUrl(String lat, String lon){
        return BASE_URL + lat + "&lon=" + lon + "&appid=7248c7ed960daa7f39956e40b175485b&units=metric";
    }

    public String getIconUrl(String icon_id) {
        return "http://openweathermap.org/img/w/" + icon_id + ".png";
    }

}