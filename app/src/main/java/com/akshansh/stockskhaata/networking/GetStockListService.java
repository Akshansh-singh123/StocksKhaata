package com.akshansh.stockskhaata.networking;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import com.akshansh.stockskhaata.common.database.stocks.StockViewModel;
import com.akshansh.stockskhaata.stocks.GetStockListEndpoint;
import com.akshansh.stockskhaata.stocks.StockFilterTerm;

import javax.inject.Inject;

public class GetStockListService implements GetStockListEndpoint {
    private final AppCompatActivity lifecycleOwner;
    private final StockViewModel viewModel;

    @Inject
    public GetStockListService(AppCompatActivity lifecycleOwner,StockViewModel viewModel) {
        this.lifecycleOwner = lifecycleOwner;
        this.viewModel = viewModel;
    }

    @Override
    public void getStocksList(@Nullable StockFilterTerm filterTerm,
                              Callback callback) throws NetworkException {
        viewModel.getAll().observe(lifecycleOwner, callback::onFetchStockSuccessful);
    }
}
