package com.akshansh.stockskhaata.screens.common.screensnavigator;

import androidx.navigation.NavController;

import com.akshansh.stockskhaata.R;
import com.akshansh.stockskhaata.common.dependencyinjection.activity.ActivityScope;

import javax.inject.Inject;


@ActivityScope
public class ScreensNavigator {
    private final NavController navController;

    @Inject
    public ScreensNavigator(NavController navController) {
        this.navController =  navController;
    }

    public void toAddStockDialog(){
        try {
            navController.navigate(R.id.showDialog);
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

    public void toFilterDialog() {
        try {
            navController.navigate(R.id.toFilterDialog);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
