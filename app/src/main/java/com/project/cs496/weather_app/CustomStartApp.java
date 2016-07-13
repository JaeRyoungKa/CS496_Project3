package com.project.cs496.weather_app;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by WonHada.com on 2016-04-20.
 */
public class CustomStartApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "NanumGothic.otf"))
                .addBold(Typekit.createFromAsset(this, "NanumGothicBold.otf"))
                .addCustom1(Typekit.createFromAsset(this, "NanumGothicExtraBold.otf"));//
    }
}