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

@Module
public class ApplicationModule {
    private final StocksRepository repository;

    public ApplicationModule(Context context) {
        StocksDatabase stocksDatabase = StocksDatabase.getInstance(context);
        repository = new StocksRepository(stocksDatabase.stocksDao());
    }

    @Provides
    @AppScope
    public StocksRepository getRepository() {
        return repository;
    }

    @Provides
    @AppScope
    public ConfirmationDialogEventBus getDialogEventBus(){
        return new ConfirmationDialogEventBus();
    }
}

