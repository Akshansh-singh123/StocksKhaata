package com.akshansh.stockskhaata.common;

import android.app.Application;

import com.akshansh.stockskhaata.common.dependencyinjection.app.ApplicationModule;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
