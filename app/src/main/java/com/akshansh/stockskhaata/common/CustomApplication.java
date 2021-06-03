package com.akshansh.stockskhaata.common;

import android.app.Application;

import com.akshansh.stockskhaata.common.dependencyinjection.app.ApplicationComponent;
import com.akshansh.stockskhaata.common.dependencyinjection.app.ApplicationModule;
import com.akshansh.stockskhaata.common.dependencyinjection.app.DaggerApplicationComponent;

public class CustomApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
