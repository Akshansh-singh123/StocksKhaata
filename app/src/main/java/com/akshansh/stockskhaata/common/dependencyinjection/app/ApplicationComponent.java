package com.akshansh.stockskhaata.common.dependencyinjection.app;

import com.akshansh.stockskhaata.common.dependencyinjection.activity.ActivityComponent;

import dagger.Component;

@AppScope
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    ActivityComponent.Builder newActivityComponentBuilder();
}
