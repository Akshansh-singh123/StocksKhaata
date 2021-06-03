package com.akshansh.stockskhaata.screens.stockslist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.akshansh.stockskhaata.screens.common.ViewMvcFactory;
import com.akshansh.stockskhaata.screens.common.controller.BaseFragment;

import javax.inject.Inject;


public class StockListFragment extends BaseFragment {
    @Inject public StockListViewController stockListViewController;
    @Inject public ViewMvcFactory viewMvcFactory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getInjector().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable  ViewGroup container, @Nullable Bundle savedInstanceState) {
        StockListViewMvc viewMvc = viewMvcFactory.getStockListViewMvc(null);
        stockListViewController.bindView(viewMvc);
        setHasOptionsMenu(true);
        return viewMvc.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        stockListViewController.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        stockListViewController.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stockListViewController.onDestroy();
    }
}
