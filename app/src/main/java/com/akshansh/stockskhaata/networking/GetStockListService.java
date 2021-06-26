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
    private LiveData<List<StockSchema>> stockListAll;
    private LiveData<List<StockSchema>> stockListFiltered;
    private LiveData<List<StockSchema>> stockListSearch;
    private LiveData<List<StockSchema>> stockListFilteredSearch;

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
            stockListFilteredSearch = viewModel.getFilteredStockList(FilterQueryHelper
                    .getFilterSearchQuery(filterTerm,searchTerm));
            removeObserver(stockListAll,stockListSearch,stockListFiltered);
            observe(stockListFilteredSearch,callback);
        }else if(searchTerm != null){
            stockListSearch = viewModel.getStockListBySubName(String.format("%%%s%%",searchTerm.getName()));
            removeObserver(stockListFiltered,stockListAll,stockListFilteredSearch);
            observe(stockListSearch,callback);
        }else if(filterTerm != null){
            stockListFiltered = viewModel.getFilteredStockList(FilterQueryHelper.getFilterQuery(filterTerm));
            removeObserver(stockListAll,stockListSearch,stockListFilteredSearch);
            observe(stockListFiltered,callback);
        }else {
            stockListAll = viewModel.getAll();
            removeObserver(stockListFiltered,stockListSearch,stockListFilteredSearch);
            observe(stockListAll,callback);
        }
    }

    private void removeObserver(LiveData<List<StockSchema>> data1, LiveData<List<StockSchema>> data2
            ,LiveData<List<StockSchema>> data3) {
        if(data1!= null)
           data1.removeObservers(lifecycleOwner);
        if(data2 != null)
            data2.removeObservers(lifecycleOwner);
        if(data3 != null)
            data3.removeObservers(lifecycleOwner);
    }

    private void observe(LiveData<List<StockSchema>> data, Callback callback) {
        data.observe(lifecycleOwner,stocks->{
            Log.e(TAG, "observe: "+stocks.size());
            callback.onFetchStockSuccessful(stocks);
        });
    }
}
