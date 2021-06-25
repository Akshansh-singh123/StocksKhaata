package com.akshansh.stockskhaata.screens.common.screensnavigator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;

import com.akshansh.stockskhaata.R;
import com.akshansh.stockskhaata.common.database.stocks.StockSchema;
import com.akshansh.stockskhaata.common.dependencyinjection.activity.ActivityScope;
import com.akshansh.stockskhaata.screens.stockslist.StockListFragmentDirections;
import com.akshansh.stockskhaata.stocks.StockFilterTerm;

import javax.inject.Inject;

@ActivityScope
public class ScreensNavigator {
    private final NavController navController;

    @Inject
    public ScreensNavigator(NavController navController) {
        this.navController =  navController;
    }

    public void toAddStockDialog(@Nullable StockSchema schema){
        try {
            StockListFragmentDirections.ShowDialog action = StockListFragmentDirections
                    .showDialog(schema);
            navController.navigate(action);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void navigateUp() {
        navController.navigateUp();
    }

    public void toConfirmationDialog(){
        try {
            navController.navigate(R.id.toConfirmationDialog);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void toFilterDialog(@Nullable StockFilterTerm filterTerm) {
        try {
            StockListFragmentDirections.ToFilterDialog action = StockListFragmentDirections
                    .toFilterDialog(filterTerm);
            navController.navigate(action);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
