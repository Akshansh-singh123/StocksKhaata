package com.akshansh.stockskhaata.screens.main;

import android.os.Bundle;

import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.akshansh.stockskhaata.R;
import com.akshansh.stockskhaata.databinding.ActivityMainBinding;
import com.akshansh.stockskhaata.screens.common.controller.BaseActivity;


public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater(),null,false);
        setContentView(binding.getRoot());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}