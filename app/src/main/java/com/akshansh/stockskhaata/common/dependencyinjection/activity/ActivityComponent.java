package com.akshansh.stockskhaata.common.dependencyinjection.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.akshansh.stockskhaata.common.dependencyinjection.controller.ControllerComponent;

import dagger.BindsInstance;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {
    ControllerComponent newControllerComponent();

    @Subcomponent.Builder
    interface Builder{
        @BindsInstance
        Builder activity(AppCompatActivity activity);
        ActivityComponent activityComponent();
    }
}
