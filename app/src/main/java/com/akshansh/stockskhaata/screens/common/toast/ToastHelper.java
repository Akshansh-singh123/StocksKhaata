package com.akshansh.stockskhaata.screens.common.toast;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

public class ToastHelper {
    private final AppCompatActivity context;

    @Inject
    public ToastHelper(AppCompatActivity context) {
        this.context = context;
    }

    public void showMessage(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
