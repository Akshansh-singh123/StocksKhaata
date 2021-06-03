package com.akshansh.stockskhaata.screens.common.controller;

import androidx.appcompat.app.AppCompatActivity;

import com.akshansh.stockskhaata.common.CustomApplication;
import com.akshansh.stockskhaata.common.dependencyinjection.activity.ActivityComponent;
import com.akshansh.stockskhaata.common.dependencyinjection.controller.ControllerComponent;

public class BaseActivity extends AppCompatActivity {
    private ControllerComponent injector;
    private ActivityComponent activityComponent;

    public ActivityComponent getActivityComponent(){
        if(activityComponent == null){
            activityComponent = ((CustomApplication)getApplication())
                    .getApplicationComponent()
                    .newActivityComponentBuilder()
                    .activity(this)
                    .activityComponent();
        }
        return activityComponent;
    }

    protected ControllerComponent getInjector(){
        if(injector == null){
            injector = getActivityComponent().newControllerComponent();
        }
        return injector;
    }
}
