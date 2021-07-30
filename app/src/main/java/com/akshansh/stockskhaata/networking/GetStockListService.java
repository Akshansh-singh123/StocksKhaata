package com.akshansh.stockskhaata.networking;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.akshansh.stockskhaata.common.Constants;
import com.akshansh.stockskhaata.common.FilterQueryHelper;
import com.akshansh.stockskhaata.common.database.stocks.StockSchema;
import com.akshansh.stockskhaata.common.database.stocks.StockViewModel;
import com.akshansh.stockskhaata.stocks.GetStockListEndpoint;
import com.akshansh.stockskhaata.stocks.StockFilterTerm;
import com.akshansh.stockskhaata.stocks.StockSearchTerm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class GetStockListService implements GetStockListEndpoint {
    private final AppCompatActivity lifecycleOwner;
    private final StockViewModel viewModel;
    private LiveData<List<StockSchema>> stockList;

    private static final String TAG = "GetStockListService";

    @Inject
    public GetStockListService(AppCompatActivity lifecycleOwner,StockViewModel viewModel) {
        this.lifecycleOwner = lifecycleOwner;
        this.viewModel = viewModel;
    }

    @Override
    public void getStocksList(@Nullable StockSearchTerm searchTerm,
                              @Nullable StockFilterTerm filterTerm,
                              Callback callback) throws NetworkException {
        if(searchTerm != null && filterTerm != null){
            stockList = viewModel.getFilteredStockList(FilterQueryHelper
                    .getFilterSearchQuery(filterTerm,searchTerm));
        }else if(searchTerm != null){
            stockList = viewModel.getFilteredStockList(FilterQueryHelper
                    .getAllSearchQuery(searchTerm));
        }else if(filterTerm != null){
            stockList = viewModel.getFilteredStockList(FilterQueryHelper
                    .getFilterQuery(filterTerm));
        }else {
            stockList = viewModel.getFilteredStockList(FilterQueryHelper.getAllQuery());
        }
        stockList.removeObservers(lifecycleOwner);
        observe(stockList,callback);
    }

    private void observe(LiveData<List<StockSchema>> data, Callback callback) {
        data.observe(lifecycleOwner,stocks->{
            Log.e(TAG, "observe: "+data.toString());
            Log.e(TAG, "observe: "+stocks.size());
            callback.onFetchStockSuccessful(stocks);
        });
    }
}
