package com.akshansh.stockskhaata.screens.common.screensnavigator;

import androidx.navigation.NavController;

import com.akshansh.stockskhaata.R;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityScoped;

@ActivityScoped
public class ScreensNavigator {
    private final NavController navController;
    private static final String TAG = "ScreensNavigator";

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
}
