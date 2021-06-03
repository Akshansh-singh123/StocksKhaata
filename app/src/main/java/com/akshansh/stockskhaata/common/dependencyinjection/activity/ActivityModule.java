package com.akshansh.stockskhaata.common.dependencyinjection.activity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.akshansh.stockskhaata.R;
import com.akshansh.stockskhaata.common.database.stocks.StockViewModel;
import com.akshansh.stockskhaata.common.database.stocks.StockViewModelFactory;
import com.akshansh.stockskhaata.common.database.stocks.StocksRepository;
import com.akshansh.stockskhaata.networking.GetStockListService;
import com.akshansh.stockskhaata.screens.common.dialogs.confirmationdialog.ConfirmationDialogEventBus;
import com.akshansh.stockskhaata.screens.common.screensnavigator.ScreensNavigator;
import com.akshansh.stockskhaata.stocks.GetStockListEndpoint;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public abstract class ActivityModule {

    @Provides
    public static NavController getNavController(@NonNull AppCompatActivity activity){
        return ((NavHostFragment) activity
                .getSupportFragmentManager()
                .findFragmentById(R.id.main_nav_host)).getNavController();
    }

    @Provides
    public static AppCompatActivity getAppCompatActivity(Activity activity){
        return (AppCompatActivity)activity;
    }

    @Provides
    public static LifecycleOwner getLifeCycleOwner(Activity activity){
        return (AppCompatActivity)activity;
    }

    @Provides
    public static LayoutInflater getLayoutInflater(Activity activity){
        return LayoutInflater.from(activity);
    }

    @Binds
    public abstract GetStockListEndpoint getStockListService(GetStockListService getStockListService);

    @Provides
    public static StockViewModel getStockViewModel(AppCompatActivity activity) {
        return new ViewModelProvider(activity)
                .get(StockViewModel.class);
    }
}
