package com.akshansh.stockskhaata.common.dependencyinjection.app;

import android.app.Application;
import android.content.Context;

import com.akshansh.stockskhaata.common.CustomApplication;
import com.akshansh.stockskhaata.common.database.stocks.StocksDatabase;
import com.akshansh.stockskhaata.common.database.stocks.StocksRepository;
import com.akshansh.stockskhaata.screens.common.dialogs.confirmationdialog.ConfirmationDialogEventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class ApplicationModule {

    @Provides
    public CustomApplication getApplication(Application application){
        return (CustomApplication)application;
    }

    @Provides
    @Singleton
    public static StocksDatabase getStocksDatabase(CustomApplication application){
        return StocksDatabase.getInstance(application);
    }

    @Provides
    @Singleton
    public StocksRepository getRepository(StocksDatabase database) {
        return new StocksRepository(database.stocksDao());
    }

    @Provides
    @Singleton
    public ConfirmationDialogEventBus getDialogEventBus(){
        return new ConfirmationDialogEventBus();
    }
}

